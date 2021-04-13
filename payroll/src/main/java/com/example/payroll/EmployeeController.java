package com.example.payroll;

import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;
    public EmployeeController(EmployeeRepository repository,
                              EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees/roles")
    public List<Employee> getByRole(@Param("role") String role){
        return this.repository.findByRole(role);
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>>all(){

        List<EntityModel<Employee>>employees = this.repository.findAll().stream()
                .map(employee -> this.assembler.toModel(employee))
                .collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getById(@PathVariable Long id){
        Employee e = this.repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return this.assembler.toModel(e);
    }
    @PutMapping("/employees/{id}")
    public Employee replaceEmployee(@PathVariable Long id, @RequestBody Employee newEmployee){
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return this.repository.save(employee);
                }).orElseGet(()->{
                    newEmployee.setId(id);
                    return this.repository.save(newEmployee);
                });
    }

    @PatchMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestParam String fieldName,
                                   @RequestBody String value){
        return repository.findById(id)
                .map(employee ->{
                   if(fieldName.equalsIgnoreCase("role")){
                       employee.setRole(value);
                        return repository.save(employee);
                   }else if(fieldName.equalsIgnoreCase("name")) {
                       employee.setName(value);
                        return repository.save(employee);
                   }else{
                       throw new FieldNotFoundException(fieldName);
                   }
                }).orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/employees/{id}")
    public void delEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }
}

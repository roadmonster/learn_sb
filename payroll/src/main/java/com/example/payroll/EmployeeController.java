package com.example.payroll;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees/roles")
    public List<Employee> getByRole(@Param("role") String role){
        return this.repository.findByRole(role);
    }

    @GetMapping("/employees")
    public List<Employee>all(){
        return this.repository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getById(@PathVariable Long id){
        return this.repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
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

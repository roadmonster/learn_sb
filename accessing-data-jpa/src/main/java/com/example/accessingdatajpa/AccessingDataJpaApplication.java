package com.example.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    public CommandLineRunner demo(CustomerRepository repository){
        return (args)->{
            repository.save(new Customer("John", "Doe"));
            repository.save(new Customer("Jane", "Doe"));
            repository.save(new Customer("Jack", "Mother"));

            log.info("All customers");
            log.info("--------------------");
            for(Customer customer:repository.findAll()){
                System.out.println(customer);
            }

            log.info("Find Customer by id 1l");
            log.info("--------------------");
            System.out.println(repository.findById(1l));

            log.info("Find customer by lastname");
            log.info("--------------------");
            for(Customer c: repository.findByLastName("Doe")){
                System.out.println(c);
            }
        };
    }
}

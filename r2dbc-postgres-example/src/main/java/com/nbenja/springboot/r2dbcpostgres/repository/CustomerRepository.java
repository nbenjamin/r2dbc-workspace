package com.nbenja.springboot.r2dbcpostgres.repository;

import com.nbenja.springboot.r2dbcpostgres.domain.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    @Query("SELECT * FROM customer WHERE lastname LIKE :lastname")
    Flux<Customer> findAllCustomerByLastname(String lastname);
}

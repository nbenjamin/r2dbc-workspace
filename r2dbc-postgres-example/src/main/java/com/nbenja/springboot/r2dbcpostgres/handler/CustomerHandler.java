package com.nbenja.springboot.r2dbcpostgres.handler;

import com.nbenja.springboot.r2dbcpostgres.domain.Customer;
import com.nbenja.springboot.r2dbcpostgres.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CustomerHandler {

    private CustomerRepository customerRepository;

    @Autowired
    private TransactionalOperator transactionalOperator;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest serverRequest) {
        return ok().body(customerRepository.findAll(), Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest)  {
        return serverRequest.bodyToMono(Customer.class)
                .flatMap( c -> ok().contentType(APPLICATION_JSON)
                .body(customerRepository.save(c).as(transactionalOperator::transactional), Customer.class));

    }

    public Mono<ServerResponse> getCustomerByLastName(ServerRequest serverRequest) {
        String lastname = serverRequest.pathVariable("lastname");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(customerRepository.findAllCustomerByLastname(lastname), Customer.class)).switchIfEmpty(notFound);

    }
}

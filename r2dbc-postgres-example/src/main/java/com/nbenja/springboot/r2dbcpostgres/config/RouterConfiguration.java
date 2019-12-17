package com.nbenja.springboot.r2dbcpostgres.config;

import com.nbenja.springboot.r2dbcpostgres.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@EnableWebFlux
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> customerRouters(CustomerHandler handler) {
        return route(GET("/api/customers").and(accept(MediaType.APPLICATION_JSON)), handler::getAllCustomers)
                .andRoute(GET("/api/customers/{lastname}").and(accept(MediaType.APPLICATION_JSON)), handler::getCustomerByLastName)
                .andRoute(POST("/api/customers").and(accept(MediaType.APPLICATION_JSON)), handler::saveCustomer);

    }
}

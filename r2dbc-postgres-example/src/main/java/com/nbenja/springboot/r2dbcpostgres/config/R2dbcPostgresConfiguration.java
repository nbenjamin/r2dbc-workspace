package com.nbenja.springboot.r2dbcpostgres.config;

import com.nbenja.springboot.r2dbcpostgres.domain.Customer;
import com.nbenja.springboot.r2dbcpostgres.repository.CustomerRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.core.publisher.Flux;

@Configuration
@EnableR2dbcRepositories
public class R2dbcPostgresConfiguration {



    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {

            // Removed this since test data is loading from test-data.sql
        /*  Flux.just(
            new Customer(1, "Ryan", "Ben", 8),
            new Customer(2, "Adam", "Ben", 6))
                    .flatMap(customerRepository::save).subscribe();*/

            customerRepository.findAll().log().subscribe(System.out::println);
        };
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("test-data.sql")));
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}

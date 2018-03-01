package ua.com.hotelbooking.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"ua.com.hotelbooking.model.repositories"})
@EntityScan(basePackages = {"ua.com.hotelbooking.model.entities"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}

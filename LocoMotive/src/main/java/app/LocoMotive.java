package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Created by tolgacaner on 20/04/2017.
 */

@SpringBootApplication
@EnableAutoConfiguration
public class LocoMotive {
    public static void main(String[] args) {
        SpringApplication.run(LocoMotive.class, args); //NOSONAR
    }
}


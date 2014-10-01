package demo;

import demo.service.SimpleItemQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    private static final SimpleItemQueue simpleItemQueue = new SimpleItemQueue();

    @Bean
    public SimpleItemQueue buildQueue()
    {
        return simpleItemQueue;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    

}

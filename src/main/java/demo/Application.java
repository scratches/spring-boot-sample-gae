package demo;

import demo.service.SimpleItemQueue;
import demo.servlets.SelfPurgingQueueServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application implements WebApplicationInitializer
{

    private static final SimpleItemQueue simpleItemQueue = new SimpleItemQueue();

    @Bean
    public SimpleItemQueue buildQueue()
    {
        return simpleItemQueue;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("itemQueueServlet", new SelfPurgingQueueServlet(simpleItemQueue));
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/cron/item/*");
    }

}

package dev.coc.examples;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "dev.coc.examples" })
@EnableJpaRepositories
public class MainApp {

    @PostConstruct
    public void init(){
    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));        
    }	
	
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}

}

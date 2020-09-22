package org.springframework.data.solr.rmitdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.solr.rmitdemo.config.SearchContext;
import org.springframework.data.solr.rmitdemo.config.WebContext;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import({ WebContext.class, SearchContext.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

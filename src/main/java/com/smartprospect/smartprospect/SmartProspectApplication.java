package com.smartprospect.smartprospect;

import com.smartprospect.smartprospect.company.CompanyService;
import com.smartprospect.smartprospect.scraper.CompanyScraper;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class SmartProspectApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SmartProspectApplication.class, args);
		CompanyService companyService = context.getBean(CompanyService.class);
		CompanyScraper companyScraper = new CompanyScraper(companyService);
		//companyScraper.ScrapeIndustriesPIT();
		//companyScraper.ScrapeServicesPIT();
//		UserRepository userRepository = context.getBean(UserRepository.class);
//		User user = userRepository.getById("sidikone63@gmail.com");
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

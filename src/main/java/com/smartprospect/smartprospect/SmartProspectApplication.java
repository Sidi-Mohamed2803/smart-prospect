package com.smartprospect.smartprospect;

import com.smartprospect.smartprospect.company.CompanyService;
import com.smartprospect.smartprospect.scraper.CompanyScraper;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserRepository;
import com.smartprospect.smartprospect.user.UserService;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import com.smartprospect.smartprospect.useraccount.UserAccountRepository;
import com.smartprospect.smartprospect.useraccount.UserAccountService;
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
//		UserService userService = context.getBean(UserService.class);
		CompanyScraper companyScraper = new CompanyScraper(companyService);
//		UserAccountRepository userAccountRepository = context.getBean(UserAccountRepository.class);
//		UserAccount userAccount = userAccountRepository.getById("Naruto2803");
//		companyScraper.ScrapeIndustriesPIT();
//		companyScraper.ScrapeServicesPIT();
//		UserRepository userRepository = context.getBean(UserRepository.class);
//		User user = userRepository.getById("sidikone63@gmail.com");
//		userService.addNewUser(user, userAccount);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}

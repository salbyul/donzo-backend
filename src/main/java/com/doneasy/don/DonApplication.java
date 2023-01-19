package com.doneasy.don;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class DonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonApplication.class, args);
	}

}

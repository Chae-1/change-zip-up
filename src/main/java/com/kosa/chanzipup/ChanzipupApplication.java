package com.kosa.chanzipup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class ChanzipupApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChanzipupApplication.class, args);
	}
}

package com.psb.summerwiki_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

<<<<<<< HEAD
import io.github.cdimascio.dotenv.Dotenv;

=======
>>>>>>> origin/main
@SpringBootApplication
public class SummerwikiApiApplication {

	public static void main(String[] args) {
<<<<<<< HEAD
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		
=======
>>>>>>> origin/main
		SpringApplication.run(SummerwikiApiApplication.class, args);
	}

}

package ca.sait.backup;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BackupApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackupApplication.class, args);
	}

}

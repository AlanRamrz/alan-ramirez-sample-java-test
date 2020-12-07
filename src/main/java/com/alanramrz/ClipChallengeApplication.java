package com.alanramrz;

import com.alanramrz.models.Transaction;
import com.alanramrz.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ClipChallengeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClipChallengeApplication.class, args);
	}

	@Bean
	public CommandLineRunner transactionSeeds(TransactionRepository transactionRepository) {
		return args -> {
			List<Transaction> transactions = new ArrayList<>();
			transactions.add(new Transaction("seed-1", 100.0, LocalDate.parse("2020-11-29"), "Description-1", (long) 1));
			transactions.add(new Transaction("seed-2", 200.0, LocalDate.parse("2020-12-02"), "Description-2", (long) 1));
			transactions.add(new Transaction("seed-3", 300.0, LocalDate.parse("2020-12-05"), "Description-3", (long) 1));
			transactions.add(new Transaction("seed-4", 400.0, LocalDate.parse("2020-12-06"), "Description-4", (long) 1));
			transactions.add(new Transaction("seed-5", 500.0, LocalDate.parse("2020-12-07"), "Description-5", (long) 1));
			transactions.add(new Transaction("seed-6", 600.0, LocalDate.parse("2020-12-11"), "Description-6", (long) 1));
			transactionRepository.saveAll(transactions);
		};
	}
}

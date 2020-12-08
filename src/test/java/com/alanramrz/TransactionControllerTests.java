package com.alanramrz;

import com.alanramrz.models.Transaction;
import com.alanramrz.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransactionRepository transactionRepository;

	@Test
	void getTransactionsByUserTest() throws Exception {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("seed-1", 100.0, LocalDate.parse("2020-11-29"), "Description-1", (long) 1));
		transactions.add(new Transaction("seed-2", 200.0, LocalDate.parse("2020-12-02"), "Description-2", (long) 1));
		Mockito.when(transactionRepository.findByUserIdOrderByDateAsc((long) 1)).thenReturn(transactions);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/transactions"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	void showTransactionByUser404Test() throws Exception {
		Transaction transaction = new Transaction("seed-1", 100.0, LocalDate.parse("2020-11-29"), "Description-1", (long) 1);
		Mockito.when(transactionRepository.findById("seed-1")).thenReturn(Optional.of(transaction));

		mockMvc.perform(MockMvcRequestBuilders.get("/users/2/transactions/seed-1"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void showTransactionByUser200Test() throws Exception {
		Transaction transaction = new Transaction("seed-1", 100.0, LocalDate.parse("2020-11-29"), "Description-1", (long) 1);
		Mockito.when(transactionRepository.findById("seed-1")).thenReturn(Optional.of(transaction));

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/transactions/seed-1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("seed-1"));
	}

	@Test
	void sumTransactionsByUserZeroTest() throws Exception {
		List<Transaction> transactions = new ArrayList<>();
		Mockito.when(transactionRepository.findByUserIdOrderByDateAsc((long) 1)).thenReturn(transactions);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/sum-transactions"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(0));
	}

	@Test
	void sumTransactionsByUserTest() throws Exception {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("seed-1", 100.0, LocalDate.parse("2020-11-29"), "Description-1", (long) 1));
		transactions.add(new Transaction("seed-2", 200.0, LocalDate.parse("2020-12-02"), "Description-2", (long) 1));
		Mockito.when(transactionRepository.findByUserIdOrderByDateAsc((long) 1)).thenReturn(transactions);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/sum-transactions"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(300));
	}

	@Test
	void getRandomTransaction409Test() throws Exception {
		List<Transaction> transactions = new ArrayList<>();
		Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/random"))
			.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	void getRandomTransaction200Test() throws Exception {
		String possibleIds[] = {"seed-1", "seed-2", "seed-3"};
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(possibleIds[0], 100.0, LocalDate.parse("2020-11-29"), "Description-1", (long) 1));
		transactions.add(new Transaction(possibleIds[1], 200.0, LocalDate.parse("2020-12-02"), "Description-2", (long) 2));
		transactions.add(new Transaction(possibleIds[2], 200.0, LocalDate.parse("2020-12-02"), "Description-3", (long) 3));
		Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/random"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.in(possibleIds)));
	}

	@Test
	void createTransactionMissingParams400Test() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/transactions"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void createTransactionInvalidAmount400Test() throws Exception {
		Object input = new Object() {
			public final Double amount = 0.0;
			public final String date = "2019-12-03";
			public final String description = "Description";
			public final int userId = 1;
		};

		mockMvc.perform(
			MockMvcRequestBuilders.post("/transactions")
			.content(objectMapper.writeValueAsString(input))
			.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void createTransaction201Test() throws Exception {
		Object input = new Object() {
			public final Double amount = 100.0;
			public final String date = "2019-12-03";
			public final String description = "Description";
			public final int userId = 1;
		};

		mockMvc.perform(
		MockMvcRequestBuilders.post("/transactions")
		.content(objectMapper.writeValueAsString(input))
		.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}
}

package com.ccms.service.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Transaction entity.
 * <p>
 * This class holds information about a customer's credit card transactions. It
 * stores the transaction details associated with the credit card, including the
 * transaction date, amount, type, and description.
 * </p>
 * 
 * The document is stored in the MongoDB collection named "Transactions".
 */

@NoArgsConstructor
@AllArgsConstructor
@Data

@Document(collection = "Transactions")
public class Transaction {

	@Id
	public String id;
	public String username;
	public List<CreditCardTransaction> creditcards;

	/**
	 * Inner class representing credit card transaction data.
	 * <p>
	 * Each credit card transaction includes a list of detailed transactions, which
	 * specify the type, amount, and date of each transaction.
	 * </p>
	 */

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class CreditCardTransaction {

		public int creditCardId;
		public List<TransactionDetail> transactions;

	}

	/**
	 * Inner class representing the details of a specific transaction.
	 * <p>
	 * Each transaction detail includes information about the transaction's amount,
	 * type (credit/debit), description, date, and time.
	 * </p>
	 */

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class TransactionDetail {

		public long transactionId;
		public String transactionDate;
		public String transactionTime;
		public String transactionType; // cr or db
		public double transactionAmount;
		public String transactionDesc;

	}

}

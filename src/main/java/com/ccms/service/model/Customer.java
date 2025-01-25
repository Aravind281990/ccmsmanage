package com.ccms.service.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a Customer entity.
 * <p>
 * This class holds information about a customer, including personal details,
 * contact information, address, and account status.
 * </p>
 * 
 * The document is stored in the MongoDB collection named "Customer".
 */

@NoArgsConstructor
@AllArgsConstructor
@Data

@Document("Customer")
public class Customer {

	@Id
	public String id;
	@NotNull
	public String username;
	public String password;
	public Name name;
	public String dob;
	public String sex;
	
	@Email
	public String email;
	public int customerId;
	public Address address;
	public boolean active;
	public Date createdAt;

    /**
     * Inner class representing the name of the customer.
     * <p>
     * This includes the first name and last name.
     * </p>
     */
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class Name {

		public String first;
		public String last;

	}

    /**
     * Inner class representing the address of the customer.
     * <p>
     * This includes the street, city, state, zip code, and country.
     * </p>
     */
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class Address {

		public String street;
		public String city;
		public String state;
	    @Min(value = 0, message = "Zip code cannot be negative")
	    public int zip;
	    public String country;
	}

}

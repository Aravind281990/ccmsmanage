package com.ccms.service.service;

import com.ccms.service.model.Customer;

/**
 * Service interface for managing customer operations.
 * <p>
 * This service provides methods for adding, retrieving, updating, and deleting customer records.
 * </p>
 */

public interface CustomerService {

	public Customer getCustomer(String username);

}

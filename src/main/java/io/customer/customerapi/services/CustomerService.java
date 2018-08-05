package io.customer.customerapi.services;

import org.springframework.stereotype.Service;

import io.customer.customerapi.crm.CRMService;
import io.customer.customerapi.crm.exception.CRMCustomerNotFound;
import io.customer.customerapi.crm.exception.CRMException;
import io.customer.customerapi.crm.model.CRMCustomerId;
import io.customer.customerapi.crm.model.CRMCustomerRecord;
import io.customer.customerapi.swagger.model.Customer;

/**
 * Service for creating, updating and deleting customer record in Organisation
 * CRM system.
 * 
 * @author Viral Patel
 *
 */
@Service
public class CustomerService {

	private final CRMService crmService;

	public CustomerService(CRMService crmService) {
		this.crmService = crmService;
	}

	/**
	 * Create new customer using CRMService. The Customer record is created using
	 * Http Request customer object.
	 * 
	 * @param customer
	 * @return customerId
	 */
	public String createCustomer(Customer customer) {

		CRMCustomerId customerRecord;

		try {

			customerRecord = crmService.createCustomerRecord(mapToCRMCustomer(customer));

		} catch (CRMException e) {
			throw new ServerException(e);
		}

		return customerRecord.getId();
	}

	/**
	 * Update customer record for given customerId using CRMService.
	 * 
	 * @param customerId
	 * @param customer
	 */
	public void updateCustomer(String customerId, Customer customer) throws ResourceNotFoundException {

		try {

			crmService.updateCustomerRecord(customerId, mapToCRMCustomer(customer));

		} catch (CRMException e) {
			throw new ServerException(e);
		} catch (CRMCustomerNotFound e) {
			throw new ResourceNotFoundException(e);
		}

	}

	/**
	 * Delete customer record of given customerId using CRMService.
	 * 
	 * @param customerId
	 */
	public void deleteCustomer(String customerId) {

		try {

			crmService.deleteCustomerRecord(customerId);

		} catch (CRMException e) {
			throw new ServerException(e);
		} catch (CRMCustomerNotFound e) {
			throw new ResourceNotFoundException(e);
		}

	}

	private CRMCustomerRecord mapToCRMCustomer(Customer customer) {

		// TODO: Use MapStruct or dozer for object mapping

		CRMCustomerRecord customerRecord = new CRMCustomerRecord();
		customerRecord.setCustomerFirstName(customer.getFirstName());
		customerRecord.setCustomerLastName(customer.getLastName());
		// ...
		return customerRecord;
	}

}

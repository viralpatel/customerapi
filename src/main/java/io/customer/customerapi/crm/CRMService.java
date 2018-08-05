package io.customer.customerapi.crm;

import io.customer.customerapi.crm.exception.CRMCustomerNotFound;
import io.customer.customerapi.crm.exception.CRMException;
import io.customer.customerapi.crm.model.CRMCustomerId;
import io.customer.customerapi.crm.model.CRMCustomerRecord;

/**
 * CRM Service for Customer record management. The service provides create,
 * update and delete interface methods.
 * 
 * @author Viral Patel
 *
 */
public interface CRMService {

	/**
	 * Create new customer record in Organisation CRM system.
	 * 
	 * @param customerRecord
	 * @return customerId
	 * @throws CRMException
	 */
	CRMCustomerId createCustomerRecord(CRMCustomerRecord customerRecord) throws CRMException;

	/**
	 * Update customer record in CRM system for given customerId.
	 * 
	 * @param customerId
	 * @param customerRecord
	 * @throws CRMException
	 * @throws CRMCustomerNotFound
	 */
	void updateCustomerRecord(String customerId, CRMCustomerRecord customerRecord)
			throws CRMException, CRMCustomerNotFound;

	/**
	 * Delete customer record from CRM system.
	 * 
	 * @param customerId
	 * @throws CRMException
	 * @throws CRMCustomerNotFound
	 */
	void deleteCustomerRecord(String customerId) throws CRMException, CRMCustomerNotFound;
}

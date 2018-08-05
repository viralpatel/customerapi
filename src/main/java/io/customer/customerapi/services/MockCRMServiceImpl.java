package io.customer.customerapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import io.customer.customerapi.crm.CRMService;
import io.customer.customerapi.crm.exception.CRMCustomerNotFound;
import io.customer.customerapi.crm.exception.CRMException;
import io.customer.customerapi.crm.model.CRMCustomerId;
import io.customer.customerapi.crm.model.CRMCustomerRecord;

/**
 * Mock implementation of CRMService until the REST APIs are available. The Mock
 * implementation uses http://mocky.io to mock response body for REST APIs.
 * 
 * @author Viral Patel
 *
 */
@Service
class MockCRMServiceImpl implements CRMService {

	private final RestTemplate crmRestTemplate;

	@Value("${crm.endpoints.customer.create}")
	private String crmCustomerCreateEndpoint;

	@Value("${crm.endpoints.customer.update}")
	private String crmCustomerUpdateEndpoint;

	@Value("${crm.endpoints.customer.delete}")
	private String crmCustomerDeleteEndpoint;

	public MockCRMServiceImpl(RestTemplate restTemplate) {
		this.crmRestTemplate = restTemplate;
	}

	@Override
	public CRMCustomerId createCustomerRecord(CRMCustomerRecord customerRecord) throws CRMException {
		ResponseEntity<CRMCustomerId> responseEntity = null;
		try {
			responseEntity = crmRestTemplate.exchange(crmCustomerCreateEndpoint, HttpMethod.POST,
					new HttpEntity<>(customerRecord), CRMCustomerId.class);

		} catch (HttpStatusCodeException e) {
			final HttpStatus status = e.getStatusCode();
			if (status != HttpStatus.OK) {
				throw new CRMException();
			}
		}

		return responseEntity.getBody();
	}

	@Override
	public void updateCustomerRecord(String customerId, CRMCustomerRecord customerRecord)
			throws CRMException, CRMCustomerNotFound {

		try {

			crmRestTemplate.exchange(crmCustomerUpdateEndpoint, HttpMethod.PUT, new HttpEntity<>(customerRecord),
					Void.class, customerId);

		} catch (HttpStatusCodeException e) {
			final HttpStatus status = e.getStatusCode();
			if (status == HttpStatus.NOT_FOUND) {
				throw new CRMCustomerNotFound();
			}
			if (status != HttpStatus.OK) {
				throw new CRMException();
			}
		}

	}

	@Override
	public void deleteCustomerRecord(String customerId) throws CRMException, CRMCustomerNotFound {

		try {

			crmRestTemplate.exchange(crmCustomerDeleteEndpoint, HttpMethod.DELETE, null, Void.class, customerId);

		} catch (HttpStatusCodeException e) {
			final HttpStatus status = e.getStatusCode();
			if (status == HttpStatus.NOT_FOUND) {
				throw new CRMCustomerNotFound();
			}
			if (status != HttpStatus.OK) {
				throw new CRMException();
			}
		}
	}

}

package io.customer.customerapi;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.customer.customerapi.crm.model.CRMAddressRecord;
import io.customer.customerapi.crm.model.CRMCustomerId;
import io.customer.customerapi.crm.model.CRMCustomerRecord;

@RunWith(SpringRunner.class)
public class MiscellaneousTests {

	@Test
	public void shouldEqualsForCRMCustomer() {
		// given
		CRMCustomerRecord customer1 = new CRMCustomerRecord();
		CRMCustomerRecord customer2 = new CRMCustomerRecord();

		// when
		customer1.setCustomerFirstName("John");
		customer2.setCustomerFirstName("John");

		// then
		assertThat(customer1, equalTo(customer2));
		assertThat(customer1.hashCode(), equalTo(customer2.hashCode()));
		assertThat(customer1.toString(), equalTo(customer2.toString()));
	}

	@Test
	public void shouldEqualsForCRMAddress() {
		// given
		CRMAddressRecord add1 = new CRMAddressRecord();
		CRMAddressRecord add2 = new CRMAddressRecord();

		// when
		add1.setAddressLine1("Street1");
		add2.setAddressLine1("Street1");

		// then
		assertThat(add1, equalTo(add2));
		assertThat(add1.hashCode(), equalTo(add2.hashCode()));
		assertThat(add1.toString(), equalTo(add2.toString()));
	}

	@Test
	public void shouldEqualsForCRMCustomerId() {
		// given
		CRMCustomerId cust1 = new CRMCustomerId();
		CRMCustomerId cust2 = new CRMCustomerId();

		// when
		cust1.setId("12");
		cust2.setId("12");

		// then
		assertThat(cust1, equalTo(cust2));
		assertThat(cust1.hashCode(), equalTo(cust2.hashCode()));
		assertThat(cust1.toString(), equalTo(cust2.toString()));
	}

	@Test
	public void applicationContextTest() {
		CustomerapiApplication.main(new String[] {});
	}
}

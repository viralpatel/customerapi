package io.customer.customerapi;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.customer.customerapi.swagger.model.Address;
import io.customer.customerapi.swagger.model.Address.AddressTypeEnum;
import io.customer.customerapi.swagger.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UpdateCustomerTests {

	@Autowired
	private MockMvc mockMvc;

	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private RestOperations restOperations;

	@Autowired
	private ObjectMapper mapper;

	@Value("${crm.endpoints.customer.update}")
	private String crmCustomerUpdateEndpoint;

	@Before
	public void setUp() {
		mockRestServiceServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
	}

	@Test
	public void shouldReturnOkForUpdateCustomer() throws Exception {
		// given
		Customer newCustomer = existCustomer();
		mockRestServiceServer.expect(requestTo(crmCustomerUpdateEndpoint)).andExpect(method(HttpMethod.PUT))
				.andRespond(withSuccess());

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.put("/customers/42")
						.content(mapper.writeValueAsString(newCustomer)).contentType(MediaType.APPLICATION_JSON)
						.header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isOk()).andDo(document("customer-update"));
	}

	@Test
	public void shouldThrowServerErrorForCRMErrors() throws Exception {

		// given
		Customer existingCustomer = existCustomer();

		mockRestServiceServer.expect(requestTo(crmCustomerUpdateEndpoint)).andExpect(method(HttpMethod.PUT))
				.andRespond(MockRestResponseCreators.withServerError());

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.put("/customers/2323")
						.content(mapper.writeValueAsString(existingCustomer)).contentType(MediaType.APPLICATION_JSON).header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void shouldThrowResourceNotFoundForCustomerNotFound() throws Exception {

		// given
		Customer newCustomer = existCustomer();

		mockRestServiceServer.expect(requestTo(crmCustomerUpdateEndpoint)).andExpect(method(HttpMethod.PUT))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.put("/customers/2323")
						.content(mapper.writeValueAsString(newCustomer)).contentType(MediaType.APPLICATION_JSON).header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isNotFound());
	}

	private Customer existCustomer() {
		return new Customer().firstName("John").lastName("Doe").emailAddress("johndoe@company.com")
				.dateOfBirth(LocalDate.of(1980, 12, 28)).postalAddresses(createPostalAddresses());
	}

	private List<Address> createPostalAddresses() {
		Address address = new Address();
		address.addressType(AddressTypeEnum.HOME).streetNumber("100").streetName("Bishop Street").suburb("Chatswood")
				.city("Sydney").state("NSW").postalCode("2010").country("AU");
		return Arrays.asList(address);
	}

}

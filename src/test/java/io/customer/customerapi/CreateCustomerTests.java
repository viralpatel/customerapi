package io.customer.customerapi;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.customer.customerapi.swagger.model.Address;
import io.customer.customerapi.swagger.model.Address.AddressTypeEnum;
import io.customer.customerapi.swagger.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class CreateCustomerTests {

	@Autowired
	private MockMvc mockMvc;

	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private RestOperations restOperations;

	@Autowired
	private ObjectMapper mapper;

	@Value("${crm.endpoints.customer.create}")
	private String crmCustomerCreateEndpoint;

	@Before
	public void setUp() {
		mockRestServiceServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
	}

	@Test
	public void shouldReturnCreatedForCustomerCreated() throws Exception {
		// given
		Customer newCustomer = newCustomer();

		mockRestServiceServer.expect(requestTo(crmCustomerCreateEndpoint)).andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("{\"id\": \"42\"}", MediaType.APPLICATION_JSON));

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.post("/customers")
						.content(mapper.writeValueAsString(newCustomer)).contentType(MediaType.APPLICATION_JSON)
						.header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isCreated()).andDo(document("customer-create"));
	}

	@Test
	public void shouldThrowServerErrorForCRMErrors() throws Exception {

		// given
		Customer newCustomer = newCustomer();

		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(crmCustomerCreateEndpoint))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
				.andRespond(MockRestResponseCreators.withServerError());

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.post("/customers")
						.content(mapper.writeValueAsString(newCustomer)).contentType(MediaType.APPLICATION_JSON)
						.header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isInternalServerError());
	}

	private Customer newCustomer() {
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

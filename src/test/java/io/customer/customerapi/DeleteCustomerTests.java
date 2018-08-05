package io.customer.customerapi;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class DeleteCustomerTests {

	@Autowired
	private MockMvc mockMvc;

	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private RestOperations restOperations;

	@Value("${crm.endpoints.customer.delete}")
	private String crmCustomerDeleteEndpoint;

	@Before
	public void setUp() {
		mockRestServiceServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
	}

	@Test
	public void shouldReturnOkForDeleteCustomer() throws Exception {
		// given

		mockRestServiceServer.expect(requestTo(crmCustomerDeleteEndpoint)).andExpect(method(HttpMethod.DELETE))
				.andRespond(withSuccess());

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.delete("/customers/42")
						.contentType(MediaType.APPLICATION_JSON).header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isOk()).andDo(document("customer-delete"));
	}

	@Test
	public void shouldThrowServerErrorForCRMErrors() throws Exception {

		// given

		mockRestServiceServer.expect(requestTo(crmCustomerDeleteEndpoint)).andExpect(method(HttpMethod.DELETE))
				.andRespond(MockRestResponseCreators.withServerError());

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.delete("/customers/2323")
						.contentType(MediaType.APPLICATION_JSON).header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void shouldThrowResourceNotFoundForCustomerNotFound() throws Exception {

		// given

		mockRestServiceServer.expect(requestTo(crmCustomerDeleteEndpoint)).andExpect(method(HttpMethod.DELETE))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

		// when
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.delete("/customers/2323")
						.contentType(MediaType.APPLICATION_JSON).header("correlationId", "4a34dfd12"))
				// then
				.andExpect(status().isNotFound());
	}

}

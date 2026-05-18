package epn.edu.ec.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import epn.edu.ec.model.Customer.CreateCustomerRequest;
import epn.edu.ec.model.Customer.CustomerResponse;
import epn.edu.ec.model.Customer.CustomersResponse;
import epn.edu.ec.service.CustomerService;

@WebMvcTest(controllers = CustomerController.class, 
                excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ActiveProfiles("test")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean  // -> @Mock
    private CustomerService customerService;

    private final long customerId = 1;
    private final CustomerResponse mockCustomerResponse = new CustomerResponse(
        customerId, "Mock Customer", "555-1234"
    );
    
    @Test
    public void getCustomers_shouldReturnListOfCustomers() throws Exception {
        CustomersResponse customersResponse = new CustomersResponse(List.of(mockCustomerResponse));
        when(customerService.getCustomers()).thenReturn(customersResponse);

        ResultActions result = mockMvc.perform(get("/customers")
                .accept(APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());
        result.andExpect(content().contentType(APPLICATION_JSON_VALUE));
        result.andExpect(content().json(objectMapper.writeValueAsString(customersResponse)));

        verify(customerService, times(1)).getCustomers();
    }

    @Test
    public void createCustomer_shouldCreateCustomer() throws Exception {
        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .name("New Customer")
                .phone("555-9999")
                .build();

        CustomerResponse customerResponse = new CustomerResponse(
                2L,
                "New Customer",
                "555-9999");

        when(customerService.createCustomer(createCustomerRequest)).thenReturn(customerResponse);

        ResultActions result = mockMvc.perform(post("/customers")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createCustomerRequest)));

        result.andExpect(status().isCreated());

        verify(customerService, times(1)).createCustomer(createCustomerRequest);
    }
}

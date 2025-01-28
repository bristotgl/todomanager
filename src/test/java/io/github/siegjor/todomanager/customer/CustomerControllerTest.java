package io.github.siegjor.todomanager.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;

    @BeforeEach
    public void setUp() {
        UUID mockUuid = UUID.randomUUID();
        customerRequest = new CustomerRequest("John Doe", "12345");
        customerResponse = new CustomerResponse(mockUuid, "John Doe", OffsetDateTime.now());
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        when(customerService.createUser(any(CustomerRequest.class))).thenReturn(customerResponse);

        String customerRequestJson = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(customerResponse.customerId().toString()))
                .andExpect(jsonPath("$.username").value(customerResponse.username()))
                .andExpect(jsonPath("$.createdAt").exists());

        Mockito.verify(customerService).createUser(any(CustomerRequest.class));
    }
}

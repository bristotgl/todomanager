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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private Customer customer;

    @BeforeEach
    public void setUp() {
        UUID mockUuid = UUID.randomUUID();
        String mockUsername = "Kaladin";
        String mockEmail = "kaladin@email.com";
        String mockPassword = "battle";
        OffsetDateTime mockCreatedAt = OffsetDateTime.now();

        customerRequest = new CustomerRequest(mockUsername, mockEmail, mockPassword);
        customer = new Customer();
        customer.setCustomerId(mockUuid);
        customer.setUsername(mockUsername);
        customer.setEmail(mockEmail);
        customer.setPassword(mockPassword);
        customer.setCreatedAt(mockCreatedAt);
    }

    @Test
    public void whenCreateCustomer_thenReturnsCreatedCustomer() throws Exception {
        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(customer);

        String customerRequestJson = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$.username").value(customer.getUsername()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.createdAt").exists());

        Mockito.verify(customerService).createCustomer(any(CustomerRequest.class));
    }

    @Test
    public void whenGetAllCustomers_thenReturnsListOfCustomers() throws Exception {
        List<Customer> customers = Collections.singletonList(customer);
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].username").value(customer.getUsername()))
                .andExpect(jsonPath("$[0].email").value(customer.getEmail()))
                .andExpect(jsonPath("$[0].createdAt").exists());

        Mockito.verify(customerService).getAllCustomers();
    }

    @Test
    public void whenGetCustomerById_thenReturnsCustomer() throws Exception {
        when(customerService.getCustomerById(eq(customer.getCustomerId()))).thenReturn(customer);

        mockMvc.perform(get("/customers/{customerId}", customer.getCustomerId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$.username").value(customer.getUsername()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.createdAt").exists());

        Mockito.verify(customerService).getCustomerById(eq(customer.getCustomerId()));
    }

    @Test
    public void whenUpdateCustomerById_thenReturnsUpdatedCustomer() throws Exception {
        UpdateCustomerRequest request = new UpdateCustomerRequest(customer.getUsername(), customer.getEmail());

        Customer editedCustomer = new Customer();
        editedCustomer.setCustomerId(customer.getCustomerId());
        editedCustomer.setUsername(request.username());
        editedCustomer.setUsername(request.email());
        editedCustomer.setCreatedAt(OffsetDateTime.now());

        String requestJson = objectMapper.writeValueAsString(request);

        when(customerService.updateCustomerById(eq(customer.getCustomerId()), eq(request))).thenReturn(editedCustomer);

        mockMvc.perform(put("/customers/{customerId}", customer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(editedCustomer.getCustomerId().toString()))
                .andExpect(jsonPath("$.username").value(editedCustomer.getUsername()))
                .andExpect(jsonPath("$.email").value(editedCustomer.getEmail()))
                .andExpect(jsonPath("$.createdAt").exists());

        Mockito.verify(customerService).updateCustomerById(eq(customer.getCustomerId()), eq(request));
    }

    @Test
    public void whenDeleteCustomerById_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/customers/{customerId}", customer.getCustomerId()))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(customer.getCustomerId());
    }
}

package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.exception.ResourceNotFoundException;
import io.github.siegjor.todomanager.exception.UsernameAlreadyTakenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomerSuccessfully() {
        CustomerRequest customerRequest = createCustomerRequest();

        String rawPassword = customerRequest.password();
        String encodedPassword = customerRequest.password() + "_HASHED";

        Customer customer = createCustomer();
        customer.setPassword(encodedPassword);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(passwordEncoder.encode(eq(rawPassword))).thenReturn(encodedPassword);

        Customer createdCustomer = customerService.createCustomer(customerRequest);

        assertNotNull(createdCustomer);
        assertEquals(customerRequest.username(), createdCustomer.getUsername());
        assertEquals(customerRequest.email(), createdCustomer.getEmail());
        assertEquals(encodedPassword, createdCustomer.getPassword());

        verify(customerRepository).save(any(Customer.class));
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void shouldNotCreateCustomerWhenUsernameAlreadyTaken() {
        CustomerRequest customerRequest = createCustomerRequest();

        when(customerRepository.existsByUsername(any(String.class))).thenReturn(true);
        when(customerRepository.existsByUsername(any(String.class))).thenReturn(true);

        assertThrows(UsernameAlreadyTakenException.class, () -> customerService.createCustomer(customerRequest));

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void shouldRetrieveAllCustomersSuccessfully() {
        Customer customer = createCustomer();

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers).hasSize(1);
        assertThat(customers).contains(customer);
    }

    @Test
    void shouldRetrieveCustomerByIdSuccessfully() {
        Customer customer = createCustomer();

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        Customer fetchedCustomer = customerService.getCustomerById(customer.getCustomerId());

        assertNotNull(fetchedCustomer);
        assertEquals(fetchedCustomer.getUsername(), customer.getUsername());
        assertEquals(fetchedCustomer.getEmail(), customer.getEmail());
        assertEquals(fetchedCustomer.getPassword(), customer.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFoundById() {
        Customer customer = createCustomer();

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(customer.getCustomerId()));

        verify(customerRepository).findById(customer.getCustomerId());
    }

    @Test
    void shouldUpdateCustomerByIdSuccessfully() {
        Customer customer = createCustomer();

        UpdateCustomerRequest request = new UpdateCustomerRequest("Shallan", "davar@email.com");

        when(customerRepository.findById(eq(customer.getCustomerId()))).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer editedCustomer = customerService.updateCustomerById(customer.getCustomerId(), request);

        assertNotNull(editedCustomer);
        assertEquals(customer.getCustomerId(), editedCustomer.getCustomerId());
        assertEquals(request.username(), editedCustomer.getUsername());
        assertEquals(request.email(), editedCustomer.getEmail());

        verify(customerRepository).findById(eq(customer.getCustomerId()));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldDeleteCustomerByIdSuccessfully() {
        Customer customer = createCustomer();

        customerService.deleteCustomerById(customer.getCustomerId());

        verify(customerRepository).deleteById(customer.getCustomerId());
    }


    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setUsername("Jasnah");
        customer.setEmail("kholin@email.com");
        customer.setPassword("urithiru");

        return customer;
    }

    private CustomerRequest createCustomerRequest() {
        return new CustomerRequest("Jasnah", "kholin@email.com", "urithiru");
    }
}

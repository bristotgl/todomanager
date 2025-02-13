package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.exception.UsernameAlreadyTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private CustomerService customerService;

    private CustomerRequest customerRequest;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest("Dalinar", "dalinar@email.com", "thewayofkings");
    }

    @Test
    void testCreateCostumer() {
        String rawPassword = "thewayofkings";
        String encodedPassword = "hashed_thewayofkings";

        Customer customer = new Customer();
        customer.setUsername("Dalinar");
        customer.setEmail("dalinar@email.com");
        customer.setPassword(encodedPassword);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(passwordEncoder.encode(eq(rawPassword))).thenReturn(encodedPassword);

        Customer createdCustomer = customerService.createCustomer(customerRequest, Locale.ENGLISH);

        assertNotNull(createdCustomer);
        assertEquals(createdCustomer.getUsername(), customerRequest.username());
        assertEquals(createdCustomer.getEmail(), customerRequest.email());
        assertEquals(createdCustomer.getPassword(), encodedPassword);

        verify(customerRepository).save(any(Customer.class));
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void testCreateCustomer_UsernameAlreadyTaken() {
        when(customerRepository.existsByUsername(any(String.class))).thenReturn(true);
        when(messageSource.getMessage(eq("validation.username.taken"), isNull(), any(Locale.class))).thenReturn("Username is already taken");

        assertThrows(UsernameAlreadyTakenException.class, () -> customerService.createCustomer(customerRequest, Locale.ENGLISH));

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void shouldReturnAllCustomers() {
        Customer customer = new Customer();
        customer.setUsername("Jasnah");
        customer.setEmail("kholin@email.com");
        customer.setPassword("urithiru");

        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).hasSize(1);
        assertThat(customers).contains(customer);
    }

}

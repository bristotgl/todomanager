package io.github.siegjor.todomanager.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUsername("Dalinar");
        customer.setEmail("dalinar@email.com");
        customer.setPassword("thewayofkings");
        customerRepository.save(customer);
    }

    @Test
    public void shouldFetchCustomerById() {
        Optional<Customer> fetchedCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(fetchedCustomer).isPresent();
        assertThat(fetchedCustomer.get().getUsername()).isEqualTo(customer.getUsername());
        assertThat(fetchedCustomer.get().getEmail()).isEqualTo(customer.getEmail());
        assertThat(fetchedCustomer.get().getPassword()).isEqualTo(customer.getPassword());
        assertThat(fetchedCustomer.get().getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldFetchAllCustomers() {
        List<Customer> fetchedCustomers = customerRepository.findAll();

        assertThat(fetchedCustomers).hasSize(1);
        assertThat(fetchedCustomers).contains(customer);
    }
}

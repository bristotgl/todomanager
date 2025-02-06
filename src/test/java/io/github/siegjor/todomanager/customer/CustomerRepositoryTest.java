package io.github.siegjor.todomanager.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldSaveAndFetchCustomer() {
        Customer customer = new Customer();
        customer.setUsername("Dalinar");
        customer.setEmail("dalinar@email.com");
        customer.setPassword("thewayofkings");

        Customer savedCustomer = customerRepository.save(customer);
        Optional<Customer> fetchedCustomer = customerRepository.findById(savedCustomer.getCustomerId());

        assertThat(fetchedCustomer).isPresent();
        assertThat(fetchedCustomer.get().getUsername()).isEqualTo("Dalinar");
        assertThat(fetchedCustomer.get().getEmail()).isEqualTo("dalinar@email.com");
        assertThat(fetchedCustomer.get().getPassword()).isEqualTo("thewayofkings");
        assertThat(fetchedCustomer.get().getCreatedAt()).isNotNull();
    }
}

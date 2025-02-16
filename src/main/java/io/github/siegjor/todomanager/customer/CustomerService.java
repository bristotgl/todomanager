package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.exception.EmailAlreadyRegisteredException;
import io.github.siegjor.todomanager.exception.ResourceNotFoundException;
import io.github.siegjor.todomanager.exception.UsernameAlreadyTakenException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Transactional
    public Customer createCustomer(CustomerRequest request, Locale locale) throws UsernameAlreadyTakenException, EmailAlreadyRegisteredException {
        boolean isUsernameTaken = customerRepository.existsByUsername(request.username());
        if (isUsernameTaken) {
            throw new UsernameAlreadyTakenException(messageSource.getMessage("error.username.taken", null, locale));
        }

        boolean isEmailTaken = customerRepository.existsByEmail(request.email());
        if (isEmailTaken) {
            throw new EmailAlreadyRegisteredException(messageSource.getMessage("error.email.registered", null, locale));
        }

        Customer customer = new Customer();
        customer.setUsername(request.username());
        customer.setEmail(request.email());

        String encodedPassword = passwordEncoder.encode(request.password());
        customer.setPassword(encodedPassword);
        System.out.println(encodedPassword);

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID customerId, Locale locale) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.customer.not_found", new Object[]{customerId}, locale)));
    }

    @Transactional
    public Customer updateCustomerById(UUID customerId, @Valid UpdateCustomerRequest request, Locale locale) {
        Customer fetchedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage("error.customer.not_found", new Object[]{customerId}, locale)));

        if (request.username() != null && !request.username().isEmpty() && !request.username().isBlank()) {
            fetchedCustomer.setUsername(request.username());
        }

        if (request.email() != null && !request.email().isEmpty() && !request.email().isBlank()) {
            fetchedCustomer.setEmail(request.email());
        }

        return customerRepository.save(fetchedCustomer);
    }

    @Transactional
    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}

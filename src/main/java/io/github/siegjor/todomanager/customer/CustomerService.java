package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.exception.EmailAlreadyRegisteredException;
import io.github.siegjor.todomanager.exception.ResourceNotFoundException;
import io.github.siegjor.todomanager.exception.UsernameAlreadyTakenException;
import io.github.siegjor.todomanager.utils.MessageKeys;
import io.github.siegjor.todomanager.utils.MessageUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, MessageUtil messageUtil) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageUtil = messageUtil;
    }

    @Transactional
    public Customer createCustomer(CustomerRequest request) throws UsernameAlreadyTakenException, EmailAlreadyRegisteredException {
        boolean isUsernameTaken = customerRepository.existsByUsername(request.username());
        if (isUsernameTaken) {
            throw new UsernameAlreadyTakenException(messageUtil.getMessage(MessageKeys.USERNAME_TAKEN));
        }

        boolean isEmailTaken = customerRepository.existsByEmail(request.email());
        if (isEmailTaken) {
            throw new EmailAlreadyRegisteredException(messageUtil.getMessage(MessageKeys.EMAIL_REGISTERED));
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

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage(MessageKeys.CUSTOMER_NOT_FOUND)));
    }

    @Transactional
    public Customer updateCustomerById(UUID customerId, @Valid UpdateCustomerRequest request) {
        Customer fetchedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage(MessageKeys.CUSTOMER_NOT_FOUND)));

        fetchedCustomer.setUsername(request.username());
        fetchedCustomer.setEmail(request.email());

        return customerRepository.save(fetchedCustomer);
    }

    @Transactional
    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}

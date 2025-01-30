package io.github.siegjor.todomanager.customer;

import io.github.siegjor.todomanager.MessageConstants;
import io.github.siegjor.todomanager.exception.UsernameAlreadyTakenException;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

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

    public Customer createCustomer(CustomerRequest request, Locale locale) throws UsernameAlreadyTakenException {
        boolean isUsernameAlreadyTaken = customerRepository.existsByUsername(request.username());
        if (isUsernameAlreadyTaken) {
            throw new UsernameAlreadyTakenException(messageSource.getMessage("validation.username.taken", null, locale));
        }

        Customer customer = new Customer();
        customer.setUsername(request.username());
        customer.setEmail(request.email());

        String encodedPassword = passwordEncoder.encode(request.password());
        customer.setPassword(encodedPassword);
        System.out.println(encodedPassword);


        return customerRepository.save(customer);
    }

}

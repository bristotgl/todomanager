package io.github.siegjor.todomanager.customer;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class CustomerService {
    public CustomerResponse createUser(CustomerRequest request) {
        return new CustomerResponse(UUID.randomUUID(), request.username(), OffsetDateTime.now());
    }
}

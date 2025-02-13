package io.github.siegjor.todomanager.customer;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponse> response = customers.stream().map(CustomerMapper.INSTANCE::customerToCustomerResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathParam("customerId") String customerId, Locale locale) {
        Customer customer = customerService.getCustomerById(UUID.fromString(customerId), locale);
        CustomerResponse response = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request, Locale locale) {
        Customer customer = customerService.createCustomer(request, locale);
        CustomerResponse response = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

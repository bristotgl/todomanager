package io.github.siegjor.todomanager.customer;

import jakarta.validation.Valid;
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

    @GetMapping(value = "{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customerId") UUID customerId, Locale locale) {
        Customer customer = customerService.getCustomerById(customerId, locale);
        CustomerResponse response = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request, Locale locale) {
        Customer customer = customerService.createCustomer(request, locale);
        CustomerResponse response = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomerById(@PathVariable("customerId") UUID customerId, @Valid @RequestBody UpdateCustomerRequest request, Locale locale) {
        Customer customer = customerService.updateCustomerById(customerId, request, locale);
        CustomerResponse response = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{customerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
    }

}

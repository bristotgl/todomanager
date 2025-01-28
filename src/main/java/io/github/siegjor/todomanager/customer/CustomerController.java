package io.github.siegjor.todomanager.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public boolean getCustomers() {
        return true;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request, Locale locale) {
        Customer customer = customerService.createCustomer(request, locale);
        CustomerResponse response = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

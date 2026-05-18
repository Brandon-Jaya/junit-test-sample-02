package epn.edu.ec.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epn.edu.ec.exception.CustomerNotFoundException;
import epn.edu.ec.model.Customer.CreateCustomerRequest;
import epn.edu.ec.model.Customer.CustomerResponse;
import epn.edu.ec.model.Customer.CustomersResponse;
import epn.edu.ec.model.Customer.UpdateCustomerRequest;
import epn.edu.ec.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public CustomersResponse getCustomers() {
        log.info("getting all customers");

        return customerService.getCustomers();
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable long id) {
        log.info("getting customer with id {}", id);

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        log.info("creating customer {}", createCustomerRequest);

        CustomerResponse customer = customerService.createCustomer(createCustomerRequest);

        log.info("customer created, customer id {}", customer.getId());

        return customer;
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable long id, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        log.info("updating customer with id {}: {}", id, updateCustomerRequest);

        customerService.updateCustomer(id, updateCustomerRequest);

        log.info("customer updated, customer id {}", id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        log.info("deleting customer with id {}", id);

        customerService.deleteCustomer(id);

        log.info("customer deleted, customer id {}", id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    private void customerNotFoundException() {
    }
}

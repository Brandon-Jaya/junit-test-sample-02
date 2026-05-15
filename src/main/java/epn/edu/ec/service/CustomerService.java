package epn.edu.ec.service;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import epn.edu.ec.exception.CustomerNotFoundException;
import epn.edu.ec.model.Customer.CreateCustomerRequest;
import epn.edu.ec.model.Customer.CustomerResponse;
import epn.edu.ec.model.Customer.CustomersResponse;
import epn.edu.ec.model.Customer.UpdateCustomerRequest;
import epn.edu.ec.repository.CustomerRepository;
import epn.edu.ec.repository.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomersResponse getCustomers() {
        return new CustomersResponse(customerRepository.findAll().stream()
                .map(this::customerResponse)
                .sorted(Comparator.comparing(CustomerResponse::getName))
                .collect(toList()));
    }

    public CustomerResponse getCustomerById(long customerId) {
        return customerResponse(findExistingCustomer(customerId));
    }

    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerRepository.save(Customer.builder()
                .name(createCustomerRequest.getName())
                .phone(createCustomerRequest.getPhone())
                .build());
        return customerResponse(customer);
    }

    public CustomerResponse updateCustomer(long customerId, UpdateCustomerRequest updateCustomerRequest) {
        Customer existingCustomer = findExistingCustomer(customerId);

        Customer updateCustomer = customerRepository.save(
                existingCustomer.toBuilder()
                        .name(updateCustomerRequest.getName())
                        .phone(updateCustomerRequest.getPhone())
                        .build()
        );

        return customerResponse(updateCustomer);
    }

    public void deleteCustomer(long customerId) {
        customerRepository.delete(findExistingCustomer(customerId));
    }

    private Customer findExistingCustomer(long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> {
            log.error("customer with id not found {}", customerId);
            throw new CustomerNotFoundException();
        });
    }

    private CustomerResponse customerResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getPhone());
    }
    
}

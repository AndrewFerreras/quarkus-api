package api.customer.repositories;

import java.util.List;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import api.customer.models.Customer;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;

@QuarkusTest
@Transactional
public class CustomerRepositoryTest {

    @Inject
    private CustomerRepository repository;

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John1");
        customer.setLastName("Doe");
        customer.setEmail("john1.doe@example.com");
        customer.setCountry((short) 214);
        customer.setPhone("8496323536");
        customer.setAddress("Un lugar en Republica Dominicana");
        boolean result = repository.createCustomer(customer);

        assertTrue(result);
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = repository.findAll();

        assertNotNull(customers);
        assertTrue(customers.isEmpty());
    }

    @Test
    public void testFindById() {
        Customer customer = new Customer();
        customer.setFirstName("John2");
        customer.setLastName("Doe");
        customer.setEmail("john2.doe@example.com");
        customer.setCountry((short) 214);
        customer.setPhone("8496323536");
        customer.setAddress("Un lugar en Republica Dominicana");
        repository.createCustomer(customer);

        Customer found = repository.findById(customer.getCustomerId());

        assertNotNull(found);
        assertEquals("Jane", found.getFirstName());
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Mark");
        customer.setLastName("Smith");
        repository.createCustomer(customer);

        boolean updated = repository.updateCustomer(
            customer.getCustomerId(), "new.email@example.com", "456 Elm St", "555-6789", (short)1,"american");

        assertTrue(updated);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Anna");
        customer.setLastName("Brown");
        repository.createCustomer(customer);

        boolean deleted = repository.deleteCustomer(customer.getCustomerId());

        assertTrue(deleted);
    }
}

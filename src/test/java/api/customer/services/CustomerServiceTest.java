package api.customer.services;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import api.customer.interfaces.ICustomerRepository;
import api.customer.interfaces.ICustomerservices;
import api.customer.models.Customer;

public class CustomerServiceTest {

    @Mock
    private ICustomerRepository repository;

    @InjectMocks
    private ICustomerservices service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John3");
        customer.setLastName("Doe");
        customer.setEmail("john2.doe@example.com");
        customer.setCountry((short) 214);
        customer.setPhone("8496323536");
        customer.setAddress("Un lugar en Republica Dominicana");
        repository.createCustomer(customer);


        when(repository.createCustomer(any(Customer.class))).thenReturn(true);

        boolean result = service.createCustomer(customer);

        assertTrue(result);
        verify(repository, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    public void testFindAll() {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        when(repository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = service.findAll();

        assertEquals(2, customers.size());
        verify(repository, times(1)).findAll();
    }

   @Test
    public void testFindById() {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        // Ajustar según el tipo de retorno del repositorio
        when(repository.findById(1)).thenReturn(customer);

        Customer result = service.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        verify(repository, times(1)).findById(1);
    }


   @Test
public void testUpdateCustomer() {
    // Configura el comportamiento del repositorio
    when(repository.updateCustomer(eq(1), anyString(), anyString(), anyString(), anyShort(), anyString())).thenReturn(true);

    // Llama al servicio
    boolean result = service.updateCustomer(12, "new.email@example.com", "456 Elm St", "8496323536", (short) 214);

    // Verifica los resultados
    assertTrue(result);

    // Verifica que el repositorio fue llamado con los parámetros correctos
    verify(repository, times(1)).updateCustomer(eq(1), anyString(), anyString(), anyString(), anyShort(), anyString());
}

    @Test
    public void testDeleteCustomer() {
        when(repository.deleteCustomer(1)).thenReturn(true);

        boolean result = service.deleteCustomer(1);

        assertTrue(result);
        verify(repository, times(1)).deleteCustomer(1);
    }
}

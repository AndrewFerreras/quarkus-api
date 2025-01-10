package api.customer.services;

import java.util.List;

import api.customer.interfaces.CustomerRepositoryInterface;
import api.customer.interfaces.customerservicesinterface;
import api.customer.models.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerService implements customerservicesinterface {

    @Inject
    private CustomerRepositoryInterface repository;

    @Override
    public boolean createCustomer(String firstname, String middlename, String lastname, String secondlastname, String email, String address, String phone, int country, String demonym) {
        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setMiddleName(middlename);
        customer.setLastName(lastname);
        customer.setSecondLastName(secondlastname);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setCountry((short) country);
        customer.setDemonym(demonym);
        customer.setDisable(false);

        return repository.createCustomer(customer);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Customer> findByCountry(short country) {
        return repository.findByCountry(country);
    }

    @Override
    public Customer findById(int customerId) {
        return repository.findById(customerId);
    }

    @Override
    public boolean updateCustomer(int customerId, String email, String address, String phone, short country) {
        return repository.updateCustomer(customerId, email, address, phone, country);
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return repository.deleteCustomer(customerId);
    }
}

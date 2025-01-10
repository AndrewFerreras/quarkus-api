package api.customer.interfaces;

import java.util.List;

import api.customer.models.Customer;

public interface CustomerRepositoryInterface {
  public boolean createCustomer(Customer customer) ;
   public List<Customer> findAll();
   public List<Customer> findByCountry(short country);     
   public Customer findById(int customerId);
   public boolean updateCustomer(int customerId, String email, String address, String phone, short country);
   public boolean deleteCustomer(int customerId);
}

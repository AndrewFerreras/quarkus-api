package api.customer.controllers;

import java.util.List;

import api.customer.models.Customer;
import api.customer.services.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    private CustomerService service;

    @POST
    public boolean createCustomer(Customer customer) {
        return service.createCustomer(
            customer.getFirstName(),
            customer.getMiddleName(),
            customer.getLastName(),
            customer.getSecondLastName(),
            customer.getEmail(),
            customer.getAddress(),
            customer.getPhone(),
            customer.getCountry(),
            customer.getDemonym()
        );
    }

    @GET
    public List<Customer> getAllCustomers() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        return service.findById(id);
    }

    @GET
    @Path("/country/{code}")
    public List<Customer> getCustomersByCountry(@PathParam("code") short code) {
        return service.findByCountry(code);
    }

    @PUT
    @Path("/{id}")
    public boolean updateCustomer(
        @PathParam("id") int id,
        @QueryParam("email") String email,
        @QueryParam("address") String address,
        @QueryParam("phone") String phone,
        @QueryParam("country") short country
    ) {
        return service.updateCustomer(id, email, address, phone, country);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteCustomer(@PathParam("id") int id) {
        return service.deleteCustomer(id);
    }
}

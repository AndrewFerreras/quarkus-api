package api.customer.controllers;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.customer.interfaces.ICountryService;
import api.customer.models.Customer;
import api.customer.services.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
import jakarta.ws.rs.core.Response;


/**
 * Controlador REST para gestionar operaciones relacionadas con clientes.
 * Proporciona endpoints para realizar operaciones CRUD sobre los clientes.
 */
@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    private CustomerService service;
   @Inject
    private ICountryService countryService;
    /**
     * Crea un nuevo cliente.
     *
     * @param customer Objeto `Customer` con los datos del cliente.
     * @return Respuesta HTTP con código de estado y mensaje correspondiente.
     */
   /*
    }*/
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(String requestBody) {
        try {
            // Crear un ObjectMapper de Jackson
            ObjectMapper objectMapper = new ObjectMapper();
    
            // Convertir el JSON recibido en un objeto Customer
            Customer customer = objectMapper.readValue(requestBody, Customer.class);
    
            // Lógica para guardar el cliente
            boolean success = this.service.createCustomer(customer);
    
            // Serializar el objeto Customer de nuevo a JSON
            String json = objectMapper.writeValueAsString(customer);
    
            // Responder según el resultado
            return success
                ? Response.status(Response.Status.CREATED).entity(json).type("application/json").build()
                : Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Error creating customer\"}").type("application/json").build();
        } catch (Exception e) {
            e.printStackTrace();
    
            // Retornar un error en formato JSON
            String errorJson = String.format("{\"error\": \"Error processing request: %s\"}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorJson)
                .type("application/json")
                .build();
        }
    }
    /**
     * Obtiene todos los clientes registrados.
     *
     * @return Lista de clientes.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCustomers() {
        List<Customer> customers = service.findAll();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al serializar datos.";
        }
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param id Identificador único del cliente.
     * @return Respuesta HTTP con el cliente encontrado o mensaje de error.
     */
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        if (id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("ID must be greater than 0").build();
        }
    
        Customer customer = service.findById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Customer not found").build();
        }
    
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(customer);
            return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error serializing customer").build();
        }
    }
    /**
     * Obtiene una lista de clientes filtrados por el código de país.
     *
     * @param code Código del país (ISO numérico).
     * @return Respuesta HTTP con los clientes encontrados o mensaje de error.
     */
    @GET
    @Path("/country/{code}")
    public Response getCustomersByCountry(@PathParam("code") short code) {
        if (code <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Country code must be greater than 0").build();
        }
    
        List<Customer> customers = service.findByCountry(code);
        if (customers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("No customers found for the given country code").build();
        }
    
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(customers);
            return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error serializing data").build();
        }
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param id      Identificador único del cliente.
     * @param email   Nuevo correo electrónico.
     * @param address Nueva dirección.
     * @param phone   Nuevo número de teléfono.
     * @param country Nuevo código del país.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @PUT
    @Path("/{id}")
    public Response updateCustomer(
       @PathParam("id") int id,
        @QueryParam("email") @Email String email,
        @QueryParam("address") @Size(max = 180) String address,
        @QueryParam("phone") @Size(max = 15) String phone,
        @QueryParam("country") Short country
    ) {
        if (id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("ID must be greater than 0").build();
        }

        if (email != null && !isValidEmail(email)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Invalid email format").build();
        }

        if (phone != null && (phone.length() < 10 || phone.length() > 15)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Phone must be between 10 and 15 characters").build();
        }

         // Validación adicional con CountryService
         if (!countryService.isValidCountryCode(Integer.toString(country))) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Invalid country code").build();
        }

        if (!countryService.isPhonePrefixForCountry(Integer.toString(country), phone)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Phone prefix does not match the country").build();
        }

        boolean success = service.updateCustomer(id, email, address, phone, country);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Error updating customer").build();
        }
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id Identificador único del cliente.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("ID must be greater than 0").build();
        }

        boolean success = service.deleteCustomer(id);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Error deleting customer").build();
        }
    }

    // Métodos auxiliares para validación de datos

    /**
     * Verifica si una cadena de texto es nula o está vacía.
     *
     * @param value Cadena de texto a verificar.
     * @return `true` si la cadena es nula o está vacía, de lo contrario `false`.
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Valida el formato de una dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico a validar.
     * @return `true` si el formato es válido, de lo contrario `false`.
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}

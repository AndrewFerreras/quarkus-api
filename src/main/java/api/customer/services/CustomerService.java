package api.customer.services;

import java.util.List;

import api.customer.interfaces.ICountryService;
import api.customer.interfaces.ICustomerRepository;
import api.customer.interfaces.ICustomerservices;
import api.customer.models.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Servicio para la gestión de clientes.
 * Implementa la lógica de negocio definida en la interfaz `ICustomerservices` y utiliza
 * el repositorio `ICustomerRepository` para interactuar con la base de datos.
 */
@ApplicationScoped
public class CustomerService implements ICustomerservices {

    @Inject
    private ICustomerRepository repository;
    @Inject
    private ICountryService servicecounty;

    /**
     * Crea un nuevo cliente con los datos proporcionados.
     *
     * @param firstname       Primer nombre del cliente.
     * @param middlename      Segundo nombre del cliente (opcional).
     * @param lastname        Primer apellido del cliente.
     * @param secondlastname  Segundo apellido del cliente (opcional).
     * @param email           Correo electrónico del cliente.
     * @param address         Dirección del cliente.
     * @param phone           Número de teléfono del cliente.
     * @param country         Código del país del cliente (ISO numérico).
     * @param demonym         Gentilicio del país del cliente.
     * @return `true` si el cliente fue creado con éxito, de lo contrario `false`.
     */
    @Override
    public boolean createCustomer(Customer customer) {
        try {
            // Verificar si el correo electrónico ya existe
            if (repository.emailExists(customer.getEmail())) {
                System.err.println("Error: Email already exists - " + customer.getEmail());
                return false;
            }

            // Verificar si el número de teléfono ya existe
            if (repository.phoneExists(customer.getPhone())) {
                System.err.println("Error: Phone number already exists - " + customer.getPhone());
                return false;
            }

            // Generar valores para campos reservados
            Integer customerId = repository.generateCustomerId();
            if (customerId == null || customerId <= 0) {
                System.err.println("Error: Unable to generate a valid customer ID.");
                return false;
            }
            customer.setCustomerId(customerId);

            // Obtener el demonym
            String demonym = servicecounty.getDemonym(Integer.toString(customer.getCountry()));
            if (demonym == null || demonym.trim().isEmpty()) {
                System.err.println("Error: Unable to retrieve demonym for country " + customer.getCountry());
                return false;
            }
            customer.setDemonym(demonym);

            // Configurar campos predeterminados
            customer.setDisable(false);

            // Crear el cliente en el repositorio
            boolean success = repository.createCustomer(customer);
            if (!success) {
                System.err.println("Error: Unable to create customer in the repository.");
            }

            return success;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unexpected exception while creating customer - " + e.getMessage());
            return false;
        }
    }
    /**
     * Obtiene una lista de todos los clientes registrados.
     *
     * @return Una lista de objetos `Customer` que representan a todos los clientes.
     */
    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    /**
     * Obtiene una lista de clientes filtrados por el código de país.
     *
     * @param country Código del país (ISO numérico).
     * @return Una lista de clientes pertenecientes al país especificado.
     */
    @Override
    public List<Customer> findByCountry(short country) {
        return repository.findByCountry(country);
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param customerId Identificador único del cliente.
     * @return Un objeto `Customer` que representa al cliente encontrado,
     *         o `null` si no se encuentra.
     */
    @Override
    public Customer findById(int customerId) {
        return repository.findById(customerId);
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param customerId Identificador único del cliente.
     * @param email      Nuevo correo electrónico.
     * @param address    Nueva dirección.
     * @param phone      Nuevo número de teléfono.
     * @param country    Nuevo código del país.
     * @return `true` si el cliente fue actualizado con éxito, de lo contrario `false`.
     */
    @Override
    public boolean updateCustomer(int customerId, String email, String address, String phone, short country) {
        // Verificar si el correo electrónico ya existe y pertenece a otro cliente
        if (email != null && repository.emailExistsForOtherCustomer(customerId, email)) {
            System.err.println("Error: Email already exists for another customer - " + email);
            return false;
        }

        // Verificar si el número de teléfono ya existe y pertenece a otro cliente
        if (phone != null && repository.phoneExistsForOtherCustomer(customerId, phone)) {
            System.err.println("Error: Phone number already exists for another customer - " + phone);
            return false;
        }

        // Obtener el demonym
        String demonym = servicecounty.getDemonym(Integer.toString(country));
        if (demonym == null || demonym.trim().isEmpty()) {
            System.err.println("Error: Unable to retrieve demonym for country " + country);
            return false;
        }

        return repository.updateCustomer(customerId, email, address, phone, country, demonym);
    }


    /**
     * Elimina un cliente por su identificador único.
     *
     * @param customerId Identificador único del cliente.
     * @return `true` si el cliente fue eliminado con éxito, de lo contrario `false`.
     */
    @Override
    public boolean deleteCustomer(int customerId) {
        return repository.deleteCustomer(customerId);
    }

}

package api.customer.services;

import java.util.List;

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
        return repository.updateCustomer(customerId, email, address, phone, country);
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

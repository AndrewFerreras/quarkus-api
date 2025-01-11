package api.customer.interfaces;

import java.util.List;

import api.customer.models.Customer;

/**
 * Interfaz que define los servicios relacionados con la gestión de clientes.
 */
public interface ICustomerservices {

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
    boolean createCustomer(String firstname, String middlename, String lastname, String secondlastname,
                           String email, String address, String phone, int country, String demonym);

    /**
     * Obtiene una lista de todos los clientes registrados.
     *
     * @return Una lista de objetos `Customer` que representan a todos los clientes.
     */
    List<Customer> findAll();

    /**
     * Encuentra clientes por el código de país.
     *
     * @param country Código del país (ISO numérico).
     * @return Una lista de clientes que pertenecen al país especificado.
     */
    List<Customer> findByCountry(short country);

    /**
     * Encuentra un cliente por su identificador único.
     *
     * @param customerId Identificador único del cliente.
     * @return Un objeto `Customer` que representa al cliente encontrado,
     *         o `null` si no se encuentra.
     */
    Customer findById(int customerId);

    /**
     * Actualiza la información de un cliente registrado.
     *
     * @param customerId Identificador único del cliente a actualizar.
     * @param email      Nuevo correo electrónico del cliente.
     * @param address    Nueva dirección del cliente.
     * @param phone      Nuevo número de teléfono del cliente.
     * @param country    Nuevo código del país del cliente.
     * @return `true` si el cliente fue actualizado con éxito, de lo contrario `false`.
     */
    boolean updateCustomer(int customerId, String email, String address, String phone, short country);

    /**
     * Elimina un cliente registrado.
     *
     * @param customerId Identificador único del cliente a eliminar.
     * @return `true` si el cliente fue eliminado con éxito, de lo contrario `false`.
     */
    boolean deleteCustomer(int customerId);
}

package api.customer.interfaces;

import java.util.List;

import api.customer.models.Customer;

/**
 * Interfaz para gestionar operaciones CRUD de clientes en el repositorio.
 */
public interface ICustomerRepository {

    /**
     * Crea un nuevo cliente en el repositorio.
     *
     * @param customer El cliente a crear.
     * @return `true` si el cliente fue creado con éxito, de lo contrario `false`.
     */
    boolean createCustomer(Customer customer);

    /**
     * Obtiene una lista de todos los clientes en el repositorio.
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
     * Actualiza la información de un cliente en el repositorio.
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
     * Elimina un cliente del repositorio.
     *
     * @param customerId Identificador único del cliente a eliminar.
     * @return `true` si el cliente fue eliminado con éxito, de lo contrario `false`.
     */
    boolean deleteCustomer(int customerId);
}

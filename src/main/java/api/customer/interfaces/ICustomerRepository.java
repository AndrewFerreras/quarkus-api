package api.customer.interfaces;

import java.util.List;

import api.customer.models.Customer;

/**
 * Interfaz para gestionar operaciones CRUD de clientes en el repositorio.
 * Define los métodos que deben implementarse para interactuar con los datos de clientes.
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
     * @param demonym    Nuevo gentilicio del cliente.
     * @return `true` si el cliente fue actualizado con éxito, de lo contrario `false`.
     */
    boolean updateCustomer(int customerId, String email, String address, String phone, short country, String demonym);

    /**
     * Elimina un cliente del repositorio.
     *
     * @param customerId Identificador único del cliente a eliminar.
     * @return `true` si el cliente fue eliminado con éxito, de lo contrario `false`.
     */
    boolean deleteCustomer(int customerId);

    /**
     * Genera un identificador único para un nuevo cliente.
     *
     * @return Un número entero que representa el nuevo ID del cliente.
     */
    Integer generateCustomerId();

    /**
     * Verifica si un correo electrónico ya existe en el repositorio.
     *
     * @param email Correo electrónico a verificar.
     * @return `true` si el correo electrónico ya existe, de lo contrario `false`.
     */
    boolean emailExists(String email);

    /**
     * Verifica si un número de teléfono ya existe en el repositorio.
     *
     * @param phone Número de teléfono a verificar.
     * @return `true` si el número de teléfono ya existe, de lo contrario `false`.
     */
    boolean phoneExists(String phone);

    /**
     * Verifica si un correo electrónico ya existe para otro cliente distinto al proporcionado.
     *
     * @param customerId Identificador único del cliente actual.
     * @param email      Correo electrónico a verificar.
     * @return `true` si el correo ya existe para otro cliente, de lo contrario `false`.
     */
    boolean emailExistsForOtherCustomer(int customerId, String email);

    /**
     * Verifica si un número de teléfono ya existe para otro cliente distinto al proporcionado.
     *
     * @param customerId Identificador único del cliente actual.
     * @param phone      Número de teléfono a verificar.
     * @return `true` si el teléfono ya existe para otro cliente, de lo contrario `false`.
     */
    boolean phoneExistsForOtherCustomer(int customerId, String phone);
}

package api.customer.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import api.customer.interfaces.ICustomerRepository;
import api.customer.models.Customer;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Implementación del repositorio de clientes.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos.
 */
@ApplicationScoped
public class CustomerRepository implements ICustomerRepository {

    @Inject
    AgroalDataSource dataSource;

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param customer El cliente a crear.
     * @return `true` si el cliente fue creado con éxito, de lo contrario `false`.
     */
    @Override
    public boolean createCustomer(Customer customer) {
        String query = """
            INSERT INTO customer (customerid, firstname, middlename, lastname, secondlastname, email, address, phone, country, demonym, disable)
            VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getMiddleName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getSecondLastName());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getAddress());
            statement.setString(7, customer.getPhone());
            statement.setShort(8, customer.getCountry());
            statement.setString(9, customer.getDemonym());
            statement.setBoolean(10, customer.getDisable());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los clientes almacenados en la base de datos.
     *
     * @return Una lista de objetos `Customer` que representan a todos los clientes.
     */
    @Override
    public List<Customer> findAll() {
        String query = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = mapResultSetToCustomer(resultSet);
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Obtiene una lista de clientes filtrados por código de país.
     *
     * @param country Código del país (ISO numérico).
     * @return Una lista de clientes pertenecientes al país especificado.
     */
    @Override
    public List<Customer> findByCountry(short country) {
        String query = "SELECT * FROM customer WHERE country = ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setShort(1, country);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = mapResultSetToCustomer(resultSet);
                    customers.add(customer);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param customerId Identificador único del cliente.
     * @return Un objeto `Customer` si se encuentra el cliente, de lo contrario `null`.
     */
    @Override
    public Customer findById(int customerId) {
        String query = "SELECT * FROM customer WHERE customerid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCustomer(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param customerId Identificador único del cliente.
     * @param email      Nuevo correo electrónico.
     * @param address    Nueva dirección.
     * @param phone      Nuevo número de teléfono.
     * @param country    Nuevo código de país.
     * @return `true` si el cliente fue actualizado con éxito, de lo contrario `false`.
     */
    @Override
    public boolean updateCustomer(int customerId, String email, String address, String phone, short country) {
        String query = """
            UPDATE customer
            SET email = ?, address = ?, phone = ?, country = ?
            WHERE customerid = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setShort(4, country);
            statement.setInt(5, customerId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos por su identificador único.
     *
     * @param customerId Identificador único del cliente.
     * @return `true` si el cliente fue eliminado con éxito, de lo contrario `false`.
     */
    @Override
    public boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM customer WHERE customerid = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un resultado del `ResultSet` a un objeto `Customer`.
     *
     * @param resultSet El conjunto de resultados de una consulta.
     * @return Un objeto `Customer` con los datos extraídos del `ResultSet`.
     * @throws Exception Si ocurre algún error al procesar el conjunto de resultados.
     */
    private Customer mapResultSetToCustomer(ResultSet resultSet) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt("customerid"));
        customer.setFirstName(resultSet.getString("firstname"));
        customer.setMiddleName(resultSet.getString("middlename"));
        customer.setLastName(resultSet.getString("lastname"));
        customer.setSecondLastName(resultSet.getString("secondlastname"));
        customer.setEmail(resultSet.getString("email"));
        customer.setAddress(resultSet.getString("address"));
        customer.setPhone(resultSet.getString("phone"));
        customer.setCountry(resultSet.getShort("country"));
        customer.setDemonym(resultSet.getString("demonym"));
        customer.setDisable(resultSet.getBoolean("disable"));
        return customer;
    }
}

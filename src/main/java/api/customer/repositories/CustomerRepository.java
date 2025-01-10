package api.customer.repositories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import api.customer.interfaces.CustomerRepositoryInterface;
import api.customer.models.Customer;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerRepository implements CustomerRepositoryInterface {

    @Inject
    AgroalDataSource dataSource;

    // 1. Crear un nuevo cliente
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

    // 2. Obtener todos los clientes existentes
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

    // 3. Obtener todos los clientes por país
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

    // 4. Obtener un cliente por ID
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

    // 5. Actualizar un cliente existente
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

    // 6. Eliminar un cliente por ID
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

    // Método auxiliar para mapear el ResultSet a un objeto Customer
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
package api.customer.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

/**
 * Pruebas de integración para el controlador CustomerController.
 * Se utiliza RestAssured para probar los endpoints REST del controlador.
 */
@QuarkusTest
public class CustomerControllerTest {

    /**
     * Prueba el endpoint POST /customers para crear un nuevo cliente.
     * Verifica que el cliente se cree con éxito devolviendo un código de estado 201
     * y que los datos enviados se reflejen en la respuesta.
     */
    @Test
    public void testCreateCustomer() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "firstName": "Test",
                  "middleName": "User",
                  "lastName": "Example",
                  "secondLastName": "Case",
                  "email": "test.user@example.com",
                  "address": "Test Address 123",
                  "phone": "32134567890",
                  "country": 250
                }""")
            .when().post("/customers")
            .then()
                .statusCode(201)
                .body(containsString("Test"))
                .body(containsString("User"))
                .body(containsString("test.user@example.com"));
    }

    /**
     * Prueba el endpoint GET /customers para obtener todos los clientes.
     * Verifica que el código de estado sea 200 y que la respuesta contenga
     * al menos un cliente.
     */
    @Test
    public void testGetAllCustomers() {
        given()
            .when().get("/customers")
            .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0))
                .body("[0].firstName", is("John"))
                .body("[0].lastName", is("Doe"));
    }

    /**
     * Prueba el endpoint GET /customers/{id} para obtener un cliente por su ID.
     * Verifica que el código de estado sea 200 y que los datos del cliente coincidan.
     */
    @Test
    public void testGetCustomerById() {
        given()
            .when().get("/customers/1")
            .then()
                .statusCode(200)
                .body("firstName", is("John"))
                .body("lastName", is("Doe"))
                .body("email", is("john.doe@example.com"));
    }

    /**
     * Prueba el endpoint GET /customers/country/{code} para obtener clientes
     * filtrados por país. Verifica que la respuesta incluya al menos un cliente
     * con el gentilicio esperado.
     */
    @Test
    public void testGetCustomersByCountry() {
        given()
            .when().get("/customers/country/840")
            .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0))
                .body("[0].firstName", is("John"))
                .body("[0].demonym", is("American"));
    }

    /**
     * Prueba el endpoint PUT /customers/{id} para actualizar un cliente existente.
     * Verifica que el cliente se actualice con éxito y los cambios se reflejen
     * al recuperar sus datos.
     */
    @Test
    public void testUpdateCustomer() {
        // Actualiza los datos del cliente con ID 1.
        given()
            .contentType("application/json")
            .queryParam("email", "updated.email@example.com")
            .queryParam("address", "Updated Address")
            .queryParam("phone", "9896543210")
            .queryParam("country", 840)
            .when().put("/customers/1")
            .then()
                .statusCode(200);

        // Verifica los cambios.
        given()
            .when().get("/customers/1")
            .then()
                .statusCode(200)
                .body("email", is("updated.email@example.com"))
                .body("address", is("Updated Address"))
                .body("phone", is("9896543210"))
                .body("country", is(840));
    }

    /**
     * Prueba el endpoint DELETE /customers/{id} para eliminar un cliente existente.
     * Verifica que el cliente se elimine con éxito y no esté disponible al intentar
     * recuperarlo.
     */
    @Test
    public void testDeleteCustomer() {
        // Elimina el cliente con ID 1.
        given()
            .when().delete("/customers/1")
            .then()
                .statusCode(204);

        // Verifica que el cliente ya no exista.
        given()
            .when().get("/customers/1")
            .then()
                .statusCode(404);
    }
}

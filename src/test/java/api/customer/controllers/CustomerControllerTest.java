package api.customer.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class CustomerControllerTest {

    @Test
    public void testGetAllCustomersEndpoint() {
        given()
          .when().get("/customers")
          .then()
             .statusCode(200)
             .body("$.size()", is(0)); // Asume que no hay datos al inicio
    }

    @Test
    public void testCreateCustomerEndpoint() {
        given()
          .contentType("application/json")
          .body("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"phone\":\"555-1234\",\"country\":1,\"demonym\":\"American\"}")
          .when().post("/customers")
          .then()
             .statusCode(200)
             .body(is("true"));
    }

    @Test
    public void testGetCustomerByIdEndpoint() {
        // Supón que el cliente con ID 1 existe
        given()
          .when().get("/customers/1")
          .then()
             .statusCode(200)
             .body("customerId", is(1))
             .body("firstName", is("John"))
             .body("lastName", is("Doe"));
    }

    @Test
    public void testGetCustomersByCountryEndpoint() {
        // Supón que hay clientes en el país con código 1
        given()
          .when().get("/customers/country/1")
          .then()
             .statusCode(200)
             .body("$.size()", greaterThan(0));
    }

    @Test
    public void testUpdateCustomerEndpoint() {
        given()
          .contentType("application/json")
          .queryParam("email", "new.email@example.com")
          .queryParam("address", "456 Elm St")
          .queryParam("phone", "555-6789")
          .queryParam("country", 2)
          .queryParam("demonym", "Canadian")
          .when().put("/customers/1")
          .then()
             .statusCode(200)
             .body(is("true"));
    }

    @Test
    public void testDeleteCustomerEndpoint() {
        // Supón que el cliente con ID 1 existe
        given()
          .when().delete("/customers/1")
          .then()
             .statusCode(200)
             .body(is("true"));
    }
}

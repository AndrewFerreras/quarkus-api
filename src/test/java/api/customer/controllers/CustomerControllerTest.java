package api.customer.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class CustomerControllerTest {

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

    @Test
    public void testGetCustomerById() {
        // Crea un cliente primero
        given()
        .when().get("/customers/1")
        .then()
           .statusCode(200)
           .body("firstName", is("John"))
           .body("lastName", is("Doe"))
           .body("email", is("john.doe@example.com"));
    }

    @Test
    public void testGetCustomersByCountry() {
        // Crea un cliente con país específico
        given()
        .when().get("/customers/country/840")
        .then()
           .statusCode(200)
           .body("$.size()", greaterThan(0))
           .body("[0].firstName", is("John"))
           .body("[0].demonym", is("American"));
    }

    @Test
    public void testUpdateCustomer() {
      given()
      .contentType("application/json")
      .queryParam("email", "updated.email@example.com")
      .queryParam("address", "Updated Address")
      .queryParam("phone", "9896543210")
      .queryParam("country", 840)
      .when().put("/customers/1")
      .then()
         .statusCode(200);

    // Verifica que el cliente se haya actualizado
    given()
      .when().get("/customers/1")
      .then()
         .statusCode(200)
         .body("email", is("updated.email@example.com"))
         .body("address", is("Updated Address"))
         .body("phone", is("9896543210"))
         .body("country", is(840));
    }

    @Test
    public void testDeleteCustomer() {
         // Elimina el cliente con ID 1
    given()
    .when().delete("/customers/1")
    .then()
       .statusCode(204);

  // Verifica que el cliente ya no exista
  given()
    .when().get("/customers/1")
    .then()
       .statusCode(404);
    }
}

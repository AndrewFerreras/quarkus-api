package api.customer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Clase que representa un cliente.
 * Contiene las propiedades necesarias para almacenar y gestionar la información de un cliente.
 */
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignorar campos no definidos en el modelo

public class Customer {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Solo visible en las respuestas
    @NotNull(message = "Customer ID cannot be null")
    private Integer customerId; // Identificador único del cliente

    @JsonProperty()
    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "First name must not exceed 15 characters")
    private String firstName; // Primer nombre del cliente

    @JsonProperty()
    @Size(max = 15, message = "Middle name must not exceed 15 characters")
    private String middleName; // Segundo nombre del cliente (opcional)

    @JsonProperty()
    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Last name must not exceed 15 characters")
    private String lastName; // Primer apellido del cliente

    @JsonProperty()
    @Size(max = 15, message = "Second last name must not exceed 15 characters")
    private String secondLastName; // Segundo apellido del cliente (opcional)

    @JsonProperty()
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 35, message = "Email must not exceed 35 characters")
    private String email; // Correo electrónico del cliente

    @JsonProperty()
    @NotBlank(message = "Address is required")
    @Size(max = 180, message = "Address must not exceed 180 characters")
    private String address; // Dirección del cliente

    @JsonProperty()
    @NotBlank(message = "Phone is required")
    @Size(max = 15, message = "Phone must not exceed 25 characters")
    private String phone; // Número de teléfono del cliente

    @JsonProperty("country")
    @NotNull(message = "Country is required")
    private Short country; // Código del país del cliente (ISO numérico)

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Solo visible en las respuestas
    @Size(max = 15, message = "Demonym must not exceed 25 characters")
    private String demonym; // Gentilicio del cliente basado en su país

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Solo visible en las respuestas
    private Boolean disable; // Indicador de si el cliente está deshabilitado (true o false)

    // Getters y Setters

// Getters y Setters

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return Identificador del cliente.
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param customerId Identificador del cliente.
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Obtiene el primer nombre del cliente.
     *
     * @return Primer nombre del cliente.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el primer nombre del cliente.
     *
     * @param firstName Primer nombre del cliente.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el segundo nombre del cliente (opcional).
     *
     * @return Segundo nombre del cliente.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Establece el segundo nombre del cliente (opcional).
     *
     * @param middleName Segundo nombre del cliente.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Obtiene el primer apellido del cliente.
     *
     * @return Primer apellido del cliente.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el primer apellido del cliente.
     *
     * @param lastName Primer apellido del cliente.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene el segundo apellido del cliente (opcional).
     *
     * @return Segundo apellido del cliente.
     */
    public String getSecondLastName() {
        return secondLastName;
    }

    /**
     * Establece el segundo apellido del cliente (opcional).
     *
     * @param secondLastName Segundo apellido del cliente.
     */
    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return Correo electrónico del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param email Correo electrónico del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return Dirección del cliente.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param address Dirección del cliente.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return Número de teléfono del cliente.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param phone Número de teléfono del cliente.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Obtiene el código de país del cliente.
     *
     * @return Código de país del cliente (ISO numérico).
     */
    public Short getCountry() {
        return country;
    }

    /**
     * Establece el código de país del cliente.
     *
     * @param country Código de país del cliente (ISO numérico).
     */
    public void setCountry(Short country) {
        this.country = country;
    }

    /**
     * Obtiene el gentilicio del cliente basado en su país.
     *
     * @return Gentilicio del cliente.
     */
    public String getDemonym() {
        return demonym;
    }

    /**
     * Establece el gentilicio del cliente basado en su país.
     *
     * @param demonym Gentilicio del cliente.
     */
    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    /**
     * Obtiene el estado de deshabilitación del cliente.
     *
     * @return `true` si el cliente está deshabilitado, `false` si no lo está.
     */
    public Boolean getDisable() {
        return disable;
    }

    /**
     * Establece el estado de deshabilitación del cliente.
     *
     * @param disable `true` para deshabilitar al cliente, `false` para habilitarlo.
     */
    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

}

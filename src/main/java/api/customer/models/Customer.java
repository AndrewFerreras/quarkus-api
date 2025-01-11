package api.customer.models;

/**
 * Clase que representa un cliente.
 * Contiene las propiedades necesarias para almacenar y gestionar la información de un cliente.
 */
public class Customer {

    private Integer customerId; // Identificador único del cliente
    private String firstName; // Primer nombre del cliente
    private String middleName; // Segundo nombre del cliente (opcional)
    private String lastName; // Primer apellido del cliente
    private String secondLastName; // Segundo apellido del cliente (opcional)
    private String email; // Correo electrónico del cliente
    private String address; // Dirección del cliente
    private String phone; // Número de teléfono del cliente
    private Short country; // Código del país del cliente (ISO numérico)
    private String demonym; // Gentilicio del cliente basado en su país
    private Boolean disable; // Indicador de si el cliente está deshabilitado (true o false)

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

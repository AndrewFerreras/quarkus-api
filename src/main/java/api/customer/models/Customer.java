package api.customer.models;

public class Customer {

  private Integer customerId;
  private String firstName;
  private String middleName;
  private String lastName;
  private String secondLastName;
  private String email;
  private String address;
  private String phone;
  private Short country;
  private String demonym;
  private Boolean disable;

  // Getters y Setters

  public Integer getCustomerId() {
      return customerId;
  }

  public void setCustomerId(Integer customerId) {
      this.customerId = customerId;
  }

  public String getFirstName() {
      return firstName;
  }

  public void setFirstName(String firstName) {
      this.firstName = firstName;
  }

  public String getMiddleName() {
      return middleName;
  }

  public void setMiddleName(String middleName) {
      this.middleName = middleName;
  }

  public String getLastName() {
      return lastName;
  }

  public void setLastName(String lastName) {
      this.lastName = lastName;
  }

  public String getSecondLastName() {
      return secondLastName;
  }

  public void setSecondLastName(String secondLastName) {
      this.secondLastName = secondLastName;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getAddress() {
      return address;
  }

  public void setAddress(String address) {
      this.address = address;
  }

  public String getPhone() {
      return phone;
  }

  public void setPhone(String phone) {
      this.phone = phone;
  }

  public Short getCountry() {
      return country;
  }

  public void setCountry(Short country) {
      this.country = country;
  }

  public String getDemonym() {
      return demonym;
  }

  public void setDemonym(String demonym) {
      this.demonym = demonym;
  }

  public Boolean getDisable() {
      return disable;
  }

  public void setDisable(Boolean disable) {
      this.disable = disable;
  }
}
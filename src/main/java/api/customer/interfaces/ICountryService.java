package api.customer.interfaces;

public interface  ICountryService {
  
    /**
     * Valida si un código de país es válido.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @return `true` si el código de país es válido, de lo contrario `false`.
     */
    boolean isValidCountryCode(String countryCode);

    /**
     * Valida si un prefijo telefónico pertenece al código de país proporcionado.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @param phonePrefix Prefijo telefónico (ejemplo: "+57").
     * @return `true` si el prefijo pertenece al país, de lo contrario `false`.
     */
    boolean isPhonePrefixForCountry(String countryCode, String phonePrefix);

    /**
     * Obtiene el gentilicio de un país dado su código de país.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @return Gentilicio en masculino del país si está disponible, de lo contrario `null`.
     */
    String getDemonym(String countryCode);
}

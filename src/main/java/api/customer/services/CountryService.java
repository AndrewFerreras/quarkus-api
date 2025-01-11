package api.customer.services;

import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CountryService {

    private static final String BASE_URL = "https://restcountries.com/v3.1";
    private final Client client;

    public CountryService() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * Valida si un código de país es válido.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @return `true` si el código de país es válido, de lo contrario `false`.
     */
    public boolean isValidCountryCode(String countryCode) {
        if (countryCode == null || countryCode.trim().isEmpty()) {
            return false;
        }

        try {
            WebTarget target = client.target(BASE_URL).path("/alpha").path(countryCode);
            Response response = target.request().get();

            return response.getStatus() == Response.Status.OK.getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Valida si un prefijo telefónico pertenece al código de país proporcionado.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @param phonePrefix Prefijo telefónico (ejemplo: "+57").
     * @return `true` si el prefijo pertenece al país, de lo contrario `false`.
     */
    public boolean isPhonePrefixForCountry(String countryCode, String phonePrefix) {
        if (countryCode == null || phonePrefix == null ||
            countryCode.trim().isEmpty() || phonePrefix.trim().isEmpty()) {
            return false;
        }

        try {
            WebTarget target = client.target(BASE_URL).path("/alpha").path(countryCode);
            Response response = target.request().get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                Map<String, Object> countryData = response.readEntity(Map.class);

                if (countryData.containsKey("idd")) {
                    Map<String, Object> idd = (Map<String, Object>) countryData.get("idd");

                    if (idd.containsKey("root") && idd.containsKey("suffixes")) {
                        String root = (String) idd.get("root");
                        List<String> suffixes = (List<String>) idd.get("suffixes");

                        // Comparar el prefijo completo (root + suffix)
                        for (String suffix : suffixes) {
                            if (phonePrefix.equals(root + suffix)) {
                                return true; // Prefijo válido para el país
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Prefijo no válido o ocurrió un error
    }

    public String getDemonym(String countryCode) {
        if (countryCode == null || countryCode.trim().isEmpty()) {
            return null;
        }

        try {
            WebTarget target = client.target(BASE_URL).path("/alpha").path(countryCode);
            Response response = target.request().get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                Map<String, Object> countryData = response.readEntity(Map.class);

                if (countryData.containsKey("demonyms")) {
                    Map<String, Object> demonyms = (Map<String, Object>) countryData.get("demonyms");

                    if (demonyms.containsKey("eng")) {
                        Map<String, String> eng = (Map<String, String>) demonyms.get("eng");

                        if (eng.containsKey("m")) {
                            return eng.get("m"); // Gentilicio en masculino
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // No se encontró el gentilicio o ocurrió un error
    }
}

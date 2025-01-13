package api.customer.services;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import api.customer.interfaces.ICountryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/**
 * Servicio para gestionar información de países usando la API de RestCountries.
 * Proporciona métodos para validar códigos de país, verificar prefijos telefónicos
 * y obtener el gentilicio de un país.
 */
@ApplicationScoped
public class CountryService implements ICountryService {

    private static final String BASE_URL = "https://restcountries.com/v3.1";
    private final Client client;

    /**
     * Constructor que inicializa el cliente HTTP.
     */
    public CountryService() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * Valida si un código de país es válido utilizando la API de RestCountries.
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

            // El código de país es válido si la respuesta es 200 (OK).
            return response.getStatus() == Response.Status.OK.getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Retorna falso si ocurre un error.
    }

    /**
     * Valida si un prefijo telefónico pertenece al código de país proporcionado.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @param phonePrefix Prefijo telefónico (ejemplo: "809").
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
                // Extraer datos del país desde la respuesta.
                List<Map<String, Object>> countriesData = response.readEntity(List.class);

                if (countriesData != null && !countriesData.isEmpty()) {
                    Map<String, Object> countryData = countriesData.get(0);

                    if (countryData.containsKey("idd")) {
                        Map<String, Object> idd = (Map<String, Object>) countryData.get("idd");

                        if (idd.containsKey("root") && idd.containsKey("suffixes")) {
                            String root = (String) idd.get("root");
                            List<String> suffixes = (List<String>) idd.get("suffixes");

                            for (String suffix : suffixes) {
                                String fullPrefix = root + suffix;
                                // Validar si el prefijo coincide con el formato esperado.
                                if (fullPrefix.equals(root + phonePrefix.substring(0, suffix.length()))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Retorna falso si ocurre un error o el prefijo no coincide.
    }

    /**
     * Obtiene el gentilicio de un país (en inglés) basado en su código.
     *
     * @param countryCode Código del país (ISO 3166-1 alfa-3).
     * @return El gentilicio en inglés (masculino) si está disponible, de lo contrario `null`.
     */
    @Override
    public String getDemonym(String countryCode) {
        String url = BASE_URL + "/alpha/" + countryCode;

        try {
            // Realizar la solicitud HTTP a la API de RestCountries.
            Response response = ClientBuilder.newClient()
                .target(url)
                .request()
                .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                // Leer la respuesta como cadena JSON.
                String jsonResponse = response.readEntity(String.class);

                // Usar un lector JSON para parsear la respuesta.
                try (JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse))) {
                    JsonArray jsonArray = jsonReader.readArray();

                    // Extraer el primer objeto del arreglo.
                    if (!jsonArray.isEmpty()) {
                        JsonObject countryObject = jsonArray.getJsonObject(0);

                        // Obtener el objeto "demonyms".
                        JsonObject demonyms = countryObject.getJsonObject("demonyms");
                        if (demonyms != null) {
                            JsonObject eng = demonyms.getJsonObject("eng");
                            if (eng != null) {
                                return eng.getString("m", null); // Retorna el gentilicio masculino.
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Retorna null si no se encuentra el gentilicio o ocurre un error.
    }
}

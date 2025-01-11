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

@ApplicationScoped
public class CountryService implements  ICountryService{

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

    @Override
    public String getDemonym(String countryCode) {
        String url = "https://restcountries.com/v3.1/alpha/" + countryCode;

        try {
            // Realizar la solicitud HTTP
            Response response = ClientBuilder.newClient()
                .target(url)
                .request()
                .get();

            if (response.getStatus() == 200) {
                // Leer la respuesta como cadena JSON
                String jsonResponse = response.readEntity(String.class);

                // Usar un lector JSON para parsear la respuesta
                try (JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse))) {
                    JsonArray jsonArray = jsonReader.readArray();

                    // Obtener el primer objeto del arreglo
                    if (!jsonArray.isEmpty()) {
                        JsonObject countryObject = jsonArray.getJsonObject(0);

                        // Extraer el gentilicio
                        JsonObject demonyms = countryObject.getJsonObject("demonyms");
                        if (demonyms != null) {
                            JsonObject eng = demonyms.getJsonObject("eng");
                            if (eng != null) {
                                return eng.getString("m", null); // Obtener el gentilicio masculino
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Retornar null si no se encuentra el gentilicio
    }
}

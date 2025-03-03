package isika.p3.amappli.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import isika.p3.amappli.entities.user.Address;

public class NominatimAPI {
    
    public static String getGPSFromAddress(Address address) {
        try {
            // Turn address object into query string
            String query = URLEncoder.encode(
                String.join(" ", address.getLine2(), address.getPostCode(), address.getCity()), 
                StandardCharsets.UTF_8
            );

            // Construct the API request url
            String url = "https://nominatim.openstreetmap.org/search?q=" + query + "&format=geojson&limit=1";

            // Do the HTTP Request
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("User-Agent", "Java-HttpClient") // Nominatim requires a User-Agent
                .build();

            System.out.println("nominatim request");
            System.out.println(request.toString());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode features = root.path("features");

            if (features.isArray() && features.size() > 0) {
                JsonNode geometry = features.get(0).path("geometry");
                JsonNode coordinates = geometry.path("coordinates");

                // Return the coordinates as a comma-separated string
                return coordinates.get(0).asText() + "," + coordinates.get(1).asText();
            } else {
                System.out.println("No results found.");
                return null; 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

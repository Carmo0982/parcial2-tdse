package co.edu.eci;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class controllerProxy {

    private static final String USER_AGENT = "Mozilla/5.0";

    private final String primaryServer = System.getenv("PRIMARY_URL");
    private final String secondaryServer = System.getenv("SECONDARY_URL");

    private String requestToServer(String serverUrl, String endpoint, String value) throws IOException {
        String UrlComplete = serverUrl + endpoint + "?value=" + value;
        URL obj = new URL(UrlComplete);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            return response.toString();

        } else {
            System.out.println("GET request not worked");
            return null;
        }
    }

    private String activePassive(String endpoint, String value) {
        System.out.println("Intentando con servidor primario...");
        try {
            String result = requestToServer(primaryServer, endpoint, value);
            if (result != null) {
                return "Respuesta servidor primario: " + result;
            }
        } catch (IOException e) {
            System.out.println("Servidor primario no disponible: " + e.getMessage());
        }

        System.out.println("Intentando con servidor secundario...");
        try {
            String result = requestToServer(secondaryServer, endpoint, value);
            if (result != null) {
                return "Respuesta servidor secundario: " + result;
            }
        } catch (IOException e) {
            System.out.println("Servidor secundario no disponible: " + e.getMessage());
        }

        System.out.println("GET DONE");
        return "ambos servidores se encuentran caidos";
    }
    
}

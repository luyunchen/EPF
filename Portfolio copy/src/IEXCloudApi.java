/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Marce
 */


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class IEXCloudApi {
    private static final String API_TOKEN = "pk_c8838e25bb8b434b88be71e22986401a";
    private static final String BASE_URL = "https://cloud.iexapis.com/stable/stock/";

    public String getCurrentStockInfo(String symbol) {
    String quoteEndpoint = BASE_URL + symbol + "/quote?token=" + API_TOKEN;
    String statsEndpoint = BASE_URL + symbol + "/advanced-stats?token=" + API_TOKEN;

    try {
        HttpClient httpClient = HttpClient.newHttpClient();

        // Make API call to /quote endpoint
        HttpRequest quoteRequest = HttpRequest.newBuilder()
                .uri(URI.create(quoteEndpoint))
                .build();
        HttpResponse<String> quoteResponse = httpClient.send(quoteRequest, HttpResponse.BodyHandlers.ofString());

        // Make API call to /stats endpoint
        HttpRequest statsRequest = HttpRequest.newBuilder()
                .uri(URI.create(statsEndpoint))
                .build();
        HttpResponse<String> statsResponse = httpClient.send(statsRequest, HttpResponse.BodyHandlers.ofString());

        // Combine the responses into a single JSON object
        String combinedJson = combineJsonResponses(quoteResponse.body(), statsResponse.body());

        return combinedJson;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

private String combineJsonResponses(String quoteJson, String statsJson) {
    try {
        // Convert the JSON strings to JSON objects
        JSONObject quoteObject = new JSONObject(quoteJson);
        JSONObject statsObject = new JSONObject(statsJson);

        // Combine the JSON objects
        for (String key : statsObject.keySet()) {
            quoteObject.put(key, statsObject.get(key));
        }

        // Return the combined JSON object as a string
        return quoteObject.toString();
    } catch (JSONException e) {
        e.printStackTrace();
        return null;
    }
}
}

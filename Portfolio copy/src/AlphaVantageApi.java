/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Eliyahu Cano
 */


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class AlphaVantageApi {
    private static final String API_KEY = "NUNW7MUCXTACICR6"; 
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    
     public AlphaVantageApi(String apiKey) {
        
    }

   public AlphaVantageApi(){
        
    }

    public String getCurrentStockInfo(String symbol) {
        //overview endpoint data pull
        String function = "OVERVIEW";
        String overViewEndpoint = BASE_URL + "?function=" + function + "&symbol=" + symbol + "&apikey=" + API_KEY;
        
        //global quote endoint data pull
        String globalQuoteFunction = "GLOBAL_QUOTE";
        String globalQuoteEndpoint = BASE_URL + "?function=" + globalQuoteFunction + "&symbol=" + symbol + "&apikey=" + API_KEY;
        
        //obtain Json responses of the data
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest overViewRequest = HttpRequest.newBuilder()
                    .uri(URI.create(overViewEndpoint))
                    .build();
            HttpResponse<String> quoteResponse = httpClient.send(overViewRequest, HttpResponse.BodyHandlers.ofString());

            
            HttpRequest globalRequest = HttpRequest.newBuilder()
                    .uri(URI.create(globalQuoteEndpoint))
                    .build();
            HttpResponse<String> statsResponse = httpClient.send(globalRequest, HttpResponse.BodyHandlers.ofString());

            // Combine the responses into a single JSON object
            String combinedJson = combineJsonResponses(quoteResponse.body(), statsResponse.body());

            return combinedJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     private String combineJsonResponses(String overViewJson, String globalJson) {
        try {
            // Convert the JSON strings to JSON objects
            JSONObject quoteObject = new JSONObject(overViewJson);
            JSONObject statsObject = new JSONObject(globalJson);

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


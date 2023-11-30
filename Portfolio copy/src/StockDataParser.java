/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Marce
 */
import org.json.JSONException;
import org.json.JSONObject;

public class StockDataParser {
    public static StockQuote parseStockQuote(String json) {
    try {

        JSONObject jsonObject = new JSONObject(json);

        StockQuote stockQuote = new StockQuote();

        // Parse information from stats
        stockQuote.setSymbol(getStringFromJsonObject(jsonObject, "symbol"));
        stockQuote.setLatestPrice(getDoubleFromJsonObject(jsonObject, "latestPrice"));
        stockQuote.setFullName(getStringFromJsonObject(jsonObject, "companyName"));

      
        // Try extracting beta again
        if (jsonObject.has("beta") && !jsonObject.isNull("beta")) {
            //stockQuote.setBeta(getDoubleFromJsonObject(jsonObject,"beta"));
            stockQuote.setBeta(jsonObject.getDouble("beta"));
        } else {
            System.out.println("Beta field not found in JSON response or is null.");
        }

      
        

        return stockQuote;
    } catch (JSONException e) {
        e.printStackTrace();
        return null;
    }
}


    private static String getStringFromJsonObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.has(key) && !jsonObject.isNull(key) ? jsonObject.getString(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Double getDoubleFromJsonObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.has(key) && !jsonObject.isNull(key) ? jsonObject.getDouble(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

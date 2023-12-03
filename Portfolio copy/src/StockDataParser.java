/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Eliyahu Cano
 */
import org.json.JSONException;
import org.json.JSONObject;

public class StockDataParser {

    public static StockQuote parseStockQuote(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            StockQuote stockQuote = new StockQuote();

            // Parse information from Overview
            stockQuote.setSymbol(getStringFromJsonObject(jsonObject, "Symbol"));
            stockQuote.setFullName(getStringFromJsonObject(jsonObject, "Name"));
            stockQuote.setBeta(getDoubleFromJsonObject(jsonObject, "Beta"));

            // Parse information from Global Quote
            JSONObject globalQuote = jsonObject.optJSONObject("Global Quote");
            if (globalQuote != null) {
                stockQuote.setLatestPrice(getDoubleFromJsonObject(globalQuote, "05. price"));
            } else {
                // Handle the case where Global Quote is not present or doesn't contain the required field
                stockQuote.setLatestPrice(null);
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

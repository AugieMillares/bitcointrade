package com.bitcointrade.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bitcointrade.model.MarketData;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/23/13
 * Time: 1:51 AM
 * <p/>
 * Modification:
 */


public class MarketDataService {

    public String getMarketData() {
        String jsonRate = "";
        URL serverAddress = null;
        try {
            serverAddress = new URL("http://pubapi.cryptsy.com/api.php?method=marketdatav2");
            HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();

            //read the result from the server
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line01;
            while ((line01 = rd.readLine()) != null) {
                sb.append(line01);
            }

            jsonRate = sb.toString();
            /*//IF RESULT IS EMPTY.... HANDLE WITH EXCEPTION
            String result = sb.toString();
            //System.out.println(result);

            Object obj = JSONValue.parse(result);
            JSONObject market = (JSONObject) ((JSONObject) ((JSONObject) obj).get("return")).get("markets");
            jsonRate = JSONValue.toJSONString(market);

            System.out.println();*/
            /*
            List<MarketData> marketDataList = new ArrayList<>();
            for (Map.Entry entry : market.entrySet()) {
                MarketData marketData = new MarketData();
                JSONObject entryValue = (JSONObject) entry.getValue();
                marketData.setLabel(entryValue.get("label").toString());
                marketData.setPrimaryCode(entryValue.get("primarycode").toString());
                marketData.setPrimaryName(entryValue.get("primaryname").toString());
                marketData.setSecondaryCode(entryValue.get("secondarycode").toString());
                marketData.setSecondaryName(entryValue.get("secondaryname").toString());
                marketData.setLastTradePrice(entryValue.get("lasttradeprice").toString());
                marketData.setLastTradeTime(entryValue.get("lasttradetime").toString());
                marketDataList.add(marketData);
            }

            //Just printing the timestampt to readable date format
            //Map<String, Object> jsonResult = JsonValueParameterUtil.getJsonObjectMap(result);
            //jsonRate = JSONValue.toJSONString(jsonResult);
            jsonRate = JSONValue.toJSONString(marketDataList);
*/
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return jsonRate;
    }
}

package com.bitcointrade.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.bitcointrade.utils.JsonValueParameterUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/23/13
 * Time: 1:51 AM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class ExchangeRatesService {
    public static String getExchangeRates() {
        String jsonRate="";
        URL serverAddress = null;
        try {
            serverAddress = new URL("http://openexchangerates.org/api/latest.json?app_id=913aaa968ca44fe28b68aec1d34a471e");
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
            //IF RESULT IS EMPTY.... HANDLE WITH EXCEPTION
            String result = sb.toString();
            System.out.println(result);

            //Just printing the timestampt to readable date format
            Map<String, Object> jsonResult = JsonValueParameterUtil.getJsonObjectMap(result);
            int timeStampInt = (Integer) jsonResult.get("timestamp");
            Timestamp timestamp = new Timestamp(timeStampInt * 1000L);
            Date date = new Date(timestamp.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:MM:ss:S");
            System.out.println(simpleDateFormat.format(date));

            //US Dollor base
            String base = (String) jsonResult.get("base");

            //ratesJson
            Map<String, Object> ratesJson = (Map<String, Object>) jsonResult.get("rates");

            jsonRate = JsonValueParameterUtil.mapToJson(ratesJson);


        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return jsonRate;
    }
}

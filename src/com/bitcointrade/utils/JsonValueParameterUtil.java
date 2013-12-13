package com.bitcointrade.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created with IntelliJ IDEA.
 * User: Augie Millares
 * Date: 11/17/13
 * Time: 12:33 AM
 * <p/>
 * Modification:
 * ----------------------------
 */


/**
 * This is to get the value from the jsonValue parameters
 * json value is a json data with name-value pairs
 */
public class JsonValueParameterUtil {


    public static String getValue(String jsonValue, String valueToGet) {
        try {
            Map<String, String> finalMap = jsonValueToMap(jsonValue);
            return finalMap.get(valueToGet.toUpperCase());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static Object getObjectValue(String jsonValue, String valueToGet) {
        Object obj = null;
        Map<String, Object> stringObjectMap = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stringObjectMap = mapper.readValue(jsonValue, Map.class);
            obj = stringObjectMap.get(valueToGet);
        } catch (IOException e) {
            System.out.println("[JsonValueParameterUtil] Error in converting jsonValue to Domain Object... Please Check");
            e.printStackTrace();
            throw new RuntimeException("[JsonValueParameterUtil] Check jsonValueToMap Method");
        }
        return obj;
    }

    public static Map<String, String> getMapConversion(String jsonValue) {
        return jsonValueToMap(jsonValue);
    }

    public static Object jsonToPojo(String jsonValue, Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonValue, obj.getClass());
        } catch (IOException e) {
            System.out.println("[JsonValueParameterUtil] Error in converting to Object");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getting the object json values from the json data
     *
     * @param jsonValue
     * @return
     */
    public static String getJsonValues(String jsonValue, String jsonValueToGet) {
        Map<String, Object> stringObjectMap = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            stringObjectMap = mapper.readValue(jsonValue, Map.class);
            return mapper.writeValueAsString(stringObjectMap.get(jsonValueToGet));
        } catch (IOException e) {
            System.out.println("[JsonValueParameterUtil] Error in converting jsonValue .. Please Check getJsonValues() Method");
            e.printStackTrace();
            throw new RuntimeException("[JsonValueParameterUtil] Check getJsonValues() Method");
        }
    }

    private static Map<String, String> jsonValueToMap(String jsonValue) {
        Map<String, Object> stringObjectMap = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stringObjectMap = mapper.readValue(jsonValue, Map.class);
        } catch (IOException e) {
            System.out.println("[JsonValueParameterUtil] Error in converting jsonValue to Domain Object... Please Check");
            e.printStackTrace();
            throw new RuntimeException("[JsonValueParameterUtil] Check jsonValueToMap Method");
        }
        //process on changing the keys to upper case
        Map<String, String> finalMap = new HashMap();
        Iterator<Entry<String, Object>> mapEntry = stringObjectMap.entrySet().iterator();
        while (mapEntry.hasNext()) {
            Entry entry = mapEntry.next();
            String key = (String) entry.getKey();
            String value = String.valueOf(entry.getValue());
            finalMap.put(key.toUpperCase(), value);
        }
        return finalMap;
    }

    /**
     * return a map of Objects
     *
     * @param jsonValue
     * @return
     */
    public static Map<String, Object> jsonValueToMapObject(String jsonValue) {
        Map<String, Object> stringObjectMap = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stringObjectMap = mapper.readValue(jsonValue, Map.class);
        } catch (IOException e) {
            System.out.println("[JsonValueParameterUtil] Error in converting jsonValue to Domain Object... Please Check");
            e.printStackTrace();
            throw new RuntimeException("[JsonValueParameterUtil] Check jsonValueToMapObject Method");
        }
        return stringObjectMap;
    }

    /**
     * return a list of the json ...NEEDS TO BE REFACTORED....
     *
     * @param jsonValue
     * @return
     */
    public static List<String> getJsonListing(String jsonValue) {
        List<String> finalList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<String> listing = mapper.readValue(jsonValue, List.class);
            for (String tmp : listing) {
                finalList.add(tmp.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return finalList;
    }

    /**
     * return a map of the complex json
     * NOTE:
     * user should know how to parse the data to it's correct type
     *
     * @param jsonValue
     * @return
     */
    public static Map<String, Object> getJsonObjectMap(String jsonValue) {
        Map<String, Object> map = new LinkedHashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(jsonValue, Map.class);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return map;
    }

    public static String mapToJson(Map<String, Object> map) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return json;
    }

    /**
     * List from Json List
     *
     * @param jsonList
     * @return
     */
    public static List<Map<String, String>> jsonListToListMap(String jsonList) {
        List<Map<String, String>> listMap = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            listMap = mapper.readValue(jsonList, List.class);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return listMap;
    }
}

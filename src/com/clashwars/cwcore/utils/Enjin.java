package com.clashwars.cwcore.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Enjin {

    private static String server_key = "30ef33e2efc7aa9fc18ad4f4494e6d2fe6b5b965c3b3936256";
    public static JSONObject users;
    private static Long lastRequest = null;

    public static JSONObject getUsers() {
        if (lastRequest != null) {
            if (users != null) {
                if (System.currentTimeMillis() - lastRequest <= 10000) {
                    return users;
                }
            }
        }
        lastRequest = System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();
        builder.append("authkey=" + server_key);
        builder.append("&characters=true");

        String result = sendRequest("get-users", builder);
        Object json = toJSON(result);

        if (json instanceof JSONObject) {
            users = (JSONObject)json;
            return users;
        }
        return null;
    }

    public static JSONObject getUser(String userID, boolean useCached) {
        JSONObject users = Enjin.users;
        if (!useCached || users == null) {
            users = getUsers();
        }
        if (users.containsKey(userID)) {
            return (JSONObject)users.get(userID);
        }
        return null;
    }

    public static String getUserIdByCharacter(String charName, boolean useCached) {
        JSONObject users = Enjin.users;
        if (!useCached || users == null) {
            users = getUsers();
        }
        for (Object userIDObj : users.keySet()) {
            String userID = (String)userIDObj;
            JSONObject userData = (JSONObject)users.get(userID);
            JSONObject charObj = (JSONObject)userData.get("characters");
            if (charObj != null && charObj.containsKey("4923")) {
                JSONArray characters = (JSONArray)charObj.get("4923");
                for (Object character : characters) {
                    if (((JSONObject)character).get("name").equals(charName)) {
                        return userID;
                    }
                }
            }
        }
        return null;
    }

    private static String sendRequest(String APIRequest, StringBuilder params) {
        StringBuilder strBuilder = new StringBuilder();;
        try {
            URL enjinUrl = new URL("http://clashwars.com/api/" + APIRequest);
            if (enjinUrl == null) {
                return "{'Error':'Invalid URL'}";
            }

            HttpURLConnection con = (HttpURLConnection)enjinUrl.openConnection();
            con.setRequestMethod("POST");
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "Mozilla/4.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(params));

            //Send the request
            con.getOutputStream().write(params.toString().getBytes());

            //Retreive the data and convert it to a string.
            InputStream in = con.getInputStream();
            byte[] buffer = new byte['?'];
            int bytesRead = in.read(buffer);
            while (bytesRead > 0) {
                strBuilder.append(new String(buffer, 0, bytesRead, "UTF-8"));
                bytesRead = in.read(buffer);
            }
        } catch (Exception e) {
            return "{'Error':'" + e.getMessage() + "'}";
        }
        return strBuilder.toString();
    }

    private static Object toJSON(String result) {
        JSONParser parser = new JSONParser();
        try {
            return parser.parse(result);
        } catch (ParseException e) {
            return null;
        }
    }
}

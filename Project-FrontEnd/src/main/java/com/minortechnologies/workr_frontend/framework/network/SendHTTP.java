/*

Disclaimer, In order to have this class created for use, this class
primarily consists of code taken from these 2 resources online:

https://www.baeldung.com/java-http-request
https://www.baeldung.com/httpurlconnection-post

My role was not to create this part of the program, however
circumstances meant that I now had to work on this in limited time.
Due to this the code in this file is primarily obtained from the 2 links above,
and I have not had the time to understand what exceptions may or may not be
thrown by the code in this file.
 */


package com.minortechnologies.workr_frontend.framework.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SendHTTP {

    /**
     * Processes and adds URL Parameters to a provided URL
     *
     * @param targetURL the base URL
     * @param params a map containing url path parameters
     * @return the formatted url as a String
     */
    private static String processUrlParams(String targetURL, Map<String, String> params){
        String newUrl = targetURL;
        if (params != null){
            if (params.size() != 0) {
                newUrl += "?";
                for (String key :
                        params.keySet()) {
                    newUrl += key + "=";
                    newUrl += params.get(key) + "&";
                }
            }
        }
        return newUrl;
    }

    /**
     * Executes an HTTP GET request
     *
     * @param targetURL The target URL to send the request to
     * @param urlParams Request Parameters contained in a Map.
     * @return The response from the server.
     * @throws IOException Not Entirely Sure the Cause. Need to analyze this code further. See start of this class file disclaimer.
     * @throws FileNotFoundException if the targetURL does not exist
     */
    public static String executeGet(String targetURL, Map<String, String> urlParams) throws IOException, FileNotFoundException{
        String finalUrl = processUrlParams(targetURL, urlParams);
        URL url = new URL(finalUrl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }

    /**
     * Executes an HTTP POST Request
     *
     * @param targetURL The target URL to send the request to
     * @param urlParams Request Parameters contained in a Map.
     * @param data The HTTP Request Body as a String.
     *             If a Map needs to be sent, create a JSONObject, pass the Map into the constructor
     *             If an Array or List needs to be sent, create a JSONArray and pass the array or list into the constructor.
     * @return The response from the target server as a String
     * @throws IOException Not Entirely Sure the Cause. Need to analyze this code further. See start of this class file disclaimer.
     * @throws FileNotFoundException if the targetURL does not exist
     */
    public static String executePost(String targetURL, Map<String, String> urlParams, String data) throws IOException {

        String finalUrl = processUrlParams(targetURL, urlParams);
        URL url = new URL(finalUrl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }
}

package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpSender implements Sender {

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public Response send(Source source) throws IOException, NotEnoughParamsException {
        //String urlWithParameterValues = URLEncoder.encode(source.getUrlWithParameterValues(), "UTF-8");
        String urlWithParameterValues = source.getUrlWithParameterValues();
        System.out.println(urlWithParameterValues);
        URL obj = new URL(urlWithParameterValues);
/*
        String responseStr = Resources.toString(obj, Charsets.UTF_8);*/


        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setConnectTimeout(10000);

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String responseStr = response.toString();

        System.out.println(responseStr);
        return new TextResponse(responseStr);
    }

}

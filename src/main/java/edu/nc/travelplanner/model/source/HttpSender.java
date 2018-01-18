package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class HttpSender implements Sender {

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public Response send(Source source) throws IOException {

        URL obj = new URL(source.getUrl());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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

        return new TextResponse(response.toString());
    }

}
package edu.nc.travelplanner.model.source;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class HttpSender implements Sender {

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public Response send(Source source) throws IOException {
        //String urlWithParameterValues = URLEncoder.encode(source.getUrlWithParameterValues(), "UTF-8");
        String urlWithParameterValues = source.getUrlWithParameterValues();
        System.out.println(urlWithParameterValues);
        URL obj = new URL(urlWithParameterValues);
        String responseStr = Resources.toString(obj, Charsets.UTF_8);
/*

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
        String responseStr = response.toString();*/

        System.out.println(responseStr);
        return new TextResponse(responseStr);
    }

}

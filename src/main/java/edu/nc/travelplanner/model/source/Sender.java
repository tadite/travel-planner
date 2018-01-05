package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.model.response.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface Sender {
    Response send(Source source) throws IOException;
}

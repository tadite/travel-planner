package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.response.Response;

import java.io.IOException;

public interface Sender {
    Response send(Source source) throws IOException, NotEnoughParamsException;
}

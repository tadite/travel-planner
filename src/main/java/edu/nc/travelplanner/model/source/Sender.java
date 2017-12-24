package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.model.response.Response;

public interface Sender {
    Response send(Source source);
}

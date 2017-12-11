package edu.nc.travelplanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.ActionState;
import org.junit.Test;

public class JacksonTests {

    @Test
    public void canSerializeActionArgs() {

        ActionArgs args = new ActionArgsBuilder().setState(ActionState.DECISION).build();
        args.addArg("test-prop1", "test-val1");
        args.addArg("test-prop2", "test-val2");
        args.addArg("test-prop3", "test-val3");

        ObjectMapper mapper = new ObjectMapper();


        try {

            String jsonInString = mapper.writeValueAsString(args);

        } catch (Exception e) {

        }

    }
}
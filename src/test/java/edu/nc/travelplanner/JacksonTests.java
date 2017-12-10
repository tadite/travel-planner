package edu.nc.travelplanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionState;
import org.junit.Test;

public class JacksonTests {

    @Test
    public void canSerializeActionArgs() {
        ActionState state = ActionState.DECISION;
        ActionArgs args = new ActionArgs(state);
        args.addArg("test-prop1", "test-val1");
        args.addArg("test-prop2", "test-val2");
        args.addArg("test-prop3", "test-val3");

        ObjectMapper mapper = new ObjectMapper();


        try {

            String jsonInString = mapper.writeValueAsString(args);

            state = ActionState.DECISION;
        } catch (Exception e) {

        }

    }
}
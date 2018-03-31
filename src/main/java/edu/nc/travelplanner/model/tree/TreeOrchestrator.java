package edu.nc.travelplanner.model.tree;

import com.itextpdf.text.DocumentException;
import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.response.Response;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.text.ParseException;

public interface TreeOrchestrator {
    Response execute(ActionArgs args) throws CustomParseException;
    Response rollback();
    void reset();
    TravelDto save() throws ParseException, NotSupportedException;
    byte[] saveToPdf() throws IOException, DocumentException;
}

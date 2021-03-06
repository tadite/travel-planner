package edu.nc.travelplanner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.ActionState;
import edu.nc.travelplanner.model.tree.TreeOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
public class ActionTreeController {

    private TreeOrchestrator orchestrator;

    @Autowired
    public ActionTreeController(TreeOrchestrator orchestrator){

        this.orchestrator=orchestrator;
    }

    @PostMapping(path = "/action/reset")
    public ResponseEntity reset() {
        this.orchestrator.reset();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/action")
    public ResponseEntity<String> executeDelete() {

        try {
            return new ResponseEntity<>(orchestrator.rollback().getRawData(), HttpStatus.OK) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping(path = "/action/reset")
    public ResponseEntity<String> resetTree() {

        try {
            orchestrator.reset();
            return new ResponseEntity<>(HttpStatus.OK) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping(path = "/action/save")
    public ResponseEntity<TravelDto> saveTree() {
        try {
            return new ResponseEntity<>(orchestrator.save(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping(path = "/action")
    public ResponseEntity<String> executePost(@RequestBody Map<String, String> argsMap) {

        ActionArgs actionArgs = new ActionArgsBuilder()
                .addAllArgs(argsMap)
                .setState(ActionState.DECISION)
                .build();

        try {
            return new ResponseEntity<>(orchestrator.execute(actionArgs).getRawData(), HttpStatus.OK) ;
        } catch (CustomParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(path = "/action")
    public ResponseEntity<String> executeGet(@RequestParam Map<String, String> argsMap) {

        ActionArgs actionArgs = new ActionArgsBuilder()
                .addAllArgs(argsMap)
                .setState(ActionState.PRESENTATION)
                .build();

        try {
            return new ResponseEntity<>(orchestrator.execute(actionArgs).getRawData(), HttpStatus.OK);
        } catch (CustomParseException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(path = "/action/saveToPdf")
    public ResponseEntity<byte[]> saveToPdf(){
        byte[] pdf = null;

        try {
            pdf = orchestrator.saveToPdf();
        } catch (IOException | DocumentException e){
            e.printStackTrace();
        }

        String pdfName = "My travel.pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData(pdfName, pdfName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdf, headers, HttpStatus.OK);
        return response;
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String getPostgresTestString(String sql) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        StringBuilder stringBuilder = new StringBuilder();
        maps.forEach(map -> map.forEach((s, o) -> stringBuilder.append(s+" : "+ o.toString()+"; ")));
        return stringBuilder.toString();
    }

    @GetMapping(path="/test")
    public String get(){
        return getPostgresTestString("SELECT * FROM APP_USER");
    }

}

package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.form.PersonForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class MainController {

    private String error;

    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloPage(Model model){

        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);

        return "base";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String helloPage(Model model,
                            @ModelAttribute("personForm")PersonForm personForm){

        String name = personForm.getName();
        if (name.length() > 0 && name != null){
            model.addAttribute("name", name);
            return "hello";
        }

        error = "name field empty";
        model.addAttribute("error", error);
        return "base";
    }*/
}

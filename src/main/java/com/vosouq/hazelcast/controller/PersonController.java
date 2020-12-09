package com.vosouq.hazelcast.controller;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.hazelcast.model.CachePersonLocalStatus;
import com.vosouq.hazelcast.model.Message;
import com.vosouq.hazelcast.model.Person;
import com.vosouq.hazelcast.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@VosouqRestController
public class PersonController {

    private PersonService personService;
    private CachePersonLocalStatus cachePersonLocalStatus;
    private final Message message;


    public PersonController(PersonService personService, CachePersonLocalStatus cachePersonLocalStatus, Message message) {
        this.personService = personService;
        this.cachePersonLocalStatus = cachePersonLocalStatus;
        this.message = message;
    }

    @GetMapping(value = "/person-list")
    public ModelAndView getPersonList() {
        if(cachePersonLocalStatus.isCacheMiss())
            personService.cacheEvict();

        message.setValue("<h5 style='color:green'>Cache Call: getPersonList() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        List<Person> personList = personService.findAllReadThroughCache();
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        return new ModelAndView("person-list", getParams(personList, message));
    }


    @GetMapping(value = "/person-find")
    public ModelAndView personFind(@ModelAttribute("identityCode") String identityCode) {
        message.setValue("<h5 style='color:green'>Cache Call: personFind() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        personService.findByIdentityCode(identityCode);
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        return new ModelAndView("fico-scoring-edit", getParams(message));
//        return new ModelAndView("person-list", getParams(message));
    }

    /** Dispatcher */
    @GetMapping(value = "/person-insert")
    public ModelAndView personInsert() {
        return new ModelAndView("person-insert");
    }

    @RequestMapping(value = "/person-add", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModelAndView personAdd(@ModelAttribute("person") Person person) {
        message.setValue("<h5 style='color:green'>Cache Call: personInsert() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        personService.personInsert(person);
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        return new ModelAndView("person-insert", getParams(message));
    }

    private HashMap<String, Object> getParams(Message message) {
        return getParams(new ArrayList<>(), message);
    }

    private HashMap<String, Object> getParams(List<Person> personList, Message message) {
        var params = new HashMap<String, Object>();
        params.put("personList", personList);
        params.put("message", message.getValue());
        params.put("seconds", message.getDuration().getSeconds());
        params.put("nanoSeconds", message.getDuration().getNano());
        return params;
    }
}

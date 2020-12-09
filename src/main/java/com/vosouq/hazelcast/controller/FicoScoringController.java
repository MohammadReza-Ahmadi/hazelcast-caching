package com.vosouq.hazelcast.controller;


import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.hazelcast.model.*;
import com.vosouq.hazelcast.service.FicoScoringService;
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
public class FicoScoringController {

    private FicoScoringService ficoScoringService;
    private CacheFicoScoringLocalStatus cacheFicoScoringLocalStatus;
    private final Message message;

    public FicoScoringController(FicoScoringService ficoScoringService, CacheFicoScoringLocalStatus cacheFicoScoringLocalStatus, Message message) {
        this.ficoScoringService = ficoScoringService;
        this.cacheFicoScoringLocalStatus = cacheFicoScoringLocalStatus;
        this.message = message;
    }

    @GetMapping(value = "/fico-scoring-list")
    public ModelAndView getFicoScoringList() {
        if(cacheFicoScoringLocalStatus.isCacheMiss())
            ficoScoringService.cacheEvict();

        message.setValue("<h5 style='color:green'>Cache Call: getFicoScoringList() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        List<FicoScoring> ficoScoringList = ficoScoringService.findAll();
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        return new ModelAndView("fico-scoring-list", getParams(ficoScoringList,message));
    }

    @GetMapping(value = "/fico-scoring-find")
    public ModelAndView ficoScoringFind() {
        message.setValue("<h5 style='color:green'>Cache Call: ficoScoringFind() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        ficoScoringService.findAll();
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        return new ModelAndView("fico-scoring-list", getParams(message));
    }

    @RequestMapping(value = "/fico-scoring-add", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModelAndView ficoScoringAddOrEdit(@ModelAttribute("ficoScoring") FicoScoring ficoScoring) {
        message.setValue("<h5 style='color:green'>Cache Call: ficoScoringAdd() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        ficoScoringService.ficoScoringInsert(ficoScoring);
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        return new ModelAndView("fico-scoring-edit", getParams(message));
    }

    @RequestMapping(value = "/fico-scoring-edit/{personId}/{ficoScoringId}",method = RequestMethod.GET)
    public ModelAndView ficoScoringEdit(@PathVariable Long personId,@PathVariable Long ficoScoringId) {
        message.setValue("<h5 style='c2olor:green'>Cache Call: getFicoScoringFindById() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        FicoScoring ficoScoring = ficoScoringService.findById(ficoScoringId,personId);
        if(ficoScoring.getPerson()==null) {
            Person person = new Person();
            person.setId(personId);;
            ficoScoring.setPerson(person);
        }
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        HashMap<String, Object> params = getParams(message);
        params.put("ficoScoring", ficoScoring);
        return new ModelAndView("fico-scoring-edit", params);
    }

    @RequestMapping(value = "/fico-scoring-update", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ModelAndView ficoScoringUpdate(@ModelAttribute("ficoScoring") FicoScoring ficoScoring) {
        message.setValue("<h5 style='color:green'>Cache Call: personInsert() ....</h5>");
        LocalDateTime start = LocalDateTime.now();
        ficoScoring = ficoScoringService.ficoScoringInsert(ficoScoring);
        message.setDuration(Duration.between(start, LocalDateTime.now()));
        HashMap<String, Object> params = getParams(message);
        params.put("ficoScoring", ficoScoring);
        return new ModelAndView("fico-scoring-edit", params);
    }

    private HashMap<String, Object> getParams(Message message) {
        return getParams(new ArrayList<>(), message);
    }

    private HashMap<String, Object> getParams(List<FicoScoring> ficoScoringList, Message message) {
        var params = new HashMap<String, Object>();
        params.put("ficoScoringList", ficoScoringList);
        params.put("message", message.getValue());
        params.put("seconds", message.getDuration().getSeconds());
        params.put("nanoSeconds", message.getDuration().getNano());
        return params;
    }

}

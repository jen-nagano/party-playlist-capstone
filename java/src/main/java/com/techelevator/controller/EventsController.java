package com.techelevator.controller;

import com.techelevator.dao.EventDao;
import com.techelevator.model.Event;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class EventsController {

    private EventDao eventDao;

    public EventsController(EventDao eventDao){
        this.eventDao = eventDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public Event addEvent(@RequestBody Event event) {
        // any logic necessary for creating events goes here

        return eventDao.createEvent(event);
    }

    @PostMapping("/test")
    public Event testEventParsing(@RequestBody Event event) {
        System.out.println("Parsed event: " + event);
        return event;
    }

}

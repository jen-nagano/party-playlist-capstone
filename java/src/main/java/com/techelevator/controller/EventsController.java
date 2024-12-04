package com.techelevator.controller;

import com.techelevator.dao.EventDao;
import com.techelevator.dao.PlaylistDao;
import com.techelevator.model.Event;
import com.techelevator.model.Playlist;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class EventsController {

    private EventDao eventDao;
    private PlaylistDao playlistDao;

    public EventsController(EventDao eventDao, PlaylistDao playlistDao){
        this.eventDao = eventDao;
        this.playlistDao = playlistDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public Event addEvent(@RequestBody Event event) {
        // any logic necessary for creating events goes here

        return eventDao.createEvent(event);
    }

    @RequestMapping(path="/events/{eventId}", method=RequestMethod.GET)
    public Event getEventById(@PathVariable int eventId) {
        Event event = eventDao.getEventById(eventId);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        return event;
    }

    @RequestMapping(path="/events/{eventId}/playlists", method=RequestMethod.GET)
    public List<Playlist> getPlaylistsForEvent(@PathVariable int eventId) {
        // Step 1: Ensure the event exists
        Event event = eventDao.getEventById(eventId); // Assuming `getEventById` exists in EventDao
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        // Step 2: Retrieve playlists associated with the event
        List<Playlist> playlists = playlistDao.getPlaylistsByEventId(eventId);
        if (playlists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found for this event");
        }

        return playlists;
    }

    @RequestMapping(path = "/events/{eventId}/playlists", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addPlaylistToEvent(@PathVariable int eventId, @RequestBody Playlist playlist) {
        // Step 1: Ensure the event exists
        Event event = eventDao.getEventById(eventId);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        // Step 2: Validate the playlist data (if necessary)
        if (playlist.getName() == null || playlist.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist name is required");
        }

        // Step 3: Create the new playlist (if it's not already in the system)
        Playlist newPlaylist = playlistDao.createPlaylist(playlist);

        // Step 4: Link the playlist to the event
        playlistDao.linkPlaylistToEvent(eventId, newPlaylist.getPlaylistId());
    }

    @PostMapping("/test-events")
    public Event testEventParsing(@RequestBody Event event) {
        System.out.println("Parsed event: " + event);
        return event;
    }

}

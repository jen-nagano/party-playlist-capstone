package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Event;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcEventDao implements EventDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcEventDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event createEvent(Event event) {
        String sql = "INSERT INTO event (name, date, start_time, end_time, creator) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING event_id;";

        try {
            // Get the generated event_id
            Integer eventId = jdbcTemplate.queryForObject(sql, Integer.class,
                    event.getName(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getCreator());

            // Set the ID in the event object
            event.setId(eventId);

            // Return the complete event object
            return event;
        } catch (Exception e) {
            throw new DaoException("Error creating event", e);
        }
    }

    @Override
    public Event getEventById(int eventId) {
        Event event = null;
        String sql = "SELECT event_id, name, date, start_time, end_time, creator " +
                "FROM event " +
                "WHERE event_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, eventId);
            if (results.next()) {
                event = mapRowToEvent(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (Exception e) {
            throw new DaoException("An unexpected error occurred while retrieving the event", e);
        }

        return event;
    }

    @Override
    public List<Event> getEventsByUserAndRole(int userId, String role) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, e.date, e.start_time, e.end_time, e.creator " +
                "FROM event e " +
                "JOIN user_event ue ON e.event_id = ue.event_id " +
                "WHERE ue.user_id = ? AND ue.role = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, role);
            while (results.next()) {
                events.add(mapRowToEvent(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return events;
    }

    @Override
    public void addUserEventRelationship(int userId, int eventId, String role) {
        String sql = "INSERT INTO user_event (user_id, event_id, role) VALUES (?, ?, ?);";

        try {
            jdbcTemplate.update(sql, userId, eventId, role);
        } catch (Exception e) {
            throw new DaoException("Error adding user-event relationship", e);
        }
    }

    @Override
    public void deleteEventForUser(int userId, int eventId) {
        String sql = "DELETE FROM user_event WHERE user_id = ? AND event_id = ?";
        jdbcTemplate.update(sql, userId, eventId);
    }

    public void removeEventFromPlaylist(int playlistId, int eventId) {
        String sql = "DELETE FROM event_playlist WHERE playlist_id = ? AND event_id = ?";

        try {
            int rowsAffected = jdbcTemplate.update(sql, playlistId, eventId);
            if (rowsAffected == 0) {
                throw new DaoException("No matching connection found for the given playlist ID and event ID.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataAccessException e) {
            throw new DaoException("Failed to remove event from playlist.", e);
        }
    }

    public void updateEvent(int eventId, Event event) {
        String sql = "UPDATE event SET name = ?, date = ?, start_time = ?, end_time = ?, creator = ? WHERE event_id = ?";
        System.out.println(sql);
        try {
            jdbcTemplate.update(sql, event.getName(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getCreator(), eventId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to update event", e);
        }
    }

    private Event mapRowToEvent(SqlRowSet results) {
        Event event = new Event();

        event.setId(results.getInt("event_id"));
        event.setName(results.getString("name"));
        event.setDate(results.getDate("date").toLocalDate());
        event.setStartTime(results.getString("start_time"));
        event.setEndTime(results.getString("end_time"));
        event.setCreator(results.getInt("creator"));

        return event;
    }
}

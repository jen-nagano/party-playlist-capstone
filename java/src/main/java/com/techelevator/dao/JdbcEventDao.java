package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Event;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcEventDao implements EventDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcEventDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event createEvent(Event event) {
        Event newEvent = null;
        String sql = "INSERT INTO event (name, date, start_time, end_time, creator) VALUES (?, ?, ?, ?, ?);";

        try {
            SqlRowSet results = null;
            results = jdbcTemplate.queryForRowSet(sql, event.getName(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getCreator());
            if (results.next()) {
                newEvent = mapRowToEvent(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return newEvent;
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

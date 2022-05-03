package project.lab6.repository.database;

import project.lab6.domain.Event1;
import project.lab6.repository.database.query.Query;
import project.lab6.repository.database.query.SaveQuery;

import java.sql.*;
import java.time.LocalDate;

public class EventDbRepository extends AbstractDbRepository<Long, Event1> {
    public EventDbRepository(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public Event1 findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null!");
        return genericFindOne(new Query() {
            @Override
            public String getSqlString() {
                return "select * from events where id=?";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setLong(1, id);
            }
        });
    }

    @Override
    public Event1 save(Event1 event1) {
        if (event1 == null)
            throw new IllegalArgumentException("user must be not null!");

        return genericSave(event1, new SaveQuery<Event1>() {
            @Override
            public void setId(Event1 entity, Connection connection) throws SQLException {
                entity.setId(getLongId(connection, "events", "id"));
            }

            @Override
            public String getSqlString() {
                return "insert into events(date, location,description,name) VALUES (?,?,?,?)";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {

                statement.setDate(1, Date.valueOf(event1.getDate()));
                statement.setString(2, event1.getLocation());
                statement.setString(3, event1.getDescription());
                statement.setString(4, event1.getName());
            }
        });
    }

    @Override
    public boolean delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null!");

        return genericDelete(new Query() {
            @Override
            public String getSqlString() {
                return "delete from events where id=?";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setLong(1, id);
            }
        });
    }

    @Override
    public boolean update(Event1 event) {
        if (event == null)
            throw new IllegalArgumentException("entity must be not null!");

        return genericUpdate(new Query() {
            @Override
            public String getSqlString() {
                return "update events set name=?, location=?, date=? ,description=? where id=?";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setString(1, event.getName());
                statement.setString(2, event.getLocation());
                statement.setString(3, event.getDate().toString());
                statement.setString(3, event.getDescription());
                statement.setLong(5, event.getId());
            }
        });
    }

    @Override
    protected String getFindAllSqlStatement() {
        return "select * from events";
    }

    @Override
    protected Event1 getEntityFromSet(ResultSet set) throws SQLException {
        Long id = set.getLong("id");
        String name = set.getString("name");
        LocalDate date = set.getTimestamp("date").toLocalDateTime().toLocalDate();
        String location = set.getString("location");
        String desc = set.getString("description");
        return new Event1(id, name, date, location, desc);
    }
}

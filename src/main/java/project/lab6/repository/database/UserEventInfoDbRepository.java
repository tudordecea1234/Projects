package project.lab6.repository.database;

import project.lab6.domain.TupleWithIdEventUser;
import project.lab6.domain.UserEventInfo;
import project.lab6.repository.database.query.Query;
import project.lab6.repository.database.query.SaveQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEventInfoDbRepository extends AbstractDbRepository<TupleWithIdEventUser, UserEventInfo> {
    public UserEventInfoDbRepository(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public UserEventInfo findOne(TupleWithIdEventUser id) {
        if (id == null) throw new IllegalArgumentException("id must be not null!");

        return genericFindOne(new Query() {
            @Override
            public String getSqlString() {
                return "select * from user_event where id_event=? and id_user=?";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setLong(1, id.getIdEvent());
                statement.setLong(2, id.getIdUser());
            }
        });
    }

    @Override
    public UserEventInfo save(UserEventInfo userInfo) {
        if (userInfo == null) throw new IllegalArgumentException("user must be not null!");

        return genericSave(userInfo, new SaveQuery<UserEventInfo>() {
            @Override
            public void setId(UserEventInfo entity, Connection connection) {
            }

            @Override
            public String getSqlString() {
                return "insert into user_event(id_user, id_event, notification) VALUES (?,?,?)";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setLong(1, userInfo.getIdUser());
                statement.setLong(2, userInfo.getIdEvent());
                statement.setBoolean(3, userInfo.isNotification());
            }
        });
    }

    @Override
    public boolean delete(TupleWithIdEventUser id) {
        if (id == null) throw new IllegalArgumentException("id must be not null!");

        return genericDelete(new Query() {
            @Override
            public String getSqlString() {
                return "delete from user_event where id_event=? and id_user=?";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setLong(1, id.getIdEvent());
                statement.setLong(2, id.getIdUser());
            }
        });
    }

    @Override
    public boolean update(UserEventInfo userInfo) {
        if (userInfo == null) throw new IllegalArgumentException("entity must be not null!");

        return genericUpdate(new Query() {
            @Override
            public String getSqlString() {
                return "update user_event set notification=? where id_event=? and id_user=?";
            }

            @Override
            public void setStatementParameters(PreparedStatement statement) throws SQLException {
                statement.setBoolean(1, userInfo.isNotification());
                statement.setLong(2, userInfo.getIdEvent());
                statement.setLong(3, userInfo.getIdUser());
            }
        });
    }

    @Override
    protected String getFindAllSqlStatement() {
        return "select * from user_event";
    }

    @Override
    protected UserEventInfo getEntityFromSet(ResultSet set) throws SQLException {
        Long idEvent = set.getLong("id_event");
        Long idUser = set.getLong("id_user");
        boolean notification = set.getBoolean("notification");
        return new UserEventInfo(idEvent, idUser, notification);
    }
}

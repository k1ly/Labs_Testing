package jdbc.dao;

import jdbc.dao.dbtable.UserTable;
import jdbc.dao.exception.DAOException;
import jdbc.dao.parameter.ParameterMap;

import java.sql.SQLException;
import java.util.*;

public class UserDAO extends AbstractDAO<User> {
    private static final String TABLE_NAME = "users";

    public UserDAO() {
        tableName = TABLE_NAME;
    }

    public void add(User user) throws DAOException {
        if (user != null) {
            try {
                Integer generatedId = insertEntity(takeFields(user));
                user.setId(generatedId);
            } catch (SQLException exception) {
                throw new DAOException("User inserting error", exception);
            }
        }
    }

    public List<User> findAll() throws DAOException {
        List<User> userList = new ArrayList<>();
        try {
            String sql = makeQuery();
            for (Object item : executeQuery(User.class, sql)) {
                userList.add((User) item);
            }
        } catch (SQLException exception) {
            throw new DAOException("User selecting error", exception);
        }
        return userList;
    }

    public List<User> find(ParameterMap parameters) throws DAOException {
        List<User> userList = new ArrayList<>();
        try {
            String sql = makeQuery() + makeQueryCondition(parameters.getParameters());
            for (Object item : executeQuery(User.class, sql)) {
                userList.add((User) item);
            }
        } catch (SQLException exception) {
            throw new DAOException("User selecting error", exception);
        }
        return userList;
    }

    public Optional<User> findUserById(int id) throws DAOException {
        Map<String, Object> userIdParameter = new LinkedHashMap<>();
        userIdParameter.put(UserTable.ID.getColumnName(), id);
        List<User> letterList = find(ParameterMap.of(userIdParameter));
        return letterList.size() == 1 ? Optional.of(letterList.get(0)) : Optional.empty();
    }

    public boolean update(int id, User user) throws DAOException {
        boolean isUserUpdated = false;
        if (user != null) {
            try {
                Map<String, Object> userIdParameter = new LinkedHashMap<>();
                userIdParameter.put(UserTable.ID.getColumnName(), id);
                isUserUpdated = updateEntity(takeFields(user), ParameterMap.of(userIdParameter)) == 1;
            } catch (SQLException exception) {
                throw new DAOException("User updating error", exception);
            }
        }
        return isUserUpdated;
    }

    @Override
    public ParameterMap takeFields(User user) {
        Map<String, Object> fields = new LinkedHashMap<>();
        if (user.getName() != null)
            fields.put(UserTable.NAME.getColumnName(), user.getName());
        if (user.getBirthDate() != null)
            fields.put(UserTable.BIRTH_DATE.getColumnName(), user.getBirthDate());
        return ParameterMap.of(fields);
    }

    @Override
    public String getColumns() {
        return UserTable.ID.getColumnName()
                + ", " + UserTable.NAME.getColumnName()
                + ", " + UserTable.BIRTH_DATE.getColumnName();
    }
}
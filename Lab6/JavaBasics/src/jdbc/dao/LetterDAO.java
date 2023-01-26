package jdbc.dao;

import jdbc.dao.dbtable.LetterTable;
import jdbc.dao.exception.DAOException;
import jdbc.dao.parameter.ParameterMap;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LetterDAO extends AbstractDAO<Letter> {
    private static final String TABLE_NAME = "letters";

    public LetterDAO() {
        tableName = TABLE_NAME;
    }

    public void add(Letter letter) throws DAOException {
        if (letter != null) {
            try {
                Integer generatedId = insertEntity(takeFields(letter));
                letter.setId(generatedId);
            } catch (SQLException exception) {
                throw new DAOException("Letter inserting error", exception);
            }
        }
    }

    public void sendLetter(Letter letter) throws DAOException {
        UserDAO userDAO = new UserDAO();
        Connection connection = null;
        try {
            connection = startTransaction();
            for (var receiver : userDAO.findAll()) {
                letter.setReceiver(receiver);
                insertEntity(takeFields(letter), connection);
            }
            commitTransaction(connection);
        } catch (SQLException | DAOException exception) {
            rollbackTransaction(connection);
            throw new DAOException("Letter sending error", exception);
        } finally {
            finishTransaction(connection);
        }
    }

    public List<Letter> findAll() throws DAOException {
        List<Letter> letterList = new ArrayList<>();
        try {
            String sql = makeQuery();
            for (Object item : executeQuery(Letter.class, sql)) {
                letterList.add((Letter) item);
            }
        } catch (SQLException exception) {
            throw new DAOException("Letter selecting error", exception);
        }
        return letterList;
    }

    public List<Letter> browseSorted(String orderAttr, boolean asc) throws DAOException {
        List<Letter> letterList = new ArrayList<>();
        try {
            String sql = makeQuery() + makeOrderQuery(orderAttr, asc);
            for (Object item : executeQuery(Letter.class, sql)) {
                letterList.add((Letter) item);
            }
        } catch (SQLException exception) {
            throw new DAOException("Letter selecting error", exception);
        }
        return letterList;
    }

    public List<Letter> find(ParameterMap parameters) throws DAOException {
        List<Letter> letterList = new ArrayList<>();
        try {
            String sql = makeQuery() + makeQueryCondition(parameters.getParameters());
            for (Object item : executeQuery(Letter.class, sql)) {
                letterList.add((Letter) item);
            }
        } catch (SQLException exception) {
            throw new DAOException("Letter selecting error", exception);
        }
        return letterList;
    }

    public Optional<Letter> findLetterById(int id) throws DAOException {
        Map<String, Object> letterIdParameter = new LinkedHashMap<>();
        letterIdParameter.put(LetterTable.ID.getColumnName(), id);
        List<Letter> letterList = find(ParameterMap.of(letterIdParameter));
        return letterList.size() == 1 ? Optional.of(letterList.get(0)) : Optional.empty();
    }

    public boolean update(int id, Letter letter) throws DAOException {
        boolean isLetterUpdated = false;
        if (letter != null) {
            try {
                Map<String, Object> letterIdParameter = new LinkedHashMap<>();
                letterIdParameter.put(LetterTable.ID.getColumnName(), id);
                isLetterUpdated = updateEntity(takeFields(letter), ParameterMap.of(letterIdParameter)) == 1;
            } catch (SQLException exception) {
                throw new DAOException("Letter updating error", exception);
            }
        }
        return isLetterUpdated;
    }

    @Override
    public ParameterMap takeFields(Letter letter) {
        Map<String, Object> fields = new LinkedHashMap<>();
        if (letter.getSender() != null && letter.getSender().getId() > 0)
            fields.put(LetterTable.SENDER_ID.getColumnName(), letter.getSender().getId());
        if (letter.getReceiver() != null && letter.getReceiver().getId() > 0)
            fields.put(LetterTable.RECEIVER_ID.getColumnName(), letter.getReceiver().getId());
        if (letter.getText() != null)
            fields.put(LetterTable.TEXT.getColumnName(), letter.getText());
        if (letter.getTheme() != null)
            fields.put(LetterTable.THEME.getColumnName(), letter.getTheme());
        if (letter.getSendDate() != null)
            fields.put(LetterTable.SEND_DATE.getColumnName(), letter.getSendDate());
        return ParameterMap.of(fields);
    }

    @Override
    public String getColumns() {
        return LetterTable.ID.getColumnName()
                + ", " + LetterTable.SENDER_ID.getColumnName()
                + ", " + LetterTable.RECEIVER_ID.getColumnName()
                + ", " + LetterTable.TEXT.getColumnName()
                + ", " + LetterTable.THEME.getColumnName()
                + ", " + LetterTable.SEND_DATE.getColumnName();
    }
}
package jdbc.dao;

import jdbc.connection.ConnectionPool;
import jdbc.dao.builder.Builder;
import jdbc.dao.builder.BuilderProvider;
import jdbc.dao.exception.DAOException;
import jdbc.dao.parameter.ParameterMap;

import java.sql.*;
import java.util.*;

public abstract class AbstractDAO<T> {
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String VALUES = " VALUES ";
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String UPDATE = "UPDATE ";
    public static final String SET = " SET ";
    public static final String WHERE = " WHERE ";
    public static final String AND = " AND ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";

    public String tableName;

    public abstract ParameterMap takeFields(T object);

    public abstract String getColumns();

    protected Connection startTransaction() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            throw new DAOException(exception);
        }
        return connection;
    }

    protected void commitTransaction(Connection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException exception) {
                throw new DAOException(exception);
            }
        }
    }

    protected void rollbackTransaction(Connection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DAOException(exception);
            }
        }
    }

    protected void finishTransaction(Connection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                throw new DAOException(exception);
            }
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.releaseConnection(connection);
        }
    }

    protected Integer insertEntity(ParameterMap parameters) throws SQLException {
        int generatedId = 0;
        if (parameters.size() > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            try {
                generatedId = insertEntity(parameters, connection);
            } finally {
                if (connection != null)
                    connectionPool.releaseConnection(connection);
            }
        }
        return generatedId;
    }

    protected Integer insertEntity(ParameterMap parameters, Connection connection) throws SQLException {
        int generatedId = 0;
        if (parameters.size() > 0) {
            PreparedStatement preparedStatement = null;
            try {
                String sql = makeInsertStatement(parameters.getParameters());
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prepare(preparedStatement, parameters.getValues());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next())
                    generatedId = resultSet.getInt(1);
            } finally {
                if (preparedStatement != null)
                    preparedStatement.close();
            }
        }
        return generatedId;
    }

    protected int updateEntity(ParameterMap parameters, ParameterMap updateId) throws SQLException {
        int updatedRowAmount = 0;
        if (parameters.size() > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = null;
            try {
                String sql = makeUpdateStatement(parameters.getParameters())
                        + makeQueryCondition(updateId.getParameters());
                preparedStatement = connection.prepareStatement(sql);
                prepare(preparedStatement, parameters.getValues());
                updatedRowAmount = preparedStatement.executeUpdate();
            } finally {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connectionPool.releaseConnection(connection);
            }
        }
        return updatedRowAmount;
    }

    protected String makeQuery() {
        return SELECT + getColumns() + FROM + tableName;
    }

    protected String makeQueryCondition(Map<String, Object> parameters) {
        StringBuilder sqlQuery = new StringBuilder().append(WHERE).append("(");
        if (parameters != null)
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                sqlQuery.append(entry.getKey()).append("=")
                        .append("'").append(entry.getValue()).append("'").append(AND);
            }
        if (sqlQuery.toString().endsWith(AND))
            sqlQuery.delete(sqlQuery.lastIndexOf(AND), sqlQuery.length());
        sqlQuery.append(")");
        return (WHERE + "()").equals(sqlQuery.toString()) ? "" : sqlQuery.toString();
    }

    protected String makeOrderQuery(String orderAttr, boolean asc) {
        StringBuilder sqlQuery = new StringBuilder();
        if (orderAttr != null) {
            sqlQuery.append(ORDER_BY).append(orderAttr).append(asc ? ASC : DESC);
        }
        return sqlQuery.toString();
    }

    protected List<Object> executeQuery(Class<T> buildClass, String sql) throws SQLException {
        List<Object> resultList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            if (buildClass != null) {
                ResultSet resultSet = statement.executeQuery(sql);
                BuilderProvider builderProvider = BuilderProvider.getInstance();
                Builder<?> builder = builderProvider.getBuilder(buildClass);
                while (resultSet.next()) {
                    resultList.add(builder.build(resultSet));
                }
            }
        } finally {
            if (connection != null)
                connectionPool.releaseConnection(connection);
        }
        return resultList;
    }

    private String makeInsertStatement(Map<String, Object> parameters) {
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String column = entry.getKey();
                columns.append(column).append(", ");
                values.append("?, ");
            }
            columns.delete(columns.lastIndexOf(","), columns.length());
            values.delete(values.lastIndexOf(","), values.length());
        }
        columns.append(")");
        values.append(")");
        return INSERT_INTO + tableName + columns + VALUES + values;
    }

    private String makeUpdateStatement(Map<String, Object> parameters) {
        StringBuilder columns = new StringBuilder();
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String column = entry.getKey();
                columns.append(column).append("=?, ");
            }
            columns.delete(columns.lastIndexOf(","), columns.length());
        }
        return UPDATE + tableName + SET + columns;
    }

    private void prepare(PreparedStatement preparedStatement, List<Object> parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            if (parameters.get(i) == null)
                preparedStatement.setNull(i + 1, Types.NULL);
            else
                preparedStatement.setObject(i + 1, parameters.get(i));
        }
    }
}
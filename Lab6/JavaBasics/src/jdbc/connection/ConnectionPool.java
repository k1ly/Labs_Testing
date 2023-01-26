package jdbc.connection;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String PROPERTY_PATH = "db";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";
    private static final String USER_PROPERTY = "db.user";
    private static final String PASSWORD_PROPERTY = "db.password";
    private static final String POOL_SIZE_PROPERTY = "db.pool_size";
    private static final String AWAITING_TIMEOUT_PROPERTY = "db.awaiting_timeout";
    private static final int DEFAULT_AWAITING_TIMEOUT = 30;

    private static final ReentrantLock lock = new ReentrantLock();

    private static ConnectionPool instance;

    private final BlockingQueue<Connection> freeConnections;
    private final BlockingQueue<Connection> usingConnections;
    private int awaitingTimeout;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>();
        usingConnections = new LinkedBlockingQueue<>();
        init();
    }

    private void init() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTY_PATH);
            if (resourceBundle == null)
                throw new RuntimeException("Database properties file not found");
            else {
                String driver = resourceBundle.getString(DRIVER_PROPERTY);
                Class.forName(driver).getDeclaredConstructor().newInstance();
                String connectionURL = resourceBundle.getString(URL_PROPERTY);
                String user = resourceBundle.getString(USER_PROPERTY);
                String password = resourceBundle.getString(PASSWORD_PROPERTY);
                String poolSize = resourceBundle.getString(POOL_SIZE_PROPERTY);
                for (int i = 0; i < Integer.parseInt(poolSize); i++) {
                    Connection connection = DriverManager.getConnection(connectionURL, user, password);
                    freeConnections.add(connection);
                }
                awaitingTimeout = Integer.parseInt(resourceBundle.getString(AWAITING_TIMEOUT_PROPERTY));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException
                | IllegalAccessException | InstantiationException | InvocationTargetException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                if (lock.tryLock(DEFAULT_AWAITING_TIMEOUT, TimeUnit.SECONDS)) {
                    if (instance == null)
                        instance = new ConnectionPool();

                } else {
//                    logger.error("Timeout exceeded");
                    throw new RuntimeException("Timeout exceeded");
                }
            } catch (InterruptedException exception) {
//                logger.error(exception);
                throw new RuntimeException(exception.getMessage(), exception);
            } finally {
                if (lock.isHeldByCurrentThread())
                    lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.poll(awaitingTimeout, TimeUnit.SECONDS);
            if (connection != null)
                usingConnections.add(connection);
        } catch (InterruptedException exception) {
//            logger.error(exception.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (usingConnections.remove(connection))
            freeConnections.add(connection);
    }

    public void destroy() {
        if (!usingConnections.isEmpty()) {
//            logger.error("Connections were not released");
            throw new RuntimeException("Connections were not released");
        }
        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                Connection connection = freeConnections.take();
                connection.close();
            } catch (InterruptedException exception) {
//                logger.error(exception.getMessage());
                throw new RuntimeException(exception.getMessage(), exception);
            } catch (SQLException exception) {
//                logger.error("Database was not closed");
                throw new RuntimeException("Database was not closed", exception);
            }
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException exception) {
//            logger.error("Drivers were not de-registered");
            throw new RuntimeException("Drivers were not de-registered", exception);
        }
    }
}
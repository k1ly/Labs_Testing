package jdbc.dao.builder;

import jdbc.dao.Letter;
import jdbc.dao.User;
import jdbc.dao.builder.impl.LetterBuilder;
import jdbc.dao.builder.impl.UserBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BuilderProvider {
    private static final ReentrantLock lock = new ReentrantLock();

    private static BuilderProvider instance;

    private final Map<String, Builder<?>> repository = new HashMap<>();

    private BuilderProvider() {
        addBuilder(User.class, new UserBuilder());
        addBuilder(Letter.class, new LetterBuilder());
    }

    public static synchronized BuilderProvider getInstance() {
        if (instance == null) {
            try {
                if (lock.tryLock(10, TimeUnit.SECONDS)) {
                    if (instance == null)
                        instance = new BuilderProvider();
                    else {
//                        logger.warn("BuilderProvider instance is been already initializing by another thread");
                    }
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

    public Builder<?> getBuilder(Class<?> buildClass) {
        return repository.get(buildClass.getSimpleName());
    }

    public synchronized void addBuilder(Class<?> buildClass, Builder<?> builder) {
        repository.put(buildClass.getSimpleName(), builder);
    }
}

package be.lghs.gate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Configuration {

    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    private static final List<Path> CONFIG_FILES = List.of(
            Paths.get("mqtt.properties"),
            Paths.get(System.getProperty("user.home"), "mqtt.properties"),
            Paths.get("/etc", "mqtt", "mqtt.properties")
    );

    private interface LockReleasable extends AutoCloseable {
        @Override
        void close();
    }

    private static Path firstExisting(List<Path> paths) throws NoSuchFileException {
        for (Path path : paths) {
            if (Files.exists(path)) {
                log.debug("selected path {}", path);
                return path;
            }
        }
        throw new NoSuchFileException(paths.toString());
    }

    private final Properties config;
    public final String server;
    public final String clientId;
    public final String clientPassword;
    public final String topic;
    private final Lock writeLock;
    private final Lock readLock;
    private final Map<Long, String> users;

    public Configuration() {
        config = new Properties();
        try {
            config.load(Files.newBufferedReader(firstExisting(CONFIG_FILES)));
        } catch (IOException e) {
            throw new UncheckedIOException("Configuration file not found in any of " + CONFIG_FILES, e);
        }
        server = config.getProperty("gate-server.mqtt.url");
        clientId = config.getProperty("gate-server.mqtt.client-id");
        clientPassword = config.getProperty("gate-server.mqtt.password");
        topic = config.getProperty("gate-server.mqtt.topic");

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();
        users = new HashMap<>();

        reload();

        Signal.handle(new Signal("HUP"), signal -> reload());
    }

    public void reload() {
        log.debug("reloading config");
        writeLock.lock();
        try (LockReleasable handle = writeLock::unlock) {
            try {
                config.load(Files.newBufferedReader(firstExisting(CONFIG_FILES)));
            } catch (IOException e) {
                throw new UncheckedIOException("Configuration file not found in any of " + CONFIG_FILES, e);
            }

            users.clear();
            for (Map.Entry<Object, Object> entry : config.entrySet()) {
                int dotIndex;
                String propertyName = (String) entry.getKey();
                if ((dotIndex = propertyName.indexOf(".")) < 0 || !propertyName.substring(0, dotIndex).equals("card")) {
                    continue;
                }

                users.put(Long.parseUnsignedLong(propertyName.substring(dotIndex + 1)), (String) entry.getValue());
            }
        }
    }

    public String getUser(long cardId) {
        readLock.lock();
        try (LockReleasable handle = readLock::unlock) {
            return users.get(cardId);
        }
    }
}

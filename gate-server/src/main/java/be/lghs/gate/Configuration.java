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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Configuration {

    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    private static final List<Path> CONFIG_FILES = List.of(
        Paths.get("mqtt.properties"),
        Paths.get(System.getProperty("user.home"), "mqtt.properties"),
        Paths.get("/etc", "gate-server", "mqtt.properties"));

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
    private final Lock writeLock;
    private final Lock readLock;

    public Configuration() {
        this.config = new Properties();

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();

        reload();

        Signal.handle(new Signal("HUP"), signal -> reload());
    }

    private LockReleasable readLock() {
        readLock.lock();
        return readLock::unlock;
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
        }
    }

    public String getServer() {
        try (LockReleasable handle = readLock()) {
            return config.getProperty("gate-server.mqtt.url");
        }
    }

    public String getClientId() {
        try (LockReleasable handle = readLock()) {
            return config.getProperty("gate-server.mqtt.client-id");
        }
    }

    public String getClientPassword() {
        try (LockReleasable handle = readLock()) {
            return config.getProperty("gate-server.mqtt.password");
        }
    }

    public String getRequestTopic() {
        try (LockReleasable handle = readLock()) {
            return config.getProperty("gate-server.mqtt.request-topic");
        }
    }

    public String getResponseTopic(String gateId) {
        try (LockReleasable handle = readLock()) {
            return config.getProperty("gate-server.mqtt.response-topic")
                .replace("{gate}", gateId);
        }
    }

    public String getUser(long cardId) {
        try (LockReleasable handle = readLock()) {
            return config.getProperty("card." + cardId);
        }
    }
}

package be.lghs.gate;

import be.lghs.gate.proto.Gate;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GateListener {

    private static final Logger log = LoggerFactory.getLogger(GateListener.class);

    private static Path firstExisting(Path... paths) throws NoSuchFileException {
        for (Path path : paths) {
            if (Files.exists(path)) {
                log.debug("loading path {}", path);
                return path;
            }
        }
        throw new NoSuchFileException(Arrays.toString(paths));
    }

    private final Properties config;
    private final String server;
    private final String clientId;
    private final String clientPassword;
    private final String topic;
    private final MqttClient mqttClient;
    private final Map<Long, String> users;

    public GateListener() throws IOException, MqttException {
        config = new Properties();
        config.load(Files.newBufferedReader(firstExisting(
            Paths.get("mqtt.properties"),
            Paths.get(System.getProperty("user.home"), "mqtt.properties"),
            Paths.get("/etc", "mqtt", "mqtt.properties")
        )));
        server = config.getProperty("gate-server.mqtt.url");
        clientId = config.getProperty("gate-server.mqtt.client-id");
        clientPassword = config.getProperty("gate-server.mqtt.password");
        topic = config.getProperty("gate-server.mqtt.topic");

        users = new HashMap<>();
        for (Map.Entry<Object, Object> entry : config.entrySet()) {
            int dotIndex;
            String propertyName = (String) entry.getKey();
            if ((dotIndex = propertyName.indexOf(".")) < 0 || !propertyName.substring(0, dotIndex).equals("card")) {
                continue;
            }

            users.put(Long.parseUnsignedLong(propertyName.substring(dotIndex + 1)), (String) entry.getValue());
        }

        mqttClient = new MqttClient(server, clientId);
    }

    public void listen() throws MqttException {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setServerURIs(new String[] { server });
        connectOptions.setUserName(clientId);
        connectOptions.setPassword(clientPassword.toCharArray());
        connectOptions.setAutomaticReconnect(true);
        mqttClient.connect(connectOptions);

        mqttClient.setCallback(Callback.from((topic, message) -> {
            // FIXME No token handling yet
            if (!topic.equals("lghs/gate/1/open/request")) {
                // TODO The logic is not the same for the internal and external gate
            }

            Gate.GateOpenRequest request = Gate.GateOpenRequest.parseFrom(message.getPayload());
            Gate.GateOpenResponse response;
            String user;
            if ((user = users.get(request.getCardId())) == null) {
                response = Gate.GateOpenResponse.newBuilder()
                    .setCardId(request.getCardId())
                    .setOk(false)
                    .build();
            } else {
                log.info("'{}' getting in ({})", user, topic);

                response = Gate.GateOpenResponse.newBuilder()
                    .setCardId(request.getCardId())
                    .setOk(true)
                    .build();
            }

            mqttClient.publish(
                topic.replace("request", "response"),
                new MqttMessage(response.toByteArray()));
        }, e -> log.error("unexpected error", e)));

        log.debug("subscribing to topic {}", topic);
        mqttClient.subscribe(topic);
    }
}

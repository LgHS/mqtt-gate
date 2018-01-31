package be.lghs.gate;

import be.lghs.gate.proto.Gate;
import com.google.protobuf.ByteString;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class GateListener {

    private static Path firstExisting(Path... paths) throws NoSuchFileException {
        for (Path path : paths) {
            if (Files.exists(path)) {
                return path;
            }
        }
        throw new NoSuchFileException(Arrays.toString(paths));
    }

    private final MqttClient mqttClient;

    public GateListener() throws IOException, MqttException {
        Properties config = new Properties();
        config.load(Files.newBufferedReader(firstExisting(
            Paths.get("mqtt.properties"),
            Paths.get(System.getProperty("user.home"), "mqtt.properties"),
            Paths.get("/etc", "mqtt", "mqtt.properties")
        )));
        String server = config.getProperty("server.url");
        String client = config.getProperty("client.id");

        mqttClient = new MqttClient(server, client);
    }

    public void listen() throws MqttException {
        System.out.println("coucou");
        mqttClient.connect();
        mqttClient.setCallback(Callback.from((topic, message) -> {
            if(!topic.equals("lghs/gate/1/open/request")) {
                System.out.println("nope: " + topic);
                return;
            }

            Gate.GateOpenRequest request = Gate.GateOpenRequest.parseFrom(message.getPayload());
            System.out.println(request);

            System.out.println(new UUID(request.getTokenHigh(), request.getTokenLow()));

            Gate.GateOpenResponse response = Gate.GateOpenResponse.newBuilder()
                .setCardId(request.getCardId())
                .setOk(false)
                .build();
            mqttClient.publish("lghs/gate/1/open/response", new MqttMessage(response.toByteArray()));
        }, Throwable::printStackTrace));


        mqttClient.subscribe("lghs/gate/+/open/request");

        UUID token = UUID.randomUUID();
        Gate.GateOpenRequest request = Gate.GateOpenRequest.newBuilder()
            .setCardId(1)
            .setTokenHigh(token.getMostSignificantBits())
            .setTokenLow(token.getLeastSignificantBits())
            .setPin(ByteString.copyFromUtf8("1234"))
            .build();

        mqttClient.publish("lghs/gate/1/open/request", new MqttMessage(request.toByteArray()));
        mqttClient.publish("lghs/gate/2/open/request", new MqttMessage(request.toByteArray()));

        System.out.println(token);
    }
}

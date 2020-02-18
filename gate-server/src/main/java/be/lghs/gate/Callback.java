package be.lghs.gate;

import be.lghs.gate.proto.Gate;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Callback implements MqttCallbackExtended {

    private static final Logger log = LoggerFactory.getLogger(GateListener.class);

    private final MqttClient mqttClient;
    private final String topic;
    private final Configuration configuration;

    public Callback(MqttClient mqttClient, String topic, Configuration configuration) {
        this.mqttClient = mqttClient;
        this.topic = topic;
        this.configuration = configuration;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.debug("subscribing to topic {}", topic);
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("connection lost", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // FIXME No token handling yet
        if (!topic.equals("lghs/gate/1/open/request")) {
            // TODO The logic is not the same for the internal and external gate
        }

        Gate.GateOpenRequest request = Gate.GateOpenRequest.parseFrom(message.getPayload());
        Gate.GateOpenResponse response;
        String user;
        if ((user = configuration.getUser(request.getCardId())) == null) {
            log.warn("unknown card id '{}'", request.getCardId());
            response = Gate.GateOpenResponse.newBuilder()
                .setCardId(request.getCardId())
                .setOk(false)
                .build();
        } else {
            log.info("'{}' getting in ({})", user, topic);

            response = Gate.GateOpenResponse.newBuilder()
                .setCardId(request.getCardId())
                .setOk(true)
                .setUsername(user)
                .build();
        }

        mqttClient.publish(
            topic.replace("request", "response"),
            new MqttMessage(response.toByteArray()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}

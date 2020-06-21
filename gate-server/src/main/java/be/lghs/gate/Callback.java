package be.lghs.gate;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Callback implements MqttCallbackExtended {

    private static final Logger log = LoggerFactory.getLogger(GateListener.class);

    private final MqttClient mqttClient;
    private final Configuration configuration;

    public Callback(MqttClient mqttClient, Configuration configuration) {
        this.mqttClient = mqttClient;
        this.configuration = configuration;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.debug("subscribing to topic {}", configuration.getRequestTopic());
        try {
            mqttClient.subscribe(configuration.getRequestTopic());
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
        String gateId = extractGateId(topic);
        // FIXME No token handling yet
        if (!gateId.equals("1")) {
            // TODO The logic is not the same for the internal and external gate
        }

        // Gate.GateOpenRequest request = Gate.GateOpenRequest.parseFrom(message.getPayload());
        // Gate.GateOpenResponse response;
        // String user;
        // if ((user = configuration.getUser(request.getCardId())) == null) {
        //     log.warn("unknown card id '{}'", request.getCardId());
        //     response = Gate.GateOpenResponse.newBuilder()
        //         .setCardId(request.getCardId())
        //         .setOk(false)
        //         .build();
        // } else {
        //     log.info("'{}' getting in ({})", user, topic);
        //
        //     response = Gate.GateOpenResponse.newBuilder()
        //         .setCardId(request.getCardId())
        //         .setOk(true)
        //         .setUsername(user)
        //         .build();
        // }

        mqttClient.publish(
            configuration.getResponseTopic(gateId),
            new MqttMessage("true".getBytes()));
            // new MqttMessage(response.toByteArray()));
    }

    private String extractGateId(String topic) {
        String requestTopic = configuration.getRequestTopic();
        int start = requestTopic.indexOf('+');
        int end = requestTopic.length() - 1 - start;

        return topic.substring(start, topic.length() - end);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}

package be.lghs.gate;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class GateListener {

    private final Configuration config;
    private final MqttClient mqttClient;

    public GateListener() throws IOException, MqttException {
        config = new Configuration();

        mqttClient = new MqttClient(config.server, config.clientId);
    }

    public void listen() throws MqttException {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setServerURIs(new String[] { config.server });
        connectOptions.setUserName(config.clientId);
        connectOptions.setPassword(config.clientPassword.toCharArray());
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(true);

        mqttClient.setCallback(new Callback(mqttClient, config.topic, config));
        mqttClient.connect(connectOptions);
    }
}

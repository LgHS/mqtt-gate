package be.lghs.gate;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class GateListener {

    private final Configuration config;
    private final MqttClient mqttClient;

    public GateListener() throws MqttException {
        config = new Configuration();

        mqttClient = new MqttClient(config.getServer(), config.getClientId());
    }

    public void listen() throws MqttException {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setServerURIs(new String[] { config.getServer() });
        connectOptions.setUserName(config.getClientId());
        connectOptions.setPassword(config.getClientPassword().toCharArray());
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(true);

        mqttClient.setCallback(new Callback(mqttClient, config));
        mqttClient.connect(connectOptions);
    }
}

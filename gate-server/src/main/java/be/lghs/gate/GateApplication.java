package be.lghs.gate;

import org.eclipse.paho.client.mqttv3.MqttException;

public class GateApplication {

    public static void main(String... args) throws MqttException {
        new GateListener().listen();
    }
}

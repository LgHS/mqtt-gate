package be.lghs.gate;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class GateApplication {

    public static void main(String... args) throws IOException, MqttException {
        new GateListener().listen();
    }
}

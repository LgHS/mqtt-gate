package be.lghs.gate;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.function.Consumer;

public class Callback {

    @FunctionalInterface
    public interface MessageArrived {
        void messageArrived(String topic, MqttMessage message) throws Exception;
    }

    public static MqttCallback from(MessageArrived callback, Consumer<Throwable> errorHandler) {
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                errorHandler.accept(cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                callback.messageArrived(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        };
    }
}

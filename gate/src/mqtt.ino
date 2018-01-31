#include "mqtt.h"

#define MQTT_LOG_ENABLED 1

#include <MqttClient.h>


MqttClient *mqtt = NULL;

// ============== Object to supply system functions ================================
class System: public MqttClient::System {
public:
  unsigned long millis() const {
    return ::millis();
  }
};

void mqtt_init() {
  // Setup MqttClient
  MqttClient::System *mqttSystem = new System;
  MqttClient::Logger *mqttLogger = new MqttClient::LoggerImpl<HardwareSerial>(Serial);
  MqttClient::Network * mqttNetwork = new MqttClient::NetworkClientImpl<Client>(client, *mqttSystem);
  // Make 128 bytes send buffer
  MqttClient::Buffer *mqttSendBuffer = new MqttClient::ArrayBuffer<128>();
  // Make 128 bytes receive buffer
  MqttClient::Buffer *mqttRecvBuffer = new MqttClient::ArrayBuffer<128>();
  // Allow up to 2 subscriptions simultaneously
  MqttClient::MessageHandlers *mqttMessageHandlers = new MqttClient::MessageHandlersImpl<2>();
  // Configure client options
  MqttClient::Options mqttOptions;
  // Set command timeout to 10 seconds
  mqttOptions.commandTimeoutMs = 10000;
  // Make client object
  mqtt = new MqttClient (
    mqttOptions, *mqttLogger, *mqttSystem, *mqttNetwork, *mqttSendBuffer,
    *mqttRecvBuffer, *mqttMessageHandlers
  );
}

bool mqtt_is_connected() {
  return mqtt->isConnected();
}

void mqtt_connect() {
  // Close connection if exists
  client.stop();
  // Re-establish TCP connection with MQTT broker
  client.connect(BROKER_URL, BROKER_PORT);
  // Start new MQTT connection
  LOG_PRINTFLN("Connecting");
  MqttClient::ConnectResult connectResult;
  // Connect
  {
    MQTTPacket_connectData options = MQTTPacket_connectData_initializer;
    options.MQTTVersion = 4;
    options.clientID.cstring = CLIENT_ID;
    options.cleansession = true;
    options.keepAliveInterval = 15; // 15 seconds
    MqttClient::Error::type rc = mqtt->connect(options, connectResult);
    if (rc != MqttClient::Error::SUCCESS) {
      LOG_PRINTFLN("Connection error: %i", rc);
      return;
    }
  }
}

void mqtt_yield() {
  mqtt->yield(LOOP_IDLE);
}

void mqtt_send_message(const char* topic, uint8_t* bytes, unsigned length) {
  MqttClient::Message message;
  message.qos = MqttClient::QOS0;
  message.retained = false;
  message.dup = false;
  message.payload = bytes;
  message.payloadLen = length;
  mqtt->publish(topic, message);
}

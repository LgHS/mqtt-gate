/*
 *******************************************************************************
 *
 * Purpose: Example of using the Arduino MqttClient with EthernetClient.
 * Project URL: https://github.com/monstrenyatko/ArduinoMqtt
 *
 *******************************************************************************
 * Copyright Oleg Kovalenko 2017.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

// Enable MqttClient logs
#define MQTT_LOG_ENABLED 1


#include <Arduino.h>
#include <SPI.h>
#include <Ethernet.h>
#include <MFRC522.h>
#include <MqttClient.h>
#include <pb_decode.h>
#include <pb_encode.h>

#include "config.h"

#include "gate.pb.h"
typedef be_lghs_gate_proto_GateOpenRequest GateOpenRequest;



byte mac[] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02
};

EthernetClient client;


#define SS_PIN 53
#define RST_PIN 49

MFRC522 mfrc522(SS_PIN, RST_PIN);  // Create MFRC522 instance


#define LOG_PRINTFLN(fmt, ...)  printfln_P(PSTR(fmt), ##__VA_ARGS__)
#define LOG_SIZE_MAX 128
void printfln_P(const char *fmt, ...) {
  char buf[LOG_SIZE_MAX];
  va_list ap;
  va_start(ap, fmt);
  vsnprintf_P(buf, LOG_SIZE_MAX, fmt, ap);
  va_end(ap);
  Serial.println(buf);
}


MqttClient *mqtt = NULL;

// ============== Object to supply system functions ================================
class System: public MqttClient::System {
public:
  unsigned long millis() const {
    return ::millis();
  }
};

// ============== Setup all objects ============================================
void setup() {

  // Setup hardware serial for logging
  Serial.begin(9600);
  while (!Serial);

  LOG_PRINTFLN("wheee");
  // delay(15000L);

  if (Ethernet.begin(mac) == 0) {
    LOG_PRINTFLN("Failed to configure Ethernet using DHCP");
  } else {
    PrintIpAddress();
  }

  LOG_PRINTFLN("setting up rfid");
  mfrc522.PCD_Init();   // Init MFRC522
  mfrc522.PCD_DumpVersionToSerial();  // Show details of PCD - MFRC522 Card Reader details
  LOG_PRINTFLN("Scan PICC to see UID, SAK, type, and data blocks...");

  LOG_PRINTFLN("setting up mqtt");
  // Setup MqttClient
  MqttClient::System *mqttSystem = new System;
  MqttClient::Logger *mqttLogger = new MqttClient::LoggerImpl<HardwareSerial>(Serial);
  MqttClient::Network * mqttNetwork = new MqttClient::NetworkClientImpl<Client>(client, *mqttSystem);
  //// Make 128 bytes send buffer
  MqttClient::Buffer *mqttSendBuffer = new MqttClient::ArrayBuffer<128>();
  //// Make 128 bytes receive buffer
  MqttClient::Buffer *mqttRecvBuffer = new MqttClient::ArrayBuffer<128>();
  //// Allow up to 2 subscriptions simultaneously
  MqttClient::MessageHandlers *mqttMessageHandlers = new MqttClient::MessageHandlersImpl<2>();
  //// Configure client options
  MqttClient::Options mqttOptions;
  ////// Set command timeout to 10 seconds
  mqttOptions.commandTimeoutMs = 10000;
  //// Make client object
  mqtt = new MqttClient (
    mqttOptions, *mqttLogger, *mqttSystem, *mqttNetwork, *mqttSendBuffer,
    *mqttRecvBuffer, *mqttMessageHandlers
  );
  LOG_PRINTFLN("yay, it works");
}

void PrintIpAddress()
{
  LOG_PRINTFLN("My IP address: %d.%d.%d.%d",
    Ethernet.localIP()[0],
    Ethernet.localIP()[1],
    Ethernet.localIP()[2],
    Ethernet.localIP()[3]);
}

void Reconnect() {
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

void SendMqttMessage(const char* topic, uint8_t* bytes, unsigned length) {
  MqttClient::Message message;
  message.qos = MqttClient::QOS0;
  message.retained = false;
  message.dup = false;
  message.payload = bytes;
  message.payloadLen = length;
  mqtt->publish(topic, message);
}

void SendGateOpenRequest(const GateOpenRequest& request) {
  /* This is the buffer where we will store our message. */
  uint8_t buffer[128];
  size_t message_length;
  bool status;

  /* Encode our message */
  {
    pb_ostream_t stream = pb_ostream_from_buffer(buffer, sizeof(buffer));
    status = pb_encode(&stream, be_lghs_gate_proto_GateOpenRequest_fields, &request);
    message_length = stream.bytes_written;

    /* Then just check for any errors.. */
    if (!status) {
      LOG_PRINTFLN("Encoding failed: %s", PB_GET_ERROR(&stream));
      return;
    }

    SendMqttMessage("lghs/gate/" GATE_NUMBER "/open/request", buffer, message_length);
  }
}

uint64_t GetRfidUid(MFRC522::Uid* uid) {
  if (uid->size > 8) {
    return 0;
  }

  // FIXME That's totally broken, Idk what I'm doing
  uint64_t nuid = 0;
  for (uint8_t i = 0; i < uid->size; ++i) {
    nuid |= (uid->uidByte[i] << (8 * (uid->size - i - 1)));
    // nuid |= (uid->uidByte[i] << (8 * i));
  }
  return nuid;
}

// ============== Main loop ====================================================
void loop() {
  // Check connection status
  if (!mqtt->isConnected()) {
    Reconnect();
    return;

    // Add subscribe here if need
  }

  // Look for new cards
  if (!mfrc522.PICC_IsNewCardPresent()) {
    return;
  }

  // Select one of the cards
  if (!mfrc522.PICC_ReadCardSerial()) {
    return;
  }

  MFRC522::Uid *uid = &mfrc522.uid;
  MFRC522::PICC_Type piccType = MFRC522::PICC_GetType(uid->sak);
  switch (piccType) {
    case MFRC522::PICC_TYPE_MIFARE_MINI:
    case MFRC522::PICC_TYPE_MIFARE_1K:
    case MFRC522::PICC_TYPE_MIFARE_4K:
      break;
    default:
      // nope
      return;
  }

  uint64_t nuid = GetRfidUid(uid);

  LOG_PRINTFLN("nuid: %lu", nuid);
  if (nuid == 0) {
    // @Beep
    return;
  }

//   uint8_t no_of_sectors = 0;
//   switch (piccType) {
//     case PICC_TYPE_MIFARE_MINI:
//       no_of_sectors = 5;
//       break;

//     case PICC_TYPE_MIFARE_1K:
//       no_of_sectors = 16;
//       break;

//     case PICC_TYPE_MIFARE_4K:
//       no_of_sectors = 40;
//       break;

//     default: // Should not happen. Ignore.
//       break;
// }

  // SendGateOpenRequest(GateOpenRequest {
  //   1, // uint64_t cardId;
  //   1, // uint64_t tokenLow;
  //   1, // uint64_t tokenHigh;
  //   0  // pb_callback_t pin;
  // });

  // Idle for 30 seconds
  // mqtt->yield(LOOP_IDLE);
}


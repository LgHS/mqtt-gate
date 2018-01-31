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


bool stuff_is_broken_abort_mission = false;

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

  LOG_PRINTFLN("setting up SPI");
  SPI.begin();

  // LOG_PRINTFLN("setting up Ethernet");
  // if (Ethernet.begin(mac) == 0) {
  //   LOG_PRINTFLN("Failed to configure Ethernet using DHCP");
  //   // @Beep
  //   stuff_is_broken_abort_mission = true;
  //   return;
  // }
  // PrintIpAddress();

  LOG_PRINTFLN("sizeof(unsigned long): %d, sizeof(uint64_t): %d",
    sizeof(unsigned long), sizeof(uint64_t));

  LOG_PRINTFLN("setting up rfid");
  mfrc522.PCD_Init();   // Init MFRC522

  byte v = mfrc522.PCD_ReadRegister(MFRC522::VersionReg);
  if ((v == 0x00) || (v == 0xFF)) {
    LOG_PRINTFLN("RFID is broken, aborting...");
    // @Beep
    stuff_is_broken_abort_mission = true;
    return;
  }
  mfrc522.PCD_DumpVersionToSerial();  // Show details of PCD - MFRC522 Card Reader details

  // LOG_PRINTFLN("setting up mqtt");
  // // Setup MqttClient
  // MqttClient::System *mqttSystem = new System;
  // MqttClient::Logger *mqttLogger = new MqttClient::LoggerImpl<HardwareSerial>(Serial);
  // MqttClient::Network * mqttNetwork = new MqttClient::NetworkClientImpl<Client>(client, *mqttSystem);
  // //// Make 128 bytes send buffer
  // MqttClient::Buffer *mqttSendBuffer = new MqttClient::ArrayBuffer<128>();
  // //// Make 128 bytes receive buffer
  // MqttClient::Buffer *mqttRecvBuffer = new MqttClient::ArrayBuffer<128>();
  // //// Allow up to 2 subscriptions simultaneously
  // MqttClient::MessageHandlers *mqttMessageHandlers = new MqttClient::MessageHandlersImpl<2>();
  // //// Configure client options
  // MqttClient::Options mqttOptions;
  // ////// Set command timeout to 10 seconds
  // mqttOptions.commandTimeoutMs = 10000;
  // //// Make client object
  // mqtt = new MqttClient (
  //   mqttOptions, *mqttLogger, *mqttSystem, *mqttNetwork, *mqttSendBuffer,
  //   *mqttRecvBuffer, *mqttMessageHandlers
  // );
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

uint64_t ByteArrayToUint64(uint8_t* bytes) {
  return *reinterpret_cast<uint64_t*>(bytes);
  // uint64_t res = 0;
  // uint8_t* res_ptr = (uint8_t*) &res;
  // for (size_t i = 0; i < 8; ++i, ++bytes, ++res_ptr) {
  //   *res_ptr = *bytes;
  // }
  // return res;
}

MFRC522::StatusCode ReadBlock(uint8_t block, MFRC522::Uid* uid, uint8_t* buffer) {
  MFRC522::MIFARE_Key key = { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
  uint8_t internal_buffer[16 + 2];
  uint8_t size = 18;
  MFRC522::StatusCode status;

  status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, block, &key, uid);
  if (status != MFRC522::STATUS_OK) {
    return status;
  }

  status = mfrc522.MIFARE_Read(block, internal_buffer, &size);
  if (status != MFRC522::STATUS_OK) {
    return status;
  }

  memcpy(buffer, internal_buffer, 16);
  return status;
}

void CleanupRfid(MFRC522::StatusCode status) {
  if (status != MFRC522::STATUS_OK) {
    Serial.print("RFID error: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
  }
  mfrc522.PCD_StopCrypto1();
}

// ============== Main loop ====================================================
void loop() {
  if (stuff_is_broken_abort_mission) return;

  // Check connection status
  // if (!mqtt->isConnected()) {
  //   Reconnect();
  //   return;

  //   // Add subscribe here if need
  // }

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
      // @Beep
      return;
  }

  // mfrc522.PICC_DumpToSerial(uid);
  // return;

  uint8_t buffer[16];
  MFRC522::StatusCode status = ReadBlock(0, uid, buffer);
  if (status != MFRC522::STATUS_OK) {
    CleanupRfid(status);
    return;
  }

  Serial.print("Data (block = 0): ");
  Serial.print(static_cast<unsigned long>(ByteArrayToUint64(buffer)), HEX);
  Serial.print(" ");
  Serial.print(static_cast<unsigned long>(ByteArrayToUint64(buffer) >> 4), HEX);
  Serial.print("  ");
  Serial.print(static_cast<unsigned long>(ByteArrayToUint64(buffer + 8)), HEX);
  Serial.print(" ");
  Serial.print(static_cast<unsigned long>(ByteArrayToUint64(buffer + 8) >> 4), HEX);
  Serial.println();

  CleanupRfid(status);

  // Remember to call PCD_StopCrypto1() after communicating with the authenticated PICC - otherwise no new communications can start.

  // SendGateOpenRequest(GateOpenRequest {
  //   1, // uint64_t cardId;
  //   1, // uint64_t tokenLow;
  //   1, // uint64_t tokenHigh;
  //   0  // pb_callback_t pin;
  // });

  // Idle for 30 seconds
  // mqtt->yield(LOOP_IDLE);
}


// What should happen
// 459EF0C2 E9080400  62636465 66676869
// What happened
// C2F09E45 E9C2F09E  65646362 66656463
// What happens
// C2F09E45 9C2F09E4  65646362 66564636

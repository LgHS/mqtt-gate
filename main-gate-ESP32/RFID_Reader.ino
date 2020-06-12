#include <SPI.h>
#include <MFRC522.h>
#include <WiFi.h>
#include <PubSubClient.h>

const int pinRST = 5;  // pin RST du module RC522
const int pinSDA = 4; // pin SDA du module RC522
MFRC522 rfid(pinSDA, pinRST);

const char* ssid = "SSID ";
const char* password = "PASSWORD";
const char* mqtt_server = "SERVER IP";
const char* HostName = "ESP32_BADGE";
const char* topic_BADGE = "lghs/porte/badge";
const char* topic_RESP = "lghs/porte/resp";
const char* mqttUser = "MQTTUSER";
const char* mqttPassword = "MQTTPASSWORD";
WiFiClient espClient;
PubSubClient client(espClient);

void callback(String topic, byte* message, unsigned int length) {
  String messageTemp;
  for (int i = 0; i < length; i++) {
    messageTemp += (char)message[i];
  }
Serial.println(messageTemp);
  if(messageTemp == "OK"){
    Serial.println("OPEN");
    digitalWrite(27,LOW);
    delay(200);
    digitalWrite(27,HIGH);
  }
}

void setup_wifi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  WiFi.setHostname(HostName);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    if (client.connect(HostName, mqttUser, mqttPassword)) {
      Serial.println("connected");
      client.subscribe(topic_RESP);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

void setup()
{
  SPI.begin();
  rfid.PCD_Init();
  Serial.begin(115200);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  pinMode(27,OUTPUT);
  digitalWrite(27,HIGH);
}

void loop()
{
  if (rfid.PICC_IsNewCardPresent())  // on a dédecté un tag
  {
    if (rfid.PICC_ReadCardSerial())  // on a lu avec succès son contenu
    {
      client.publish_P(topic_BADGE, rfid.uid.uidByte, rfid.uid.size, false);
      for (byte i = 0; i < rfid.uid.size; i++)
      {
        Serial.print(rfid.uid.uidByte[i]);
        if (i < rfid.uid.size - 1)
        {
          Serial.print(", ");
        }
        else
          Serial.println("};");
      }
      Serial.println();
      delay (2000);
    }
  }
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
}

#include <Arduino.h>
#include <SPI.h>
#include <Ethernet.h>
#include <stdint.h>

#include "config.h"
#include "mqtt.h"
#include "network.h"
#include "rfid.h"
#include "gate_protocol.h"

bool stuff_is_broken_abort_mission = false;

byte mac[] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02
};

EthernetClient client;


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

void setup() {
  Serial.begin(9600);
  while (!Serial);

  LOG_PRINTFLN("setting up SPI");
  SPI.begin();

  LOG_PRINTFLN("setting up Ethernet");
  network_init();

  LOG_PRINTFLN("setting up rfid");
  rfid_init();

  LOG_PRINTFLN("setting up mqtt");
  mqtt_init();

  LOG_PRINTFLN("yay, it works");
}

uint64_t byte_array_to_uint64(uint8_t* bytes) {
  return *reinterpret_cast<uint64_t*>(bytes);
}

void loop() {
  if (stuff_is_broken_abort_mission) return;

  // if (!mqtt_is_connected) {
  //   mqtt_connect();
  //   // Add subscribe here if needed
  // }

  // Look for new cards
  if (!rfid_select_card()) {
    return;
  }

  // FIXME Not checking card type anymore

  uint8_t buffer[16];
  if (!rfid_read_block(0, buffer)) {
    // @Beep
    rfid_unselect_card();
    return;
  }

  Serial.print("Data (block = 0): ");
  Serial.print(static_cast<unsigned long>(byte_array_to_uint64(buffer)), HEX);
  Serial.print(" ");
  Serial.print(static_cast<unsigned long>(byte_array_to_uint64(buffer) >> 4), HEX);
  Serial.print("  ");
  Serial.print(static_cast<unsigned long>(byte_array_to_uint64(buffer + 8)), HEX);
  Serial.print(" ");
  Serial.print(static_cast<unsigned long>(byte_array_to_uint64(buffer + 8) >> 4), HEX);
  Serial.println();

  rfid_unselect_card();

  // proto_send_open_gate_request(1, 2, 3, 0);

  // mqtt_yield();
}

// What should happen
// 459EF0C2 E9080400  62636465 66676869
// What happened
// C2F09E45 E9C2F09E  65646362 66656463
// What happens
// C2F09E45 9C2F09E4  65646362 66564636

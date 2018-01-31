#ifndef MQTT_H
#define MQTT_H

#include <stdint.h>

void mqtt_init();
bool mqtt_is_connected();
void mqtt_connect();
void mqtt_yield();
void mqtt_send_message(const char* topic, uint8_t* bytes, unsigned length);

#endif

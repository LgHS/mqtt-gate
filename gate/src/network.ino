#include "network.h"

void network_init() {
  if (Ethernet.begin(mac) == 0) {
    LOG_PRINTFLN("Failed to configure Ethernet using DHCP");
    // @Beep
    stuff_is_broken_abort_mission = true;
    return;
  }
}


void network_dump_ip() {
  LOG_PRINTFLN("IP address is: %d.%d.%d.%d",
    Ethernet.localIP()[0],
    Ethernet.localIP()[1],
    Ethernet.localIP()[2],
    Ethernet.localIP()[3]);
}

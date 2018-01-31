#ifndef RFID_H
#define RFID_H

#include <stdint.h>

// mfrc522.PCD_DumpVersionToSerial();  // Show details of PCD - MFRC522 Card Reader details

void rfid_init();
bool rfid_select_card();
void rfid_unselect_card();
bool rfid_read_block(uint8_t block, uint8_t* buffer);

#endif

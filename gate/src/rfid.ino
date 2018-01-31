#include "rfid.h"

#include <MFRC522.h>

#include "config.h"

MFRC522 mfrc522(RFID_SS_PIN, RFID_RST_PIN);  // Create MFRC522 instance
MFRC522::Uid *uid;

void rfid_init() {
  mfrc522.PCD_Init();
  byte v = mfrc522.PCD_ReadRegister(MFRC522::VersionReg);
  if ((v == 0x00) || (v == 0xFF)) {
    LOG_PRINTFLN("RFID is broken, aborting...");
    // @Beep
    stuff_is_broken_abort_mission = true;
    return;
  }
}

bool rfid_select_card() {
  if (!mfrc522.PICC_IsNewCardPresent() || !mfrc522.PICC_ReadCardSerial()) {
    return false;
  }

  uid = &mfrc522.uid;
  return true;
}

void rfid_unselect_card() {
  // if (status != MFRC522::STATUS_OK) {
  //   Serial.print("RFID error: ");
  //   Serial.println(mfrc522.GetStatusCodeName(status));
  // }
  mfrc522.PCD_StopCrypto1();
  uid = NULL;
}

bool rfid_read_block(uint8_t block, uint8_t* buffer) {
  MFRC522::MIFARE_Key key = { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
  uint8_t internal_buffer[16 + 2];
  uint8_t size = 18;
  MFRC522::StatusCode status;

  status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, block, &key, uid);
  if (status != MFRC522::STATUS_OK) {
    // return status;
    return false;
  }

  status = mfrc522.MIFARE_Read(block, internal_buffer, &size);
  if (status != MFRC522::STATUS_OK) {
    // return status;
    return false;
  }

  memcpy(buffer, internal_buffer, 16);
  return status == MFRC522::STATUS_OK;
}



  // MFRC522::PICC_Type piccType = MFRC522::PICC_GetType(uid->sak);
  // switch (piccType) {
  //   case MFRC522::PICC_TYPE_MIFARE_MINI:
  //   case MFRC522::PICC_TYPE_MIFARE_1K:
  //   case MFRC522::PICC_TYPE_MIFARE_4K:
  //     break;
  //   default:
  //     // @Beep
  //     return;
  // }

  // mfrc522.PICC_DumpToSerial(uid);
  // return;

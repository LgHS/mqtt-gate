import MFRC522
import time

class RfidReader:
    DEFAULT_KEY = [0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF]

    def __init__(self):
        self.mifare_reader = MFRC522.MFRC522()
        self.current_card_uid = None
        self.enabled = True
        self.last_session_end = 0

    def wait_for_card(self):
        now = time.time()
        while self.enabled and now - self.last_session_end < 2:
            # print('waiting for session timeout')
            time.sleep(0.5)
            now = time.time()

        while self.enabled:
            (status, tag_type) = self.mifare_reader.MFRC522_Request(self.mifare_reader.PICC_REQIDL)
            if status != self.mifare_reader.MI_OK:
                # print('MFRC522_Request#status not ok: %s' % status)
                time.sleep(0.1)
                continue

            (status, self.current_card_uid) = self.mifare_reader.MFRC522_Anticoll()
            if status != self.mifare_reader.MI_OK:
                print('MFRC522_Anticoll#status not ok')
                continue

            if not self.current_card_uid:
                print('got not uid')
                continue

            # Select the scanned tag
            bleh = self.mifare_reader.MFRC522_SelectTag(self.current_card_uid)
            return True

        return False

    def read_sector(self, sector, key, uid):
        status = self.mifare_reader.MFRC522_Auth(self.mifare_reader.PICC_AUTHENT1A, sector, key, uid)
        if status != self.mifare_reader.MI_OK:
            return None

        return self.mifare_reader.MFRC522_Read(sector)

    def read_sector(self, sector, key = None):
        if not key:
            key = self.DEFAULT_KEY

        status = self.mifare_reader.MFRC522_Auth(
            self.mifare_reader.PICC_AUTHENT1A,
            sector,
            key,
            self.current_card_uid)

        if status != self.mifare_reader.MI_OK:
            return (status, None, None)

        return self.mifare_reader.MFRC522_Read(sector)

    def release_card(self):
        self.mifare_reader.MFRC522_StopCrypto1()
        self.current_card_uid = None
        self.last_session_end = time.time()

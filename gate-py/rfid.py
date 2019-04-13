import MFRC522
import time


class ShutdownRequest(Exception):
    pass


class RfidReader:
    def __init__(self, spi_device, gpio_pin):
        self.mifare_reader = MFRC522.MFRC522(device=spi_device, gpio_pin=gpio_pin)
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

            (status, current_card_uid) = self.mifare_reader.MFRC522_Anticoll()
            if status != self.mifare_reader.MI_OK:
                print('MFRC522_Anticoll#status not ok')
                continue

            if not current_card_uid:
                print('got not uid')
                continue

            # Select the scanned tag
            bleh = self.mifare_reader.MFRC522_SelectTag(current_card_uid)
            return RfidCard(self, current_card_uid)

        raise ShutdownRequest()

class RfidCard:
    DEFAULT_KEY = [0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF]

    def __init__(self, reader, uid):
        self.reader = reader
        self.mifare_reader = reader.mifare_reader
        self.uid = uid

    def __enter__(self):
        return self

    def __exit__(self, exception_type, exception_val, trace):
        self.mifare_reader.MFRC522_StopCrypto1()
        self.reader.last_session_end = time.time()

    def auth_sector(self, sector, key = None):
        if not key:
            key = self.DEFAULT_KEY

        status = self.mifare_reader.MFRC522_Auth(
            self.mifare_reader.PICC_AUTHENT1A,
            sector,
            key,
            self.uid)

        return status == self.mifare_reader.MI_OK

    def read_sector(self, sector):
        (status, data, _) = self.mifare_reader.MFRC522_Read(sector)
        if status != self.mifare_reader.MI_OK:
            return None

        return data

    def write_sector(self, sector, data):
        return self.mifare_reader.MFRC522_Write(sector, data)

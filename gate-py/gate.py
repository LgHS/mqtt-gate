#!/usr/bin/env python
# -*- coding: utf8 -*-

import time
import OPi.GPIO as GPIO
import MFRC522
import signal
import json

import uids

GPIO.setmode(GPIO.BOARD)
GPIO.setup(3, GPIO.OUT)
GPIO.output(3, GPIO.HIGH)

continue_reading = True


def end_read(signal, frame):
    global continue_reading
    continue_reading = False


def read_sector(mifare_reader, sector, key, uid):
    status = mifare_reader.MFRC522_Auth(mifare_reader.PICC_AUTHENT1A, sector, key, uid)
    if status != mifare_reader.MI_OK:
        return None

    return mifare_reader.MFRC522_Read(sector)


def main():
    global continue_reading

    default_key = [0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF]

    mifare_reader = MFRC522.MFRC522()

    last_card = None
    last_rfid_read = 0
    last_gpio_high = 0remo
    gpio_is_high = False

    # This loop keeps checking for chips. If one is near it will get the UID and authenticate
    while continue_reading:
        now = time.time()

        if now - last_gpio_high > 3 and gpio_is_high:
            last_gpio_high = now
            gpio_is_high = False
            GPIO.output(3, GPIO.HIGH)

        (status, tag_type) = mifare_reader.MFRC522_Request(mifare_reader.PICC_REQIDL)

        if status != mifare_reader.MI_OK:
            continue

        (status, uid) = mifare_reader.MFRC522_Anticoll()

        if not uid:
            continue

        if now - last_rfid_read < 1:
            last_rfid_read = now
            continue

        # If we have the UID, continue
        if status != mifare_reader.MI_OK:
            continue

        # Select the scanned tag
        mifare_reader.MFRC522_SelectTag(uid)

        (status, data, _) = read_sector(mifare_reader, 1, default_key, uid)

        if " ".join(str(e) for e in uid) in uids.uids:
            GPIO.output(3, GPIO.LOW)
            gpio_is_high = True
            last_card = uid
            last_rfid_read = now
            last_gpio_high = now
        mifare_reader.MFRC522_StopCrypto1()


if __name__ == '__main__':
    signal.signal(signal.SIGINT, end_read)
    signal.signal(signal.SIGTERM, end_read)
    signal.signal(signal.SIGQUIT, end_read)

    while continue_reading:
        try:
            main()
        finally:
            GPIO.cleanup()

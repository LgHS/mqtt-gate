#!/usr/bin/env python
# -*- coding: utf8 -*-

# TODO
# * mqtt
# * protobuf
# * write mocks to be able to test without hardware
# * thread the client to open the door on message

import signal
import time
import uuid
import logging

import gpio
import rfid
import server
import config

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def read_card(card, door, mqtt):
    print('reading card')
    if not card.auth_sector(1):
        print('couldn\'t auth to sector 1')
        return

    uid_bytes = bytes(card.uid)
    data = card.read_sector(1)
    # the_guy_token = the_guy['token']
    # if the_guy_token is None:
    #     if data != [0]*16:
    #         print('no token but data on card, skipping. %s' % data)
    #         return

    #     print('generating new token')
    #     new_token = uuid.uuid4()

    #     print('writing to card: %s' % new_token.bytes)
    #     status = card.write_sector(1, new_token.bytes)
    #     print('status: %d' % status)
    #     the_guy['token'] = new_token.bytes

    # elif the_guy_token != bytes(data):
    #     print('invalid token: %s' % data)
    #     return

    mqtt.door_open_request(uid_bytes, uuid.UUID(bytes=bytes(data)))


if __name__ == '__main__':
    reader = rfid.RfidReader(
        config.rfid['spi-device'])

    def shutdown(signal, frame):
        reader.enabled = False

    signal.signal(signal.SIGINT, shutdown)
    signal.signal(signal.SIGTERM, shutdown)
    signal.signal(signal.SIGQUIT, shutdown)
    signal.signal(signal.SIGPIPE, shutdown)

    def open_door(payload):
        print(payload)
        if payload.ok:
            door.unlock()
            time.sleep(2)
            door.lock()

    while reader.enabled:
        try:
            with gpio.Door(door_gpio_pin=config.door['gpio-pin'],
                           led_gpio_pin=config.led['bcm-pin'],
                           led_count=config.led['led-count']) as door, \
                 server.MqttClient(url=config.server['url'],
                                   port=config.server['port'],
                                   password=config.server['password'],
                                   client_id=config.server['client-id'],
                                   gate_id=config.server['gate-id'],
                                   tls=config.server['tls']) as mqtt:
                mqtt.set_open_callback(open_door)

                while reader.enabled:
                    with reader.wait_for_card() as card:
                        if not card:
                            break

                        read_card(card, door, mqtt)
        except rfid.ShutdownRequest:
            pass
        except Exception as e:
            logger.exception(e)

    print('shut down')

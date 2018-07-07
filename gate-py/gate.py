#!/usr/bin/env python
# -*- coding: utf8 -*-

# TODO
# * mqtt
# * protobuf
# * write mocks to be able to test without hardware
# * thread the client to open the door on message

import json
import signal
import time
import uuid

import gpio
import rfid
import uids

def read_card(card, door):
    if not card.auth_sector(1):
        print('couldn\'t auth to sector 1')
        return

    uid_bytes = bytes(card.uid)
    the_guy = uids.authorized.get(uid_bytes)
    if not the_guy:
        print('unknown uid: %s' % card.uid)
        return

    data = card.read_sector(1)
    the_guy_token = the_guy['token']
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

    door.unlock()
    print('opening door for %s' % the_guy['name'])

    time.sleep(2)
    door.lock()


if __name__ == '__main__':
    reader = rfid.RfidReader()

    def shutdown(signal, frame):
        reader.enabled = False

    signal.signal(signal.SIGINT, shutdown)
    signal.signal(signal.SIGTERM, shutdown)
    signal.signal(signal.SIGQUIT, shutdown)
    signal.signal(signal.SIGPIPE, shutdown)

    while reader.enabled:
        try:
            with gpio.Door() as door:
                while reader.enabled:
                    with reader.wait_for_card() as card:
                        if not card:
                            break

                        read_card(card, door)
        except Exception as e:
            print(e)

    print('shut down')

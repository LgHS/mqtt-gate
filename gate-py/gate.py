#!/usr/bin/env python
# -*- coding: utf8 -*-

# TODO
# * mqtt
# * protobuf
# * write mocks to be able to test without hardware
# * thread the client to open the door on message

import time
import signal
import json

import gpio
import rfid
import uids

reader = rfid.RfidReader()

def shutdown(signal, frame):
    reader.enabled = False

def main(door):
    while reader.enabled:
        if not reader.wait_for_card():
            print('shutting down')
            return

        (status, data, _) = reader.read_sector(1)

        formatted = " ".join(str(e) for e in reader.current_card_uid)
        the_guy = uids.authorized.get(formatted)
        if not the_guy:
            print('unknown uid: %s' % formatted)
            reader.release_card()
            continue

        door.unlock()
        print('opening door for %s' % the_guy)

        reader.release_card()

        time.sleep(1)
        door.lock()



if __name__ == '__main__':
    signal.signal(signal.SIGINT, shutdown)
    signal.signal(signal.SIGTERM, shutdown)
    signal.signal(signal.SIGQUIT, shutdown)
    signal.signal(signal.SIGPIPE, shutdown)

    while reader.enabled:
        with gpio.Door() as door:
            main(door)

    print('shut down')

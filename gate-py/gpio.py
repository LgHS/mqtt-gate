import time
import OPi.GPIO as GPIO

class Door:
    THE_DOOR_PIN = 3

    def __init__(self):
        self._is_door_locked = False
        self._last_door_unlock = 0

    def __enter__(self):
        print('setup')
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(self.THE_DOOR_PIN, GPIO.OUT)
        GPIO.output(self.THE_DOOR_PIN, GPIO.HIGH)
        return self

    def __exit__(self, exception_type, exception_val, trace):
        print('cleanup')
        if not self._is_door_locked:
            self.lock()

        GPIO.cleanup()

    def unlock(self):
        GPIO.output(self.THE_DOOR_PIN, GPIO.LOW)
        self._is_door_locked = False
        self._last_door_unlock = time.time()

    def lock(self):
        GPIO.output(self.THE_DOOR_PIN, GPIO.HIGH)
        self._is_door_locked = True

    def is_locked(self):
        # TODO just read the GPIO state, no need to store state
        return self._is_door_locked

    def get_last_unlock(self):
        return self._last_door_unlock

    def is_open(self):
        # TODO Solder the cable and wire the GPIO
        return False

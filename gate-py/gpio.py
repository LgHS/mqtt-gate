import time
import RPi.GPIO as GPIO
# import neopixel
# import board


class Door:

    def __init__(self, door_gpio_pin, led_gpio_pin, led_count):
        self._door_gpio_pin = door_gpio_pin
        self._led_bcm_pin = led_gpio_pin
        self._led_count = led_count
        self._is_door_locked = False
        self._last_door_unlock = 0

    def __enter__(self):
        GPIO.setmode(GPIO.BOARD)
        # self._pixels = neopixel.NeoPixel(
        #     getattr(board, 'D{}'.format(self._led_bcm_pin)),
        #     self._led_count)

        GPIO.setup(self._door_gpio_pin, GPIO.OUT)
        GPIO.output(self._door_gpio_pin, GPIO.LOW)
        return self

    def __exit__(self, exception_type, exception_val, trace):
        if not self._is_door_locked:
            self.lock()

        GPIO.cleanup()

    def unlock(self):
        print('unlocking door')
        GPIO.output(self._door_gpio_pin, GPIO.HIGH)
        self._is_door_locked = False
        self._last_door_unlock = time.time()

        # self._pixels.fill([0, 255, 0])

    def lock(self):
        print('locking door')
        GPIO.output(self._door_gpio_pin, GPIO.LOW)
        self._is_door_locked = True

        # self._pixels.fill([255, 0, 0])

    def is_locked(self):
        # TODO just read the GPIO state, no need to store state
        return self._is_door_locked

    def get_last_unlock(self):
        return self._last_door_unlock

    def is_open(self):
        # TODO Solder the cable and wire the GPIO
        return False

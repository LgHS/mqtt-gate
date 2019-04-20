import paho.mqtt.client as mqtt

import gate_pb2 as proto


class MqttClient:

    def __init__(self, url, port, client_id, password, gate_id, tls=True):
        self.client = mqtt.Client(client_id=client_id)
        self.gate_id = gate_id

        self.client.username_pw_set(client_id, password)
        if tls:
            self.client.tls_set()
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.client.connect(url, port, 60)
        self.open_callback = None

    def __enter__(self):
        self.client.loop_start()
        return self

    def __exit__(self, exception_type, exception_val, trace):
        self.client.loop_stop()

    def set_open_callback(self, callback):
        self.open_callback = callback

    def on_connect(self, client, userdata, flags, rc):
        print('connected to server')
        self.client.subscribe("lghs/gate/{0}/open/response".format(self.gate_id))

    def on_message(self, client, userdata, message):
        if self.open_callback:
            response = proto.GateOpenResponse()
            response.ParseFromString(message.payload)
            self.open_callback(response)

    def door_open_request(self, card_id, token):
        request = proto.GateOpenRequest()
        request.cardId = int.from_bytes(card_id, byteorder='big')
        request.tokenLow = int.from_bytes(token.bytes[:8], byteorder='big')
        request.tokenHigh = int.from_bytes(token.bytes[9:], byteorder='big')
        request.pin = bytes()  # doesn't even have a pin pad...

        data = request.SerializeToString()
        self.client.publish("lghs/gate/{0}/open/request".format(self.gate_id), data)

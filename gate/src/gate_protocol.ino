#include "gate_protocol.h"

#include <pb_decode.h>
#include <pb_encode.h>

#include "gate.pb.h"
typedef be_lghs_gate_proto_GateOpenRequest GateOpenRequest;

#include "config.h"
#include "mqtt.h"

void proto_send_open_gate_request(uint64_t card_id, uint64_t token_low, uint64_t token_high, uint8_t* pin, uint8_t pin_length) {
 GateOpenRequest request = {
    card_id,
    token_low,
    token_high,
    // FIXME
    0  // pb_callback_t pin;
  };

  /* This is the buffer where we will store our message. */
  uint8_t buffer[128];
  size_t message_length;
  bool status;

  /* Encode our message */
  {
    pb_ostream_t stream = pb_ostream_from_buffer(buffer, sizeof(buffer));
    status = pb_encode(&stream, be_lghs_gate_proto_GateOpenRequest_fields, &request);
    message_length = stream.bytes_written;

    /* Then just check for any errors.. */
    if (!status) {
      LOG_PRINTFLN("Encoding failed: %s", PB_GET_ERROR(&stream));
      return;
    }

    mqtt_send_message("lghs/gate/" GATE_NUMBER "/open/request", buffer, message_length);
  }
}

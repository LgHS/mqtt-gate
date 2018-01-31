#ifndef GATE_PROTOCOL_H
#define GATE_PROTOCOL_H

#include <stdint.h>

void proto_send_open_gate_request(
    uint64_t card_id,
    uint64_t token_low,
    uint64_t token_high,
    uint8_t* pin,
    uint8_t pin_length);

#endif

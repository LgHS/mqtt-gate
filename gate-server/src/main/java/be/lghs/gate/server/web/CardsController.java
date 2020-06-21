package be.lghs.gate.server.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardsController {

    @GetMapping({"/new", "/{id:" + PathRegexes.UUID + "}"})
    public String cardForm(@PathVariable(value = "id", required = false) UUID id) {
        return "cards/form";
    }
}

package be.lghs.gate.server.web;

import be.lghs.gate.server.configuration.Roles;
import be.lghs.gate.server.repositories.DashboardRepository;
import be.lghs.gate.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    @Secured(Roles.ROLE_MEMBER)
    @GetMapping
    public String index(Model model) {
        var currentUser = UserService.getCurrentUser()
            .orElseThrow();
        var cards = dashboardRepository.getUserCards(currentUser.getId());

        model
            .addAttribute("username", currentUser.getName())
            .addAttribute("cards", cards);

        return "index";
    }
}

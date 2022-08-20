package teamproject.lam_server.domain.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.interaction.service.InteractionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interactions")
public class InteractionApiController {

    private final InteractionService interactionService;
}

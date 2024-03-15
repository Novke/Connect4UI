package com.novica.Connect4Service.rest;

import com.novica.Connect4Service.service.GameLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UIController {

    private final GameLogic gameLogic;

    @GetMapping("/start")
    public ResponseEntity<?> startGame() {
        return ResponseEntity.ok(gameLogic.startGame());
    }

    @PostMapping("/play")
    public ResponseEntity<?> playMove(@RequestParam int move) {
        return ResponseEntity.ok(gameLogic.playMove(move));
    }

}

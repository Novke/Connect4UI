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

    @GetMapping("/board")
    public ResponseEntity<?> getBoard() {
        return ResponseEntity.ok(gameLogic.getBoard());
    }

    @PutMapping("/reset")
    public ResponseEntity<?> resetGame() {
        gameLogic.resetBoard();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/play")
    public ResponseEntity<?> playMove(@RequestParam int move) {
        return ResponseEntity.ok(gameLogic.playMove(move));
    }

    @PostMapping("/playvsai/level/{depth}")
    public ResponseEntity<?> autoplayMove(@RequestParam int move, @PathVariable int depth) {
        return ResponseEntity.ok(gameLogic.autoplayMove(move, depth));
    }
}

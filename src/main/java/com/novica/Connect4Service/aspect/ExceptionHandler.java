package com.novica.Connect4Service.aspect;

import com.novica.Connect4Service.service.GameLogic;
import com.novica.Connect4Service.util.CljUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandler {

    private final GameLogic gameLogic;
    private final CljUtil cljUtil;
    @org.springframework.web.bind.annotation.ExceptionHandler(com.novica.Connect4Service.exception.Alert.class)
    public org.springframework.http.ResponseEntity<?> handleAlert(com.novica.Connect4Service.exception.Alert e) {
        return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(com.novica.Connect4Service.event.GameEnded.class)
    public org.springframework.http.ResponseEntity<?> handleGameEnded(com.novica.Connect4Service.event.GameEnded e) {
        StringBuilder builder = new StringBuilder();

        long status = (Long) gameLogic.getStatus().deref();
        builder.append("===========================\n");
        if (status == 100){
            builder.append("\n").append("=== Igrac 1 je pobedio! === ").append("\n");
            gameLogic.player1win();
        } else if (status == 200){
            builder.append("\n").append("=== Igrac 2 je pobedio! ===").append("\n");
            gameLogic.player2win();
        } else if (status == 300){
            builder.append("\n").append("Nereseno!").append("\n");
        }
        builder.append("===========================\n");

        builder.append("\n").append("REZULTAT: ").append("\n")
                .append("Igrac 1: ").append(gameLogic.getIgrac1pobede()).append("\n")
                .append("Igrac 2: ").append(gameLogic.getIgrac2pobede()).append("\n");

        builder.append(cljUtil.boardToString(gameLogic.getBoard())).append("\n");

        gameLogic.resetBoard();

        return org.springframework.http.ResponseEntity.ok(builder.toString());
    }
}

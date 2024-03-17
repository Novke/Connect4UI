package com.novica.Connect4Service.aspect;

import com.novica.Connect4Service.event.BotFailed;
import com.novica.Connect4Service.event.BotPlayed;
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

    @org.springframework.web.bind.annotation.ExceptionHandler(BotPlayed.class)
    public org.springframework.http.ResponseEntity<?> handleBotPlayed(BotPlayed e) {

        StringBuilder builder = new StringBuilder();

        Long status = (Long) gameLogic.getStatus().deref();

        if (status < 10){

            builder.append("Bot je odigrao ").append(status).append(". kolonu").append("\n");

            builder.append(cljUtil.boardToString(gameLogic.getBoard())).append("\n");
            builder.append("Igrac 1 je na potezu").append("\n");

        } else {
            //status 100 - igrac 1 pobedio
            //status 200 - igrac 2 pobedio
            //status 205 - igrac 2 pobedio s potezom 5
            //status 300 - nereseno
            long potez = status % 10;

            if (potez > 0 && status < 300 && status > 200){
                builder.append("Bot").append(" je odigrao kolonu ").append(potez).append(".\n");
            }

            builder.append("===========================\n");
            if (status == 100){
                builder.append("\n").append("=== Igrac 1 je pobedio! === ").append("\n");
                gameLogic.player1win();
            } else if (status >= 200 && status < 300){
                builder.append("\n").append("===    Bot je pobedio!  ===").append("\n");
                gameLogic.player2win();
            } else if (status == 300){
                builder.append("\n").append("Nereseno!").append("\n");
            }
            builder.append("===========================\n");

            builder.append("\n").append("REZULTAT: ").append("\n")
                    .append("Igrac 1: ").append(gameLogic.getIgrac1pobede()).append("\n")
                    .append("Bot    : ").append(gameLogic.getIgrac2pobede()).append("\n");

            builder.append(cljUtil.boardToString(gameLogic.getBoard())).append("\n");

            gameLogic.resetBoard();
        }

        return org.springframework.http.ResponseEntity.ok(builder.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BotFailed.class)
    public org.springframework.http.ResponseEntity<?> handleBotFailed(BotFailed e) {
        return org.springframework.http.ResponseEntity.internalServerError().body("Bot se zbunio!\n\n" + cljUtil.boardToString(gameLogic.getBoard()));
    }

}

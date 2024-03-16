package com.novica.Connect4Service.aspect;

import clojure.lang.Atom;
import com.novica.Connect4Service.service.GameLogic;
import com.novica.Connect4Service.util.CljUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ResponseHandler {

    private final GameLogic gameLogic;
    private final CljUtil cljUtil;

    @Around("execution(* com.novica.Connect4Service.rest.UIController.*(..))")
    public Object handleResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();

        Atom board = gameLogic.getBoard();
        Atom igrac = gameLogic.getIgrac();
        Atom status = gameLogic.getStatus();

        StringBuilder builder = new StringBuilder();
        builder
                .append(cljUtil.boardToString(board))
                .append("\n").append("Igrac na potezu: ").append(igrac.deref()).append("\n");

        return ResponseEntity.ok(builder.toString());
    }
}

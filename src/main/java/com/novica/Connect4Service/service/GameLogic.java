package com.novica.Connect4Service.service;

import clojure.lang.Atom;
import com.novica.Connect4Service.util.CljUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameLogic {

    private final CljUtil cljUtil;

    private static Atom board;
    private static Atom igrac;

    public Object startGame() {
        igrac = (Atom) cljUtil.atomize.invoke(1);
        board = (Atom) cljUtil.atomize.invoke(cljUtil.initBoard.invoke());
        return board.deref();
    }

    public Object playMove(int move) {
        try {
            cljUtil.play.invoke(board, move, igrac);
            return board.deref();
        } catch (NullPointerException ex){
            return "Game not started";
        }
    }
}

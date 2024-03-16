package com.novica.Connect4Service.service;

import clojure.lang.Atom;
import com.novica.Connect4Service.exception.GameNotStartedAlert;
import com.novica.Connect4Service.util.CljUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameLogic {

    private final CljUtil cljUtil;

    @Getter
    private Atom board;
    @Getter
    private Atom igrac;
    @Getter
    private int igrac1pobede = 0;
    @Getter
    private int igrac2pobede = 0;

    public Object startGame() {
        igrac = (Atom) cljUtil.atomize(1);
        board = (Atom) cljUtil.atomize(cljUtil.initBoard());
        return board.deref();
    }

    public Object playMove(int move) {
        try {
            cljUtil.play(board, move, igrac);
            return board.deref();
        } catch (NullPointerException ex){
            throw new GameNotStartedAlert();
        }
    }

    public Object autoplayMove(int move, int depth) {
        try {
            cljUtil.autoplay(board, move, depth, igrac);
            return board.deref();
        } catch (NullPointerException ex){
            throw new GameNotStartedAlert();
        }
    }

}

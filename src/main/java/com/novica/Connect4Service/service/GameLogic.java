package com.novica.Connect4Service.service;

import clojure.lang.Atom;
import com.novica.Connect4Service.event.BotFailed;
import com.novica.Connect4Service.event.BotPlayed;
import com.novica.Connect4Service.event.GameEnded;
import com.novica.Connect4Service.exception.Alert;
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
    private Atom status;
    @Getter
    private int igrac1pobede = 0;
    @Getter
    private int igrac2pobede = 0;

    public Object startGame() {
        igrac = (Atom) cljUtil.atomize(1);
        board = (Atom) cljUtil.atomize(cljUtil.initBoard());
        status = (Atom) cljUtil.atomize(-1);
        return board.deref();
    }

    public void resetBoard() {
        try {
            cljUtil.resetBoard(board);
        } catch (NullPointerException ex){
            throw new GameNotStartedAlert();
        }
        status = (Atom) cljUtil.atomize(-1);
    }

    public Object playMove(int move) {
        try {
            cljUtil.play(board, move, igrac, status);
            if (((Long)status.deref())>9) throw new GameEnded();
            return board.deref();
        } catch (NullPointerException ex){
            throw new GameNotStartedAlert();
        } catch (GameEnded ex) {
            throw ex;
        } catch (Exception ex) {
            throw new Alert(ex.getMessage());
        }
    }

    public Object autoplayMove(int move, int depth) {
        try {
            cljUtil.autoplay(board, move, depth, igrac, status);
            throw new BotPlayed();
        } catch (NullPointerException ex){
            throw new GameNotStartedAlert();
        } catch (Exception ex){
            throw new BotFailed();
        }
    }

    public void player1win(){
        igrac1pobede++;
    }

    public void player2win(){
        igrac2pobede++;
    }

}

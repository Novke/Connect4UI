package com.novica.Connect4Service.util;

import clojure.lang.Atom;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import com.novica.Connect4Service.exception.GameNotStartedAlert;
import org.springframework.stereotype.Component;

@Component
public class CljUtil {

    public final IFn require;
    public final IFn initBoard;
    public final IFn resetBoard;
    public final IFn atomize;
    public final IFn play;
    public final IFn autoplay;

    public CljUtil() {
        require = clojure.java.api.Clojure.var("clojure.core", "require");
        require.invoke(clojure.java.api.Clojure.read("board.board"));
        require.invoke(clojure.java.api.Clojure.read("bot.bot"));

        initBoard = clojure.java.api.Clojure.var("board.board", "init-board");
        resetBoard = clojure.java.api.Clojure.var("board.board", "reset-board!");
        atomize = clojure.java.api.Clojure.var("clojure.core", "atom");

//        play = clojure.java.api.Clojure.var("board.board", "play!");
        play = clojure.java.api.Clojure.var("bot.bot", "responsive-play!");
//        autoplay = clojure.java.api.Clojure.var("bot.bot", "autoplay!");
        autoplay = clojure.java.api.Clojure.var("bot.bot", "responsive-autoplay!");
    }

    public Object initBoard() {
        return initBoard.invoke();
    }
    public void resetBoard(Object board){
        resetBoard.invoke(board);
    }
    public Object atomize(Object arg){
        return atomize.invoke(arg);
    }
//    public Object play(Object board, int move, Object igrac){
//        return play.invoke(board, move, igrac);
//    }
    public Object play(Object board, int move, Object igrac, Object status){
        return play.invoke(board, move, igrac, status);
    }
//    public Object autoplay(Object board, int move, int depth, Object igrac){
//        return autoplay.invoke(board, move, depth, igrac);
//    }
    public Object autoplay(Object board, int move, int depth, Object igrac, Object status){
        return autoplay.invoke(board, move, depth, igrac, status);
    }
    public String boardToString(Atom board){

        try {
            StringBuilder builder = new StringBuilder();

            //dodaje indekse kolona
            builder.append("  ");
            for (int i = 1; i < 8; i++) builder.append(i).append(" ");
            builder.append("\n");

            //dodaje red prazne linije
            for (int i = 0; i < 17; i++) builder.append("-");
            builder.append("\n");

            for (int i = 5; i >= 0; i--) {
                builder.append("|")
                        .append(" ");
                for (int j = 0; j < 7; j++) {
                    //red  //kolona
                    builder.append(((PersistentVector) ((PersistentVector) board.deref()).get(i)).get(j))
                            .append(" ");
                }
                builder.append("|")
                        .append("\n");
            }

            //dodaje red prazne linije
            builder.append("-".repeat(17));

            return builder.toString();
        } catch (NullPointerException ex){
            throw new GameNotStartedAlert();
        }
    }

}

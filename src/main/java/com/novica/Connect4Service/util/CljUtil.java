package com.novica.Connect4Service.util;

import clojure.lang.IFn;
import org.springframework.stereotype.Component;

@Component
public class CljUtil {

    public final IFn require;
    public final IFn initBoard;
    public final IFn atomize;
    public final IFn play;

    public CljUtil() {
        require = clojure.java.api.Clojure.var("clojure.core", "require");
        require.invoke(clojure.java.api.Clojure.read("board.board"));
        require.invoke(clojure.java.api.Clojure.read("bot.bot"));

        initBoard = clojure.java.api.Clojure.var("board.board", "init-board");
        atomize = clojure.java.api.Clojure.var("clojure.core", "atom");

        play = clojure.java.api.Clojure.var("board.board", "play!");
    }

}

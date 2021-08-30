package com.leoconon.atividadejogodavelha;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class Player implements Serializable {

    private final String name;
    private final String playerChar;
    private final transient TextView turnIndicator;
    private final transient TextView nameIndicator;
    private final transient TextView scoreIndicator;
    private boolean myTurn = false;
    private int score = 0;

    public Player(String name, String playerChar, TextView turnIndicator, TextView nameIndicator, TextView scoreIndicator) {
        this.name = name;
        this.playerChar = playerChar;
        this.turnIndicator = turnIndicator;
        this.nameIndicator = nameIndicator;
        this.scoreIndicator = scoreIndicator;
        initialize();
    }

    private void initialize() {
        nameIndicator.setText(playerChar + " " + name);
        turnIndicator.setVisibility(View.INVISIBLE);
    }

    public String getPlayerChar() {
        return playerChar;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void turnStarted() {
        myTurn = true;
        turnIndicator.setVisibility(View.VISIBLE);
        nameIndicator.setTypeface(null, Typeface.BOLD);
    }

    public void turnOver() {
        myTurn = false;
        turnIndicator.setVisibility(View.INVISIBLE);
        nameIndicator.setTypeface(null, Typeface.NORMAL);
    }

    public void wonTurn() {
        score++;
        scoreIndicator.setText(String.valueOf(score));
    }

}

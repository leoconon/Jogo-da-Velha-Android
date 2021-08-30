package com.leoconon.atividadejogodavelha;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

public class GameActivity extends AppCompatActivity {

    private static final String UNDEFINED_CHAR_CARD = "?";
    private static final String PLAYER_1_CHAR_CARD = "X";
    private static final String PLAYER_2_CHAR_CARD = "O";
    private static final int DEFAULT_CARD_COLOR = Color.LTGRAY;
    private static final int SELECTED_CARD_COLOR = Color.BLUE;
    private static final int WIN_CARD_COLOR = Color.GREEN;

    private int turnCount = 0;
    private Player player1;
    private Player player2;
    private Player playing;
    private Button[][] buttonCardArray = new Button[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        loadButtonCards();

        player1 = new Player(
                getIntent().getStringExtra("NAME_1"),
                PLAYER_1_CHAR_CARD,
                findViewById(R.id.player1TurnIndicator),
                findViewById(R.id.player1Name),
                findViewById(R.id.player1Score)
        );

        player2 = new Player(
                getIntent().getStringExtra("NAME_2"),
                PLAYER_2_CHAR_CARD,
                findViewById(R.id.player2TurnIndicator),
                findViewById(R.id.player2Name),
                findViewById(R.id.player2Score)
        );

        playing = player1;
        player1.turnStarted();
        player2.turnOver();
    }

    private void loadButtonCards() {
        buttonCardArray[0][0] = findViewById(R.id.button11);
        buttonCardArray[0][1] = findViewById(R.id.button12);
        buttonCardArray[0][2] = findViewById(R.id.button13);
        buttonCardArray[1][0] = findViewById(R.id.button21);
        buttonCardArray[1][1] = findViewById(R.id.button22);
        buttonCardArray[1][2] = findViewById(R.id.button23);
        buttonCardArray[2][0] = findViewById(R.id.button31);
        buttonCardArray[2][1] = findViewById(R.id.button32);
        buttonCardArray[2][2] = findViewById(R.id.button33);
    }

    public void onClickCard(View view) {
        Button pressed = (Button) view;
        if (pressed.getText().equals(UNDEFINED_CHAR_CARD)) {
            pressed.setText(playing.getPlayerChar());
            pressed.getBackground().setTint(SELECTED_CARD_COLOR);
            if (!hasWinning() && !hasDraw()) {
                changeTurn();
            }
        }
    }

    public void onClickFinishGame(View view) {
        Player winner;

        if (player1.getScore() == player2.getScore()) {
            winner = null;
        } else if (player1.getScore() > player2.getScore()) {
            winner = player1;
        } else {
            winner = player2;
        }

        Intent intent = new Intent(this, WinnerActivity.class);
        intent.putExtra("WINNER", winner);
        startActivity(intent);
        finish();
    }

    private void changeTurn() {
        playing.turnOver();
        playing = playing == player1 ? player2 : player1;
        playing.turnStarted();
        turnCount++;
    }

    private boolean hasWinning() {
        for (int i = 0; i < 3; i++) {
            boolean hasVictoryOnHor = areCardsEqualsAndDefined(i, 0, i, 1, i, 2);
            boolean hasVictoryOnVer = areCardsEqualsAndDefined(0, i, 1, i, 2, i);
            if (hasVictoryOnHor || hasVictoryOnVer) {
                return true;
            }
        }

        boolean hasVictoryOnDiag1 = areCardsEqualsAndDefined(0, 0, 1, 1, 2, 2);
        boolean hasVictoryOnDiag2 = areCardsEqualsAndDefined(2, 0, 1, 1, 0, 2);

        if (hasVictoryOnDiag1 || hasVictoryOnDiag2) {
            return true;
        }

        return false;
    }

    private boolean areCardsEqualsAndDefined(int x1, int y1, int x2, int y2, int x3, int y3) {
        Button button1 = buttonCardArray[x1][y1];
        Button button2 = buttonCardArray[x2][y2];
        Button button3 = buttonCardArray[x3][y3];

        if (button2.getText().equals(UNDEFINED_CHAR_CARD)) {
            return false;
        }

        if (button1.getText().equals(button2.getText()) && button2.getText().equals(button3.getText())) {
            button1.getBackground().setTint(WIN_CARD_COLOR);
            button2.getBackground().setTint(WIN_CARD_COLOR);
            button3.getBackground().setTint(WIN_CARD_COLOR);
            declareVictory();
            return true;
        }

        return false;
    }

    private void declareVictory() {
        playing.wonTurn();
        showMessage(playing.getName() + " venceu!", "Parabéns!!!", dialog -> {
            changeTurn();
            turnCount = 0;
            iterateOverCards(button -> {
                button.setText(UNDEFINED_CHAR_CARD);
                button.getBackground().setTint(DEFAULT_CARD_COLOR);
            });
        });
    }

    private void iterateOverCards(Consumer<Button> action) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                action.accept(buttonCardArray[i][j]);
            }
        }
    }

    private boolean hasDraw() {
        if (turnCount == 8) {
            showMessage("Velha!", "Ninguém venceu...", dialog -> {
                changeTurn();
                turnCount = 0;
                iterateOverCards(button -> {
                    button.setText(UNDEFINED_CHAR_CARD);
                    button.getBackground().setTint(DEFAULT_CARD_COLOR);
                });
            });
            return true;
        }
        return false;
    }

    private void showMessage(String title, String message, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setOnDismissListener(onDismiss)
                .show();
    }

}

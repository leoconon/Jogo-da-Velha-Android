package com.leoconon.atividadejogodavelha;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Player winner = (Player) getIntent().getSerializableExtra("WINNER");

        if (winner == null) {
            TextView t1 = findViewById(R.id.text1);
            TextView tName = findViewById(R.id.textName);
            TextView t2 = findViewById(R.id.text2);

            t1.setText("Disputado!");
            tName.setText("Deu empate!");
            t2.setText("joguem melhor da próxima vez...");
            return;
        }

        TextView tName = findViewById(R.id.textName);
        tName.setText(winner.getName());
    }

    public void onClickBackToMain(View view) {
        finish();
    }

}

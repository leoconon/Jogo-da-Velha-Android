package com.leoconon.atividadejogodavelha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickPlay(View view) {
        final String name1 = ((EditText) findViewById(R.id.name1)).getText().toString();
        final String name2 = ((EditText) findViewById(R.id.name2)).getText().toString();

        if (!isValidPlayerName(name1, 1) || !isValidPlayerName(name2, 2)) {
            return;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("NAME_1", name1);
        intent.putExtra("NAME_2", name2);
        startActivity(intent);
    }

    private boolean isValidPlayerName(String name, int position) {
        if (name == null || name.trim().isEmpty()) {
            String namePos = position == 1 ? "primeiro" : "segundo";
            new AlertDialog.Builder(this)
                    .setTitle("Num pode")
                    .setMessage("O " + namePos + " jogador deve ter um nome!")
                    .show();
            return false;
        }
        return true;
    }

}

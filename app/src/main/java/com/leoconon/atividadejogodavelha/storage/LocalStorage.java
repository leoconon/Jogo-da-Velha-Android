package com.leoconon.atividadejogodavelha.storage;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage {

    private final static String PLAYERS_PREFERENCES = "com.leoconon.atividadejogodavelha.preferences.PLAYERS";
    private final static String PLAYERS_NAMES = "PLAYERS_NAMES";

    private final AppCompatActivity activity;

    public LocalStorage(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void savePlayerNames(String player1, String player2) {
        Set<String> set = new HashSet<>();
        set.add(player1);
        set.add(player2);
        SharedPreferences preferences = activity.getSharedPreferences(PLAYERS_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(PLAYERS_NAMES, set);
        editor.commit();
    }

    public Set<String> retrievePlayerNames() {
        SharedPreferences preferences = activity.getSharedPreferences(PLAYERS_PREFERENCES, MODE_PRIVATE);
        return preferences.getStringSet(PLAYERS_NAMES, null);
    }

}

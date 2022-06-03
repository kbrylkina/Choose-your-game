package ru.myitschool.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddGameButtonActivity extends AppCompatActivity {
    String eT = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_button);
    }

    public void saveGameButtonClick(View v) {
        EditText eNG = (EditText)findViewById(R.id.edit_name_game);
        eT = eNG.getText().toString();
        GamesBD game = new GamesBD(0, eT);
        if(eT.length()>0){
            new AddGame(this, game).execute();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void gameAdd(){

    }
    private static class AddGame extends AsyncTask<Void, Void, Void>{
        private AddGameButtonActivity activity;
        private GamesBD gamesBD;

        public AddGame(AddGameButtonActivity activity, GamesBD gamesBD) {
            this.activity = activity;
            this.gamesBD = gamesBD;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DataBase db = new DataBase(activity);
            if(!db.containsGameWithName(gamesBD.getGame_name())) {
                long id = db.insertGame(gamesBD.getGame_name());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            activity.gameAdd();
        }
    }
}
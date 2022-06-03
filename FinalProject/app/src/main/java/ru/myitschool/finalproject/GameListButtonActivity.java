package ru.myitschool.finalproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class GameListButtonActivity extends ListActivity {

    /*GameListClass arr_class = new GameListClass();
    public static String game = new String();
    public static int counter=0;*/
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list_button);
        gameAdapter = new GameAdapter(this);
        setListAdapter(gameAdapter);
        new LoadAllGamesTask(this).execute();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        GamesBD gamesBD = gameAdapter.getGamesBD(position);
        Intent intent = new Intent(this, OneGameActivity.class);
        intent.putExtra(OneGameActivity.GAME_ID_ARG, gamesBD.getId());
        intent.putExtra(OneGameActivity.GAME_NAME_ARG, gamesBD.getGame_name());
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void gamesLoaded(List<GamesBD> gamesBDList){
        gameAdapter.refresh(gamesBDList);
    }

    private static class LoadAllGamesTask extends AsyncTask<Void, Void, List<GamesBD>> {
        private GameListButtonActivity activity;

        public LoadAllGamesTask(GameListButtonActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<GamesBD> doInBackground(Void... voids) {
            DataBase db = new DataBase(activity);
            List<GamesBD> list = db.selectAllGames();
            return list;
        }

        @Override
        protected void onPostExecute(List<GamesBD> gamesBDS) {
            activity.gamesLoaded(gamesBDS);
        }
    }
}
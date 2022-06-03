package ru.myitschool.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class OneGameActivity extends AppCompatActivity {
    public static final String GAME_ID_ARG = "game_id";
    public static final String GAME_NAME_ARG = "game_name";
    /*GameListClass arr_class = new GameListClass();
    String game_1 = new String();
    GameListButtonActivity list_click = new GameListButtonActivity();
    float mark_1;*/
    private ReviewAdapter reviewAdapter;
    private long game_id;
    private String game_name;
    private TextView markTV, nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_game);
        game_id = getIntent().getLongExtra(GAME_ID_ARG, 0);
        game_name = getIntent().getStringExtra(GAME_NAME_ARG);
        reviewAdapter = new ReviewAdapter(this);
        ListView lv = findViewById(R.id.myreviewslist);
        lv.setAdapter(reviewAdapter);
        nameTV = (TextView) findViewById(R.id.name);
        nameTV.setText(String.valueOf(game_name));
        markTV = (TextView) findViewById(R.id.mark);
        new LoadAllReviewsTask(this, game_id).execute();
        new AverageMark(this, game_id).execute();
    }
    public void addReviewButtonClick(View v) {
        Intent intent = new Intent(this, AddReviewButtonActivity.class);
        intent.putExtra(AddReviewButtonActivity.GAME_ID_ARG, game_id);
        intent.putExtra(AddReviewButtonActivity.GAME_NAME_ARG, game_name);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, GameListButtonActivity.class);
        startActivity(intent);
        finish();
    }

    private void reviewsLoaded(List<ReviewsBD> reviewBDList){
        reviewAdapter.refresh(reviewBDList);
    }

    private static class LoadAllReviewsTask extends AsyncTask<Void, Void, List<ReviewsBD>> {
        private OneGameActivity activity;
        private long game_id;

        public LoadAllReviewsTask(OneGameActivity activity, long game_id) {
            this.activity = activity;
            this.game_id = game_id;
        }

        @Override
        protected List<ReviewsBD> doInBackground(Void... voids) {
            DataBase db = new DataBase(activity);
            List<ReviewsBD> list = db.selectAllReviews(game_id);
            return list;
        }

        @Override
        protected void onPostExecute(List<ReviewsBD> reviewsBDS) {
            activity.reviewsLoaded(reviewsBDS);
        }

    }

    private void markAverage(double average){
        if(average==0)
            markTV.setText("Оценок пока нет");
        else{
            markTV.setText("Средняя оценка: " + String.valueOf(average));
        }
    }

    private static class AverageMark extends AsyncTask<Void, Void, Double> {
        private OneGameActivity activity;
        private long game_id;

        public AverageMark(OneGameActivity activity, long game_id) {
            this.activity = activity;
            this.game_id = game_id;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            DataBase db = new DataBase(activity);
            double average = db.selectAverageMark(game_id);
            return average;
        }

        @Override
        protected void onPostExecute(Double average) {
            activity.markAverage(average);
        }
    }
}
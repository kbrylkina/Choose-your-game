package ru.myitschool.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddReviewButtonActivity extends AppCompatActivity {

    public static final String GAME_ID_ARG = "game_id";
    public static final String GAME_NAME_ARG = "game_name";
    private long game_id;
    private String game_name;
    int n_marks;
    String eTR = new String();
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_button);
        game_id = getIntent().getLongExtra(GAME_ID_ARG, 0);
        game_name = getIntent().getStringExtra(GAME_NAME_ARG);
        backToast = Toast.makeText(getBaseContext(), "Вы не поставили оценку", Toast.LENGTH_SHORT);
    }
    public void saveReviewButtonClick(View v) {
        EditText eR = (EditText)findViewById(R.id.edit_review);
        eTR = eR.getText().toString();
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton myRadioButton = findViewById(checkedRadioButtonId);
        if(checkedRadioButtonId!=-1) {
            n_marks = Integer.parseInt(myRadioButton.getText().toString());
            ReviewsBD reviewsBD = new ReviewsBD(0, eTR, n_marks, game_id);
            new AddReview(this, reviewsBD).execute();
        }
        else{
            backToast.show();
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, OneGameActivity.class);
        intent.putExtra(OneGameActivity.GAME_ID_ARG, game_id);
        intent.putExtra(OneGameActivity.GAME_NAME_ARG, game_name);
        startActivity(intent);
        finish();
        backToast.cancel();
    }

    private void reviewAdd(){
        Intent intent = new Intent(this, OneGameActivity.class);
        intent.putExtra(OneGameActivity.GAME_ID_ARG, game_id);
        intent.putExtra(OneGameActivity.GAME_NAME_ARG, game_name);
        startActivity(intent);
        finish();
        backToast.cancel();
    }
    private static class AddReview extends AsyncTask<Void, Void, Void> {
        private AddReviewButtonActivity activity;
        private ReviewsBD reviewsBD;

        public AddReview(AddReviewButtonActivity activity, ReviewsBD reviewsBD) {
            this.activity = activity;
            this.reviewsBD = reviewsBD;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DataBase db = new DataBase(activity);
            long id = db.insertReview(reviewsBD.getText(), reviewsBD.getMark(), reviewsBD.getGame_id());
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            activity.reviewAdd();
        }

    }
}

package ru.myitschool.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addGameButtonClick(View v) {
        Intent intent = new Intent(this, AddGameButtonActivity.class);
        startActivity(intent);
        finish();
    }
    public void gameListButtonClick(View v){
        Intent intent = new Intent(this, GameListButtonActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            finish();
            backToast.cancel();
        }
        else{
            backToast = Toast.makeText(getBaseContext(), "Нажмите ещё раз, чтобы выйти",
                    Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
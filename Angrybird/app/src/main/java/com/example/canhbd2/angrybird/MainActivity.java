package com.example.canhbd2.angrybird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> arrayList;
    ImageView imgQuestion, imgAnswer;
    int REQUEST_CODE = 123;
    String NameInageOr = "";
    TextView txtScore;
    int Total = 100;
    SharedPreferences SaveScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgQuestion = (ImageView) findViewById(R.id.imageQuestion);
        imgAnswer = (ImageView) findViewById(R.id.imageAnswer);
        txtScore = (TextView) findViewById(R.id.txtScore);
        SaveScore = getSharedPreferences("ScoreGame", MODE_PRIVATE);

        txtScore.setText(Total + "");

        String[] arraynames = getResources().getStringArray(R.array.list_name);
        arrayList = new ArrayList<>(Arrays.asList(arraynames));
        //tron mang
        Collections.shuffle(arrayList);
        NameInageOr = arrayList.get(5);
        int Images = getResources().getIdentifier(arrayList.get(5), "drawable", getPackageName());
        imgQuestion.setImageResource(Images);
        imgAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, ImagesActivity.class), REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onStart() {
        Total = SaveScore.getInt("score",100);
        txtScore.setText(Total + "");
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String NameImage = data.getStringExtra("Imageposition");
            int ImageAnwer = getResources().getIdentifier(NameImage, "drawable", getPackageName());
            imgAnswer.setImageResource(ImageAnwer);
            //so sanh hinh dau
            if (NameInageOr.equals(NameImage)) {
                Toast.makeText(this, "You are correct \n plus 10 score", Toast.LENGTH_SHORT).show();
                // cong diem
                Total += 10;
                Score();
                new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        //tron mang
                        Collections.shuffle(arrayList);
                        NameInageOr = arrayList.get(5);
                        int Images = getResources().getIdentifier(arrayList.get(5), "drawable", getPackageName());
                        imgQuestion.setImageResource(Images);
                    }
                }.start();
            } else {
                Toast.makeText(this, "You are incorrect \n minus 5 score", Toast.LENGTH_SHORT).show();
                Total -=5;
                Score();
            }
            txtScore.setText(Total + "");
        }
        //Kiem tra khong chon hinh
        if(requestCode ==REQUEST_CODE && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "You are not pick up picture, sorry about that \n minus 15 score ^^", Toast.LENGTH_SHORT).show();
            Total -= 15;
            Score();
            txtScore.setText(Total + "");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menureload) {
            //tron mang
            Collections.shuffle(arrayList);
            NameInageOr = arrayList.get(5);
            int Images = getResources().getIdentifier(arrayList.get(5), "drawable", getPackageName());
            imgQuestion.setImageResource(Images);
        }
        return super.onOptionsItemSelected(item);
    }
    private void Score(){
        SharedPreferences.Editor editor = SaveScore.edit();
        editor.putInt("score",Total);
        editor.commit();
    }
}

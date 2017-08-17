package com.example.canhbd2.angrybird;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ImagesActivity extends Activity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        tableLayout = (TableLayout) findViewById(R.id.myTable);


        int columns =8;
        int row = 3;

        Collections.shuffle(MainActivity.arrayList);
        for (int i = 1; i <= columns; i++) {
            TableRow tableRow = new TableRow(this);

            for (int j = 1; j <= row; j++) {
                ImageView imageView = new ImageView(this);
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px,px);
                imageView.setLayoutParams(layoutParams);
                final int position = row * (i-1) + j-1;
                int Images = getResources().getIdentifier(MainActivity.arrayList.get(position), "drawable", getPackageName());
                imageView.setImageResource(Images);
                //add imagesview into table row
                tableRow.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("Imageposition", MainActivity.arrayList.get(position));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
            //add table row int table
            tableLayout.addView(tableRow);
        }
    }
}

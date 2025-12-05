package com.example.schetchicks;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView count;
    ArrayList<String> history = new ArrayList<>();
    private ListView listHistory;
    //private ArrayList<String> history;
    private ArrayAdapter<String> adapter;
    int number = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String key_his = "history_key";
    private static final String key_num = "number_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        count = (TextView)findViewById(R.id.count);
        Button buttonNext = findViewById(R.id.button);
        Button buttonNext2 = findViewById(R.id.button2);
        Button buttonNext3 = findViewById(R.id.button3);
        ListView listHistory = (ListView)findViewById(R.id.listHistory);
        int n = 1;
        listHistory.smoothScrollToPosition(n);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
        listHistory.setAdapter(adapter);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, AddOneActivity.class);
                count.setText(String.valueOf(number + 1));
                LocalTime ct = LocalTime.now();
                history.add(ct.format(formatter) + " - Увеличено с " + number  +" до " + (number + 1));
                //System.out.println(history.size() - 1);
                addItemToListView(history.getLast());
                number += 1;

            }
        });


        buttonNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, AddOneActivity.class);
                count.setText(String.valueOf(number - 1));
                LocalTime ct = LocalTime.now();
                history.add(ct.format(formatter) + " - Уменьшено с " + number +" до " + (number - 1));
                addItemToListView(history.getLast());
                number += -1;

            }
        });

        buttonNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, AddOneActivity.class);
                count.setText(String.valueOf(0));
                LocalTime ct = LocalTime.now();
                history.add(ct.format(formatter) + " - Счётчик сброшен с " + number + " до " + (0));
                addItemToListView(history.getLast());
                number = 0;

            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(key_num, number);
        outState.putStringArrayList(key_his, history);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        number = savedInstanceState.getInt(key_num);
        ArrayList<String> savedHistory = savedInstanceState.getStringArrayList(key_his);

        history.addAll(savedHistory);
        adapter.notifyDataSetChanged();

        addItemToListView(String.valueOf(number));

        count.setText(String.valueOf(number));
    }


    protected void addItemToListView(String last) {
        adapter.notifyDataSetChanged();
        //listHistory.scrollListBy(1);
    }
}
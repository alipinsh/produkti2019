package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MenuActivity extends AppCompatActivity {
    Button button_exit, button_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button_exit = (Button) findViewById(R.id.button4);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               // moveTaskToBack(true);
                System.exit(0);
            }
        });
        button_list = (Button) findViewById(R.id.button2);
        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b) {
                switch (b.getId()) {

                case R.id.button2:
                openList();
                break;
                    default:
                        break;
                }
    }

}); }
    public void openList(){
        Intent intent = new Intent(this, OrderListActivity.class);
        startActivity(intent);
}}
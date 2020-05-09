package com.example.m2calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  float number = 0;
  TextView display;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    init();
  }

  @Override
  public void onUserInteraction() {
    display.setText(String.valueOf(number));
  }

  void init() {
    display = findViewById(R.id.display_number);
    display.setText(String.valueOf(number));
  }

  public void addOne(View v) {
    number++;
  }

}

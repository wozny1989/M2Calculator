package com.example.m2calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  String previousOperand;
  String currentOperand;
  String operation;
  TextView display;
  Vibrator vibrator;
  final long vibrationTime = 10;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.display = findViewById(R.id.display_number);
    this.vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

//    Number buttons
    Button btnZero = findViewById(R.id.btn_zero);
    Button btnOne = findViewById(R.id.btn_one);
    Button btnTwo = findViewById(R.id.btn_two);
    Button btnThree = findViewById(R.id.btn_three);
    Button btnFour = findViewById(R.id.btn_four);
    Button btnFive = findViewById(R.id.btn_five);
    Button btnSix = findViewById(R.id.btn_six);
    Button btnSeven = findViewById(R.id.btn_seven);
    Button btnEight = findViewById(R.id.btn_eight);
    Button btnNine = findViewById(R.id.btn_nine);
    Button btnDot = findViewById(R.id.btn_dot);

    btnZero.setOnClickListener(this);
    btnOne.setOnClickListener(this);
    btnTwo.setOnClickListener(this);
    btnThree.setOnClickListener(this);
    btnFour.setOnClickListener(this);
    btnFive.setOnClickListener(this);
    btnSix.setOnClickListener(this);
    btnSeven.setOnClickListener(this);
    btnEight.setOnClickListener(this);
    btnNine.setOnClickListener(this);
    btnDot.setOnClickListener(this);

//    Operation buttons
    Button btnEqual = findViewById(R.id.btn_equal);
    Button btnPlus = findViewById(R.id.btn_plus);
    Button btnMinus = findViewById(R.id.btn_minus);
    Button btnMultiply = findViewById(R.id.btn_multiply);
    Button btnDivide = findViewById(R.id.btn_divide);

    btnEqual.setOnClickListener(this);
    btnPlus.setOnClickListener(this);
    btnMinus.setOnClickListener(this);
    btnMultiply.setOnClickListener(this);
    btnDivide.setOnClickListener(this);

//    Other buttons
    Button clear = findViewById(R.id.btn_C);
    Button sign = findViewById(R.id.btn_sign);
    Button percent = findViewById(R.id.btn_percent);

//    Extend view buttons
    Button btnTwoNd = findViewById(R.id.btn_two_nd);
    Button btnXTwo = findViewById(R.id.btn_x_two);
    Button btnXThree = findViewById(R.id.btn_x_three);
    Button btnXY = findViewById(R.id.btn_x_y);
    Button btnEX = findViewById(R.id.btn_e_x);
    Button btnTenX = findViewById(R.id.btn_ten_x);

    Button btnOneX = findViewById(R.id.btn_one_x);
    Button btnTwoX = findViewById(R.id.btn_two_x);
    Button btnThreeX = findViewById(R.id.btn_three_x);
    Button btnYX = findViewById(R.id.btn_y_x);
    Button btnLogTen = findViewById(R.id.btn_log_ten);

    clear.setOnClickListener(this);
    sign.setOnClickListener(this);
    percent.setOnClickListener(this);



    init();

    btnTwoNd.setText(Html.fromHtml("2<sup>nd</sup>"));
    btnXTwo.setText(Html.fromHtml("X<sup>2</sup>"));
    btnXThree.setText(Html.fromHtml("X<sup>3</sup>"));
    btnXY.setText(Html.fromHtml("X<sup>y</sup>"));
    btnEX.setText(Html.fromHtml("e<sup>x</sup>"));
    btnTenX.setText(Html.fromHtml("10<sup>x</sup>"));

    btnOneX.setText(Html.fromHtml("1&frasl;<sub>x</sub>"));
    btnTwoX.setText(Html.fromHtml("<sup>2</sup>&radic;x"));
    btnThreeX.setText(Html.fromHtml("<sup>3</sup>&radic;x"));
    btnYX.setText(Html.fromHtml("<sup>y</sup>&radic;x"));
    btnLogTen.setText(Html.fromHtml("log<sub>10</sub>"));
  }

  void init() {
    clear();
  }

  public void clear() {
    this.display.setText("");
    this.previousOperand = "";
    this.currentOperand = "";
    this.operation = null;
  }

  public void appendNumber(String number) {
    if (number.equals(".") && this.currentOperand.contains(".")) return;
    this.currentOperand = this.currentOperand + number;
  }

  public void chooseOperation(String operation) {
    if (this.currentOperand.equals("")) return;
    if (!this.previousOperand.equals("")) {
      this.compute();
    }
    this.operation = operation;
    this.previousOperand = this.currentOperand;
    this.currentOperand = "";
  }

  public void compute() {
    Double computation;
    Double prev = Double.parseDouble(this.previousOperand);
    Double current = Double.parseDouble(this.currentOperand);
    if (Double.isNaN(prev) || Double.isNaN(current)) return;
    switch (this.operation) {
      case "+":
        computation = prev + current;
        break;
      case "-":
        computation = prev - current;
        break;
      case "×":
        computation = prev * current;
        break;
      case "÷":
        computation = prev / current;
        break;
      default:
        return;
    }
    this.currentOperand = String.valueOf(computation);
    this.operation = null;
    this.previousOperand = "";
  }

  public void updateDisplay() {
    this.tryClearDot();
    this.display.setText(this.currentOperand);
  }

  public void tryClearDot() {
    String[] numberSplitByDot = this.currentOperand.split(Pattern.quote("."));
    if (numberSplitByDot.length == 2 && numberSplitByDot[1].length() == 1 && Double.parseDouble(numberSplitByDot[1]) == 0) {
      this.currentOperand = numberSplitByDot[0];
    }
  }

  public void changeSign() {
    this.currentOperand = String.valueOf(Double.parseDouble(this.currentOperand) * -1.0);
  }

  public void computePercent() {
    this.currentOperand = String.valueOf(Double.parseDouble(this.currentOperand) * 0.01);
  }

  @Override
  public void onClick(View v) {
    try {
      String tagElement = v.getTag().toString();
      String textElement = ((Button)v).getText().toString();
      switch (tagElement) {
        case "number":
        case "dot":
          this.appendNumber(textElement);
          break;
        case "operation":
          this.chooseOperation(textElement);
          break;
        case "equal":
          this.compute();
          break;
        case "clear":
          this.clear();
          break;
        case "sign":
          this.changeSign();
          break;
        case "percent":
          this.computePercent();
          break;
        default:
          return;
      }
    } catch (Exception e) {

    } finally {
      this.vibrator.vibrate(this.vibrationTime);
      updateDisplay();
    }
  }
}

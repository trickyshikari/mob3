package com.example.lab3;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String stringColors[] = new String[] { "Черный", "Синий", "Зеленый", "Красный", "Жёлтый" };
    private int colors[] = new int[] { Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };
    private int curColor;
    private int curColorName;
    private Button corAnswer;
    private int corAnswers = 0;
    private TextView timerView;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.btnStart);

        timerView = findViewById(R.id.tvTimer);
        findViewById(R.id.tvNamecolor).setVisibility(View.GONE);
        findViewById(R.id.tvViewcolor).setVisibility(View.GONE);
        findViewById(R.id.tvTimer).setVisibility(View.GONE);
        findViewById(R.id.btnNo).setVisibility(View.GONE);
        findViewById(R.id.btnYes).setVisibility(View.GONE);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                start();
            }
        };

        start.setOnClickListener(ocl);
    }

    private void start() {

        corAnswers = 0;

        findViewById(R.id.btnStart).setVisibility(View.GONE);

        timer = new CountDownTimer(60000, 100) {

            public void onTick(long millisUntilFinished) {
                timerView.setText(millisUntilFinished / 1000 + "." + (millisUntilFinished % 1000) / 100);
            }

            public void onFinish() {
                findViewById(R.id.btnStart).setVisibility(View.VISIBLE);

                findViewById(R.id.tvNamecolor).setVisibility(View.GONE);
                findViewById(R.id.tvViewcolor).setVisibility(View.GONE);
                findViewById(R.id.tvTimer).setVisibility(View.GONE);
                findViewById(R.id.btnNo).setVisibility(View.GONE);
                findViewById(R.id.btnYes).setVisibility(View.GONE);

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                dlgAlert.setMessage("Правильных ответов: " + corAnswers);
                dlgAlert.setTitle("Конец игры ");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
            }

        }.start();

        // Обработчик событий кнопок-ответов
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == corAnswer) {
                    corAnswers++;
                    Toast.makeText(getApplicationContext(), "Правильно!", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(getApplicationContext(), "Неправильно!", Toast.LENGTH_SHORT).show();
                }

                generate();
            }
        };

        // Находим кнопки ответов
        Button btnYes = findViewById(R.id.btnYes),
                btnNo = findViewById(R.id.btnNo);
        // Привязываем к ним обработчик событий
        btnYes.setOnClickListener(onClickListener);
        btnNo.setOnClickListener(onClickListener);

        // Делаем видимыми элементы игры
        findViewById(R.id.btnYes).setVisibility(View.VISIBLE);
        findViewById(R.id.btnNo).setVisibility(View.VISIBLE);
        findViewById(R.id.tvNamecolor).setVisibility(View.VISIBLE);
        findViewById(R.id.tvViewcolor).setVisibility(View.VISIBLE);
        findViewById(R.id.tvTimer).setVisibility(View.VISIBLE);

        // Генерируем стартовые данные
        generate();
    }

    // Функция генерации данных
    private void generate() {

        curColorName = (int)Math.floor(Math.random() * stringColors.length);
        curColor = colors[(int)Math.floor(Math.random() * colors.length)];
        TextView tvLeft = findViewById(R.id.tvNamecolor);
        tvLeft.setText(stringColors[curColorName]);
        tvLeft.setTextColor(curColor);
        curColor = (int)Math.floor(Math.random() * colors.length);
        TextView tvRight = findViewById(R.id.tvViewcolor);
        tvRight.setText(stringColors[(int)Math.floor(Math.random() * stringColors.length)]);
        tvRight.setTextColor(colors[curColor]);
        if (colors[curColorName] == colors[curColor])
            corAnswer = findViewById(R.id.btnYes);
        else
            corAnswer = findViewById(R.id.btnNo);
    }
}

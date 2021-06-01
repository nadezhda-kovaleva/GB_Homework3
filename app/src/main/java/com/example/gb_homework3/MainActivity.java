package com.example.gb_homework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gb_homework3.Calculator;
import com.example.gb_homework3.R;

public class MainActivity extends AppCompatActivity {
    private Calculator calc;
    private TextView text;
    private TextView textOperation;
    static String operator = "0";

    private static final String AppTheme = "Theme.Homework4";
    private static final int MyLightCodeStyle = 0;
    private static final int MyDarkCodeStyle = 1;
    private static final String NameSharedPreference = "SharedPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyDarkCoolStyle));
        setContentView(R.layout.activity_main);

        checkNightModeActivated();

        calc = new Calculator();
        initView();
        initThemeChooser();
    }

    public void checkNightModeActivated() {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        if (sharedPref.getInt(AppTheme, 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMyCoolStyle),
                MyLightCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonDarkAction),
                MyDarkCodeStyle);
    }

    private void initRadioButton(View button, final int codeStyle){
        button.setOnClickListener(v -> {
            setAppTheme(codeStyle);
            if (codeStyle == MyLightCodeStyle) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate();
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle){
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle){
        if (codeStyle == MyLightCodeStyle) {
            setTheme(R.style.MyCoolStyle);
            return R.style.MyCoolStyle;
        }
        setTheme(R.style.MyDarkCoolStyle);
        return R.style.MyDarkCoolStyle;
    }


    private void initView() {
        text = findViewById(R.id.textView);
        textOperation = findViewById(R.id.textView2);
        initButtonsClickListener();
    }

    private void initButtonsClickListener() {
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button0 = findViewById(R.id.button_null);
        Button button_plus = findViewById(R.id.button_plus);
        Button button_min = findViewById(R.id.button_min);
        Button button_mult = findViewById(R.id.button_mult);
        Button button_div = findViewById(R.id.button_div);
        Button button_clear = findViewById(R.id.button_clear);
        Button button_comma = findViewById(R.id.button_comma);
        Button button_proc = findViewById(R.id.button_proc);
        Button button_equal = findViewById(R.id.button_equal);
        Button button_return = findViewById(R.id.button_return);

        button1.setOnClickListener(numberButtonsClickListener);
        button2.setOnClickListener(numberButtonsClickListener);
        button3.setOnClickListener(numberButtonsClickListener);
        button4.setOnClickListener(numberButtonsClickListener);
        button5.setOnClickListener(numberButtonsClickListener);
        button6.setOnClickListener(numberButtonsClickListener);
        button7.setOnClickListener(numberButtonsClickListener);
        button8.setOnClickListener(numberButtonsClickListener);
        button9.setOnClickListener(numberButtonsClickListener);
        button0.setOnClickListener(numberButtonsClickListener);
        button_clear.setOnClickListener(numberClearClickListener);
        button_comma.setOnClickListener(numberButtonsClickListener);
        button_return.setOnClickListener(returnButtonsClickListener);
        button_plus.setOnClickListener(operationPlusButtonsClickListener);
        button_min.setOnClickListener(operationMinButtonsClickListener);
        button_div.setOnClickListener(operationDivButtonsClickListener);
        button_mult.setOnClickListener(operationMultButtonsClickListener);
        button_proc.setOnClickListener(operationProcButtonsClickListener);
        button_equal.setOnClickListener(equalButtonsClickListener);
    }

    public View.OnClickListener numberButtonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            String textFromTV = tv.getText().toString();
            text.append(textFromTV);
        }
    };

    public View.OnClickListener numberClearClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text.setText("");
            calc.number2 = "";
            calc.number1 = "";
            calc.result = 0;
            textOperation.setText("");
        }
    };

    public View.OnClickListener returnButtonsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = text.getText().toString().trim();
            if (str.length() == 0) return;
            str = str.substring(0, str.length() - 1);
            text.setText(str);
        }
    };

    public View.OnClickListener operationPlusButtonsClickListener = v -> setOperator("+");

    public View.OnClickListener operationMinButtonsClickListener = v -> setOperator("-");

    public View.OnClickListener operationDivButtonsClickListener = v -> setOperator("/");

    public View.OnClickListener operationMultButtonsClickListener = v -> setOperator("*");

    public View.OnClickListener operationProcButtonsClickListener = v -> setOperator("%");

    public View.OnClickListener equalButtonsClickListener = v -> Equals();

    public void setOperator(String _operator) {
        if (calc.number1.equals("")) {
            calc.number1 = text.getText().toString().trim();
        } else {
            calc.number2 = text.getText().toString().trim();
            calc.number1 = String.valueOf(calc.result);
        }
        operator = _operator;
        text.setText("");
        textOperation.setText(operator);
    }

    public void Equals() {
        String str = text.getText().toString().trim();
        if (str.length() == 0) return;

        calc.number2 = text.getText().toString().trim();
        if (calc.number1.equals("")) return;


        switch (operator) {
            case "+":
                calc.Sum();
                break;

            case "-":
                calc.Min();
                break;

            case "/":
                calc.Div();

                break;

            case "*":
                calc.Mult();
                break;

            case "%":
                calc.Proc();
                break;

            default:
                calc.result = 0;
        }

        text.setText("" + calc.result);
        textOperation.setText("");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("calc", calc);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        calc = savedInstanceState.getParcelable("calc");
        text = findViewById(R.id.textView);
        text.setText(String.valueOf(calc.result));
    }
}
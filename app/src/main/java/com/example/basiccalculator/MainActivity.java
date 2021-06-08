package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;

import org.mariuszgromada.math.mxparser.*;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText display;

    private TextView resultView;

    private boolean usingRoot = false, usingModulus = false;
    ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        parent = findViewById(R.id.parent);

        Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
        ImageButton backBtn;
        Button addBtn, divBtn, mulBtn, modBtn, dotBtn, subBtn, equalBtn, clearBtn, powBtn, bracketBtn, rootBtn;


        display = findViewById(R.id.displayTxt);
        display.setShowSoftInputOnFocus(false);

        btn0 = findViewById(R.id.button0);
        btn0.setOnClickListener(this);

        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(this);

        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(this);

        btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(this);

        btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(this);

        btn6 = findViewById(R.id.button6);
        btn6.setOnClickListener(this);

        btn7 = findViewById(R.id.button7);
        btn7.setOnClickListener(this);

        btn8 = findViewById(R.id.button8);
        btn8.setOnClickListener(this);

        btn9 = findViewById(R.id.button9);
        btn9.setOnClickListener(this);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);

        subBtn = findViewById(R.id.subBtn);
        subBtn.setOnClickListener(this);

        mulBtn = findViewById(R.id.mulBtn);
        mulBtn.setOnClickListener(this);

        modBtn = findViewById(R.id.modulusBtn);
        modBtn.setOnClickListener(this);

        divBtn = findViewById(R.id.divBtn);
        divBtn.setOnClickListener(this);

        dotBtn = findViewById(R.id.dotBtn);
        dotBtn.setOnClickListener(this);

        equalBtn = findViewById(R.id.equalBtn);
        equalBtn.setOnClickListener(this);

        bracketBtn = findViewById(R.id.bracketBtn);
        bracketBtn.setOnClickListener(this);

        clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(this);

        clearBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                display.setText("");
                resultView.setText("");
                return true;
            }
        });


        rootBtn = findViewById(R.id.rootBtn);
        rootBtn.setOnClickListener(this);

        powBtn = findViewById(R.id.powerBtn);
        powBtn.setOnClickListener(this);

        resultView = findViewById(R.id.resultView);
        resultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Long Press to Copy", Toast.LENGTH_SHORT).show();
            }
        });

        resultView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updateDisplay(resultView.getText().toString());
                display.setSelection(display.getSelectionStart() + resultView.getText().length() - 1);
                return true;
            }
        });
    }

    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.clearBtn:
                display.setText("");
                break;

            case R.id.button0:
                updateDisplay("0");
                break;

            case R.id.button1:
                updateDisplay("1");
                break;

            case R.id.button2:
                updateDisplay("2");
                break;

            case R.id.button3:
                updateDisplay("3");
                break;

            case R.id.button4:
                updateDisplay("4");
                break;

            case R.id.button5:
                updateDisplay("5");
                break;

            case R.id.button6:
                updateDisplay("6");
                break;

            case R.id.button7:
                updateDisplay("7");
                break;

            case R.id.button8:
                updateDisplay("8");
                break;

            case R.id.button9:
                updateDisplay("9");
                break;

            case R.id.addBtn:
                updateDisplay("+");
                break;

            case R.id.subBtn:
                updateDisplay("-");
                break;

            case R.id.mulBtn:
                updateDisplay("×");
                break;

            case R.id.divBtn:
                updateDisplay("÷");
                break;

            case R.id.backBtn:
                StringBuilder oldStr = new StringBuilder(display.getText().toString());
                int pos = display.getSelectionStart();
                if (oldStr.length() != 0 && pos != 0) {
                    oldStr.deleteCharAt(pos - 1);
                    display.setText(oldStr);
                    display.setSelection(pos - 1);
                } else {
                    Toast.makeText(this, "You are at START of expression!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bracketBtn:
                int openPar = 0, closePar = 0;
                String strVar = display.getText().toString();
                int textLength = strVar.length();

                int cursorPos = display.getSelectionStart();
                for (int i = 0; i < textLength; i++) {
                    if (strVar.charAt(i) == '(')
                        openPar++;
                    else if (strVar.charAt(i) == ')')
                        closePar++;
                }
                if (openPar == closePar || strVar.charAt(textLength - 1) == '(' || (!java.lang.Character.isDigit(strVar.charAt(cursorPos - 1)) && strVar.charAt(cursorPos - 1) != ')')) {
                    updateDisplay("(");
                } else if (closePar < openPar && strVar.charAt(textLength - 1) != '(') {
                    updateDisplay(")");
                }
                break;

            case R.id.dotBtn:
                updateDisplay(".");
                break;

            case R.id.powerBtn:
                updateDisplay("^");
                break;

            case R.id.rootBtn:
                strVar = display.getText().toString();
                if (strVar.length() != 0) {
                    Toast.makeText(this, "Root Symbol should come first", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!compatibilityCheck(true)) {
                    showRootModSnack();
                } else {
                    usingRoot = true;
                    updateDisplay("√");
                }
                break;

            case R.id.modulusBtn:
                strVar = display.getText().toString();
                if (strVar.length() == 0) {
                    Toast.makeText(this, "Enter a Number First", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!compatibilityCheck(false)) {
                    showRootModSnack();
                } else {
                    usingModulus = true;
                    updateDisplay("%");
                }
                break;

            case R.id.equalBtn:
                String strExpr = display.getText().toString();

                String value = "";

                if (strExpr.length() == 0) {
                    Toast.makeText(this, "Enter Some Expression!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (usingModulus || usingRoot) {
                    value = parserRootModulus(strExpr, usingRoot);
                } else {
                    strExpr = strExpr.replaceAll("÷", "/");
                    strExpr = strExpr.replaceAll("×", "*");

                    Expression exp = new Expression(strExpr);
                    value = String.valueOf(exp.calculate());
                }

                //Removing trailing Zero
                if (value.endsWith(".0") || value.endsWith(".00"))
                    value = value.substring(0, value.indexOf("."));

                if (value.equals("NaN")) {
                    display.setText(getString(R.string.syntaxError));
                    display.setSelection(display.getText().length());
                    break;
                }
                if(value.indexOf('.')!=-1 && value.length() - value.indexOf('.')>4 && value.indexOf('E')==-1) {
                    value = String.format("%.4f",Double.parseDouble(value));
                }

                display.setText(value);
                display.setSelection(display.getText().length());

                resultView.setText(value);

                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void showRootModSnack() {
        Snackbar.make(parent, "% and √ use SINGLE Expression", Snackbar.LENGTH_INDEFINITE).setAction("Got it", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setActionTextColor(Color.RED).show();
    }

    private String parserRootModulus(String strExpr, boolean isRoot) {
        if (isRoot) {
            if (!compatibilityCheck(true)) {
                showRootModSnack();
                return "NaN";
            }

            float val = Float.parseFloat(strExpr.substring(1, strExpr.length()));
            usingRoot = false;
            return String.valueOf(Math.sqrt(val));
        } else {

            if (!compatibilityCheck(false)) {
                showRootModSnack();
                return  "NaN";
            }
            int len = strExpr.length();
            float i = Float.parseFloat(strExpr.substring(0, strExpr.indexOf('%')));
            float j = Float.parseFloat(strExpr.substring(strExpr.indexOf('%') + 1, len));
            usingModulus = false;
            return String.valueOf(i % j);
        }
    }

    private boolean compatibilityCheck(boolean isRoot) {

        String[] list = {"√", "+", "-", "÷", "×","^", "(", ")", "%"};
        boolean isContains = false;
        String strTxt = display.getText().toString();

        if (isRoot) {
            for (int i = 1; i < list.length; i++) {
                if (strTxt.contains(list[i])) {
                    isContains = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < list.length - 1; i++) {
                if (strTxt.contains(list[i])) {
                    isContains = true;
                    break;
                }
            }
        }

        return !isContains;
    }

    private void updateDisplay(String strAdd) {
        String oldStr = display.getText().toString();
        if (oldStr.equals(getString(R.string.syntaxError))) {
            display.setText("");
            oldStr = "";
        }

        if (oldStr.length() >= 36) {
            Snackbar.make(parent, "Expression Size Limit exceeding", Snackbar.LENGTH_INDEFINITE).setAction("Got it", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Try Shorting Your Expression", Toast.LENGTH_SHORT).show();
                }
            }).setActionTextColor(Color.RED).show();
            return;
        }

        // Getting cursor position
        int cursorPos = display.getSelectionStart();

        //splitting Strings into half
        String firstHalf = oldStr.substring(0, cursorPos);
        String nextHalf = oldStr.substring(cursorPos);

        display.setText(String.format("%s%s%s", firstHalf, strAdd, nextHalf));

        //updating cursor position
        display.setSelection(cursorPos + 1);
    }
}

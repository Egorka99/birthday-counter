package com.tstu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private EditText inputDate, textDaysLeft, textHoursLeft;
    private TextView days;
    private TextView text;
    private TextView hours;

    private ConstraintLayout constraintLayout2;
    private ConstraintLayout constraintLayout3;

    // Идентификатор канала
    private static String CHANNEL_ID = "Birthday app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputDate = findViewById(R.id.inputDate);

        text = findViewById(R.id.mainText);
        textDaysLeft = findViewById(R.id.editTextDaysLeft);
        textHoursLeft = findViewById(R.id.editTextHoursLeft);
        days = findViewById(R.id.Days);
        hours = findViewById(R.id.Hours);

        constraintLayout2 = findViewById(R.id.constraintLayout2);
        constraintLayout3 = findViewById(R.id.constraintLayout3);

        inputDate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputDate.addTextChangedListener(new TextWatcher() {
            int beforeTextChangedLength;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeTextChangedLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                // text is being removed
                if (beforeTextChangedLength > length) return;
                try {
                    Utils.isValidDate(editable.toString());
                } catch (Exception e) {
                    inputDate.setError(e.getMessage());
                }

                String str = editable.toString();
                String[] strArr = str.split(Utils.DASH_STRING);
                // only add dash after input year with zero dash and input month with one dash
                if ((length == 2 && strArr.length == 1)) {
                    inputDate.setText(str + Utils.DASH_STRING);
                    inputDate.setSelection(inputDate.length());
                }

            }
        });
    }

    public void findOutBirthday(View view) {

        try {
            handleAfterBirthdayEventSearch();
            String date = inputDate.getText().toString();
            Utils.isValidDate(date);
            date += "-" + Calendar.getInstance().get(Calendar.YEAR);
            Date birthDayDate = Utils.getBirthdayDate(Objects.requireNonNull(format.parse(date)));
            long milliseconds = (birthDayDate.getTime() - new Date().getTime());
            long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
            long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(days);
            if (days != 364) {
                textDaysLeft.setText(String.valueOf(Math.abs(days)));
                textHoursLeft.setText(String.valueOf(Math.abs(hours)));
            } else {
                handleBirthdayEvent();
            }

        } catch (Exception ignore) {
        }

    }

    private void handleAfterBirthdayEventSearch() {
        text.setText("До дня рождения осталось :");

        constraintLayout2.setVisibility(View.VISIBLE);
        constraintLayout3.setVisibility(View.INVISIBLE);
    }

    private void handleBirthdayEvent() {
        text.setText("С днем рождения!");

        constraintLayout2.setVisibility(View.INVISIBLE);
        constraintLayout3.setVisibility(View.VISIBLE);
    }


}
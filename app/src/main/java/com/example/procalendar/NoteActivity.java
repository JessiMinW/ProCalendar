package com.example.procalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.procalendar.Calendar_Alarm.WorkAlarm;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity
{

    Button btnThoat, btnPrev, btnNext, btnDateView;
    EditText txtWrite;
    int day, month, year;
    NoteDatabase myDatabase;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        context = this;

        myDatabase = new NoteDatabase(getApplicationContext());
        txtWrite = findViewById(R.id.txtWrite);
        txtWrite.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                WorkAlarm workAlarm = new WorkAlarm(btnDateView.getText().toString(), txtWrite.getText().toString());
                myDatabase.deleteOne(workAlarm);
                myDatabase.addAlarm(workAlarm);
            }
        });

        btnDateView = findViewById(R.id.btnDateView);
        btnPrev = findViewById(R.id.btnPre);
        btnNext = findViewById(R.id.btnNext);
        btnThoat = findViewById(R.id.btnThoat);
        btnThoat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        btnDateView.setText(day + "/" + (month + 1) + "/" + year);
        btnPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                calendar.add(Calendar.DATE, -1);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                btnDateView.setText(day + "/" + (month + 1) + "/" + year);

                String body = myDatabase.getData(btnDateView.getText().toString()).getNote();
                txtWrite.setText(body);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                calendar.add(Calendar.DATE, 1);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                btnDateView.setText(day + "/" + (month + 1) + "/" + year);

                 String body = myDatabase.getData(btnDateView.getText().toString()).getNote();
                 txtWrite.setText(body);
            }
        });
        btnDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int _year, int _month, int _dayOfMonth)
                    {
                        day = _dayOfMonth;
                        month = _month;
                        year = _year;
                        btnDateView.setText(day + "/" + (month + 1) + "/" + year);
                        String body = myDatabase.getData(btnDateView.getText().toString()).getNote();
                        txtWrite.setText(body);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, datePickerListener, year, month, day);

                if (!isFinishing()) datePickerDialog.show();
            }
        });

        if (myDatabase.getData(btnDateView.getText().toString()).getDate().compareTo("") != 0)
        {
            String body = myDatabase.getData(btnDateView.getText().toString()).getNote();
            txtWrite.setText(body);
        }
    }
}

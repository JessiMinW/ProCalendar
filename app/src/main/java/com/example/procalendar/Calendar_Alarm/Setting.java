package com.example.procalendar.Calendar_Alarm;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.procalendar.MyDataBase;
import com.example.procalendar.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Setting extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button btnThem, btnXoa;
    ListView lvLichHen;

    private int day, month, year, hour, minus;

    private int itemSelIndex = -1;

    private MyDataBase myDataBase;
    private ArrayList<WorkAlarm> list;
    private CustomListAdapter myAdapter;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntentAlarm;
    private Context context;
    private Calendar mCld;

    public Setting()
    {

    }

    public static Setting newInstance(String param1, String param2)
    {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        context = getContext();
        //alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        //alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        //final Intent intent = new Intent(getContext(), AlarmReceiver.class);
        mCld = Calendar.getInstance();
        myDataBase = new MyDataBase(getContext());
        list = new ArrayList<WorkAlarm>();
        lvLichHen = view.findViewById(R.id.lvLichHen);
        if (myDataBase.getNotesCount() > 0)
        {
            for (int i = 0; i < myDataBase.getNotesCount(); i++)
            {
                WorkAlarm mAlarm = new WorkAlarm(myDataBase.getData(i).getID(), myDataBase.getData(i).getDate()
                , myDataBase.getData(i).getNote());
                list.add(mAlarm);
            }
            myAdapter = new CustomListAdapter(getContext(), R.layout.layout_listview_item, list);
            lvLichHen.setAdapter(myAdapter);
        }

        btnThem = view.findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog inputAlarm = new Dialog(getContext());
                inputAlarm.setContentView(R.layout.layout_alarm_input);
                inputAlarm.setTitle("Đặt nhắc nhở");

                final EditText txtSelDate = inputAlarm.findViewById(R.id.txtSelDate);
                Button btnSelDate = inputAlarm.findViewById(R.id.btnSelDate);
                btnSelDate.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // chon ngay nhac nho
                        Calendar calendar = Calendar.getInstance();
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        month = calendar.get(Calendar.MONTH);
                        year = calendar.get(Calendar.YEAR);
                        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int _year, int _month, int _dayOfMonth)
                            {
                                day = _dayOfMonth;
                                month = _month;
                                year = _year;
                                txtSelDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        };
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datePickerListener, year, month, day);
                        datePickerDialog.show();
                    }
                });
                final EditText txtSelTime = inputAlarm.findViewById(R.id.txtSelTime);
                final EditText txtContent = inputAlarm.findViewById(R.id.txtContent);
                Button btnSelTime = inputAlarm.findViewById(R.id.btnSelTime);
                btnSelTime.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // chon gio nhac nho
                        Calendar calendar = Calendar.getInstance();
                        hour = calendar.get(Calendar.HOUR_OF_DAY);
                        minus = calendar.get(Calendar.MINUTE);
                        TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener()
                        {
                            @Override
                            public void onTimeSet(TimePicker view, int _hour, int _minus)
                            {
                                hour = _hour;
                                minus = _minus;
                                txtSelTime.setText(hour + ":" + minus);
                            }
                        };
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timePickerListener, hour, minus, true);
                        timePickerDialog.show();
                    }
                });
                Button dialogBtn = inputAlarm.findViewById(R.id.btnOK);
                dialogBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String dateSel = txtSelDate.getText().toString();
                        String timeSel = txtSelTime.getText().toString();
                        if (dateSel.length() > 0 && timeSel.length() > 0)
                        {
                            WorkAlarm workAlarm = new WorkAlarm(dateSel + " - " + timeSel, txtContent.getText().toString());
                            mCld.set(Calendar.YEAR, getYear(workAlarm.getDate()));
                            mCld.set(Calendar.MONTH, getMonth(workAlarm.getDate()) - 1);
                            mCld.set(Calendar.DAY_OF_MONTH, getDay(workAlarm.getDate()));
                            mCld.set(Calendar.HOUR_OF_DAY, getHour(workAlarm.getDate()));
                            mCld.set(Calendar.MINUTE, getMinus(workAlarm.getDate()));
                            mCld.set(Calendar.SECOND, 0);
                            mCld.set(Calendar.MILLISECOND, 0);
                            list.add(workAlarm);
                            workAlarm.setID(list.indexOf(workAlarm));
                            myDataBase.addAlarm(workAlarm);
                            sortByDate(list);
                            // cap nhat database
                            myDataBase.deleteAll();
                            for (int i = 0; i < list.size(); i++)
                            {
                                myDataBase.addAlarm(list.get(i));
                            }
                            myAdapter = new CustomListAdapter(getActivity(), R.layout.layout_listview_item, list);
                            lvLichHen.setAdapter(myAdapter);
                            // thong bao
                            Intent intent = new Intent(getContext(), AlarmReceiver.class);
                            alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                            pendingIntentAlarm = PendingIntent.getBroadcast(context,(int) mCld.getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, mCld.getTimeInMillis(), pendingIntentAlarm);
                        }
                        inputAlarm.dismiss();
                    }
                });
                inputAlarm.show();
            }
        });

        btnXoa = view.findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (itemSelIndex >= 0)
                {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, getYear(list.get(itemSelIndex).getDate()));
                    c.set(Calendar.MONTH, getMonth(list.get(itemSelIndex).getDate()) - 1);
                    c.set(Calendar.DAY_OF_MONTH, getDay(list.get(itemSelIndex).getDate()));
                    c.set(Calendar.HOUR_OF_DAY, getHour(list.get(itemSelIndex).getDate()));
                    c.set(Calendar.MINUTE, getMinus(list.get(itemSelIndex).getDate()));
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    Calendar now = Calendar.getInstance();
                    if (c.after(now)) {
                        PendingIntent pd = PendingIntent.getBroadcast(context, (int) c.getTimeInMillis(), new Intent(getContext(), AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pd);
                    }
                    list.remove(itemSelIndex);
                    // cap nhat lai id
                    for (int i = 0; i < list.size(); i++)
                        list.get(i).setID(i);
                    myAdapter = new CustomListAdapter(getActivity(), R.layout.layout_listview_item, list);
                    lvLichHen.setAdapter(myAdapter);
                    // cap nhat database
                    myDataBase.deleteAll();
                    for (int i = 0; i < list.size(); i++)
                    {
                        myDataBase.addAlarm(list.get(i));
                    }
                    itemSelIndex = -1;
                }
            }
        });

        lvLichHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelIndex = position;
            }
        });

        return view;
    }

    // sap xep tang dan thoi gian

    private void sortByDate(List<WorkAlarm> myList)
    {
        for (int i = 0; i < myList.size(); i++)
            for (int j = i + 1; j < myList.size(); j++)
            {
                if (compareDate(myList.get(i).getDate(), myList.get(j).getDate()))
                {
                    WorkAlarm temp = new WorkAlarm(myList.get(i).getDate(), myList.get(i).getNote());
                    myList.set(i, myList.get(j));
                    myList.set(j, temp);
                }
            }
        for (int i = 0; i < myList.size(); i++)
            myList.get(i).setID(i);
    }
    private boolean compareDate(String dateTime1, String dateTime2)
    {
        if (getYear(dateTime1) > getYear(dateTime2))
            return true;
        else if (getYear(dateTime1) < getYear(dateTime2))
            return false;
        else
        {
            if (getMonth(dateTime1) > getMonth(dateTime2))
                return true;
            else if (getMonth(dateTime1) < getMonth(dateTime2))
                return false;
            else
            {
                if (getDay(dateTime1) > getDay(dateTime2))
                    return true;
                else if (getDay(dateTime1) < getDay(dateTime2))
                    return false;
                else
                {
                    if (getHour(dateTime1) > getHour(dateTime2))
                        return true;
                    else if (getHour(dateTime1) < getHour(dateTime2))
                        return false;
                    else
                    {
                        if (getMinus(dateTime1) > getMinus(dateTime2))
                            return true;
                        else if (getMinus(dateTime1) < getMinus(dateTime2))
                            return false;
                    }
                }
            }
        }
        return true;
    }
    private int getDay(String dateTime)
    {
        int i = 0;
        for (i = 0; i < dateTime.length(); i++)
        {
            if (dateTime.charAt(i) == '/')
                break;
        }
        return Integer.parseInt(dateTime.substring(0, i));
    }
    private int getMonth(String dateTime)
    {
        int dem = 0, begin = 0, end = 0;
        for (int i = 0; i < dateTime.length(); i++)
        {
            if (dateTime.charAt(i) == '/')
            {
                dem++;
            }
            if (dem == 1) begin = i;
            if (dem == 2)
            {
                end = i;
                break;
            }
        }
        return Integer.parseInt(dateTime.substring(begin, end));
    }
    private int getYear(String dateTime)
    {
        int dem = 0, begin = 0, end = 0;
        for (int i = 0; i < dateTime.length(); i++)
        {
            if (dateTime.charAt(i) == '/')
            {
                dem++;
            }
            if (dem == 2)
            {
                begin = i + 1;
                dem++;
            }
            if (dateTime.charAt(i) == ' ')
            {
                end = i;
                break;
            }
        }
        return Integer.parseInt(dateTime.substring(begin, end));
    }
    private int getHour(String dateTime)
    {
        int begin = 0, end = 0;
        for (int i = 0; i < dateTime.length(); i++)
        {
            if (dateTime.charAt(i) == '-')
            {
                begin = i + 2;
            }
            if (dateTime.charAt(i) == ':')
            {
                end = i;
                break;
            }
        }
        return Integer.parseInt(dateTime.substring(begin, end));
    }
    private int getMinus(String dateTime)
    {
        int begin = 0, end = dateTime.length();
        for (int i = 0; i < dateTime.length(); i++)
        {
            if (dateTime.charAt(i) == ':')
            {
                begin = i + 1;
                break;
            }
        }
        return Integer.parseInt(dateTime.substring(begin, end));
    }
}

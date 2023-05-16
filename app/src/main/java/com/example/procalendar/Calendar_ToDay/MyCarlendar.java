package com.example.procalendar.Calendar_ToDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.procalendar.R;

import java.util.Calendar;

public class MyCarlendar extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView txtYear, txtMonth, txtDay, txtDayOfWeek, txtLunarMonth, txtLunarDay;

    public static MyCarlendar newInstance(String param1, String param2)
    {
        MyCarlendar fragment = new MyCarlendar();
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
        View view = inflater.inflate(R.layout.fragment_carlendar, container, false);

        txtYear = view.findViewById(R.id.txtYear);
        txtMonth = view.findViewById(R.id.txtMonth);
        txtDay = view.findViewById(R.id.txtDay);
        txtDayOfWeek = view.findViewById(R.id.txtDayOfWeek);
        txtLunarMonth = view.findViewById(R.id.txtLunarMonth);
        txtLunarDay = view.findViewById(R.id.txtLunarDay);

        Calendar cld = Calendar.getInstance();
        txtYear.setText(cld.get(Calendar.YEAR) + "");
        txtMonth.setText("Tháng " + (cld.get(Calendar.MONTH) + 1));
        txtDay.setText(cld.get(Calendar.DAY_OF_MONTH) + "");
        String dayOfWeek = "";
        switch (cld.get(Calendar.DAY_OF_WEEK))
        {
            case 2:
                dayOfWeek = "Thứ Hai";
                break;
            case 3:
                dayOfWeek = "Thứ Ba";
                break;
            case 4:
                dayOfWeek = "Thứ Tư";
                break;
            case 5:
                dayOfWeek = "Thứ Năm";
                break;
            case 6:
                dayOfWeek = "Thứ Sáu";
                break;
            case 7:
                dayOfWeek = "Thứ Bảy";
                break;
            case 1:
                dayOfWeek = "Chủ Nhật";
                break;
                default: break;
        }
        txtDayOfWeek.setText(dayOfWeek);

        LunarConvertion convert = new LunarConvertion();
        convert.SolarToLunar(cld.get(Calendar.DAY_OF_MONTH), cld.get(Calendar.MONTH) + 1, cld.get(Calendar.YEAR));
        txtLunarMonth.setText("Tháng " + convert.getMonth() + " (Âm Lịch)");
        txtLunarDay.setText(convert.getDay() + "");

        return view;
    }
}

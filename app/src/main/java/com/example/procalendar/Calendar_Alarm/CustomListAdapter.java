package com.example.procalendar.Calendar_Alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.procalendar.Calendar_Alarm.WorkAlarm;
import com.example.procalendar.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter
{
    private ArrayList<WorkAlarm> list;
    private Context context;
    private int idLayout;

    public CustomListAdapter(Context _context, int _idLayout, ArrayList<WorkAlarm> _list)
    {
        context = _context;
        list = _list;
        idLayout = _idLayout;
    }

    @Override
    public int getCount() {
        if (list.size() != 0 && !list.isEmpty()) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }
        TextView txtDate = convertView.findViewById(R.id.item_date);
        TextView txtBody = convertView.findViewById(R.id.item_body);
        final WorkAlarm workAlarm = list.get(position);
        if (!list.isEmpty() && list != null)
        {
            txtDate.setText(workAlarm.getDate());
            txtBody.setText(workAlarm.getNote());
        }
        return convertView;
    }
}

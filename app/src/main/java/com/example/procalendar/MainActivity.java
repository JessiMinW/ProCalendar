package com.example.procalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;

import com.example.procalendar.Calendar_Alarm.Setting;
import com.example.procalendar.Calendar_Note.MyNote;
import com.example.procalendar.Calendar_ToDay.MyCarlendar;
import com.example.procalendar.Calendar_Total.TotalCalendar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(MyCarlendar.newInstance("", ""));
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    public void openFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                {
                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_calendar:
                            openFragment(TotalCalendar.newInstance("", ""));
                            return true;
                        case R.id.nav_home:
                            openFragment(MyCarlendar.newInstance("", ""));
                            return true;
                        case R.id.nav_setting:
                            openFragment(Setting.newInstance("",""));
                            return true;
                        case R.id.nav_note:
                            openFragment(MyNote.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
}

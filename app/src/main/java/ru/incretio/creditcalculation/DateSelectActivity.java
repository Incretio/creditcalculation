package ru.incretio.creditcalculation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import ru.incretio.apps.creditcalculation.R;
import ru.incretio.creditcalculation.utils.DateUtils;

public class DateSelectActivity extends Activity implements OnDateChangeListener {
    CalendarView cvCalendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateselect);
        this.cvCalendar = findViewById(R.id.cvCalendar);
        this.cvCalendar.setOnDateChangeListener(this);
    }

    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent();
        intent.putExtra(getResources().getString(R.string.dateExtra), DateUtils.formatDate(year, month, dayOfMonth));
        setResult(-1, intent);
        finish();
    }
}

package ru.incretio.creditcalculation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.incretio.apps.creditcalculation.R;
import ru.incretio.creditcalculation.logics.Credit;
import ru.incretio.creditcalculation.logics.CreditGraphicRecord;
import ru.incretio.creditcalculation.utils.DateUtils;

public class TableActivity extends Activity {
    LinearLayout llTable;
    LayoutParams params;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        this.llTable = (LinearLayout) findViewById(R.id.llTable);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        TypedValue typedValue = new TypedValue();
        LinearLayout.LayoutParams monthNumberParams = new LinearLayout.LayoutParams(0, -2);
        getResources().getValue(R.dimen.monthNumberWeight, typedValue, true);
        monthNumberParams.weight = typedValue.getFloat();
        LinearLayout.LayoutParams paymentDateParams = new LinearLayout.LayoutParams(0, -2);
        getResources().getValue(R.dimen.paymentDateWeight, typedValue, true);
        paymentDateParams.weight = typedValue.getFloat();
        LinearLayout.LayoutParams defaultParams = new LinearLayout.LayoutParams(0, -1);
        getResources().getValue(R.dimen.defaultWeight, typedValue, true);
        defaultParams.weight = typedValue.getFloat();
        LinearLayout.LayoutParams parincipalBalanceParams = new LinearLayout.LayoutParams(0, -1);
        getResources().getValue(R.dimen.parincipalBalanceWeight, typedValue, true);
        parincipalBalanceParams.weight = typedValue.getFloat();
        if (GlobalData.getCredit() == null || GlobalData.getCredit().getCreditGraphic() == null) {
            Log.i(GlobalData.TAG, "Нет данных для отображения");
            return;
        }
        for (CreditGraphicRecord creditGraphicRecord : GlobalData.getCredit().getCreditGraphic()) {
            Log.i(GlobalData.TAG, String.valueOf(creditGraphicRecord.getMonthNumber()));
            LinearLayout llRow = new LinearLayout(this);
            llRow.setOrientation(LinearLayout.HORIZONTAL);
            llRow.addView(getNewTextView(String.valueOf(creditGraphicRecord.getMonthNumber())), monthNumberParams);
            llRow.addView(getNewTextView(DateUtils.formatDateWithoutDayOfMonth(creditGraphicRecord.getPaymentDate())), paymentDateParams);
            llRow.addView(getNewTextView(Credit.formatDouble(creditGraphicRecord.getPrincipalPayment())), defaultParams);
            llRow.addView(getNewTextView(Credit.formatDouble(creditGraphicRecord.getInterestPayment())), defaultParams);
            llRow.addView(getNewTextView(Credit.formatDouble(creditGraphicRecord.getMonthlyPayment())), defaultParams);
            llRow.addView(getNewTextView(Credit.formatDouble(creditGraphicRecord.getPrincipalBalance())), parincipalBalanceParams);
            this.llTable.addView(llRow, layoutParams);
        }
    }

    private TextView getNewTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setBackgroundResource(R.drawable.border);
        textView.setGravity(1);
        return textView;
    }
}

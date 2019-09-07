package ru.incretio.creditcalculation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ru.incretio.creditcalculation.logics.AnnuityCredit;
import ru.incretio.creditcalculation.logics.Credit;
import ru.incretio.creditcalculation.utils.DateUtils;

public class MainActivity extends AppCompatActivity {
    private String TEXT_CLEANED;
    private String TEXT_CREDIT_AMOUNT;
    private String TEXT_CREDIT_RATE;
    private String TEXT_DATE_EXTRA;
    private String TEXT_DONE;
    private String TEXT_EMPTY;
    private String TEXT_FIRST_PAYOUT;
    private String TEXT_INCORRECT_DATA;
    private String TEXT_ALREADY_PAID;
    private String TEXT_LOADING;
    private String TEXT_MONTHLY_PAYMENT;
    private String TEXT_OBJECT_PRICE;
    private String TEXT_OVERPAYMENT_AMOUNT;
    private String TEXT_PAYOUT_PERIOD;
    private String TEXT_START_DATE;
    private String TEXT_STOP_DATE;
    private String TEXT_TOTAL_AMOUNT_PAYMENT;
    private String TEXT_ZERO;
    private String TEXT_DECIMAL_ZERO;
    private Button btnCalc;
    private EditText edtCreditRate;
    private EditText edtFirstPayout;
    private EditText edtObjectPrice;
    private EditText edtPayoutPeriod;
    private EditText edtStartDate;
    private TextView txtCreditAmount;
    private TextView txtMonthlyPayment;
    private TextView txtOverpaymentAmount;
    private TextView txtStopDate;
    private TextView txtTotalAmountPayment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setConstant();
        setContentView((int) R.layout.activity_main);
        this.btnCalc = (Button) findViewById(R.id.btnCalc);
        this.edtObjectPrice = (EditText) findViewById(R.id.edtObjectPrice);
        this.edtFirstPayout = (EditText) findViewById(R.id.edtFirstPayout);
        this.edtPayoutPeriod = (EditText) findViewById(R.id.edtPayoutPeriod);
        this.edtCreditRate = (EditText) findViewById(R.id.edtCreditRate);
        this.edtStartDate = (EditText) findViewById(R.id.edtStartDate);
        this.txtCreditAmount = (TextView) findViewById(R.id.txtCreditAmount);
        this.txtMonthlyPayment = (TextView) findViewById(R.id.txtMonthlyPayment);
        this.txtTotalAmountPayment = (TextView) findViewById(R.id.txtTotalAmountPayment);
        this.txtOverpaymentAmount = (TextView) findViewById(R.id.txtOverpaymentAmount);
        this.txtStopDate = (TextView) findViewById(R.id.txtStopDate);
        loadData();
    }

    private void setConstant() {
        this.TEXT_CREDIT_AMOUNT = getResources().getString(R.string.creditAmount);
        this.TEXT_MONTHLY_PAYMENT = getResources().getString(R.string.monthlyPayment);
        this.TEXT_TOTAL_AMOUNT_PAYMENT = getResources().getString(R.string.totalAmountPayment);
        this.TEXT_OVERPAYMENT_AMOUNT = getResources().getString(R.string.overpaymentAmount);
        this.TEXT_STOP_DATE = getResources().getString(R.string.stopDate);
        this.TEXT_OBJECT_PRICE = getResources().getString(R.string.objectPrice);
        this.TEXT_FIRST_PAYOUT = getResources().getString(R.string.firstPayout);
        this.TEXT_PAYOUT_PERIOD = getResources().getString(R.string.payoutPeriod);
        this.TEXT_CREDIT_RATE = getResources().getString(R.string.creditAmount);
        this.TEXT_START_DATE = getResources().getString(R.string.startDate);
        this.TEXT_EMPTY = getResources().getString(R.string.empty);
        this.TEXT_CLEANED = getResources().getString(R.string.cleaned);
        this.TEXT_DONE = getResources().getString(R.string.done);
        this.TEXT_INCORRECT_DATA = getResources().getString(R.string.incorrectData);
        this.TEXT_ALREADY_PAID = getResources().getString(R.string.alreadyPaid);
        this.TEXT_LOADING = getResources().getString(R.string.loading);
        this.TEXT_ZERO = getResources().getString(R.string.zero);
        this.TEXT_DECIMAL_ZERO = getResources().getString(R.string.decimalZero);
        this.TEXT_DATE_EXTRA = getResources().getString(R.string.dateExtra);
    }

    public void onBtnClearClick(View v) {
        showMessage(this.TEXT_CLEANED);
        this.edtObjectPrice.requestFocus();
        this.edtObjectPrice.setText(this.TEXT_EMPTY);
        this.edtFirstPayout.setText(this.TEXT_EMPTY);
        this.edtPayoutPeriod.setText(this.TEXT_EMPTY);
        this.edtCreditRate.setText(this.TEXT_EMPTY);
        this.edtStartDate.setText(this.TEXT_EMPTY);
        this.txtCreditAmount.setText(this.TEXT_EMPTY);
        this.txtMonthlyPayment.setText(this.TEXT_EMPTY);
        this.txtTotalAmountPayment.setText(this.TEXT_EMPTY);
        this.txtOverpaymentAmount.setText(this.TEXT_EMPTY);
        this.txtStopDate.setText(this.TEXT_EMPTY);
        if (!(GlobalData.getCredit() == null || GlobalData.getCredit().getCreditGraphic() == null)) {
            GlobalData.getCredit().getCreditGraphic().clear();
        }
        saveData();
    }

    public void onBtnCalcClick(View v) {
        try {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.btnCalc.getWindowToken(), 2);
            if (this.edtFirstPayout.getText().toString().trim().isEmpty()) {
                this.edtFirstPayout.setText(this.TEXT_ZERO);
            }
            if (this.edtStartDate.getText().toString().trim().isEmpty()) {
                this.edtStartDate.setText(DateUtils.formatDate(new Date()));
            }
            if (Double.parseDouble(this.edtObjectPrice.getText().toString()) <= Double.parseDouble(this.edtFirstPayout.getText().toString())){
                this.txtCreditAmount.setText(this.TEXT_DECIMAL_ZERO);
                this.txtMonthlyPayment.setText(this.TEXT_DECIMAL_ZERO);
                this.txtTotalAmountPayment.setText(this.TEXT_DECIMAL_ZERO);
                this.txtOverpaymentAmount.setText(this.TEXT_DECIMAL_ZERO);
                this.txtStopDate.setText(this.edtStartDate.getText().toString());
                showMessage(this.TEXT_ALREADY_PAID);
                GlobalData.setCredit(null);
            } else {
                Credit credit = new AnnuityCredit(this.edtObjectPrice.getText().toString(), this.edtFirstPayout.getText().toString(), this.edtStartDate.getText().toString(), this.edtPayoutPeriod.getText().toString(), this.edtCreditRate.getText().toString());
                credit.creditGraphicCalculation();
                this.txtCreditAmount.setText(Credit.formatDouble(credit.getCreditAmount()));
                this.txtMonthlyPayment.setText(Credit.formatDouble(credit.getMonthlyPayment()));
                this.txtTotalAmountPayment.setText(Credit.formatDouble(credit.getTotalAmountPayment()));
                this.txtOverpaymentAmount.setText(Credit.formatDouble(credit.getOverpaymentAmount()));
                this.txtStopDate.setText(DateUtils.formatDate(credit.getStopDateCredit()));
                if (credit.getFirstPayment() == 0.0d) {
                    this.edtFirstPayout.setText(this.TEXT_ZERO);
                }
                this.edtStartDate.setText(DateUtils.formatDate(credit.getStartDate()));
                showMessage(this.TEXT_DONE);
                GlobalData.setCredit(credit);
            }
        } catch (Exception e) {
            showMessage(this.TEXT_INCORRECT_DATA);
            e.printStackTrace();
        }
        saveData();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(this.TEXT_CREDIT_AMOUNT, this.txtCreditAmount.getText().toString());
        outState.putString(this.TEXT_MONTHLY_PAYMENT, this.txtMonthlyPayment.getText().toString());
        outState.putString(this.TEXT_TOTAL_AMOUNT_PAYMENT, this.txtTotalAmountPayment.getText().toString());
        outState.putString(this.TEXT_OVERPAYMENT_AMOUNT, this.txtOverpaymentAmount.getText().toString());
        outState.putString(this.TEXT_STOP_DATE, this.txtStopDate.getText().toString());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.txtCreditAmount.setText(savedInstanceState.getString(this.TEXT_CREDIT_AMOUNT));
        this.txtMonthlyPayment.setText(savedInstanceState.getString(this.TEXT_MONTHLY_PAYMENT));
        this.txtTotalAmountPayment.setText(savedInstanceState.getString(this.TEXT_TOTAL_AMOUNT_PAYMENT));
        this.txtOverpaymentAmount.setText(savedInstanceState.getString(this.TEXT_OVERPAYMENT_AMOUNT));
        this.txtStopDate.setText(savedInstanceState.getString(this.TEXT_STOP_DATE));
    }

    private void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appAbout:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            this.edtStartDate.setText(data.getStringExtra(this.TEXT_DATE_EXTRA));
        }
    }

    public void onBtnStartDateClick(View view) {
        startActivityForResult(new Intent(this, DateSelectActivity.class), 1);
    }

    public void onBtnTableClick(View view) {
        showMessage(this.TEXT_LOADING);
        startActivity(new Intent(this, TableActivity.class));
    }

    public void onBtnGraphicClick(View view) {
        showMessage(this.TEXT_LOADING);
        startActivity(new Intent(this, GraphicActivity.class));
    }

    private void saveData() {
        Editor ed = getPreferences(0).edit();
        ed.putString(this.TEXT_OBJECT_PRICE, this.edtObjectPrice.getText().toString());
        ed.putString(this.TEXT_FIRST_PAYOUT, this.edtFirstPayout.getText().toString());
        ed.putString(this.TEXT_PAYOUT_PERIOD, this.edtPayoutPeriod.getText().toString());
        ed.putString(this.TEXT_CREDIT_RATE, this.edtCreditRate.getText().toString());
        ed.putString(this.TEXT_START_DATE, this.edtStartDate.getText().toString());
        ed.apply();
    }

    private void loadData() {
        SharedPreferences sp = getPreferences(0);
        this.edtObjectPrice.setText(sp.getString(this.TEXT_OBJECT_PRICE, this.TEXT_EMPTY));
        this.edtFirstPayout.setText(sp.getString(this.TEXT_FIRST_PAYOUT, this.TEXT_EMPTY));
        this.edtPayoutPeriod.setText(sp.getString(this.TEXT_PAYOUT_PERIOD, this.TEXT_EMPTY));
        this.edtCreditRate.setText(sp.getString(this.TEXT_CREDIT_RATE, this.TEXT_EMPTY));
        this.edtStartDate.setText(sp.getString(this.TEXT_START_DATE, this.TEXT_EMPTY));
    }
}

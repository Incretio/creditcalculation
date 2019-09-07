package ru.incretio.creditcalculation.logics;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class Credit {
    private final List<CreditGraphicRecord> creditGraphic = new ArrayList();
    private double creditRate;
    private double firstPayment;
    private double objectPrice;
    protected int payoutPeriod;
    private Date startDate;

    protected abstract double getInterestPayment(double d);

    protected abstract double getMonthlyPayment(double d, int i);

    public double getFirstPayment() {
        return this.firstPayment;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Credit(double objectPrice2, double firstPayment2, Date startDate2, int payoutPeriod2, double creditRate2) {
        this.objectPrice = objectPrice2;
        this.firstPayment = firstPayment2;
        this.startDate = startDate2;
        this.payoutPeriod = payoutPeriod2;
        this.creditRate = creditRate2;
    }

    public List<CreditGraphicRecord> getCreditGraphic() {
        return this.creditGraphic;
    }

    public Date getStopDateCredit() {
        return IncDate(this.startDate, 2, this.payoutPeriod);
    }

    private Date IncDate(Date date, int periodType, int periodValue) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(periodType, periodValue);
        return calendar.getTime();
    }

    public double getCreditAmount() {
        return this.objectPrice - this.firstPayment;
    }

    public double getMonthlyPayment() {
        return getMonthlyPayment(getCreditAmount(), 1);
    }

    public double getTotalAmountPayment() {
        return getMonthlyPayment() * ((double) this.payoutPeriod);
    }

    public double getOverpaymentAmount() {
        return getTotalAmountPayment() - getCreditAmount();
    }

    protected double getMonthCreditRate() {
        return this.creditRate / 1200.0d;
    }

    private double getPrincipalPayment(double principalBalance, int monthNumber) {
        return getMonthlyPayment(principalBalance, monthNumber) - getInterestPayment(principalBalance);
    }

    public static String formatDouble(double value) {
        return String.format("%,.2f", new Object[]{Double.valueOf(value)});
    }

    private double getNewPrincipalBalance(double oldPrincipalBalance, int monthNumber) {
        return oldPrincipalBalance - getPrincipalPayment(oldPrincipalBalance, monthNumber);
    }

    public void creditGraphicCalculation() {
        creditGraphicCalculation(getCreditAmount(), 1);
    }

    private void creditGraphicCalculation(double principalBalance, int monthNumber) {
        if (monthNumber == 1) {
            this.creditGraphic.clear();
        }
        if (monthNumber <= this.payoutPeriod) {
            CreditGraphicRecord creditGraphicRecord = new CreditGraphicRecord(monthNumber, IncDate(this.startDate, 2, monthNumber), getPrincipalPayment(principalBalance, monthNumber), getInterestPayment(principalBalance), getNewPrincipalBalance(principalBalance, monthNumber), getMonthlyPayment(principalBalance, monthNumber), 0.0d);
            this.creditGraphic.add(creditGraphicRecord);
            creditGraphicCalculation(creditGraphicRecord.getPrincipalBalance(), monthNumber + 1);
        }
    }

    static double convertStrToDoubleOrGetDefaultValue(String valueText, double ifErrorValue) {
        try {
            return Double.parseDouble(valueText);
        } catch (NumberFormatException e) {
            return ifErrorValue;
        }
    }
}

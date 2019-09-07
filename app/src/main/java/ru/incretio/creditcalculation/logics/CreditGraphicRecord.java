package ru.incretio.creditcalculation.logics;

import java.util.Date;

public class CreditGraphicRecord {
    private double InterestPayment;
    private double MonthlyPayment;
    private double PartialEarlyPayment;
    private double PrincipalBalance;
    private double PrincipalPayment;
    private int monthNumber;
    private Date paymentDate;

    public CreditGraphicRecord(int monthNumber2, Date paymentDate2, double principalPayment, double interestPayment, double principalBalance, double monthlyPayment, double partialEarlyPayment) {
        this.monthNumber = monthNumber2;
        this.paymentDate = paymentDate2;
        this.PrincipalPayment = principalPayment;
        this.InterestPayment = interestPayment;
        this.PrincipalBalance = principalBalance;
        this.MonthlyPayment = monthlyPayment;
        this.PartialEarlyPayment = partialEarlyPayment;
    }

    public int getMonthNumber() {
        return this.monthNumber;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public double getPrincipalPayment() {
        return this.PrincipalPayment;
    }

    public double getInterestPayment() {
        return this.InterestPayment;
    }

    public double getPrincipalBalance() {
        return this.PrincipalBalance;
    }

    public double getMonthlyPayment() {
        return this.MonthlyPayment;
    }

    public double getPartialEarlyPayment() {
        return this.PartialEarlyPayment;
    }
}

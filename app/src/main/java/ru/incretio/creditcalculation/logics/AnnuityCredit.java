package ru.incretio.creditcalculation.logics;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.incretio.creditcalculation.utils.DateUtils;

public class AnnuityCredit extends Credit {
    public AnnuityCredit(double objectPrice, double firstPayment, Date startDate, int payoutPeriod, double creditRate) {
        super(objectPrice, firstPayment, startDate, payoutPeriod, creditRate);
    }

    public AnnuityCredit(String objectPriceText, String firstPaymentText, String startDateText, String payoutPeriodText, String creditRateText) throws ParseException {
        this(Double.parseDouble(objectPriceText), Credit.convertStrToDoubleOrGetDefaultValue(firstPaymentText, 0.0d), DateUtils.convertStrToDateElseGetDefaultValue(startDateText, new Date()), Integer.parseInt(payoutPeriodText), Double.parseDouble(creditRateText));
    }

    public double getMonthlyPayment(double principalBanalce, int monthNumber) {
        return (getMonthCreditRate() * principalBanalce) / (1.0d - Math.pow(getMonthCreditRate() + 1.0d, (double) (-((this.payoutPeriod - monthNumber) + 1))));
    }

    public double getInterestPayment(double principalBalance) {
        return getMonthCreditRate() * principalBalance;
    }

    public static void main(String[] args) {
        Credit credit = new AnnuityCredit(2934000.0d, 800000.0d, new GregorianCalendar(2016, 9, 18).getTime(), 180, 11.9d);
        System.out.println("Сумма кредита: " + Credit.formatDouble(credit.getCreditAmount()));
        System.out.println("Ежемесячный платёж: " + Credit.formatDouble(credit.getMonthlyPayment()));
        System.out.println("Общая сумма платежа: " + Credit.formatDouble(credit.getTotalAmountPayment()));
        System.out.println("Сумма переплаты: " + Credit.formatDouble(credit.getOverpaymentAmount()));
        System.out.println("Дата закрытия кредита: " + DateUtils.formatDate(credit.getStopDateCredit()));
        credit.creditGraphicCalculation();
        for (CreditGraphicRecord creditGraphicRecord : credit.getCreditGraphic()) {
            System.out.println(String.format("%d\t%s\t%s\t%s\t%s\t%s\t%s", new Object[]{Integer.valueOf(creditGraphicRecord.getMonthNumber()), DateUtils.formatDate(creditGraphicRecord.getPaymentDate()), Credit.formatDouble(creditGraphicRecord.getPrincipalPayment()), Credit.formatDouble(creditGraphicRecord.getInterestPayment()), Credit.formatDouble(creditGraphicRecord.getPartialEarlyPayment()), Credit.formatDouble(creditGraphicRecord.getMonthlyPayment()), Credit.formatDouble(creditGraphicRecord.getPrincipalBalance())}));
        }
    }
}

package ru.incretio.creditcalculation;

import ru.incretio.creditcalculation.logics.Credit;

public class GlobalData {
    public static final String TAG = "CreditCalculation";
    private static Credit credit;

    public static Credit getCredit() {
        return credit;
    }

    public static void setCredit(Credit credit2) {
        credit = credit2;
    }
}

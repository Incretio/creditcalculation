package ru.incretio.creditcalculation.utils;

public class IntegerGroupFormat {
    public static final String regexForClear = "[,. ]";
    private char groupingSeparator = ',';

    public IntegerGroupFormat() {
        // noop
    }

    public IntegerGroupFormat(char groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    public String format(String integerString) {
        if (integerString == null || integerString.isEmpty()) {
            return "";
        }
        String fixedValue = integerString.trim().replaceAll(regexForClear, "");

        StringBuilder result = new StringBuilder();
        int k = 1;
        for (int j = fixedValue.length() - 1; j >= 0; j--) {
            char charAt = fixedValue.charAt(j);
            result.append(charAt);
            if (k % 3 == 0 && j != 0) {
                result.append(groupingSeparator);
            }
            k++;
        }
        return result.reverse().toString();
    }

    public char getGroupingSeparator() {
        return groupingSeparator;
    }
}

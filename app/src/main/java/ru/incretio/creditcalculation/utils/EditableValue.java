package ru.incretio.creditcalculation.utils;

import android.widget.EditText;

public class EditableValue {
    final private EditText editable;

    public EditableValue(EditText editable) {
        this.editable = editable;
    }

    public String getValue() {
        return editable.getText().toString().trim().replaceAll(IntegerGroupFormat.regexForClear, "");
    }

    public boolean isEmptyValue() {
        return getValue().isEmpty();
    }
}

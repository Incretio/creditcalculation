package ru.incretio.creditcalculation.watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import ru.incretio.creditcalculation.utils.IntegerGroupFormat;

public class NumberTextWatcher implements TextWatcher {

    private IntegerGroupFormat integerGroupFormat;

    private EditText editText;

    public NumberTextWatcher(EditText et) {
        this.integerGroupFormat = new IntegerGroupFormat();
        this.editText = et;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.removeTextChangedListener(this);

        int selectionStart = editText.length() - editText.getSelectionStart();

        String newValue = integerGroupFormat.format(editable.toString());
        editText.setText(newValue);

        int newSelectionPosition = newValue.length() - selectionStart;

        if (newValue.isEmpty() || newSelectionPosition < 0) {
            editText.setSelection(0);
        } else if (newSelectionPosition > newValue.length()) {
            editText.setSelection(newValue.length());
        } else {
            editText.setSelection(newSelectionPosition);
        }

        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // noop
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // noop
    }


}

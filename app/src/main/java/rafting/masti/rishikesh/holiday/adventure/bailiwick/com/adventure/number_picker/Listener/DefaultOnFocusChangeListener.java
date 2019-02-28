package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.Listener;

import android.view.View;
import android.widget.EditText;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.Enums.ActionEnum;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.NumberPicker;


/**
 * Created by travijuu on 03/06/17.
 */

public class DefaultOnFocusChangeListener implements View.OnFocusChangeListener {

    NumberPicker layout;

    public DefaultOnFocusChangeListener(NumberPicker layout) {
        this.layout = layout;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;

        if (!hasFocus) {
            try {
                int value = Integer.parseInt(editText.getText().toString());
                layout.setValue(value);

                if (layout.getValue() == value) {
                    layout.getValueChangedListener().valueChanged(value, ActionEnum.MANUAL);
                } else {
                    layout.refresh();
                }
            } catch (NumberFormatException e) {
                layout.refresh();
            }
        }
    }
}

package tyy.com.yydatetimepicker;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by shuaitao on 2018/2/26.
 */

public class YYDateTimePicker extends AlertDialog {
    protected YYDateTimePicker(Context context) {
        super(context);
    }

    protected YYDateTimePicker(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected YYDateTimePicker(Context context, int themeResId) {
        super(context, themeResId);
    }
}

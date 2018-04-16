package tyy.com.yydatetimepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.MonthDisplayHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import tyy.com.yydatetimepicker.adapter.TYYMonthAdapter;
import tyy.com.yydatetimepicker.adapter.TYYYearPickerAdapter;

/**
 * Created by shuaitao on 2018/4/5.
 */

public class TYYYearMonthPickerDialog extends AlertDialog implements DialogInterface.OnClickListener{


    private OnYearMonthSetListener mYearMonthSetListener;
    private RecyclerView mYearPikcerView;
    private RecyclerView mMonthPickerView;
    private TYYMonthAdapter mMonthAdapter;
    private TYYYearPickerAdapter mYearAdapter;
    private int mYear;
    private int mMonth;

    private TextView mMonthTitleView;
    private TextView mYearTitleView;

    /**
     * Creates a new YearMonth picker dialog for the current date using the parent
     * context's default date picker dialog theme.
     *
     * @param context the parent context
     */
    public TYYYearMonthPickerDialog(@NonNull Context context){
        this(context, 0,null,-1, -1);
    }

    /**
     * Creates a new YearMonth picker dialog for the current date.
     *
     * @param context the parent context
     * @param themeResId the resource ID of the theme against which to inflate
     *                   this dialog, or {@code 0} to use the parent
     *                   {@code context}'s default alert dialog theme
     */
    public TYYYearMonthPickerDialog(@NonNull Context context, @StyleRes int themeResId) {
        this(context, themeResId,null,-1, -1);
    }

    /**
     * Creates a new date picker dialog for the specified date using the parent
     * context's default date picker dialog theme.
     *
     * @param context the parent context
     * @param listener the listener to call when the user sets the date
     * @param year the initially selected year
     * @param month the initially selected month (0-11 for compatibility with
     *              {@link Calendar#MONTH})
     */
    public TYYYearMonthPickerDialog(@NonNull Context context, @Nullable OnYearMonthSetListener listener, int year, int month){
        this(context, 0, listener,year,month);
    }

    /**
     * Creates a new date picker dialog for the specified date using the parent
     * context's default date picker dialog theme.
     *
     * @param context the parent context
     * @param listener the listener to call when the user sets the date
     * @param year the initially selected year
     * @param month the initially selected month (0-11 for compatibility with
     *              {@link Calendar#MONTH})
     */
    public TYYYearMonthPickerDialog(@NonNull Context context, @StyleRes int themeResId,
                                    @Nullable OnYearMonthSetListener listener, int year, int month){
        this(context, themeResId, listener, null, year, month);
    }


    private TYYYearMonthPickerDialog(@NonNull Context context, @StyleRes int themeResId,
                                     @Nullable OnYearMonthSetListener listener, @Nullable Calendar calendar, int year, int monthOfYear){
        super(context, resolveDialogTheme(context, themeResId));
        mYearMonthSetListener = listener;
        mYear = year;
        mMonth = monthOfYear;

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.tyy_date_picker_dialog, null);
        setView(view);
        mMonthPickerView = view.findViewById(R.id.tyy_month_picker_view);
        mYearPikcerView = view.findViewById(R.id.tyy_year_picker_view);
        mMonthTitleView = view.findViewById(R.id.tyy_date_picker_header_month);
        mMonthTitleView.setOnClickListener(mOnTitleViewClickedListener);
        mYearTitleView = view.findViewById(R.id.tyy_date_picker_header_year);
        mYearTitleView.setOnClickListener(mOnTitleViewClickedListener);

        initYearAndMonth();
        mMonthPickerView.setLayoutManager(new GridLayoutManager(context, 3));
        mMonthAdapter = new TYYMonthAdapter(context);
        mMonthPickerView.setAdapter(mMonthAdapter);
        mMonthAdapter.setSelectedMonth(mMonth);

        mYearPikcerView.setLayoutManager(new LinearLayoutManager(context));
        mYearAdapter = new TYYYearPickerAdapter();
        mYearPikcerView.setAdapter(mYearAdapter);
        mYearAdapter.setSelectedYear(mYear);

        mYearPikcerView.scrollToPosition(mYearAdapter.getSelectedPosition());

        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.tyy_ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.tyy_cancel), this);

        mMonthAdapter.setMonthSetListener(new TYYMonthAdapter.TYYOnMonthSetListener() {
            @Override
            public void onMonthSet(int month, String title) {
                mMonth = month;
                if (mMonthTitleView != null){
                    mMonthTitleView.setText(title);
                }
            }
        });
        mYearAdapter.setOnYearSetListener(new TYYYearPickerAdapter.TYYOnYearSetListener() {
            @Override
            public void onYearSet(int selectedYear) {
                mYear = selectedYear;
                if (mYearTitleView != null)
                    mYearTitleView.setText(Integer.toString(mYear));
            }
        });

        String[] monthList = context.getResources().getStringArray(R.array.tyy_month_set);
        if (monthList != null){
            mMonthTitleView.setText(monthList[mMonth % monthList.length]);
        }
        mYearTitleView.setText(Integer.toString(mYear));

        mMonthTitleView.performClick();
    }


    static @StyleRes int resolveDialogTheme(@NonNull Context context, @StyleRes int themeResId) {
        if (themeResId == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.datePickerDialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return themeResId;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (BUTTON_POSITIVE == which){
            if (mYearMonthSetListener != null)
                mYearMonthSetListener.onYearMonthSet(this, mYear, mMonth);
        }
    }

    /**
     * The listener used to indicate the user has finished selecting a date.
     */
    public interface OnYearMonthSetListener {
        /**
         * @param dialog the dialog associated
         * @param year the selected year
         * @param month the selected month (0-11 for compatibility with
         *              {@link Calendar#MONTH})
         */
        void onYearMonthSet(TYYYearMonthPickerDialog dialog, int year, int month);
    }

    private void initYearAndMonth(){
        Calendar calendar = Calendar.getInstance();
        if (mMonth == -1)
            mMonth = calendar.get(Calendar.MONTH);
        if (mYear == -1)
            mYear = calendar.get(Calendar.YEAR);
    }

    private View.OnClickListener mOnTitleViewClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tyy_date_picker_header_month){
                mMonthTitleView.setSelected(true);
                mYearTitleView.setSelected(false);
                mMonthPickerView.setVisibility(View.VISIBLE);
                mYearPikcerView.setVisibility(View.GONE);
            }else if (v.getId() == R.id.tyy_date_picker_header_year){
                mMonthTitleView.setSelected(false);
                mYearTitleView.setSelected(true);
                mMonthPickerView.setVisibility(View.GONE);
                mYearPikcerView.setVisibility(View.VISIBLE);
            }
        }
    };
}

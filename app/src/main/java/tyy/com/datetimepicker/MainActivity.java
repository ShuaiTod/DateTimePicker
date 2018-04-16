package tyy.com.datetimepicker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import tyy.com.yydatetimepicker.TYYYearMonthPickerDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int mYear = 2019;
    private int mMonth = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_name).setOnClickListener(this);
        findViewById(R.id.tv_datepicker).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_name:{
                TYYYearMonthPickerDialog yyYearMonthPicker = new TYYYearMonthPickerDialog(this, new TYYYearMonthPickerDialog.OnYearMonthSetListener(){
                    @Override
                    public void onYearMonthSet(TYYYearMonthPickerDialog view, int year, int month) {
                        mYear = year;
                        mMonth = month;
                        ((TextView)findViewById(R.id.tv_name)).setText(Integer.toString(mYear) + "Year" + Integer.toString(month + 1) + "Month");
                    }

                }, mYear, mMonth);
                yyYearMonthPicker.show();
            }
            break;
            case R.id.tv_datepicker:{
                final Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                },calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
            break;

        }
    }
}

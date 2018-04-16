package tyy.com.yydatetimepicker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tyy.com.yydatetimepicker.R;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class TYYYearPickerAdapter extends RecyclerView.Adapter <TYYYearPickerAdapter.TYYYearItemHolder>{

    private int mMinYear = 1900;
    private int mMaxYear = 2100;
    private int mSelectedYear = -1;

    private TYYOnYearSetListener mOnYearSetListener;

    public interface TYYOnYearSetListener{
        void onYearSet(int selectedYear);
    }
    @Override
    public TYYYearItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TYYYearItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tyy_item_recycler_year_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(TYYYearItemHolder holder, int position) {
        holder.mTitleView.setText(Integer.toString(mMinYear + position));
        holder.mTitleView.setSelected(position == (mSelectedYear - mMinYear));
    }

    @Override
    public int getItemCount() {
        return mMaxYear - mMinYear;
    }

    public void setSelectedYear(int year){
        mSelectedYear = year;
    }

    public int getSelectedPosition(){
        return mSelectedYear - mMinYear;
    }

    public void setOnYearSetListener(TYYOnYearSetListener listener){
        mOnYearSetListener = listener;
    }

    public class TYYYearItemHolder extends RecyclerView.ViewHolder{
        TextView mTitleView;

        public TYYYearItemHolder(View itemView) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.tyy_tv_title);
            mTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != NO_POSITION){
                        mSelectedYear = mMinYear + position;
                        notifyDataSetChanged();
                        if (mOnYearSetListener != null){
                            mOnYearSetListener.onYearSet(mMinYear + position);
                        }
                    }
                }
            });
        }
    }
}

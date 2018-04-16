package tyy.com.yydatetimepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import tyy.com.yydatetimepicker.R;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class TYYMonthAdapter extends RecyclerView.Adapter <TYYMonthAdapter.TYYMonthItemHolder>{
    private List<String> mMonthStringList;
    private int mSelectedMonth = -1;
    private TYYOnMonthSetListener mMonthSetListener;

    public interface TYYOnMonthSetListener{
        void onMonthSet(int month, String title);
    }

    public TYYMonthAdapter(Context context){
        if (context != null){
            mMonthStringList = Arrays.asList(context.getResources().getStringArray(R.array.tyy_month_set));
        }
    }

    @Override
    public TYYMonthItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TYYMonthItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tyy_item_recycler_month_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(TYYMonthItemHolder holder, int position) {
        String monthTitle = mMonthStringList.get(position);
        holder.mTitleView.setText(monthTitle);
        holder.mTitleView.setSelected(position == this.mSelectedMonth);
    }

    @Override
    public int getItemCount() {
        return mMonthStringList != null ? mMonthStringList.size() : 0;
    }

    public void setMonthSetListener(TYYOnMonthSetListener onMonthSetListener){
        mMonthSetListener = onMonthSetListener;
    }

    public void setSelectedMonth(int month){
        this.mSelectedMonth = month;
        notifyDataSetChanged();
    }

    class TYYMonthItemHolder extends RecyclerView.ViewHolder{
        TextView mTitleView;

         TYYMonthItemHolder(View itemView) {
            super(itemView);
            mTitleView = itemView.findViewById(R.id.tyy_tv_title);
            mTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != NO_POSITION){
                        mSelectedMonth = position;
                        notifyDataSetChanged();
                        if (mMonthSetListener != null){
                            mMonthSetListener.onMonthSet(position, mMonthStringList.get(position));
                        }
                    }
                }
            });
        }
    }
}

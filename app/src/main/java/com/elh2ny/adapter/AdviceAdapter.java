package com.elh2ny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.model.AdviceModel;
import com.elh2ny.model.RoomModel;
import com.elh2ny.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<AdviceModel> mDataSet;
    private Context mContext;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.textView1)TextView textView1;

        public TextView getTextView1() {
            return textView1;
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

//                    mStackLevel++;
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
//                    if (prev != null) {
//                        ft.remove(prev);
//                    }
//                    ft.addToBackStack(null);
//
//                    // Create and show the dialog.
//                    DialogFragment newFragment = MyDialogFragment.newInstance(mStackLevel);
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("user_id", getArguments().getString(Constants.DETAIL_USER_ID));
//                    newFragment.setArguments(bundle1);
//                    newFragment.show(ft, "dialog");
                }
            });

        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public AdviceAdapter(Context mContext, List<AdviceModel> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.advice_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        //change text font
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView1());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
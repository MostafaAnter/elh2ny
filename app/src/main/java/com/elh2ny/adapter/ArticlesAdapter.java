package com.elh2ny.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.model.AdviceModel;
import com.elh2ny.model.Article;
import com.elh2ny.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<Article> mDataSet;
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
    public ArticlesAdapter(Context mContext, List<Article> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_article, viewGroup, false);

            return new ViewHolder(v);
        } else {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_loading_item, viewGroup, false);

            return new LoadingViewHolder(v);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder view, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        if (view instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) view;
            //change text font
            Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView1());
        } else {
            LoadingViewHolder viewHolder = (LoadingViewHolder) view;
            viewHolder.progressBar.setIndeterminate(true);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * for loading more view
     * start of loading more data
     */
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.progressBar) ProgressBar progressBar;
        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
}
package com.elh2ny.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa on 07/07/16.
 */
public class RoomRegisterationDialog extends DialogFragment implements View.OnClickListener {
    int mNum;

    @Nullable
    @BindView(R2.id.card_view1)
    CardView cardView1;

    @Nullable
    @BindView(R2.id.linear1)
    LinearLayout linear1;
    @Nullable
    @BindView(R2.id.linear2)
    LinearLayout linear2;
    @Nullable
    @BindView(R2.id.linear3)
    LinearLayout linear3;
    @Nullable
    @BindView(R2.id.linear4)
    LinearLayout linear4;

    @Nullable
    @BindView(R2.id.linear5)
    LinearLayout linear5;

    @Nullable
    @BindView(R2.id.editText1)
    EditText editText1;
    @Nullable
    @BindView(R2.id.editText2)
    EditText editText2;
    @Nullable
    @BindView(R2.id.editText3)
    EditText editText3;
    @Nullable
    @BindView(R2.id.editText4)
    EditText editText4;
    @Nullable
    @BindView(R2.id.editText5)
    EditText editText5;

    @Nullable
    @BindView(R2.id.text1)
    TextView text1;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static RoomRegisterationDialog newInstance(int num) {
        RoomRegisterationDialog f = new RoomRegisterationDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NO_TITLE, theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.room_registeration_dialog, container, false);
        ButterKnife.bind(this, v);
        text1.setText(getArguments().getString("name"));

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
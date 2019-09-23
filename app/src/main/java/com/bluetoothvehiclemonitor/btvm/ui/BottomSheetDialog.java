package com.bluetoothvehiclemonitor.btvm.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetoothvehiclemonitor.btvm.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mBottomSheetListener;
    ImageView mClose;
    Button mButton2;
    TextView mDialogTv;
    ConstraintLayout mDialogLayout;

    public interface BottomSheetListener {
        String updateDialogText();
        String updateButtonText();
        void onButtonClicked();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bottom_sheet, container, false);
        mDialogLayout = view.findViewById(R.id.img_dialog);
        mDialogTv = view.findViewById(R.id.tv_dialog_info);
        mDialogTv.setText(mBottomSheetListener.updateDialogText());
        mClose = view.findViewById(R.id.img_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mButton2 = view.findViewById(R.id.btn_confirm);
        mButton2.setText(mBottomSheetListener.updateButtonText());
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetListener.onButtonClicked();
                dismiss();
            }
        });

        return view;
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mBottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement BottomSheetListener");
        }
    }*/
}

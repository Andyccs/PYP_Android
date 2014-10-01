package com.humblecoder.pyp.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.humblecoder.pyp.R;

public class PYPDialog extends ProgressDialog {

    private TextView loadingText;

    private String title;

    public PYPDialog(Context context) {
        super(context);
    }

    public PYPDialog(Context context, int theme) {
        super(context, theme);
    }

    private PYPDialog(Context context, String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDimAmount(0.5f);


        setContentView(R.layout.loading_progress);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,0);

        loadingText = (TextView) findViewById(R.id.loading_text);
        setupDialog(title);
    }

    public static PYPDialog showProgress(Context context, String title) {
        PYPDialog dialog = new PYPDialog(context, title);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    private void setupDialog(String title) {
        loadingText.setText(title);
    }
}

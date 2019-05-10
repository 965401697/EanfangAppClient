package com.eanfang.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author WQ
 */
public class CopyTvUtil {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Context context;
    private TextView textView;
    private String textString;

    public CopyTvUtil(Context context, TextView textView, String textString) {
        this.context = context;
        this.textView = textView;
        this.textString = textString;
    }

    public void init() {
        if (textString==null) {
            textString = textView.getText().toString();
        }
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        myClip = ClipData.newPlainText("text", textString);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(context, textString + "\n 已复制完成： ", Toast.LENGTH_SHORT).show();
    }
}
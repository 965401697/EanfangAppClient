package com.eanfang.witget.mentionedittext.text.listener;

import android.text.Spanned;

/**
 * @author guanluocang
 * @data 2019/4/9
 * @description
 */

public interface ParserConverter {
    Spanned convert(CharSequence source);
}

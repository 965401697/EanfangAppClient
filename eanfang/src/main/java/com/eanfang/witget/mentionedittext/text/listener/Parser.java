package com.eanfang.witget.mentionedittext.text.listener;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * @author guanluocang
 * @data 2019/4/9
 * @description
 */

public class Parser implements ParserConverter, HtmlTagHandler.OnHtmlAtClickListener {

    public Parser() {
    }

    private OnParseAtClickListener onAtClickListener;

    public Parser(OnParseAtClickListener onAtClickListener) {
        this.onAtClickListener = onAtClickListener;
    }

    @Override
    public Spanned convert(CharSequence source) {
        if (TextUtils.isEmpty(source)) return new SpannableString("");
        String sourceString = source.toString();
//        sourceString = LinkUtil.replaceUrl(sourceString);

        return Html.fromHtml(sourceString, null, new HtmlTagHandler(this));
    }

    @Override
    public void onAtClik(Long mUserId) {
        onAtClickListener.onAtClik(mUserId);
    }

    public interface OnParseAtClickListener {
        void onAtClik(Long mUserId);
    }
}

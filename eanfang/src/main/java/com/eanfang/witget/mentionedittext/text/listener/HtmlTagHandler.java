package com.eanfang.witget.mentionedittext.text.listener;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.eanfang.model.security.SecurityCommonBean;

import org.xml.sax.XMLReader;

import java.util.Map;

/**
 * @author guanluocang
 * @data 2019/4/9
 * @description
 */

public class HtmlTagHandler implements Html.TagHandler {
    private static final String USER = "user";
    private static final String ID = "id";
    public static final String NAME = "name";

    private OnHtmlAtClickListener onAtClickListener;

    public HtmlTagHandler(OnHtmlAtClickListener onAtClickListener) {
        this.onAtClickListener = onAtClickListener;
    }

    public HtmlTagHandler() {
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals(USER)) {
            if (opening) {
                Map<String, String> map = HtmlParserUtil.parseStart(tag, output, xmlReader);
                Long id = Long.valueOf(map.get(ID));
                String name = map.get(NAME);
                output.setSpan(new SecurityCommonBean(name, id), output.length(), output.length(), Spannable.SPAN_MARK_MARK);
            } else {
                endTag(tag, output, xmlReader);
            }
        }
    }

    private Object getLast(Spanned text, Class kind) {
              /*
               * This knows that the last returned object from getSpans()
			         * will be the most recently added.
			         */
        Object[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            return objs[objs.length - 1];
        }
    }

    public void endTag(String tag, Editable text, XMLReader xmlReader) {
        //myfont标签不能裸着，即必须有html等标签包裹，或者前面有其他内容，否则字体大小不能起作用
        //即getlast变成从后面取，最后的内容的范围是0到文本全长度
        int len = text.length();
        Object obj = getLast(text, SecurityCommonBean.class);
        int where = text.getSpanStart(obj);
        text.removeSpan(obj);
        Log.e("AAA", "where:" + where + ",len:" + len);
        if (where != len) {
            final SecurityCommonBean t = (SecurityCommonBean) obj;

            if (null != t) {
                text.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean("isLookOther", true);
//                        bundle.putLong("mUserId", t.getMUserId());
                        onAtClickListener.onAtClik(t.getMUserId());
//                        JumpItent.jump(E, SecurityPersonalActivity.class, bundle);
//                        Toast.makeText(widget.getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public interface OnHtmlAtClickListener {
        void onAtClik(Long mUserId);
    }
}

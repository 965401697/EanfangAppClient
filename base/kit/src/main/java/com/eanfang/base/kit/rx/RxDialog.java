package com.eanfang.base.kit.rx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class RxDialog {
    public static final int POSITIVE = -1;
    public static final int NEGATIVE = -2;
    public static final int NEUTRAL = -3;

    private AlertDialog.Builder builder;
    private String positiveText;
    private String negativeText;
    private String neutralText;
    private Context context;
    private View view;
    private List<Integer> idList = new ArrayList<>();

    public RxDialog(Context mActivity) {
        this.context = mActivity;
        builder = new AlertDialog.Builder(mActivity);
    }

    public RxDialog setPositiveText(@StringRes int textId) {
        String text = context.getString(textId);
        setPositiveText(text);
        return this;
    }

    public RxDialog setPositiveText(CharSequence text) {
        positiveText = (String) text;
        return this;
    }

    public RxDialog setNegativeText(@StringRes int textId) {
        String text = context.getString(textId);
        setNegativeText(text);
        return this;
    }

    public RxDialog setNeutralText(@StringRes int textId) {
        String text = context.getString(textId);
        setNeutralText(text);
        return this;
    }

    public RxDialog setNeutralText(CharSequence text) {
        neutralText = (String) text;
        return this;
    }

    public RxDialog setNegativeText(CharSequence text) {
        negativeText = (String) text;
        return this;
    }

    public RxDialog clickView(int viewId) {
        idList.add(viewId);
        return this;
    }

    public Observable dialogToObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> e) throws Exception {
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case -1:
                                e.onNext(POSITIVE);
                                break;
                            case -2:
                                e.onNext(NEGATIVE);
                                break;
                            case -3:
                                e.onNext(NEUTRAL);
                                break;
                            default:
                                e.onNext(i);
                        }
                    }
                };
                View.OnClickListener mviewListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        e.onNext(v.getId());
                    }
                };
                if (view != null) {
                    for (int id : idList) {
                        view.findViewById(id).setOnClickListener(mviewListener);
                    }
                }
                builder.setPositiveButton(positiveText, onClickListener);
                builder.setNegativeButton(negativeText, onClickListener);
                builder.setNeutralButton(neutralText, onClickListener);
                builder.show();
            }
        });
    }


    public Flowable dialogToFlowable() {
        return Flowable.create((FlowableOnSubscribe) e -> {
            DialogInterface.OnClickListener onClickListener = (dialogInterface, i) -> {
                switch (i) {
                    case -1:
                        e.onNext(POSITIVE);
                        break;
                    case -2:
                        e.onNext(NEGATIVE);
                        break;
                    case -3:
                        e.onNext(NEUTRAL);
                        break;
                    default:
                        e.onNext(i);
                }
            };
            View.OnClickListener mviewListener = v -> e.onNext(v.getId());
            if (view != null) {
                for (int id : idList) {
                    view.findViewById(id).setOnClickListener(mviewListener);
                }
            }
            builder.setPositiveButton(positiveText, onClickListener);
            builder.setNegativeButton(negativeText, onClickListener);
            builder.setNeutralButton(neutralText, onClickListener);
            builder.show();
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * Set the title using the given resource id.
     */
    public RxDialog setTitle(@StringRes int titleId) {
        builder.setTitle(titleId);
        return this;
    }

    /**
     * Set the title displayed in the {@link Dialog}.
     */
    public RxDialog setTitle(@Nullable CharSequence title) {
        builder.setTitle(title);
        return this;
    }

    /**
     * Set the title using the custom view {@code customTitleView}.
     */
    public RxDialog setCustomTitle(@Nullable View customTitleView) {
        builder.setCustomTitle(customTitleView);
        return this;
    }

    /**
     * Set the message to display using the given resource id.
     */
    public RxDialog setMessage(@StringRes int messageId) {
        builder.setMessage(messageId);
        return this;
    }

    /**
     * Set the message to display.
     */
    public RxDialog setMessage(@Nullable CharSequence message) {
        builder.setMessage(message);
        return this;
    }

    /**
     * Set the resource id of the {@link Drawable} to be used in the title.
     */
    public RxDialog setIcon(@DrawableRes int iconId) {
        builder.setIcon(iconId);
        return this;
    }

    /**
     * Set the {@link Drawable} to be used in the title.
     */
    public RxDialog setIcon(@Nullable Drawable icon) {
        builder.setIcon(icon);
        return this;
    }

    /**
     * Set an icon as supplied by a theme attribute. e.g.
     */
    public RxDialog setIconAttribute(@AttrRes int attrId) {
        builder.setIconAttribute(attrId);
        return this;
    }

    /**
     * Sets whether the dialog is cancelable or not.  Default is true.
     */
    public RxDialog setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    /**
     * Sets the callback that will be called if the dialog is canceled.
     */
    public RxDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        builder.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * Sets the callback that will be called when the dialog is dismissed for any reason.
     */
    public RxDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        builder.setOnDismissListener(onDismissListener);
        return this;
    }

    /**
     * Sets the callback that will be called if a key is dispatched to the dialog.
     */
    public RxDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        builder.setOnKeyListener(onKeyListener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content, you will be notified of the
     * selected item via the supplied listener. This should be an array type i.e. R.array.foo
     */
    public RxDialog setItems(@ArrayRes int itemsId, final DialogInterface.OnClickListener listener) {
        builder.setItems(itemsId, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content, you will be notified of the
     * selected item via the supplied listener.
     */
    public RxDialog setItems(CharSequence[] items, final DialogInterface.OnClickListener listener) {
        builder.setItems(items, listener);
        return this;
    }

    /**
     * Set a list of items, which are supplied by the given {@link ListAdapter}, to be
     * displayed in the dialog as the content, you will be notified of the
     * selected item via the supplied listener.
     */
    public RxDialog setAdapter(final ListAdapter adapter, final DialogInterface.OnClickListener listener) {
        builder.setAdapter(adapter, listener);
        return this;
    }

    /**
     * Set a list of items, which are supplied by the given {@link Cursor}, to be
     * displayed in the dialog as the content, you will be notified of the
     * selected item via the supplied listener.
     */
    public RxDialog setCursor(final Cursor cursor, final DialogInterface.OnClickListener listener,
                              String labelColumn) {
        builder.setCursor(cursor, listener, labelColumn);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content,
     * you will be notified of the selected item via the supplied listener.
     * This should be an array type, e.g. R.array.foo. The list will have
     * a check mark displayed to the right of the text for each checked
     * item. Clicking on an item in the list will not dismiss the dialog.
     * Clicking on a button will dismiss the dialog.
     */
    public RxDialog setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems,
                                        final DialogInterface.OnMultiChoiceClickListener listener) {
        builder.setMultiChoiceItems(itemsId, checkedItems, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content,
     * you will be notified of the selected item via the supplied listener.
     * The list will have a check mark displayed to the right of the text
     * for each checked item. Clicking on an item in the list will not
     * dismiss the dialog. Clicking on a button will dismiss the dialog.
     */
    public RxDialog setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
                                        final DialogInterface.OnMultiChoiceClickListener listener) {
        builder.setMultiChoiceItems(items, checkedItems, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content,
     * you will be notified of the selected item via the supplied listener.
     * The list will have a check mark displayed to the right of the text
     * for each checked item. Clicking on an item in the list will not
     * dismiss the dialog. Clicking on a button will dismiss the dialog.
     */
    public RxDialog setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn,
                                        final DialogInterface.OnMultiChoiceClickListener listener) {
        builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content, you will be notified of
     * the selected item via the supplied listener. This should be an array type i.e.
     * R.array.foo The list will have a check mark displayed to the right of the text for the
     * checked item. Clicking on an item in the list will not dismiss the dialog. Clicking on a
     * button will dismiss the dialog.
     */
    public RxDialog setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem,
                                         final DialogInterface.OnClickListener listener) {
        builder.setSingleChoiceItems(itemsId, checkedItem, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content, you will be notified of
     * the selected item via the supplied listener. The list will have a check mark displayed to
     * the right of the text for the checked item. Clicking on an item in the list will not
     * dismiss the dialog. Clicking on a button will dismiss the dialog.
     */
    public RxDialog setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn,
                                         final DialogInterface.OnClickListener listener) {
        builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content, you will be notified of
     * the selected item via the supplied listener. The list will have a check mark displayed to
     * the right of the text for the checked item. Clicking on an item in the list will not
     * dismiss the dialog. Clicking on a button will dismiss the dialog.
     */
    public RxDialog setSingleChoiceItems(CharSequence[] items, int checkedItem, final DialogInterface.OnClickListener listener) {
        builder.setSingleChoiceItems(items, checkedItem, listener);
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog as the content, you will be notified of
     * the selected item via the supplied listener. The list will have a check mark displayed to
     * the right of the text for the checked item. Clicking on an item in the list will not
     * dismiss the dialog. Clicking on a button will dismiss the dialog.
     */
    public RxDialog setSingleChoiceItems(ListAdapter adapter, int checkedItem, final DialogInterface.OnClickListener listener) {
        builder.setSingleChoiceItems(adapter, checkedItem, listener);
        return this;
    }

    /**
     * Sets a listener to be invoked when an item in the list is selected.
     *
     * @see AdapterView#setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener)
     */
    public RxDialog setOnItemSelectedListener(final AdapterView.OnItemSelectedListener listener) {
        builder.setOnItemSelectedListener(listener);
        return this;
    }

    /**
     * Set a custom view resource to be the contents of the Dialog. The
     * resource will be inflated, adding all top-level views to the screen.
     */
    public RxDialog setView(int layoutResId) {
        //builder.setView(layoutResId);
        View view = LayoutInflater.from(context).inflate(layoutResId, null, false);
        return setView(view);
    }

    /**
     * Sets a custom view to be the contents of the alert dialog.
     */
    public RxDialog setView(View view) {
        builder.setView(view);
        this.view = view;
        return this;
    }

    /**
     * Sets the Dialog to use the inverse background, regardless of what the
     * contents is.
     */
    @Deprecated
    public RxDialog setInverseBackgroundForced(boolean useInverseBackground) {
        builder.setInverseBackgroundForced(useInverseBackground);
        return this;
    }
}

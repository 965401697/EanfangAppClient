package net.eanfang.worker.ui.adapter.viewholder;

import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-03
 * Describe :
 */
@Getter
public class NewOrderScreenViewHolder extends BaseViewHolder {
    private Button systemType;
    public NewOrderScreenViewHolder(View view) {
        super(view);
        systemType = view.findViewById(R.id.btn_system_type);
    }
}
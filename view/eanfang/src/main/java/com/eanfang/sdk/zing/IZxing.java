package com.eanfang.sdk.zing;

import android.view.KeyEvent;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public interface IZxing {
    /**
     * 设置view
     * @param barcodeScannerView
     */
    IZxing setView(DecoratedBarcodeView barcodeScannerView);

    /**
     * 设置条形码类型
     * @param a
     */
    IZxing barcodeformat(BarcodeFormat...a);
    IZxing setStatusText(String text);
    IZxing decodeContinuous(BarcodeCallback callback);
    void resume();
    void pause();
    boolean onKeyDown(int keyCode, KeyEvent event);

}

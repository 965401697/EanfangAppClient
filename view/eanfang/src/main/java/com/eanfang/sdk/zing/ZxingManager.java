package com.eanfang.sdk.zing;

import android.view.KeyEvent;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;

public class ZxingManager implements IZxing {
    private DecoratedBarcodeView barcodeScannerView;
    private static ZxingManager zxingManager;

    public static ZxingManager getInstance() {
        if (zxingManager == null) {
            zxingManager = new ZxingManager();
        }
        return zxingManager;
    }

    @Override
    public IZxing setView(DecoratedBarcodeView barcodeScannerView) {
        this.barcodeScannerView = barcodeScannerView;
        return this;
    }

    @Override
    public IZxing barcodeformat(BarcodeFormat... a) {
        Collection<BarcodeFormat> formats = Arrays.asList(a);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        return this;
    }

    @Override
    public IZxing setStatusText(String text) {
        barcodeScannerView.setStatusText(text);
        return this;
    }

    @Override
    public IZxing decodeContinuous(BarcodeCallback callback) {
        barcodeScannerView.decodeContinuous(callback);
        return this;
    }

    @Override
    public void resume() {
        barcodeScannerView.resume();
    }

    @Override
    public void pause() {
        barcodeScannerView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event);
    }
}

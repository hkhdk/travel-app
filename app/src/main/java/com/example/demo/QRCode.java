package com.example.demo;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

public class QRCode {
    public Bitmap qrcode(String content)
    {
        int width = 400;
        int height = 400;

        HashMap map = new HashMap<>();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        map.put(EncodeHintType.MARGIN, 1);

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, width, height, map);
        }catch (WriterException e){
            e.printStackTrace();
        }
        return bitmap;
    }
}

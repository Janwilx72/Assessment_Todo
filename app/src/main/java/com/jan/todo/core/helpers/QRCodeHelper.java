package com.jan.todo.core.helpers;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class QRCodeHelper
{
    public Bitmap generateQRCodeImage(final String jsonData, final int width, final int height) throws WriterException
    {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        final BitMatrix bitMatrix = qrCodeWriter.encode(jsonData, BarcodeFormat.QR_CODE, width, height, hints);

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK : android.graphics.Color.WHITE);
            }
        }

        return bitmap;
    }
}

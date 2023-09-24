package com.yzq.zxinglibrary.encode;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.p000v4.view.ViewCompat;
import android.text.TextUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;

public class CodeCreator {
    public static Bitmap createQRCode(String str, int i, int i2, Bitmap bitmap) {
        Bitmap bitmap2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = i;
        int i8 = i2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int i9 = i7 / 2;
        int i10 = i8 / 2;
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            float min = Math.min(((((float) i7) * 1.0f) / 5.0f) / ((float) bitmap.getWidth()), ((((float) i8) * 1.0f) / 5.0f) / ((float) bitmap.getHeight()));
            matrix.postScale(min, min);
            bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            bitmap2 = null;
        }
        if (bitmap2 != null) {
            int width = bitmap2.getWidth();
            int height = bitmap2.getHeight();
            i4 = width;
            i3 = height;
            i6 = (i7 - width) / 2;
            i5 = (i8 - height) / 2;
        } else {
            i6 = i9;
            i5 = i10;
            i4 = 0;
            i3 = 0;
        }
        Hashtable hashtable = new Hashtable();
        hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hashtable.put(EncodeHintType.MARGIN, 0);
        try {
            BitMatrix encode = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, i, i2, hashtable);
            int[] iArr = new int[(i7 * i8)];
            for (int i11 = 0; i11 < i8; i11++) {
                for (int i12 = 0; i12 < i7; i12++) {
                    int i13 = ViewCompat.MEASURED_STATE_MASK;
                    if (i12 >= i6 && i12 < i6 + i4 && i11 >= i5 && i11 < i5 + i3) {
                        int pixel = bitmap2.getPixel(i12 - i6, i11 - i5);
                        if (pixel != 0) {
                            i13 = pixel;
                        } else if (!encode.get(i12, i11)) {
                            i13 = -1;
                        }
                        iArr[(i11 * i7) + i12] = i13;
                    } else if (encode.get(i12, i11)) {
                        iArr[(i11 * i7) + i12] = -16777216;
                    } else {
                        iArr[(i11 * i7) + i12] = -1;
                    }
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(i7, i8, Bitmap.Config.ARGB_8888);
            createBitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
            return createBitmap;
        } catch (WriterException e) {
            System.out.print(e);
            return null;
        }
    }
}

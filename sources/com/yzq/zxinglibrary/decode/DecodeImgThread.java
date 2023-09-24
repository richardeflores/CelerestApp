package com.yzq.zxinglibrary.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.util.Hashtable;
import java.util.Vector;

public class DecodeImgThread extends Thread {
    private DecodeImgCallback callback;
    private String imgPath;
    private Bitmap scanBitmap;

    public DecodeImgThread(String str, DecodeImgCallback decodeImgCallback) {
        this.imgPath = str;
        this.callback = decodeImgCallback;
    }

    public void run() {
        super.run();
        if (!TextUtils.isEmpty(this.imgPath) && this.callback != null) {
            Bitmap bitmap = getBitmap(this.imgPath, 400, 400);
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            Hashtable hashtable = new Hashtable(2);
            Vector vector = new Vector();
            if (vector.isEmpty()) {
                vector = new Vector();
                vector.addAll(DecodeFormatManager.ONE_D_FORMATS);
                vector.addAll(DecodeFormatManager.QR_CODE_FORMATS);
                vector.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
            }
            hashtable.put(DecodeHintType.POSSIBLE_FORMATS, vector);
            multiFormatReader.setHints(hashtable);
            Result result = null;
            try {
                result = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(bitmap))));
                Log.i("解析结果", result.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null) {
                this.callback.onImageDecodeSuccess(result);
            } else {
                this.callback.onImageDecodeFailed();
            }
        }
    }

    public static Bitmap getBitmap(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        while (true) {
            i4 >>= 1;
            if (i4 < i || (i3 = i3 >> 1) < i2) {
                return i5;
            }
            i5 <<= 1;
        }
        return i5;
    }
}

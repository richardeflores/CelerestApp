package com.yzq.zxinglibrary.decode;

import com.google.zxing.Result;

public interface DecodeImgCallback {
    void onImageDecodeFailed();

    void onImageDecodeSuccess(Result result);
}

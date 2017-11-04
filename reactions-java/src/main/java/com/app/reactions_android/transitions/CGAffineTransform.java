package com.app.reactions_android.transitions;


import android.graphics.Bitmap;
import android.graphics.Matrix;

public class CGAffineTransform {
    private static int DEFAULT_SCALE = 0;
    private static int DEFAULT_ROTATE = 0;
    private static int TX = 0;
    private static int TY = 0;
    private Bitmap mImageBitMap;

    public CGAffineTransform(Bitmap mImageBitMap) {
        new CGAffineTransform(mImageBitMap, DEFAULT_SCALE, DEFAULT_ROTATE, TX, TY);
    }

    public CGAffineTransform(Bitmap mImageBitMap, int scale, int rotate, int tx, int ty) {
        this.mImageBitMap = mImageBitMap;
        DEFAULT_SCALE = scale;
        DEFAULT_ROTATE = rotate;
        TX = tx;
        TY = ty;
    }

    protected Matrix affineMatrix() {
        Matrix A = new Matrix();
        A.postTranslate(-mImageBitMap.getWidth() / 2, mImageBitMap.getHeight() / 2);
        Matrix B = new Matrix();
        B.setValues(new float[]{DEFAULT_SCALE, -DEFAULT_ROTATE, TX, DEFAULT_ROTATE, DEFAULT_SCALE,
                TY, 0.0F, 0.0F, 1.0F});
        A.postConcat(B);
        return A;
    }

}

package com.example.khalil.pixidustwebapi.Utils;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by Khalil on 2/6/2018.
 */

public class ConvertPathIntoBase64 {
    public static String ConvertPathIntoBase64(String FilePath){
        Bitmap bm = BitmapFactory.decodeFile(FilePath);
        byte[] imgbyte = getBytesFromBitmap(bm);

        String base64Image = Base64.encodeToString(imgbyte, Base64.NO_WRAP);
        return base64Image;
    }
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}

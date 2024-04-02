package com.denproj.educonnectv2.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilesUtil {


    public static String EVENT_POSTER_PATH = "eventPoster";

    public static String moveImageToInternalStorage(String path, String fileName, Uri uri, Context context) throws IOException {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        InputStream inStream = contextWrapper.getContentResolver().openInputStream(uri);
        File pathToImageFolder = contextWrapper.getDir(path, Context.MODE_PRIVATE);
        File imageFile = new File(pathToImageFolder, fileName);
        FileOutputStream fos = new FileOutputStream(imageFile);

        BitmapFactory.decodeStream(inStream).compress(Bitmap.CompressFormat.JPEG, 100, fos);

        fos.close();
        assert inStream != null;
        inStream.close();

        return path + "/" + fileName;
    }

    public static Bitmap decodeImageFromPath (String path, String filename, Context context) throws IOException {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File pathToImage = contextWrapper.getDir(path, Context.MODE_PRIVATE);
        File image = new File(pathToImage, filename);
        FileInputStream fis = new FileInputStream(image);
        Bitmap bitmap = BitmapFactory.decodeStream(fis);

        fis.close();

        return bitmap;
    }

}


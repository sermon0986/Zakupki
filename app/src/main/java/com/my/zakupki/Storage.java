package com.my.zakupki;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Storage {

//    public static boolean checkStoragePermission(Context context) {
//        return (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//    }
//
//    public static void RequestStoragePermission(Activity activity, int requestID) {
//        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestID);
//    }

    public static void SaveFavorites(Context context)
    {
//        if(checkStoragePermission(context)){
            write_favorites(context);
//        }else
    }

    public static void LoadFavorites(Context context)
    {
//        if(checkStoragePermission(context)){
            read_favorites(context);
//        }
    }

    private static void write_favorites(Context context) {
        String filePath = context.getFilesDir().getPath();

        File dir = new File(filePath);
        boolean maked = dir.mkdirs();

        File file;
        FileOutputStream outputStream;
        try {
            file = new File(filePath, "favorites");
            outputStream = new FileOutputStream(file);
            outputStream.write(Common.Favorites.ToString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean read_favorites(Context context) {
        String filePath = context.getFilesDir().getPath();
        File f = new File(filePath + "/favorites");
        if (!f.exists())
            return false;
        File file = new File(filePath, "favorites");
        String fileContent = GetStringFromFile(file);
        Common.Favorites.FromString(fileContent);
        return true;
    }

    public static String GetStringFromFile(File file)
    {
        try {
            FileInputStream inputStream = new FileInputStream(file);

            byte[] data = new byte[32768];
            int bytesRead;

            byte[] allData = new byte[0];
            int allBytes = 0;

            while ((bytesRead = inputStream.read(data)) != -1) {
                allData = ConcatenateByteArrays(allData, data, bytesRead);
                allBytes += bytesRead;
            }
            inputStream.close();
            return new String(allData, 0, allBytes);
        }catch (Exception ignore) {
            return "";
        }
    }

    private static byte[] ConcatenateByteArrays(byte[] a, byte[] b, int len) {
        byte[] result = new byte[a.length + len];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, len);
        return result;
    }
}

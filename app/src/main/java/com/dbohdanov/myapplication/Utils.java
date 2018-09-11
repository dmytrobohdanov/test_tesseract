package com.dbohdanov.myapplication;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 */
public class Utils {
    private static final String TAG = "taagUtils";

    public static String copyTessDataFiles( Context context) {
        String directryWithDataPath = "tessdata";
        String fileNewPath = Environment.getExternalStorageDirectory().toString() + "/deleteIfSee/";

        File dir = new File(fileNewPath + directryWithDataPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(TAG, "ERROR: Creation of directory " + fileNewPath + directryWithDataPath + " failed, check does Android Manifest have permission to write to external storage.");
            }
        } else {
            Log.i(TAG, "Created directory " + fileNewPath + directryWithDataPath);
        }

        try {
            String fileList[] = context.getAssets().list(directryWithDataPath);

            for (String fileName : fileList) {

                // open file within the assets folder
                // if it is not already there copy it to the sdcard
                String pathToDataFile = fileNewPath + directryWithDataPath + "/" + fileName;

                if (!(new File(pathToDataFile)).exists()) {

                    InputStream in = context.getAssets().open(directryWithDataPath + "/" + fileName);

                    OutputStream out = new FileOutputStream(pathToDataFile);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    Log.d(TAG, "Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Unable to copy files to tessdata " + e.toString());
        }

        return fileNewPath;
    }
}

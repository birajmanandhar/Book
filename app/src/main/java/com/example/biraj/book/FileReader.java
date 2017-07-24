package com.example.biraj.book;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Biraj on 7/21/2017.
 */

public class FileReader {

    public static final String internalStoragePath = Environment.getExternalStorageDirectory().toString();
    public static final String sep = "/";
    public static final String directoryName = "MeroKhata";
    public static final String userDataFileName = "BookData.json";
    public static final String applicationPath = internalStoragePath+sep+directoryName;
    public static final String bookInfoDataPath = applicationPath+sep+userDataFileName;
    public static final String userImageFolderName = "AppPics";
    public static final String userImageFolderPath = applicationPath+sep+userImageFolderName;

    //public static final String = ;

    /*
    Read all text from a file

    Here's a compact, robust idiom for Java 7, wrapped up in a utility method:

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        //byte[] encoded = Files.readAllBytes(Paths.get(path));
        //return new String(encoded, encoding);
        String content = new String(Files.readAllBytes(Paths.get("duke.java")));
    }*/


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

}


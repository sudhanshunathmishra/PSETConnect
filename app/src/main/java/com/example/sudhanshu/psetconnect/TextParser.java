package com.example.sudhanshu.psetconnect;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TextParser {

    public static List<String> textParserForSubClasses(Context context, String fileName) {

        AssetManager am = context.getAssets();
        List<String> listOfSubClasses = new ArrayList<String>();

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            String line = bReader.readLine();
            while (line != null) {
                listOfSubClasses.add(line);
                line = bReader.readLine();
            }
            bReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    return  listOfSubClasses;

    }

}


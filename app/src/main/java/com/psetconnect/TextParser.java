package com.psetconnect;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


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


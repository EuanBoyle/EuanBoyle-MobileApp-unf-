package uk.ac.stir.cs.dissertation;


import android.content.Context;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadInCSV {

    public ArrayList<Recipe> populateSearchList(Context context){

        ArrayList<Recipe> values = new ArrayList<>();

        try {
            InputStream input = context.getResources().openRawResource(R.raw.recipes);
            CSVReader reader = new CSVReader(new InputStreamReader(input));


            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                Recipe recipe = new Recipe(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4]);
                values.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;
    }
}
package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getName();

    private static final String KEY_NAME = "name";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_MAINNAME = "mainName";
    private static final String KEY_INGRRDIENTS = "ingredients";
    private static final String KEY_ALSO_KNOW_AS= "alsoKnownAs";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichObject = new JSONObject(json);
        //get the name JSONObject
        JSONObject name = sandwichObject.getJSONObject(KEY_NAME);

        //get origin
        String origin = sandwichObject.getString(KEY_PLACE_OF_ORIGIN);
        //get description
        String description = sandwichObject.getString(KEY_DESCRIPTION);
        //get image
        String image = sandwichObject.getString(KEY_IMAGE);
        //get nameString
        String nameString = name.getString(KEY_MAINNAME);
        //get ingredients into jsonArray and convert them to a list
        List<String> ingredients = toStringList(sandwichObject.getJSONArray(KEY_INGRRDIENTS));
        //get alsoKnownAs into jsonArray and convert them to a list
        List<String> alsoKnownAs = toStringList(name.getJSONArray(KEY_ALSO_KNOW_AS));

        // return the Sandwich class which takes six parameters.
        return new Sandwich(nameString, alsoKnownAs, origin, description, image, ingredients);
    }

    /**
     * Method toStringList takes in one parameter of type JSONArray,
     * iterates through the jsonArray and converts it to String list
     * @param jsonArray
     * @return list
     *
     */
    private static List<String> toStringList(JSONArray jsonArray) {
        List<String> stringList = new ArrayList<>();
        try {
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                stringList.add(jsonArray.getString(i));
            }

        } catch (JSONException e ){

            Log.e(TAG,"Error parsing",e);

        }
        return stringList;
    }
}

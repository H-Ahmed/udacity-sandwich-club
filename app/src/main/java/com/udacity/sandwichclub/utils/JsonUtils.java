package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Log.d("JsonUtlis", json);
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichObject = new JSONObject(json);

        JSONObject name = sandwichObject.getJSONObject(NAME);

        String mainName = name.getString(MAIN_NAME);
        sandwich.setMainName(mainName);

        JSONArray alsoKnownAsJsonArray = name.getJSONArray(ALSO_KNOWN_AS);
        List<String> alsoKnownAs = new ArrayList<>();
        if(alsoKnownAsJsonArray != null) {
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }
        }
        sandwich.setAlsoKnownAs(alsoKnownAs);

        String placeOfOrigin = sandwichObject.getString(PLACE_OF_ORIGIN);
        sandwich.setPlaceOfOrigin(placeOfOrigin);

        String description = sandwichObject.getString(DESCRIPTION);
        sandwich.setDescription(description);

        String image = sandwichObject.getString(IMAGE);
        sandwich.setImage(image);

        JSONArray ingredientsJsonArray = sandwichObject.getJSONArray(INGREDIENTS);
        List<String> ingredients = new ArrayList<>();
        if(ingredientsJsonArray != null){
            for (int i = 0; i < ingredientsJsonArray.length() ; i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }
        }
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}

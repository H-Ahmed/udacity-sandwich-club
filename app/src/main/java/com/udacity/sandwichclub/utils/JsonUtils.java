package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Log.d("JsonUtlis", json);
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichObject = new JSONObject(json);

        JSONObject name = sandwichObject.getJSONObject("name");

        String mainName = name.getString("mainName");
        sandwich.setMainName(mainName);

        JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        if(alsoKnownAsJsonArray != null) {
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }
        }
        sandwich.setAlsoKnownAs(alsoKnownAs);

        String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
        sandwich.setPlaceOfOrigin(placeOfOrigin);

        String description = sandwichObject.getString("description");
        sandwich.setDescription(description);

        String image = sandwichObject.getString("image");
        sandwich.setImage(image);

        JSONArray ingredientsJsonArray = sandwichObject.getJSONArray("ingredients");
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

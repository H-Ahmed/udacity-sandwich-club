package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        TextView mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        TextView mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        TextView mAlsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);

        LinearLayout mDescriptionLinearLayout = (LinearLayout) findViewById(R.id.description_layout);
        LinearLayout mPlaceOfOriginLinearLayout  = (LinearLayout) findViewById(R.id.place_of_origin_layout);
        LinearLayout mIngredientsLinearLayout  = (LinearLayout) findViewById(R.id.ingredients_layout);
        LinearLayout mAlsoKnownLinearLayout  = (LinearLayout) findViewById(R.id.also_known_as_layout);

        String description = sandwich.getDescription();
        if(description == null || description.equals("")){
            mDescriptionLinearLayout.setVisibility(View.GONE);
        }else {
            mDescriptionTextView.setText(description);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if(placeOfOrigin == null || placeOfOrigin.equals("")){
            mPlaceOfOriginLinearLayout.setVisibility(View.GONE);
        }else {
            mPlaceOfOriginTextView.setText(placeOfOrigin);
        }

        List<String> ingredients = sandwich.getIngredients();
        if(ingredients.size() != 0) {
            for (int i = 0; i < ingredients.size(); i++) {
                if(i == ingredients.size() - 1) {
                    mIngredientsTextView.append(ingredients.get(i));
                }else {
                    mIngredientsTextView.append(ingredients.get(i) + "\n");
                }
            }
        }else {
            mIngredientsLinearLayout.setVisibility(View.GONE);
        }


        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if(alsoKnownAs.size() != 0) {
            for (int i = 0; i < alsoKnownAs.size(); i++) {
                if(i == alsoKnownAs.size() - 1){
                    mAlsoKnownTextView.append(alsoKnownAs.get(i));
                }else {
                    mAlsoKnownTextView.append(alsoKnownAs.get(i) + "\n");
                }
            }
        }else {
            mAlsoKnownLinearLayout.setVisibility(View.GONE);
        }
    }
}

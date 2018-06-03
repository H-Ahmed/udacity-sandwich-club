package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.description_tv)
    TextView mDescriptionTextView;
    @BindView(R.id.origin_tv)
    TextView mPlaceOfOriginTextView;
    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTextView;
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


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

        String description = sandwich.getDescription();
        if (description == null || description.equals("")) {
            mDescriptionTextView.setText(getString(R.string.detail_no_data));
        } else {
            mDescriptionTextView.setText(description);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.equals("")) {
            mPlaceOfOriginTextView.setText(getString(R.string.detail_no_data));
        } else {
            mPlaceOfOriginTextView.setText(placeOfOrigin);
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() != 0) {
            for (int i = 0; i < ingredients.size(); i++) {
                if (i == ingredients.size() - 1) {
                    mIngredientsTextView.append(ingredients.get(i));
                } else {
                    mIngredientsTextView.append(ingredients.get(i) + "\n");
                }
            }
        } else {
            mIngredientsTextView.setText(getString(R.string.detail_no_data));
        }


        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.size() != 0) {
            for (int i = 0; i < alsoKnownAs.size(); i++) {
                if (i == alsoKnownAs.size() - 1) {
                    mAlsoKnownTextView.append(alsoKnownAs.get(i));
                } else {
                    mAlsoKnownTextView.append(alsoKnownAs.get(i) + "\n");
                }
            }
        } else {
            mAlsoKnownTextView.setText(getString(R.string.detail_no_data));
        }
    }
}

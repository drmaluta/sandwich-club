package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mTvDescription, mTvOrigin, mTvAsKnownAs , mTvIngredient;
    private ImageView ingredientsIv;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ingredientsIv = findViewById(R.id.image_iv);
        mTvDescription = findViewById(R.id.description_tv);
        mTvOrigin = findViewById(R.id.origin_tv);
        mTvAsKnownAs = findViewById(R.id.also_known_tv);
        mTvIngredient = findViewById(R.id.ingredients_tv);

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
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            Log.e(TAG, getString(R.string.error), e);
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.problem)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mTvDescription.setText(sandwich.getDescription().equals("") ? getString(R.string.detail_error_message) : sandwich.getDescription());
        mTvOrigin.setText(sandwich.getPlaceOfOrigin().equals("") ? getString(R.string.detail_error_message) : sandwich.getPlaceOfOrigin());
        displayList(mTvAsKnownAs,sandwich.getAlsoKnownAs());
        displayList(mTvIngredient,sandwich.getIngredients());
    }

    private void displayList(TextView textView, List<String> stringList){
        if (stringList.size() == 0){
            textView.append(getString(R.string.detail_error_message)); // if we have no data display the string
        } else {
            for(int i = 0; i < stringList.size(); i++){
                if(i == stringList.size() - 1){
                    textView.append(stringList.get(i));
                } else {
                    textView.append(stringList.get(i) + "\n");
                }
            }
        }
    }
}

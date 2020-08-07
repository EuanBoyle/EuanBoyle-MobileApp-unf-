package uk.ac.stir.cs.dissertation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Objects;

public class RecipePage extends AppCompatActivity {

    private ImageButton searchButton;
    private ImageButton homeButton;
    private ImageButton favRecipeButton;
    private TextView titlelbl;
    private TextView durationlbl;
    private TextView servinglbl;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Retrieve info from bundle
        Bundle bundle = getIntent().getExtras();
        final ArrayList<String> recipeInfo;

        titlelbl = findViewById(R.id.titlelbl);
        durationlbl = findViewById(R.id.durationlbl);
        servinglbl = findViewById(R.id.servinglbl);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        if(bundle != null){
            recipeInfo = bundle.getStringArrayList("recipe");
            if (recipeInfo != null) {
                titlelbl.setText(recipeInfo.get(0));
                servinglbl.setText(recipeInfo.get(1));
                durationlbl.setText(recipeInfo.get(2));
                viewModel.setInstructions(recipeInfo.get(3));
                viewModel.setIngredients(recipeInfo.get(4));
            }
        }

        favRecipeButton = findViewById(R.id.favRecipeButton);
        favRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "SELECT title " +
                               "FROM favourites " +
                               "WHERE title = ?";

                Cursor c = db.rawQuery(query, new String[]{titlelbl.getText().toString()});

                if (c.getCount() > 0){
                    db.execSQL("DELETE FROM favourites WHERE title = '" + titlelbl.getText().toString()+ "'");
                    Toast.makeText(RecipePage.this, "Recipe removed from favourites", Toast.LENGTH_LONG).show();
                }else{
                    ContentValues content = new ContentValues();
                    content.put("title", titlelbl.getText().toString());
                    content.put("servings", servinglbl.getText().toString());
                    content.put("duration", durationlbl.getText().toString());
                    content.put("instructions", viewModel.getInstructions());
                    content.put("ingredients", viewModel.getIngredients());

                    db.insert("favourites", null, content);

                    Toast.makeText(RecipePage.this, "Recipe added to favourites", Toast.LENGTH_LONG).show();
                }

                c.close();
            }
        });

        homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        searchButton = findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePage.this, SearchPage.class);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Instructions"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImplement();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImplement();
    }

    // This will automatically take the user back to the previous activity when the user clicks on
    // the "up" button.
    private Intent getParentActivityIntentImplement() {
        Intent intent = null;

        Bundle extras = getIntent().getExtras();
        String goToIntent = extras.getString("goto");

        // Determine which activity we came from with the bundle.
        if (goToIntent.equals("SearchPage")) {
            intent = new Intent(this, SearchPage.class);

            // Set flags to reuse the previous activity instead of creating a new activity instance.
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else if(goToIntent.equals("FavouritePage")) {
            intent = new Intent(this, FavouritePage.class);
        }
        return intent;
    }
}

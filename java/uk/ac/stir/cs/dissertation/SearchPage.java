package uk.ac.stir.cs.dissertation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SearchPage extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ImageButton homeButton;
    ReadInCSV csv;
    ArrayList<Recipe> list;
    SearchView searchView;
    ListView listView;
    ListViewAdapter adapter;
    ImageButton favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        //populate
        csv = new ReadInCSV();
        list = csv.populateSearchList(this);

        //list view
        listView = findViewById(R.id.listview);
        adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);

        //set up searchView
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Recipes");


        homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("goto", "SearchPage");

                ArrayList<String> recipe = new ArrayList<>();
                recipe.add(adapter.getItem(position).getTitle());
                recipe.add(adapter.getItem(position).getServings());
                recipe.add(adapter.getItem(position).getDuration());
                recipe.add(adapter.getItem(position).getInstructions());
                recipe.add(adapter.getItem(position).getIngredients());

                bundle.putStringArrayList("recipe", recipe);

                Intent intent = new Intent(SearchPage.this, RecipePage.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        favButton = findViewById(R.id.btnFav);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPage.this, FavouritePage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
package uk.ac.stir.cs.dissertation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class PantryAddItem extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Ingredient> list;
    SearchView searchView;
    ListView listView;
    ListViewPantryAddItemAdapter adapter;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_add_item);

        //populate
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM ingredients";
        Cursor c = db.rawQuery(query, null);

        list = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();

            while (c.moveToNext()) {

                Ingredient ingredient = new Ingredient(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name")), c.getDouble(c.getColumnIndex("price")));

                list.add(ingredient);
            }
        }else{
            Toast.makeText(this, c.getCount(), Toast.LENGTH_SHORT).show();
        }
        c.close();

        //list view
        listView = findViewById(R.id.listview);
        adapter = new ListViewPantryAddItemAdapter(this, list);
        listView.setAdapter(adapter);

        //set up searchView
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Ingredients");

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
}
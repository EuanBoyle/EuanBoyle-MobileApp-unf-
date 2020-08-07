package uk.ac.stir.cs.dissertation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class PantryPage extends AppCompatActivity {

    ListView listView;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    ListViewIngredientsAdapter adapter;
    ArrayList<Ingredient> list;
    int pos;

    ImageButton btnHome;
    ImageButton btnSearch;
    ImageButton btnFav;

    Button addIngredientBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_page);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        listView = findViewById(R.id.listView);
        registerForContextMenu(listView);

        list = data();

        adapter = new ListViewIngredientsAdapter(this, list);
        listView.setAdapter(adapter);


        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryPage.this, SearchPage.class);
                startActivity(intent);
            }
        });

        btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryPage.this, FavouritePage.class);
                startActivity(intent);
            }
        });

        addIngredientBtn = findViewById(R.id.addIngredientbtn);
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryPage.this, PantryAddItem.class);
                startActivity(intent);
            }
        });

    }

    public ArrayList<Ingredient> data(){
        ArrayList<Ingredient> list = new ArrayList<>();


        String query = "SELECT * from pantry";
        Cursor c = db.rawQuery(query, null);

        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                list.add(new Ingredient(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name")), c.getDouble(c.getColumnIndex("price"))));
            }
        } else {
            Toast.makeText(this, "Your pantry is empty.",
                    Toast.LENGTH_SHORT).show();
        }
        c.close();
        return list;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            pos = info.position;
            menu.setHeaderTitle(adapter.getItem(pos).getName());
            menu.add("Remove");
        }
    }

    public boolean onContextItemSelected(MenuItem item){

        if(item.getTitle().equals("Remove")){
            db.execSQL("DELETE FROM pantry WHERE id = " + adapter.getItem(pos).getId());
            list.remove(pos);
            adapter.notifyDataSetChanged();
        }
        return false;
    }
}

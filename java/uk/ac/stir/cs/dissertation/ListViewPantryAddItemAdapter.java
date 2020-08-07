package uk.ac.stir.cs.dissertation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewPantryAddItemAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Ingredient> ingredientsList = null;
    private ArrayList<Ingredient> arraylist;
    ViewHolder holder;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ListViewPantryAddItemAdapter(Context context, List<Ingredient> ingredientsList) {
        mContext = context;
        this.ingredientsList = ingredientsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(ingredientsList);

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    /*public void remove(String item) {
        arraylist.remove(item);
    }*/

    public class ViewHolder {
        TextView name;
        TextView price;
        Button add;
    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredientsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.pantry_add_item_row, null);
            // Locate the TextViews in list_row.xml
            holder.name = view.findViewById(R.id.ingredientNamelbl);
            holder.price = view.findViewById(R.id.pricelbl);
            holder.add = view.findViewById(R.id.addItemBtn);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(ingredientsList.get(position).getName());
        holder.price.setText(String.format("%.2f", ingredientsList.get(position).getPrice()));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues content = new ContentValues();
                content.put("name", ingredientsList.get(position).getName());
                content.put("price", ingredientsList.get(position).getPrice());
                db.insert("pantry", null, content);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ingredientsList.clear();
        if (charText.length() == 0) {
            ingredientsList.addAll(arraylist);
        } else {
            for (Ingredient wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ingredientsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}



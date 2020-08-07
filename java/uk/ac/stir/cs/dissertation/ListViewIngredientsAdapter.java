package uk.ac.stir.cs.dissertation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ListViewIngredientsAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Ingredient> ingredientsList = null;
    private ArrayList<Ingredient> arraylist;
    ViewHolder holder;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ListViewIngredientsAdapter(Context context, List<Ingredient> ingredientsList) {
        mContext = context;
        this.ingredientsList = ingredientsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(ingredientsList);

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void remove(Ingredient item) {
        arraylist.remove(item);
    }

    public class ViewHolder {
        TextView name;
        TextView price;
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
            view = inflater.inflate(R.layout.ingredient_row, null);
            // Locate the TextViews in list_row.xml
            holder.name = view.findViewById(R.id.ingredientNamelbl);
            holder.price = view.findViewById(R.id.pricelbl);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText( ingredientsList.get(position).getName());
        holder.price.setText(String.format("%.2f", ingredientsList.get(position).getPrice()));

        return view;
    }
}


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

public class ListViewFavouritesAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Recipe> recipeList = null;
    private ArrayList<Recipe> arraylist;
    ViewHolder holder;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public ListViewFavouritesAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        this.recipeList = recipeList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(recipeList);

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void remove(Recipe item) {
        arraylist.remove(item);
    }

    public class ViewHolder {
        TextView title;
        TextView duration;
        TextView servings;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Recipe getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.favourite_row, null);
            // Locate the TextViews in list_row.xml
            holder.title = view.findViewById(R.id.recipeNamelbl);
            holder.servings = view.findViewById(R.id.servelbl);
            holder.duration = view.findViewById(R.id.durationlbl);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.title.setText(recipeList.get(position).getTitle());
        holder.servings.setText(recipeList.get(position).getServings());
        holder.duration.setText(recipeList.get(position).getDuration());
        return view;
    }
}

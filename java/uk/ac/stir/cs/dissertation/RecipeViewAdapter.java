package uk.ac.stir.cs.dissertation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeViewAdapter extends BaseAdapter {
    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    ArrayList<String> list;

    public RecipeViewAdapter(Context context, ArrayList<String> instructionsList) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.list = instructionsList;
    }

    public class ViewHolder {
        TextView mTextView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final RecipeViewAdapter.ViewHolder holder;
        if (view == null) {
            holder = new RecipeViewAdapter.ViewHolder();
            view = inflater.inflate(R.layout.recipe_row, null);
            // Locate the TextViews in list_row.xml
            holder.mTextView = view.findViewById(R.id.textBox);

            view.setTag(holder);
        } else {
            holder = (RecipeViewAdapter.ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.mTextView.setText(list.get(position));

        return view;
    }
}

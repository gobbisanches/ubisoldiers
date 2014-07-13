package com.github.gobbisanches.ubisoldiers.app;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.github.gobbisanches.ubisoldiers.mechanics.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanches on 12/07/2014.
 */
public class ItemListFragment extends ListFragment {
    private static final String LIST_ID = "com.github.gobbisanches.ubisoldier.app.ItemListFragment.ListId";
    private static final String ITEMS = "com.github.gobbisanches.ubisoldier.app.ItemListFragment.Items";

    public interface ItemListFragmentListener {
        public void onSelectItem(int id, Item item, int position, ArrayList<Item> allItems);
    }

    private class ItemArrayAdapter extends ArrayAdapter<Item> {
        private ItemArrayAdapter(Context context, int resource, List<Item> objects) {
            super(context, resource, objects);

            Log.d("UBISOLDIERS", "Objects = " + objects.toString());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_item_layout, null);
            }

            Item item = getItem(position);
            ImageView itemIconView = (ImageView) convertView.findViewById(R.id.itemIconView);
            TextView nameView = (TextView) convertView.findViewById(R.id.nameView);
            TextView stat1TextView = (TextView) convertView.findViewById(R.id.stat1TextView);
            ImageView stat1IconView = (ImageView) convertView.findViewById(R.id.stat1IconView);
            TextView stat2TextView = (TextView) convertView.findViewById(R.id.stat2TextView);
            ImageView stat2IconView = (ImageView) convertView.findViewById(R.id.stat2IconView);

            Log.d("UBISOLDIERS", "Item = " + item.toString());

            itemIconView.setImageResource(ResourceManager.getResourceForItem(item));
            nameView.setText(item.getName());
            if ((item.getAttack() > 0) && item.getDefense() > 0) {
                setStat(item.getAttack(), ResourceManager.getResourceForAttack(), stat1TextView, stat1IconView);
                setStat(item.getDefense(), ResourceManager.getResourceForDefense(), stat2TextView, stat2IconView);
            } else if ((item.getAttack() > 0) && item.getDefense() == 0) {
                setStat(item.getAttack(), ResourceManager.getResourceForAttack(), stat1TextView, stat1IconView);
                hideStat(stat2TextView, stat2IconView);
            } else if ((item.getAttack() == 0) && item.getDefense() > 0) {
                setStat(item.getDefense(), ResourceManager.getResourceForDefense(), stat1TextView, stat1IconView);
                hideStat(stat2TextView, stat2IconView);
            } else {
                throw new RuntimeException("Invalid item state");
            }

            return convertView;
        }

        private void setStat(double value, int iconResId, TextView textView, ImageView iconView) {
            textView.setText(String.valueOf((int) Math.floor(value)));
            iconView.setImageResource(iconResId);

            textView.setVisibility(View.VISIBLE);
            iconView.setVisibility(View.VISIBLE);
        }

        private void hideStat(TextView textView, ImageView iconView) {
            textView.setVisibility(View.INVISIBLE);
            iconView.setVisibility(View.INVISIBLE);
        }
    }

    private ArrayList<Item> items;
    private ItemArrayAdapter adapter;
    private int listId;
    private ItemListFragmentListener listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listId = getArguments().getInt(LIST_ID);
        items = (ArrayList<Item>) getArguments().getSerializable(ITEMS);
        adapter = new ItemArrayAdapter(getActivity(), R.layout.item_list_item_layout, items);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        listener.onSelectItem(listId, adapter.getItem(position), position, items);
    }

    private void setItemListListener(ItemListFragmentListener listener) {
        this.listener = listener;
    }

    public static ItemListFragment newInstance(int listId, ArrayList<Item> items, ItemListFragmentListener listener) {
        Log.d("UBISOLDIERS", "In newInstance: objects = " + items.toString());

        Bundle args = new Bundle();
        args.putInt(LIST_ID, listId);
        args.putSerializable(ITEMS, items);

        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(args);
        fragment.setItemListListener(listener);
        return fragment;
    }
}

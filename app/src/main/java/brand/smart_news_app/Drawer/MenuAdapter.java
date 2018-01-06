package brand.smart_news_app.Drawer;

import android.graphics.Color;
import android.service.quicksettings.Tile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import brand.smart_news_app.R;
import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

import static brand.smart_news_app.R.color.colorPrimary;
import static brand.smart_news_app.R.drawable.pic;

/**
 * Created by Hind on 12/11/2017.
 */

public class MenuAdapter extends BaseAdapter {

    private ArrayList<String> mOptions = new ArrayList<>();
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();
    int[] pic;
    public static TextView title;
    public ImageView im;

    public MenuAdapter(ArrayList<String> options) {
        mOptions = options;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    public void setViewSelected(int position, boolean selected) {

        // Looping through the options in the menu
        // Selecting the chosen option
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                mOptionViews.get(i).setSelected(selected);
            } else {
                mOptionViews.get(i).setSelected(!selected);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);

        pic = new int[]{R.mipmap.home, R.mipmap.search, R.mipmap.saved, R.mipmap.user, R.mipmap.notification, R.mipmap.phone, R.mipmap.terms, R.mipmap.info};

        // Using the DuoOptionView to easily recreate the demo
        final DuoOptionView optionView;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row, parent, false);
        if (convertView == null) {
            title = (TextView) convertView.findViewById(R.id.textRow);
            im = (ImageView) convertView.findViewById(R.id.imageView2);
            title.setText(mOptions.get(position));
            im.setImageResource(pic[position]);
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row, parent, false);
            title = (TextView) convertView.findViewById(R.id.textRow);
            im = (ImageView) convertView.findViewById(R.id.imageView2);
            title.setText(mOptions.get(position));
            im.setImageResource(pic[position]);

        }



        return convertView;
    }

}

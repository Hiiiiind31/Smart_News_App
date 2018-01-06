package brand.smart_news_app.Tabs_Fragments.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import brand.smart_news_app.Activities.Activity_Search;
import brand.smart_news_app.R;

public class fragment_discover extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_fragment_discover, container, false);

        TextView search = inflate.findViewById(R.id.goto_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_Search.class);
                startActivity(i);
            }
        });

        return inflate;
    }

}


package com.example.athmos.rssfeed.View;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.athmos.rssfeed.R;

/**
 * Created by athmos on 26/01/17.
 */

public class MenuAdapter extends ArrayAdapter<String> {
    private String                      TAG = "MenuAdapter";
    private final AppActivity           myActivity;
    private String[]                    Options;
    private ListView Menu;

    public                              MenuAdapter(AppActivity activity, ListView menu) {
        super(activity, 0);
        Log.d(TAG, "onCreate");
        myActivity = activity;
        Options = getOptions();
        Menu = menu;
        initMenuDependOnRole();
        Menu.setOnItemClickListener(listOnItemClick());
    }

    private void                        initMenuDependOnRole() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myActivity, R.layout.item_menu, Options);
        Menu.setAdapter(adapter);
    }

    private String[]                    getOptions() {
        return new String[]{
                "Mes flux RSS", "Ajouter un flux", "Deconnexion"
        };
    }
    private AdapterView.OnItemClickListener listOnItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        myActivity.onClickGetFlux();
                        break;
                    case 1:
                        myActivity.onClickAddFlux();
                        break;
                    case 2:
                        myActivity.onClickDeco();
                        break;
                }
            }
        };
    }
}

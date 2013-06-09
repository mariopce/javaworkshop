package com.mobica.supercontacts;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class MainActivity extends FragmentActivity {

    private static final String CONTACTS_TAG = "contacts";
	private ContactListFragment contactFragment;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactFragment = (ContactListFragment) getSupportFragmentManager().findFragmentByTag(CONTACTS_TAG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(this);
        sv.setBackgroundColor(getResources().getColor(android.R.color.white));
        sv.setOnQueryTextListener(contactFragment);
        searchItem.setActionView(sv);
        return true;
    }
    
}

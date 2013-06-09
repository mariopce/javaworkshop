package com.mobica.supercontacts;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.SearchView.OnQueryTextListener;

public class ContactListFragment extends ListFragment implements OnQueryTextListener {

	private static final int ID = 0;
	private ContactsCursorAdapter mAdapter;
	private String mFilter = "";
	private LoaderCallbacks<Cursor> mLoaderCallback;
	public ContactListFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText("No contacts");
		mAdapter = new ContactsCursorAdapter(getActivity(), null, false);
		setListAdapter(mAdapter);
		
		mLoaderCallback = new LoaderCallbacks<Cursor>() {
			
			// These are the Contacts rows that we will retrieve.
		    final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
		    		ContactsContract.CommonDataKinds.Phone._ID,
		        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
		        ContactsContract.CommonDataKinds.Phone.NUMBER
		    };
			
			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				mAdapter.swapCursor(null);
			}
			
			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
				mAdapter.swapCursor(cursor);
			}
			
			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle options) {
				Uri baseUri =  ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
				if (mFilter.length()>0){
					baseUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,
			                  Uri.encode(mFilter));
				}
		        // Now create and return a CursorLoader that will take care of
		        // creating a Cursor for the data being displayed.
		        String select = "((" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOTNULL) AND ("
		                + ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + "=1) AND ("
		                + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " != '' ))";
		        return new CursorLoader(getActivity(), baseUri,
		                CONTACTS_SUMMARY_PROJECTION, select, null,
		                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
			}
		};
		getLoaderManager().initLoader(ID,null, mLoaderCallback).forceLoad();
	}

	@Override
	public boolean onQueryTextChange(String query) {
		   // Called when the action bar search text has changed.  Update
        // the search filter, and restart the loader to do a new query
        // with this filter.
        mFilter = query;
        getLoaderManager().restartLoader(0, null, mLoaderCallback);
        return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		 // Don't care about this.
        return true;
	}
	
	
}

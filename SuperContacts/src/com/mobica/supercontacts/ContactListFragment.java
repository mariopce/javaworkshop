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

public class ContactListFragment extends ListFragment {

	private static final int ID = 0;
	private ContactsCursorAdapter mAdapter;
	private String mFilter = "";
	public ContactListFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText("No contacts");
		mAdapter = new ContactsCursorAdapter(getActivity(), null, false);
		setListAdapter(mAdapter);
		LoaderManager.LoaderCallbacks<Cursor> loaderCallback = new LoaderCallbacks<Cursor>() {
			
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
					baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI;
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
		getLoaderManager().initLoader(ID,null, loaderCallback).forceLoad();
	}
	
	
}

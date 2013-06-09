package com.mobica.supercontacts;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobica.supercontacts.model.ContactItem;

public class ContactAdapter extends ArrayAdapter<ContactItem> {

	// never ContactAdapter.this.getName(); proguard
	private static String TAG = "ContactAdapter";
	private LayoutInflater inflator;

	public ContactAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		inflator = LayoutInflater.from(context);
	}

	static class ViewHolder {
		TextView nameView;
		TextView phoneView;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		try {
			if (convertView == null) {
				convertView = this.inflator.inflate(R.layout.contact_item_layout, null);
				holder = new ViewHolder();
				holder.nameView = (TextView) convertView.findViewById(R.id.name_display);
				holder.phoneView = (TextView) convertView.findViewById(R.id.phone_display);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ContactItem contact = (ContactItem) getItem(position);
			holder.nameView.setText(contact.mDisplayName);
			holder.phoneView.setText(contact.mPhone);
		} catch (Exception e) {
			Log.e(TAG, e.toString(), e);
		}
		return convertView;
	}

}

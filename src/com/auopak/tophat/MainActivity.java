package com.auopak.tophat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
	ArrayList<String> listItems = new ArrayList<String>();
	ImageButton addItemBtn;
	Button pickItemBtn;
	ListView itemList;
	EditText itemEditText;
	ArrayAdapter<String> listAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addItemBtn = (ImageButton) findViewById(R.id.addItemBtn);
		pickItemBtn = (Button) findViewById(R.id.pickItemBtn);
		itemList = (ListView) findViewById(R.id.itemList);
		itemEditText = (EditText) findViewById(R.id.itemEditText);

		listAdapter = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_list_item_1, listItems);
		listAdapter.setNotifyOnChange(true);
		itemList.setAdapter(listAdapter);
		
		// Add names button
		addItemBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// do nothing if no text in EditText
				if (itemEditText.getText().length() == 0) {
					Toast.makeText(MainActivity.this, "No Names Entered",Toast.LENGTH_SHORT).show();
			        return;
				}
				else{
				listItems.add(itemEditText.getText().toString());
				listAdapter.notifyDataSetChanged();
				/*
				 * Toast.makeText(getApplicationContext(), "Added Item: " +
				 * itemEditText.getText().toString(),
				 * Toast.LENGTH_SHORT).show();
				 */
				// ensure the text box is cleared afterwards
				itemEditText.setText("");
				// call refresh of list
			}
			
		}});
		
		//When listview is clicked
		itemList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				final int pos = position;
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				//Delete listview Yes or No?
				builder.setMessage("Do you want to delete this item?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										MainActivity.this.listItems.remove(pos);
										listAdapter.notifyDataSetChanged();
									}
								})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		// Winner button
		pickItemBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if nothing in listview make toast
				if(listAdapter.getCount()==0){
					
					Toast.makeText(MainActivity.this, "No Items Available",Toast.LENGTH_SHORT).show();
				}else{
				     pickItem();
				}
				
			}
		});

	}

	// To pick the winner button
	public void pickItem() {
		int item = -1;
		item = (int) ((double) Math.random() * (double) listItems.size());
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(listItems.get(item))
				.setTitle("The Lucky Person is.. ")
				.setCancelable(false)
				.setPositiveButton("Alright",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
		listAdapter.notifyDataSetChanged();
	}

	
}
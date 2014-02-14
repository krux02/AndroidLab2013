package com.example.l2plabv0;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectSemester extends Activity {

	private Spinner spinner2;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_semester);

		addItemsOnSpinner2();
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
		
		CommonData.writeLog("oncreate", "semester selection");
	}

	// add items into spinner dynamically
	public void addItemsOnSpinner2() {

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list = new ArrayList<String>();
		/* Download and fillup courses */
		try {
			String url = "http://seoul.freehostia.com/semester.json";
			String jsonFromServer = new CommonHttpGETorPOST().execute(url)
					.get();
			JSONObject jsonObjectFromServer = new JSONObject(jsonFromServer);
			JSONArray dataSetArray = jsonObjectFromServer
					.getJSONArray("dataSet");
			list.clear();
			for (int i = 0; i < dataSetArray.length(); ++i) {
				JSONObject singleLinkItem = dataSetArray.getJSONObject(i);
				String singleCourseName = singleLinkItem.getString("name");
				list.add(singleCourseName);
			}
			//list.add("<Not yet declared/>");
			/* End */
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(dataAdapter);
		} catch (Exception e) {
			CommonData.writeLog("CourseList Download", e);
		}

	}

	public void addListenerOnSpinnerItemSelection() {
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	// get the selected dropdown list value
	public void addListenerOnButton() {

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				Toast.makeText(
						SelectSemester.this,
						"OnClickListener : " + "\nSpinner 2 : "
								+ String.valueOf(spinner2.getSelectedItem()),
						Toast.LENGTH_SHORT).show();
				 */
				
				 Intent i = new Intent(SelectSemester.this, LinkListActivity.class);
				 String semesterId = String.valueOf(spinner2.getSelectedItem());
				 i.putExtra("TITLE", semesterId );
	                //Intent i = new Intent(SplashScreen.this, LinkListActivity.class);
	             startActivity(i);
	 
			}

		});
	}

}

package ca.ualberta.cs.jlooFuelTrack;

//Assumptions: All entries for each field is required
//Referenced a lot from lonelyTwitter Lab sessions

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//This class is about the activities available in fuellog. Users can add entries into the log that
// will save all the information. They also have the option reset their logs.
public class FuelLogActivity extends Activity {

	private static final String FILENAME = "file.sav";

	/**gave subject it's own EditText to be able to choose specific ones with features to help
	user input correctly. For example I picked a number EditText for Odometer that way users
	 can't input non-numeric numbers. If users tried to copy and paste works, it won't allow it.) */
	private EditText date;
	private EditText station;
	private EditText odo;
	private EditText grade;
	private EditText amount;
	private EditText unit;

	private ListView oldFuelLogList;

	private ArrayList<FuelData> fuelDatas = new ArrayList<FuelData>();
	private ArrayAdapter<FuelData> adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		date = (EditText) findViewById(R.id.date);
		station = (EditText) findViewById(R.id.station);
		odo = (EditText) findViewById(R.id.odo);
		grade = (EditText) findViewById(R.id.grade);
		amount = (EditText) findViewById(R.id.amount);
		unit = (EditText) findViewById(R.id.unit);

		Button saveButton = (Button) findViewById(R.id.save);
		Button eclearButton = (Button) findViewById(R.id.eclear);
		Button clearButton = (Button) findViewById(R.id.clear);
		Button exitButton = (Button) findViewById(R.id.exit);
		oldFuelLogList = (ListView) findViewById(R.id.oldFuelLogList);

		saveButton.setOnClickListener(new View.OnClickListener() {
		//saves log entry and adjusts decimal places before saving
			public void onClick(View v) {
				setResult(RESULT_OK);

				//Adjustment of decimal format for Odometer, Fuel Amount, Fuel Unit Cost and Fuel Cost
				//Reference: http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
				String odometer = odo.getText().toString();
				Float odonum=Float.parseFloat(odometer);
				DecimalFormat odf = new DecimalFormat("#.0");

				String famount = amount.getText().toString();
				Float fuelamount=Float.parseFloat(famount);
				DecimalFormat df = new DecimalFormat("#.000");

				String ucost = unit.getText().toString();
				Float unitcost=Float.parseFloat(ucost);

				DecimalFormat tf = new DecimalFormat("#.00");

				//Saves the log entry (it takes all the field entries and puts them together)
				//Fuel Cost is also calculated in text
				String text = "Date: " + date.getText().toString() +"\n"+ "Station: " + station.getText().toString() +"\n"+ "Odometer Reading: " + odf.format(odonum) + "km" +"\n"+ "Fuel Grade: " + grade.getText().toString() +"\n"+ "Fuel Amount: " + df.format(fuelamount) + "L" +"\n"+ "Fuel Unit Cost: " + odf.format(unitcost) + "c/L" +"\n"+ "Fuel Cost: $" + tf.format((unitcost/100) * fuelamount);
				FuelData newestFuelData = new NormalFuelData(text);

				fuelDatas.add(newestFuelData);
				adapter.notifyDataSetChanged();
				saveInFile();
				//finish();

			}
		});

		eclearButton.setOnClickListener(new View.OnClickListener() {
			//clears up all the field entries (cancelling out of adding)
			public void onClick(View v) {
				setResult(RESULT_OK);
				date.setText("");
				station.setText("");
				odo.setText("");
				grade.setText("");
				amount.setText("");
				unit.setText("");

				adapter.notifyDataSetChanged();
				saveInFile();
				//finish();

			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() {
			//clears the entire fuel log
			public void onClick(View v) {
				setResult(RESULT_OK);
				fuelDatas.clear();

				adapter.notifyDataSetChanged();
				saveInFile();
				//finish();

			}
		});

		exitButton.setOnClickListener(new View.OnClickListener() {
			//exits application
			public void onClick(View v) {
				setResult(RESULT_OK);
				System.exit(0);

				adapter.notifyDataSetChanged();
				saveInFile();
				//finish();

			}
		});
	}

		@Override
	protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			loadFromFile();
			adapter = new ArrayAdapter<FuelData>(this,
					R.layout.list_item, fuelDatas);
			oldFuelLogList.setAdapter(adapter);
	}

	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));

			Gson gson = new Gson();

			//From https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan.20/16
			Type listType = new TypeToken<ArrayList<NormalFuelData>>() {}.getType();
			fuelDatas = gson.fromJson(in, listType);


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fuelDatas = new ArrayList<FuelData>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}

	}
	
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
			Gson gson = new Gson();
			gson.toJson(fuelDatas, out);
			out.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();

		}
	}
}
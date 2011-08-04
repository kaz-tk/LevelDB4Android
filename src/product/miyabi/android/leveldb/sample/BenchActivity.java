package product.miyabi.android.leveldb.sample;

import product.miyabi.android.leveldb.R;
import android.app.Activity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class BenchActivity extends Activity {
	Spinner mBenchSelectSpinner;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		setContentView(R.layout.bench_activity);
		mBenchSelectSpinner = (Spinner)findViewById(R.id.spinner1);
		
		
		String[] items = getResources().getStringArray(R.array.bench_items);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for(String item : items){
			adapter.add(item);
		}
		mBenchSelectSpinner.setAdapter(adapter);
		
		
		
	};
}

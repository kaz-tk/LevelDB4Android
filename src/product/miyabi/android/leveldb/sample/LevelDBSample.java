package product.miyabi.android.leveldb.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import product.miyabi.android.leveldb.R;
import product.miyabi.android.leveldb.db.*;
public class LevelDBSample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        int [] vsn = Database.VERSION();

        Toast.makeText(getApplicationContext(), "LevelDB Version:"+vsn[0]+"."+vsn[1],Toast.LENGTH_LONG).show();
    }
}
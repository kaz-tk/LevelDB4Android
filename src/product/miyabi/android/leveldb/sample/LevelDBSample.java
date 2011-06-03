package product.miyabi.android.leveldb.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import product.miyabi.android.leveldb.R;
import product.miyabi.android.leveldb.db.*;
import product.miyabi.android.leveldb.db.options.Options;
public class LevelDBSample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        int [] vsn = Database.VERSION();
        Database database = new Database();
        Options options = new Options();
        Status status = database.Open(options, "/sdcard/leveldb.db");
        Toast.makeText(getApplicationContext(), "LevelDB Version:"+vsn[0]+"."+vsn[1],Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), status.toString(),Toast.LENGTH_LONG).show();
        Log.i("LevelDB","Path");
        
    }
}
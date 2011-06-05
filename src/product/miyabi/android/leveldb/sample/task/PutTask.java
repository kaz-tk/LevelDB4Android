package product.miyabi.android.leveldb.sample.task;

import java.util.Date;

import product.miyabi.android.leveldb.db.Database;
import product.miyabi.android.leveldb.sample.Cns;
import android.app.Activity;

public  class PutTask extends BaseTask {
	public PutTask(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
		mProgressDialog.setMax(Cns.COUNT);

	}
	@Override
	protected Long doInBackground(Database... params) {
		// TODO Auto-generated method stub
		String key[] = new String[Cns.COUNT];
		String val[] = new String[Cns.COUNT];
		
		for(int i=0;i<Cns.COUNT;i++){
			key[i] = String.valueOf(i);
			val[i] = String.valueOf(i+10000);
		}
		
		Date startDate = new Date();
		for(int i=0;i<Cns.COUNT;i++){
			params[0].Put(key[i], val[i]);
			publishProgress(i);
			//onProgressUpdate(i);
		}
		Date endDate = new Date();
		
		return endDate.getTime() - startDate.getTime();
	}
}

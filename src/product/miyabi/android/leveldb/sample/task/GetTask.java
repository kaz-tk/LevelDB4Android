package product.miyabi.android.leveldb.sample.task;

import java.text.DecimalFormat;
import java.util.Date;

import product.miyabi.android.leveldb.db.Database;
import product.miyabi.android.leveldb.sample.Cns;
import android.app.Activity;

public  class GetTask extends BaseTask {
	public GetTask(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
		msg.what = MSG_WHAT_PROGRESS_FIRST;
		mProgressDialog.setMax(Cns.COUNT);

	}
	@Override
	protected Long doInBackground(Database... params) {
		// TODO Auto-generated method stub
		String key[] = new String[Cns.COUNT];
		String val[] = new String[1];
		DecimalFormat decFormat = new DecimalFormat("000000");

		mProgressDialog.setMessage("Constructing Data");
		for(int i=0;i<Cns.COUNT;i++){
			publishProgress(i);
			key[i] = String.valueOf(decFormat.format(i));
		}
		msg.what = MSG_WHAT_PROGRESS_SECOND;
		mProgressDialog.setMessage("Getting Data");
		Date startDate = new Date();
		for(int i=0;i<Cns.COUNT;i++){
			params[0].Get(key[i], val);
			publishProgress(i);
			//onProgressUpdate(i);
		}
		Date endDate = new Date();
		
		return endDate.getTime() - startDate.getTime();
	}
}

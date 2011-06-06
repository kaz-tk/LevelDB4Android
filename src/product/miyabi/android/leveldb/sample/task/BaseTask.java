package product.miyabi.android.leveldb.sample.task;

import product.miyabi.android.leveldb.db.Database;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseTask extends AsyncTask<Database, Integer, Long> {

	protected Message msg = new Message();
	protected Activity mActivity;
	protected ProgressDialog mProgressDialog;
	private ProgressUpdateThread mProgressUpdateThread;
	
	protected final int MSG_WHAT_PROGRESS_FIRST = 0;
	protected final int MSG_WHAT_PROGRESS_SECOND = 1;
	
	
	
	public interface TaskFinishedCallback{
		void onFinished();
	}
	
	Handler mHander = new Handler(){
		
		@Override
		public void handleMessage(final Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what){
			case MSG_WHAT_PROGRESS_FIRST:
				mProgressDialog.setSecondaryProgress(msg.arg1);
				break;
			case MSG_WHAT_PROGRESS_SECOND:
				int max = mProgressDialog.getMax();
				mProgressDialog.setSecondaryProgress(max);
				mProgressDialog.setProgress(msg.arg1);
				break;
			}
		}
	};
	class ProgressUpdateThread extends Thread{
		int mProgress    =0;
		int lastProgress =0;
		boolean isRunning=true;
		
		void finish(){
			isRunning=false;
		}

		public void setProgress(int p) {
			// TODO Auto-generated method stub
			mProgress = p;
		}

		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			while(isRunning){
				if(mProgress ==lastProgress){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				final int p = mProgress;
				lastProgress = p;
				msg.arg1 = p;
				mHander.dispatchMessage(msg);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	public BaseTask(Activity activity) {
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		mProgressUpdateThread = new ProgressUpdateThread();
		mProgressUpdateThread.start();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mProgressDialog.show();
		
	}
	
	
		
	protected void publishProgress(int p) {
		// TODO Auto-generated method stub

		mProgressUpdateThread.setProgress(p);
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		mProgressDialog.setProgress(values[0]);
	}
	
	@Override
	protected void onPostExecute(Long result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mProgressDialog.dismiss();
		mProgressUpdateThread.finish();
		
		Toast.makeText(mActivity, result + "[ms]",Toast.LENGTH_SHORT).show();
		
		((TaskFinishedCallback) mActivity).onFinished();
		
	}

}

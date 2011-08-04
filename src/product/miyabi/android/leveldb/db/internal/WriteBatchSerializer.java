package product.miyabi.android.leveldb.db.internal;

import java.util.Set;

import product.miyabi.android.leveldb.db.WriteBatch;

public class WriteBatchSerializer {
	int mSize;
	String [] mKeys;
	String [] mValues;
	
	private WriteBatchSerializer(WriteBatch batch) {
		// TODO Auto-generated constructor stub
		mSize = batch.size();
		mKeys = new String[mSize];
		mValues = new String[mSize];
		
		Set<String> keys = batch.keySet();
		int index = 0;
		for(String key: keys){
			mKeys[index] = key;
			mValues[index] = batch.get(key);
		}
	}
	
	public static WriteBatchSerializer factory(WriteBatch batch){
		if(batch == null){
			return null;
		}
		return new WriteBatchSerializer(batch);
	}

	//---------------------------
	// Call from Native
	//---------------------------
	public int getSize() {
		return mSize;
	}
	
	public String[] getKeys() {
		return mKeys;
	}
	
	public String[] getValues() {
		return mValues;
	}
	
}

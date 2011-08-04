package product.miyabi.android.leveldb.db;


/**
 * @abstract Handle Database Status
 * @author miyabi
 *
 */
public class Status {
	private int mByteLengthMsg;
	private Code mDatabaseStatusCode;
	private String mMsg;
	
	public enum Code{
		OK,
		NOTFOUND,
		CORRUPTION,
		NOTSUPPORTED,
		INVALIDARGUMENT,
		IOERROR
	}
	
	
	public Status(Code code,String msg){
		mDatabaseStatusCode = code;
		mMsg                = msg;
	}

	public static Status factory( Code code , String msg){
		return new Status( code,msg );
	}
	
	/**
	 * @abstract Copy Constructor
	 * @param status
	 * @return 
	 * @throws CloneNotSupportedException 
	 */
	public  Status(Status status) throws CloneNotSupportedException{
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mMsg;
	}
	
}

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
		IOERROR;
		
		static public Code factory(int code){
			
			switch(code){
			case 0:
				return Code.OK;
			case 1:
				return Code.NOTFOUND;
			case 2:
				return Code.CORRUPTION;
			case 3:
				return Code.NOTSUPPORTED;
			case 4:
				return Code.INVALIDARGUMENT;
			case 5:
				return Code.IOERROR;
			default:
				return Code.CORRUPTION;
			}
		}
	}
	
	
	private Status(Code code,String msg){
		mDatabaseStatusCode = code;
		mMsg                = msg;
	}
	Status(int code,String msg){
		mDatabaseStatusCode = Code.factory(code);
		mMsg                = msg;
	}

	
	
	
	//Call from Native
	public static Status factory(int code , String msg){
		return new Status( code,msg );
	}
	
	//Call from Java
	public static Status factory(Code code , String msg){
		return new Status(code,msg );
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

/*
 * database.cpp
 *
 *  Created on: 2011/05/31
 *      Author: miyabi
 */


#include <product_miyabi_android_leveldb_db_Database.h>
#include <jni.h>
#include <android/log.h>
#include <leveldb/db.h>
#include <Database.h>
#include <jnihelper.h>
#include <android/log.h>
leveldb::DB* db_;

JNIEXPORT jintArray JNICALL Java_product_miyabi_android_leveldb_db_Database_VERSION
  (JNIEnv *env, jclass clazz)
{
	jboolean bools;
	jint jMajorVsn,jiMinorVsn;
	jint* vsnArray;
	jintArray vsn = env->NewIntArray(2);
	vsnArray = env->GetIntArrayElements(vsn,&bools);

	vsnArray[0] = leveldb::kMajorVersion;
	vsnArray[1] = leveldb::kMinorVersion;

	//__android_log_print(ANDROID_LOG_DEFAULT,"LevelDB","%d:%d",leveldb::kMajorVersion,leveldb::kMinorVersion);
	env->ReleaseIntArrayElements(vsn,vsnArray,0);

	return vsn;
}


/**
 *TODO option渡してるけど未処理
 *TODO
 */
JNIEXPORT jobject JNICALL Java_product_miyabi_android_leveldb_db_Database_OpenNative
(JNIEnv *env, jobject clazz, jobject options, jstring jdbname){

	jboolean* isCopy;
	const char* dbnamechar = env->GetStringUTFChars(jdbname,isCopy);
	std::string* dbname = new std::string(dbnamechar);
    env->ReleaseStringUTFChars(jdbname,dbnamechar);

    //__android_log_print(ANDROID_LOG_DEFAULT,"LevelDB","Path");

	leveldb::Options opts;
    if (options != NULL) {
      //opts = *options;
        opts.create_if_missing = true;
    } else {

      opts.create_if_missing = true;
    }
    leveldb::Options last_options_ = opts;

    leveldb::Status status = leveldb::DB::Open(opts, *dbname, &db_);

    return convertStatus(env,status);
}

/**
 *TODO dbnameとleveldb::DBのハッシュマップに従って削除
 */
JNIEXPORT void JNICALL Java_product_miyabi_android_leveldb_db_Database_ReleaseNative
  (JNIEnv *env, jobject clazz, jstring dbname){
	delete db_;
}

JNIEXPORT jobject JNICALL Java_product_miyabi_android_leveldb_db_Database_PutNative
  (JNIEnv *env, jobject clazz, jobject writeopts, jstring dbname, jstring jkey, jstring jvalue){

	// TODO dbnameから、操作対象のDBを判別
	// TODO WriteOptsのNativeの変換
	// TODO　keyとValueの変換
	jboolean *isCopyJKey,*isCopyJValue;
	std::string nkey = env->GetStringUTFChars(jkey,isCopyJKey);
	std::string nValue = env->GetStringUTFChars(jvalue,isCopyJValue);

	leveldb::WriteOptions nwriteopts;
	leveldb::Slice key  = nkey;
	leveldb::Slice value= nValue;

	leveldb::Status status = db_->Put(nwriteopts,key,value);

	return convertStatus(env,status);
}


JNIEXPORT jobject JNICALL Java_product_miyabi_android_leveldb_db_Database_GetNative
  (JNIEnv *env, jobject clazz, jobject readopts, jstring jdbname, jstring jkey, jobjectArray jstrings){
	leveldb::ReadOptions nreadopts;

	jboolean *isCopy;
	const char* ckey = env->GetStringUTFChars(jkey,isCopy);
	std::string strkey=ckey;
	env->ReleaseStringUTFChars(jkey,ckey);
	leveldb::Slice nkey= strkey;
	std::string nValue;

	leveldb::Status status = db_->Get(nreadopts,nkey,&nValue);

	const char* valuec = nValue.c_str();
	jstring valuestr = env->NewStringUTF(valuec);
	env->SetObjectArrayElement(jstrings,0,valuestr);

	return convertStatus(env,status);
}


JNIEXPORT jboolean JNICALL Java_product_miyabi_android_leveldb_db_Database_GetPropertyNative
  (JNIEnv *env, jobject clazz, jstring jstrdbname, jstring jstrpropkey, jobjectArray jstrarrayvalue){

	jboolean *isCopy;
	const char* cpropkey = env->GetStringUTFChars(jstrpropkey,isCopy);
	std::string strpropkey=cpropkey;
	env->ReleaseStringUTFChars(jstrpropkey,cpropkey);
	leveldb::Slice nkey= strpropkey;
	std::string nValue;

	bool issuccess = db_->GetProperty(nkey,&nValue);

	const char* valuec = nValue.c_str();
	jstring valuestr = env->NewStringUTF(valuec);
	env->SetObjectArrayElement(jstrarrayvalue,0,valuestr);

	return convertSuccess(env,issuccess);
}



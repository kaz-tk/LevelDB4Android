/*
 * database.cpp
 *
 *  Created on: 2011/05/31
 *      Author: miyabi
 */


#include <product_miyabi_android_leveldb_db_Database.h>
#include <leveldb/db.h>
#include <jni.h>
#include <android/log.h>


JNIEXPORT jintArray JNICALL Java_product_miyabi_android_leveldb_db_Database_VERSION
  (JNIEnv *env, jobject clazz){
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




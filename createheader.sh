#!/bin/sh
javah -classpath bin -d jni product.miyabi.android.leveldb.db.Database
javah -classpath bin -d jni product.miyabi.android.leveldb.db.Status
javah -classpath bin -d jni product.miyabi.android.leveldb.db.options.Options
javap -classpath bin -s  product/miyabi/android/leveldb/db/Status

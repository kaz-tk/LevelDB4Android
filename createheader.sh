#!/bin/sh
javah -classpath bin -d jni product.miyabi.android.leveldb.db.Database
javap -classpath bin -c  product.miyabi.android.leveldb.db.Status

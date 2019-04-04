package com.raizlabs.android.dbflow.config;

import com.zyf.italker.factory.model.db.AppDatabase;
import com.zyf.italker.factory.model.db.User_Table;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class AppDatabaseAppDataBase_Database extends DatabaseDefinition {
  public AppDatabaseAppDataBase_Database(DatabaseHolder holder) {
    addModelAdapter(new User_Table(holder, this), holder);
  }

  @Override
  public final Class<?> getAssociatedDatabaseClassFile() {
    return AppDatabase.class;
  }

  @Override
  public final boolean isForeignKeysSupported() {
    return false;
  }

  @Override
  public final boolean isInMemory() {
    return false;
  }

  @Override
  public final boolean backupEnabled() {
    return false;
  }

  @Override
  public final boolean areConsistencyChecksEnabled() {
    return false;
  }

  @Override
  public final int getDatabaseVersion() {
    return 1;
  }

  @Override
  public final String getDatabaseName() {
    return "AppDataBase";
  }
}

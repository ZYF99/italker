package com.zyf.italker.factory.model.db;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.converter.DateConverter;
import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.sql.language.property.TypeConvertedProperty;
import com.raizlabs.android.dbflow.sql.language.property.TypeConvertedProperty.TypeConverterGetter;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.Date;

/**
 * This is generated code. Please do not modify */
public final class User_Table extends ModelAdapter<User> {
  /**
   * Primary Key */
  public static final Property<String> id = new Property<String>(User.class, "id");

  public static final Property<String> name = new Property<String>(User.class, "name");

  public static final Property<String> phone = new Property<String>(User.class, "phone");

  public static final Property<String> portrait = new Property<String>(User.class, "portrait");

  public static final Property<String> desc = new Property<String>(User.class, "desc");

  public static final Property<Integer> sex = new Property<Integer>(User.class, "sex");

  public static final Property<String> alias = new Property<String>(User.class, "alias");

  public static final Property<Integer> follows = new Property<Integer>(User.class, "follows");

  public static final Property<Integer> following = new Property<Integer>(User.class, "following");

  public static final Property<Boolean> isFollow = new Property<Boolean>(User.class, "isFollow");

  public static final TypeConvertedProperty<Long, Date> modifyAt = new TypeConvertedProperty<Long, Date>(User.class, "modifyAt", true,
  new TypeConvertedProperty.TypeConverterGetter() {
  @Override
  public TypeConverter getTypeConverter(Class<?> modelClass) {
    User_Table adapter = (User_Table) FlowManager.getInstanceAdapter(modelClass);
  return adapter.global_typeConverterDateConverter;
  }
  });

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,name,phone,portrait,desc,sex,alias,follows,following,isFollow,modifyAt};

  private final DateConverter global_typeConverterDateConverter;

  public User_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterDateConverter = (DateConverter) holder.getTypeConverterForClass(Date.class);
  }

  @Override
  public final Class<User> getModelClass() {
    return User.class;
  }

  @Override
  public final String getTableName() {
    return "`User`";
  }

  @Override
  public final User newInstance() {
    return new User();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`name`":  {
        return name;
      }
      case "`phone`":  {
        return phone;
      }
      case "`portrait`":  {
        return portrait;
      }
      case "`desc`":  {
        return desc;
      }
      case "`sex`":  {
        return sex;
      }
      case "`alias`":  {
        return alias;
      }
      case "`follows`":  {
        return follows;
      }
      case "`following`":  {
        return following;
      }
      case "`isFollow`":  {
        return isFollow;
      }
      case "`modifyAt`":  {
        return modifyAt;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, User model) {
    values.put("`id`", model.getId() != null ? model.getId() : null);
    values.put("`name`", model.getName() != null ? model.getName() : null);
    values.put("`phone`", model.getPhone() != null ? model.getPhone() : null);
    values.put("`portrait`", model.getPortrait() != null ? model.getPortrait() : null);
    values.put("`desc`", model.getDesc() != null ? model.getDesc() : null);
    values.put("`sex`", model.getSex());
    values.put("`alias`", model.getAlias() != null ? model.getAlias() : null);
    values.put("`follows`", model.getFollows());
    values.put("`following`", model.getFollowing());
    values.put("`isFollow`", model.isFollow() ? 1 : 0);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    values.put("`modifyAt`", refmodifyAt != null ? refmodifyAt : null);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, User model, int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getName());
    statement.bindStringOrNull(3 + start, model.getPhone());
    statement.bindStringOrNull(4 + start, model.getPortrait());
    statement.bindStringOrNull(5 + start, model.getDesc());
    statement.bindLong(6 + start, model.getSex());
    statement.bindStringOrNull(7 + start, model.getAlias());
    statement.bindLong(8 + start, model.getFollows());
    statement.bindLong(9 + start, model.getFollowing());
    statement.bindLong(10 + start, model.isFollow() ? 1 : 0);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    statement.bindNumberOrNull(11 + start, refmodifyAt);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, User model) {
    statement.bindStringOrNull(1, model.getId());
    statement.bindStringOrNull(2, model.getName());
    statement.bindStringOrNull(3, model.getPhone());
    statement.bindStringOrNull(4, model.getPortrait());
    statement.bindStringOrNull(5, model.getDesc());
    statement.bindLong(6, model.getSex());
    statement.bindStringOrNull(7, model.getAlias());
    statement.bindLong(8, model.getFollows());
    statement.bindLong(9, model.getFollowing());
    statement.bindLong(10, model.isFollow() ? 1 : 0);
    Long refmodifyAt = model.getModifyAt() != null ? global_typeConverterDateConverter.getDBValue(model.getModifyAt()) : null;
    statement.bindNumberOrNull(11, refmodifyAt);
    statement.bindStringOrNull(12, model.getId());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, User model) {
    statement.bindStringOrNull(1, model.getId());
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `User`(`id`,`name`,`phone`,`portrait`,`desc`,`sex`,`alias`,`follows`,`following`,`isFollow`,`modifyAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `User` SET `id`=?,`name`=?,`phone`=?,`portrait`=?,`desc`=?,`sex`=?,`alias`=?,`follows`=?,`following`=?,`isFollow`=?,`modifyAt`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `User` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `User`(`id` TEXT, `name` TEXT, `phone` TEXT, `portrait` TEXT, `desc` TEXT, `sex` INTEGER, `alias` TEXT, `follows` INTEGER, `following` INTEGER, `isFollow` INTEGER, `modifyAt` TEXT, PRIMARY KEY(`id`))";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, User model) {
    model.setId(cursor.getStringOrDefault("id"));
    model.setName(cursor.getStringOrDefault("name"));
    model.setPhone(cursor.getStringOrDefault("phone"));
    model.setPortrait(cursor.getStringOrDefault("portrait"));
    model.setDesc(cursor.getStringOrDefault("desc"));
    model.setSex(cursor.getIntOrDefault("sex"));
    model.setAlias(cursor.getStringOrDefault("alias"));
    model.setFollows(cursor.getIntOrDefault("follows"));
    model.setFollowing(cursor.getIntOrDefault("following"));
    int index_isFollow = cursor.getColumnIndex("isFollow");
    if (index_isFollow != -1 && !cursor.isNull(index_isFollow)) {
      model.setFollow(cursor.getBoolean(index_isFollow));
    } else {
      model.setFollow(false);
    }
    int index_modifyAt = cursor.getColumnIndex("modifyAt");
    if (index_modifyAt != -1 && !cursor.isNull(index_modifyAt)) {
      model.setModifyAt(global_typeConverterDateConverter.getModelValue(cursor.getLong(index_modifyAt)));
    } else {
      model.setModifyAt(global_typeConverterDateConverter.getModelValue(null));
    }
  }

  @Override
  public final boolean exists(User model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(User.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(User model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.getId()));
    return clause;
  }
}

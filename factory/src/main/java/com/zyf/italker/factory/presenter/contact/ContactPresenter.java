package com.zyf.italker.factory.presenter.contact;


import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.zyf.italker.factory.data.DataSource;
import com.zyf.italker.factory.data.helper.UserHelper;
import com.zyf.italker.factory.model.card.UserCard;
import com.zyf.italker.factory.model.db.AppDatabase;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.model.db.User_Table;
import com.zyf.italker.factory.persistence.Account;
import com.zyf.italker.factory.presenter.BasePresenter;
import com.zyf.italker.factory.utils.DiffUiDataCallback;
import java.util.ArrayList;
import java.util.List;


/**
 * 联系人的presenter实现
 */
public class ContactPresenter extends BasePresenter<ContactContract.View>
        implements ContactContract.Presenter {
    public ContactPresenter(ContactContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        //加载本地数据库数据
        Log.d("users", "加载本地数据库");
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)//正向排序
                .limit(100)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<User>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction,
                                                  @NonNull List<User> tResult) {
                        Log.d("users", tResult.toString());
                        getView().getRecyclerAdapter().replace(tResult);
                        getView().onAdapterDataChanged();

                    }
                })
                .execute();

        //加载网络数据
        UserHelper.refreshContacts(new DataSource.Callback<List<UserCard>>() {

            @Override
            public void onDataNotAvaliable(int strRes) {
                //网络失败，因为本地有数据，所以不管错误
            }

            @Override
            public void onDataLoaded(final List<UserCard> userCards) {
                final List<User> users = new ArrayList<>();
                for (UserCard userCard : userCards) {
                    users.add(userCard.build());
                }
                Log.d("users", users.toString());

                //丢到事物中保存数据库
                DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                definition.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {

                        FlowManager.getModelAdapter(User.class)
                                //.save()
                                .saveAll(users);

                    }
                }).build().execute();
                //网络的数据一般是新的，我们需要直接刷新到界面
                List<User>old = getView().getRecyclerAdapter().getItems();
                diff(old,users);
            }
        });

        //TODO 问题1：关注后虽然存储数据库，但是没有刷新联系人
        //TODO 问题2：如果刷新数据库，或者从网络刷新，最终刷新的时候是全局刷新
        //TODO 问题3：本地刷新和网络刷新，在添加到界面的时候会有可能冲突；导致数据显示异常
        //TODO 问题4：如何识别已经在数据库中有这样的数据了


    }

    private void diff(List<User> oldList,List<User> newList) {
        //进行数据对比
        DiffUiDataCallback callback = new DiffUiDataCallback<User>(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //再对比完成后进行数据的赋值
        getView().getRecyclerAdapter().replace(newList);

        //尝试刷新界面
        result.dispatchUpdatesTo(getView().getRecyclerAdapter());
        getView().onAdapterDataChanged();

    }

}

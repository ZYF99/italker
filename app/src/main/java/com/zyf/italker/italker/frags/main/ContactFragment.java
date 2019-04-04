package com.zyf.italker.italker.frags.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zyf.italker.common.app.PresenterFragment;
import com.zyf.italker.common.widget.EmptyView;
import com.zyf.italker.common.widget.a.PortraitView;
import com.zyf.italker.common.widget.recycler.RecyclerAdapter;
import com.zyf.italker.factory.model.db.User;
import com.zyf.italker.factory.presenter.contact.ContactContract;
import com.zyf.italker.factory.presenter.contact.ContactPresenter;
import com.zyf.italker.italker.R;
import com.zyf.italker.italker.activities.MessageActivity;
import com.zyf.italker.italker.activities.PersonalActivity;
import butterknife.BindView;
import butterknife.OnClick;


public class ContactFragment extends PresenterFragment<ContactContract.Presenter>
implements ContactContract.View {

    @BindView(R.id.empty)
    EmptyView mEmptyView;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    //适配器，User，可以直接从数据库查询数据
    private RecyclerAdapter<User>mAdapter;

    public ContactFragment() {

    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        //初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<User>(){

            @Override
            protected int getItemViewType(int position, User userCard) {
                //返回cell的布局id
                return R.layout.cell_contact_list;
            }

            @Override
            protected ViewHolder<User> onCreatViewHolder(View root, int viewType) {
                return new ContactFragment.ViewHolder(root);
            }
        });

        //点击事件监听
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, User user) {
                //跳转到聊天界面
                MessageActivity.show(getContext(),user);
            }
        });
        //初始化占位布局
        mEmptyView.bind(mRecycler);
        setmPlaceHolderView(mEmptyView);
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        // 进行一次数据加载
        mPresenter.start();
    }
    @Override
    protected ContactContract.Presenter initPresenter() {
        //初始化Presenter
        return new ContactPresenter(this);
    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        //进行界面操作
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<User>{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_desc)
        TextView mDesc;



        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User user) {

            mPortraitView.setup(Glide.with(ContactFragment.this),user);
            mName.setText(user.getName());
            mDesc.setText(user.getDesc());
        }
        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            //显示个人详情信息
            PersonalActivity.show(getContext(),mData.getId());

        }
    }


}

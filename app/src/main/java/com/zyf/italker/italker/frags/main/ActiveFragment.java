package com.zyf.italker.italker.frags.main;



import com.zyf.italker.common.app.Fragment;
import com.zyf.italker.common.widget.GalleryView;
import com.zyf.italker.italker.R;

import butterknife.BindView;


public class ActiveFragment extends Fragment {
    @BindView(R.id.galleyView)
    GalleryView mGalley;

    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }


    @Override
    protected void initData() {
        super.initData();
        mGalley.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }

}

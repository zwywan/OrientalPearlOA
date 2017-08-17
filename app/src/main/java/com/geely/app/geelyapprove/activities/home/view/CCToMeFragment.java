package com.geely.app.geelyapprove.activities.home.view;

import android.support.v4.app.Fragment;

/**
 * Created by Oliver on 2016/9/23.
 */

public class CCToMeFragment extends Fragment {

//    private static int tabIndex;
//    private ImageView ivEmpty;
//
//
//    public CCToMeFragment() {
//
//    }
//
//    public static CCToMeFragment newInstance(int tabIndex) {
//        Bundle args = new Bundle();
//        CCToMeFragment fragment = new CCToMeFragment();
//        args.putInt(DetailFragment.ARG_TAB, tabIndex);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    private List<CCToMeEntity> entityList = new ArrayList<>();
//
//    ListAdapter<CCToMeEntity> adapter = new ListAdapter<CCToMeEntity>((ArrayList<CCToMeEntity>) entityList, R.layout.item_flow_apply) {
//        @Override
//        public void bindView(ViewHolder holder, CCToMeEntity obj) {
//            //       holder.setVisibility(R.id.tv_sftj_label, View.GONE);
//            holder.setVisibility(R.id.tv_lcbh, View.GONE);
//            holder.setVisibility(R.id.tv_lcbh_label, View.GONE);
//
//        }
//    };
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        tabIndex = getArguments().getInt(DetailFragment.ARG_TAB);
//    }
//
//    private SimpleListView lv;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.main_viewpage_page_content, null, false);
//        lv = (SimpleListView) view.findViewById(R.id.viewpager_listview);
//        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
//        lv.setAdapter(adapter);
//        adapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                if (adapter.getCount() == 0) {
//                    ivEmpty.setVisibility(View.VISIBLE);
//                } else {
//                    ivEmpty.setVisibility(View.GONE);
//                }
//                super.onChanged();
//            }
//        });
//
//        loadData(tabIndex, 0, 10);
//        return view;
//    }
//
//    void loadData(int tabIndex, int pageIndex, int pageSize) {
//        Map<String, Object> params = CommonValues.getCommonParams();
//
//        HttpManager.getInstance().requestResultForm(CommonValues.REQ_CC_TO_ME, params, CCToMeEntity.class, new HttpManager.ResultCallback<CCToMeEntity>() {
//            @Override
//            public void onSuccess(String content, CCToMeEntity ccToMeEntity) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(String content) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                });
//            }
//        });
//    }

}

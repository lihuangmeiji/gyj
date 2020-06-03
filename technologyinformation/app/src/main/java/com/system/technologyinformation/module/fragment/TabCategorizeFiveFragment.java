package com.system.technologyinformation.module.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.system.technologyinformation.R;
import com.system.technologyinformation.common.ui.BaseLazyFragment;
import com.system.technologyinformation.model.GroupInfoBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.module.contract.TabCategorizeFiveContract;
import com.system.technologyinformation.module.presenter.TabCategorizeFivePresenter;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.users.UserAddressActivity;

import android.widget.TextView;

import java.util.List;

import butterknife.BindView;

public class TabCategorizeFiveFragment extends BaseLazyFragment<TabCategorizeFivePresenter> implements TabCategorizeFiveContract.View {
    public static final String TAG = "GroupsActivity";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.vs_showerror)
    ViewStub vs_showerror;

    Intent intent;


    @Override
    protected void loadLazyData() {

    }

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_tab_categorize_five;
    }

    @Override
    public void setUserGroupInfo(List<GroupInfoBean> groupInfoBeanList) {

        vs_showerror.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshUserTimeResult(BaseResponseInfo result) {
        if (result.getCode() == 0) {
            getPresenter().getUserGroupInfo();
        } else {
            openLogin();
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hiddenLoading() {
        super.hiddenLoading();
    }

    @Override
    public void showErrorMsg(String msg, int code) {
        super.showErrorMsg(msg, code);
        hiddenLoading();
        if (code == -1) {
            getPresenter().refreshUserTime();
            return;
        } else if (code == 405) {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_network);
            //vs_showerror.inflate();
            vs_showerror.setVisibility(View.VISIBLE);
            LinearLayout lin_load = getView().findViewById(R.id.lin_load);
            lin_load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().getUserGroupInfo();
                }
            });
        }else if (code == -2) {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_no_join_group);
            vs_showerror.setVisibility(View.VISIBLE);
            TextView tvJoinGroup = getView().findViewById(R.id.tv_join_group);
            tvJoinGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (login != null) {
                        intent = new Intent(getContext(), UserAddressActivity.class);
                        startActivityForResult(intent, 2);
                    } else {
                        intent = new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
            });
        } else {
            recyclerView.setVisibility(View.GONE);
            vs_showerror.setLayoutResource(R.layout.layout_empty);
            vs_showerror.setVisibility(View.VISIBLE);
        }
        toast(msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loadUserInfo();
            getPresenter().getUserGroupInfo();
        }
    }

    /*
     * 作废
     * */
         /*  EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            grouplist = EMClient.getInstance().groupManager().getAllGroups();
            groupAdapter = new GroupAdapter(getBaseContext(), 1, grouplist);
            groupListView.setAdapter(groupAdapter);
            registerGroupChangeReceiver();
            groupListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // enter group chat
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    // it is group chat
                    intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                    intent.putExtra("userId", groupAdapter.getItem(position).getGroupId());
                    startActivityForResult(intent, 0);

                }

            });

    void registerGroupChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
                    if (EaseCommonUtils.getTopActivity(getContext()).equals(MainActivity.class.getName())) {
                        refresh();
                    }
                }
            }
        };
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getContext());
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void refresh() {
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupAdapter = new GroupAdapter(getContext(), 1, grouplist);
        groupListView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }
*/

}

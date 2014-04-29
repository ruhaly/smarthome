package com.changhong.smarthome.phone.property.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.sdk.entity.Pager;
import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.sdk.widget.PullRefreshListView;
import com.changhong.sdk.widget.PullRefreshListView.NewScrollerListener;
import com.changhong.sdk.widget.PullRefreshListView.OnRefreshListener;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.foundation.logic.LoginLogic;
import com.changhong.smarthome.phone.property.ComplaintDetailActivity;
import com.changhong.smarthome.phone.property.PopupWindowActivity;
import com.changhong.smarthome.phone.property.adapter.BxlistAdapter;
import com.changhong.smarthome.phone.property.entry.ServicesItem;
import com.changhong.smarthome.phone.property.http.HttpAction;
import com.changhong.smarthome.phone.property.logic.GetTsItemListLogic;
import com.changhong.smarthome.phone.property.logic.GetTsItemList_wLogic;
import com.changhong.smarthome.phone.property.view.SwipeListView;
import com.changhong.smarthome.phone.sns.Constant;
import com.lidroid.xutils.HttpUtils;


/**
 * [意见箱主界面]<BR>
 * [功能详细描述]
 * @author 王磊
 * @version [ChangHong SmartHome V100R001C03, 2014-4-24] 
 */
public class ComplaintFragment extends SuperFragment
{
    private View view;
    
    //储存网路返回的数据实例
    private GetTsItemListLogic listtsitemlogic;
    
    private GetTsItemList_wLogic listtsitemlogic_w;
    
    //服务中投诉列表 重写了ListView实现了下拉刷新
    private PullRefreshListView tsfwlistview;
    
    //服务记录中的投诉列表
    private SwipeListView tsfwlistview_w;
    
    //用来标记需要删除的服务（服务中）
    private int ts;
    
    //用来标记需要删除的服务（服务记录）
    private int ts_w;
    
    private HttpUtils httpUtil;
    
    //页面中的 " 我要投诉 " 按钮
    private Button ts_button;
    
    //服务中菜单
    private RelativeLayout tsLayout;
    
    //服务记录菜单
    private RelativeLayout tsLayout_w;
    
    //服务中菜单上的标识图片 用来显示ListView是展开还是收缩
    private ImageView ts_expand1;
    
    //服务记录菜单上的标识图片 用来显示ListView是展开还是收缩
    private ImageView ts_expand2;
    
    //服务中菜单上的标识文字
    private TextView tsfw_text;
    
    //服务记录菜单上的标识文字
    private TextView tsfw_text_w;
    
    //清空服务记录中的列表
    public TextView ts_clear;
    
    //服务中的投诉服务列表数据
    private BxlistAdapter tslistadapter;
    
    //服务记录中的投诉服务列表数据
    private BxlistAdapter tslistadapter_w;
    
    /**
     * 一页请求的个数
     */
    private int pageSize = 3;
    
    /**
     * 当前请求的是第几页(真正数据从1开始)
     */
    private int curPage = 1;
    
    /**
     * 标记加载更多
     */
    private boolean isMore = true;
    
    /**
     * 标记是否有加载更多请求
     */
    //private boolean isLoadingMore = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
        //初始进入物业服务加载全部服务信息
            case HttpAction.TSLIST_SUCCESS_MSGWHAT_ALL:
                if(GetTsItemListLogic.services_tsList.size() > 0)
                {
                    tslistadapter.updateData(GetTsItemListLogic.services_tsList);
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PopupWindowActivity.class);
                    intent.putExtra("bz", "complaint");
                    startActivityForResult(intent, 7);
                }
                break;
            //发送我要投诉请求返回的成功信息
            case HttpAction.TSADD_SUCCESS_MSGWHAT:
                tslistadapter.updateData(GetTsItemListLogic.services_tsList);
                
                showToast("提交成功!");
                
                break;
            //服务中服务下拉刷新返回的成功信息
            case HttpAction.TSLIST_SUCCESS_MSGWHAT:
                if (curPage == 1)
                {
                    GetTsItemListLogic.services_tsList = (ArrayList<ServicesItem>) msg.obj;
                }
                else
                {
                    GetTsItemListLogic.services_tsList.addAll((ArrayList<ServicesItem>) msg.obj);
                }
                tsfwlistview.onRefreshComplete();
                tsfwlistview.showLoadFinish();
                
                tslistadapter.updateData(GetTsItemListLogic.services_tsList);
                break;
            //服务记录下拉刷新返回的成功信息
            case HttpAction.TSLIST_SUCCESS_MSGWHAT_W:
                listtsitemlogic_w.services_tsList_w = (ArrayList<ServicesItem>) msg.obj;
                tslistadapter_w.updateData(listtsitemlogic_w.services_tsList_w);
                //tslistadapter_w.notifyDataSetChanged();
                tsfwlistview_w.onRefreshComplete();
                
                break;
            //清空返回的成功信息
            case HttpAction.TSDELETEALL_SUCCESS_MSGWHAT:
                listtsitemlogic_w.services_tsList_w.clear();
                tslistadapter_w.notifyDataSetChanged();
                
                break;
            //服务中服务删除返回的成功信息
            case HttpAction.TSDELETE_SUCCESS_MSGWHAT:
                GetTsItemListLogic.services_tsList.remove(ts);
                tslistadapter.notifyDataSetChanged();
                
                break;
            //服务记录中服务删除返回的成功信息
            case HttpAction.TSDELETE_SUCCESS_MSGWHAT_W:
                listtsitemlogic_w.services_tsList_w.remove(ts_w);
                tslistadapter_w.notifyDataSetChanged();
                
                break;
            case DATA_FORMAT_ERROR_MSGWHAT:
                
                if (tsfwlistview_w != null)
                {
                    tsfwlistview_w.onRefreshComplete();
                }
                if (tsfwlistview != null)
                {
                    tsfwlistview.onRefreshComplete();
                }
                break;
            default:
                break;
        }
        super.handleMsg(msg);
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        
        view = inflater.inflate(R.layout.main_tsfw, container, false);
        tsfwlistview = (PullRefreshListView) view.findViewById(R.id.tsfwlist);
        tsfwlistview_w = (SwipeListView) view.findViewById(R.id.tsfwlist_w);
        tsfwlistview.setRefreshable(true);
        tsfwlistview.setOnRefreshListener(new OnRefreshListener()
        {
            
            @Override
            public void onRefresh()
            {
                refresh();
                
            }
        });
        tsfwlistview.setNewScrollerListener(createScroller());
        
        tsLayout = (RelativeLayout) view.findViewById(R.id.tsLayout);
        tsLayout_w = (RelativeLayout) view.findViewById(R.id.tsLayout_w);
        ts_button = (Button) view.findViewById(R.id.ts_button);
        ts_expand1 = (ImageView) view.findViewById(R.id.ts_expand1);
        ts_expand2 = (ImageView) view.findViewById(R.id.ts_expand2);
        tsfw_text = (TextView) view.findViewById(R.id.tsfw_text);
        tsfw_text_w = (TextView) view.findViewById(R.id.tsfw_text_w);
        ts_clear = (TextView) view.findViewById(R.id.ts_clear);
        
        initTsList();
        initTsAdapter();
        //网络请求加载投诉页面数据
        showProcessDialog(dismiss);
        httpUtil = new HttpUtils();
        listtsitemlogic.setData(fHandler);
        Pager pager = new Pager();
        pager.setPageId(curPage);
        pager.setPageSize(pageSize);
        listtsitemlogic.requesttsItemListAll("2",
                LoginLogic.getInstance().getBaseAccountInfo(),
                pager,
                httpUtil);
        listtsitemlogic_w.setData(fHandler);
        
        return view;
    }
    
    // 初始化投诉列表
    private void initTsList()
    {
        ts_button.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PopupWindowActivity.class);
                intent.putExtra("bz", "complaint");
                startActivityForResult(intent, 7);
                getActivity().overridePendingTransition(R.anim.anim_enter,
                        R.anim.anim_exit);
                
            }
            
        });
        
        ts_clear.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(getActivity()).setTitle("确认")
                        .setMessage("你确定清除所有的数据吗？")
                        .setPositiveButton("删除",
                                new DialogInterface.OnClickListener()
                                {
                                    
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which)
                                    {
                                        httpUtil = new HttpUtils();
                                        listtsitemlogic_w.handletsDeleteALL("5",
                                                LoginLogic.getInstance().getBaseAccountInfo(),
                                                httpUtil);
                                        
                                    }
                                    
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener()
                                {
                                    
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which)
                                    {
                                        dialog.dismiss();
                                        
                                    }
                                    
                                })
                        .show();
                
            }
        });
        
        /*//对服务中的投诉服务下拉刷新
        tsfwlistview.setOnRefreshListener(new SwipeListView.OnRefreshListener()
        {
            public void onRefresh()
            { //
                httpUtil = new HttpUtils();
                listtsitemlogic.requesttsItemList("2",
                        BillLogic.getInstance().accountInfo,
                        httpUtil);
                
            }
        });*/
        
        //对服务记录的投诉服务下拉刷新
        tsfwlistview_w.setOnRefreshListener(new SwipeListView.OnRefreshListener()
        {
            public void onRefresh()
            { //
                httpUtil = new HttpUtils();
                listtsitemlogic_w.requesttsItemList("2",
                        LoginLogic.getInstance().getBaseAccountInfo(),
                        httpUtil);
                
            }
        });
        
        //点击服务中菜单中的图片改变 服务中菜单和服务记录菜单的状态
        ts_expand1.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                if (tsfwlistview.getVisibility() == View.VISIBLE)
                {
                    tsfwlistview.setVisibility(View.GONE);
                    tsfw_text.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand1.setBackgroundResource(R.drawable.drawable_expand_close);
                    
                }
                else
                {
                    tsfwlistview.setVisibility(View.VISIBLE);
                    tsfwlistview_w.setVisibility(View.GONE);
                    tsfw_text.setBackgroundResource(R.drawable.iconbg_select);
                    tsfw_text_w.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand1.setBackgroundResource(R.drawable.drawable_expand_open);
                    ts_expand2.setBackgroundResource(R.drawable.drawable_expand_close);
                }
                ;
                
            }
            
        });
        //点击服务中菜单改变 服务中菜单和服务记录菜单的状态
        tsLayout.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                if (tsfwlistview.getVisibility() == View.VISIBLE)
                {
                    tsfwlistview.setVisibility(View.GONE);
                    tsfw_text.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand1.setBackgroundResource(R.drawable.drawable_expand_close);
                    
                }
                else
                {
                    tsfwlistview.setVisibility(View.VISIBLE);
                    tsfwlistview_w.setVisibility(View.GONE);
                    tsfw_text.setBackgroundResource(R.drawable.iconbg_select);
                    tsfw_text_w.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand1.setBackgroundResource(R.drawable.drawable_expand_open);
                    ts_expand2.setBackgroundResource(R.drawable.drawable_expand_close);
                }
                ;
                
            }
            
        });
        //点击服务记录菜单中的图片改变 服务中菜单和服务记录菜单的状态
        ts_expand2.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                if (tsfwlistview_w.getVisibility() == View.VISIBLE)
                {
                    tsfwlistview_w.setVisibility(View.GONE);
                    tsfw_text_w.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand2.setBackgroundResource(R.drawable.drawable_expand_close);
                }
                else
                {
                    tsfwlistview_w.setVisibility(View.VISIBLE);
                    tsfwlistview.setVisibility(View.GONE);
                    tsfw_text_w.setBackgroundResource(R.drawable.iconbg_select);
                    tsfw_text.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand2.setBackgroundResource(R.drawable.drawable_expand_open);
                    ts_expand1.setBackgroundResource(R.drawable.drawable_expand_close);
                }
                ;
                
            }
            
        });
        //点击服务记录菜单改变 服务中菜单和服务记录菜单的状态
        tsLayout_w.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                if (tsfwlistview_w.getVisibility() == View.VISIBLE)
                {
                    tsfwlistview_w.setVisibility(View.GONE);
                    tsfw_text_w.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand2.setBackgroundResource(R.drawable.drawable_expand_close);
                }
                else
                {
                    tsfwlistview_w.setVisibility(View.VISIBLE);
                    tsfwlistview.setVisibility(View.GONE);
                    tsfw_text_w.setBackgroundResource(R.drawable.iconbg_select);
                    tsfw_text.setBackgroundResource(R.drawable.iconbg_unselect);
                    ts_expand2.setBackgroundResource(R.drawable.drawable_expand_open);
                    ts_expand1.setBackgroundResource(R.drawable.drawable_expand_close);
                }
                ;
                
            }
            
        });
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetTsItemListLogic.getInstance())
        {
            listtsitemlogic_w = GetTsItemList_wLogic.getInstance();
            listtsitemlogic = GetTsItemListLogic.getInstance();
        }
        
    }
    
    public void initTsAdapter()
    {
        if (GetTsItemListLogic.services_tsList != null)
        {
            tslistadapter = new BxlistAdapter(getActivity(),
                    GetTsItemListLogic.services_tsList);
            tsfwlistview.setAdapter(tslistadapter);
            
            
            
            tsfwlistview.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    Intent intent = new Intent(
                            getActivity().getApplicationContext(),
                            ComplaintDetailActivity.class);
                    
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicesitem",
                            (ServicesItem) tslistadapter.getItem(position - 1));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_enter,
                            R.anim.anim_exit);
                    
                }
            });
            
        }
        if (listtsitemlogic_w.services_tsList_w == null)
        {
            listtsitemlogic_w.services_tsList_w = new ArrayList<ServicesItem>();
        }
        
        if (listtsitemlogic_w.services_tsList_w != null)
        {
            tslistadapter_w = new BxlistAdapter(getActivity(),
                    listtsitemlogic_w.services_tsList_w);
            tsfwlistview_w.setAdapter(tslistadapter_w);
            tsfwlistview_w.setOnItemLongClickListener(new OnItemLongClickListener()
            {
                
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,
                        View view, final int position, long id)
                {
                    new AlertDialog.Builder(getActivity()).setTitle("确认")
                            .setMessage("你确定删除此条数据吗?")
                            .setPositiveButton("删除",
                                    new DialogInterface.OnClickListener()
                                    {
                                        
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which)
                                        {
                                            
                                            // services_bx_w.clear();
                                            httpUtil = new HttpUtils();
                                            listtsitemlogic_w.deleteTsItem("4",
                                                    LoginLogic.getInstance().getBaseAccountInfo(),
                                                    tslistadapter_w.getItemId(position - 1),
                                                    httpUtil);
                                            ts_w = position - 1;
                                            
                                        }
                                        
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener()
                                    {
                                        
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which)
                                        {
                                            dialog.dismiss();
                                            
                                        }
                                        
                                    })
                            .show();
                    
                    return false;
                }
            });
            
            tsfwlistview_w.setOnItemClickListener(new OnItemClickListener()
            {
                
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id)
                {
                    Intent intent = new Intent(
                            getActivity().getApplicationContext(),
                            ComplaintDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicesitem",
                            listtsitemlogic_w.services_tsList_w.get(position - 1));
                    intent.putExtras(bundle);
                    startActivityForResult(intent,
                            listtsitemlogic_w.services_tsList_w.get(position - 1)
                                    .getFwid());
                    getActivity().overridePendingTransition(R.anim.anim_enter,
                            R.anim.anim_exit);
                }
            });
            
        }
    }
    
    //投诉详情页面关闭后回调的方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2)
        { // 子窗口ChildActivity的回传数
            if (data != null)
            {
                Bundle bundle = data.getExtras();
                if (bundle != null)
                {
                    
                    String content_pj = bundle.getString("content_pj");
                    String pf = bundle.getString("pf").substring(0, 1);
                    
                    for (int n = 0; n < listtsitemlogic_w.services_tsList_w.size(); n++)
                    {
                        
                        if (listtsitemlogic_w.services_tsList_w.get(n)
                                .getFwid() == requestCode)
                        {
                            listtsitemlogic_w.services_tsList_w.get(n)
                                    .setContent_pj(content_pj);
                            listtsitemlogic_w.services_tsList_w.get(n)
                                    .setPf(Integer.parseInt(pf));
                            listtsitemlogic_w.services_tsList_w.get(n)
                                    .setFwzt(4);//更改服务状态
                            
                            tslistadapter_w = new BxlistAdapter(getActivity(),
                                    listtsitemlogic_w.services_tsList_w);
                            tsfwlistview_w.setAdapter(tslistadapter_w);
                            
                        }
                        
                    }
                }
            }
            
        }
        else if (resultCode == 7)
        {
            if (requestCode == 7)
            {
                Bundle bundle = data.getExtras();
                if (bundle != null)
                {
                    String phonenum = bundle.getString("phonenum");
                    String newfwcontent = bundle.getString("newfwcontent");
                    ArrayList<?> addImageList = bundle.getStringArrayList("addImageList");
                    String voiceStr = bundle.getString("voiceStr");
                    String complainType = bundle.getString("typec");
                    // 新增报修
                    httpUtil = new HttpUtils();
                    showProcessDialog(dismiss);
                    listtsitemlogic.setData(fHandler);
                    listtsitemlogic.addTsItem("1",
                            LoginLogic.getInstance().getBaseAccountInfo(),
                            phonenum,
                            newfwcontent,
                            addImageList,
                            voiceStr,
                            "jpg",
                            "ram",
                            complainType,
                            httpUtil);
                }
            }
        }
        
    }
    
    //提供网路加载Dialog  
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            listtsitemlogic.stopRequest();
        }
    };
    
    @Override
    public void updateView(Message msg)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void updateView(Intent intent)
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * lisView滑动加载监听
     * @return
     */
    public NewScrollerListener createScroller()
    {
        NewScrollerListener scroller = new NewScrollerListener()
        {
            private boolean isLoadMoreFile = false;
            
            @Override
            public void newScrollChanged(AbsListView view, int scrollState)
            {
                if ((scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING)
                        && isLoadMoreFile)
                {
                    
                    startToLoadMore();
                }
            }
            
            @Override
            public void newScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount)
            {
                isLoadMoreFile = (firstVisibleItem + visibleItemCount == totalItemCount) ? true
                        : false;
            }
        };
        return scroller;
    }
    
    /**
     * 加载更多
     */
    protected void startToLoadMore()
    {
        /*if (hasMore)
        {
            if (!isLoadingMore)
            {*/
        if (isMore)
        {
            tsfwlistview.showLoading();
            curPage = curPage + 1;
            /* isLoadingMore = true;
             isMore = true;*/
            // Log.d(TAG, "startToLoadMore--->");
            getDynamicList("", "", curPage, pageSize);
        }
        isMore = true;
        //}
        //}
        
        // 处理云端滑动获取更多
        
        // 网络不可用时直接返回
        //        if (!isNetworkAvailable())
        //        {
        //            return;
        //        }
    }
    
    /**
     * 获取动态列表
     * @param userId
     * @param themeType
     * @param pageIndex
     * @param pageSize
     */
    private void getDynamicList(String themeType, String themeSubType,
            int pageIndex, int pageSize)
    {
        Log.d("bx", "getDynamicList----start>");
        
        httpUtil = new HttpUtils();
        listtsitemlogic.setData(fHandler);
        Pager pager = new Pager();
        pager.setPageId(pageIndex);
        pager.setPageSize(pageSize);
        listtsitemlogic.requesttsItemList("2",
                LoginLogic.getInstance().getBaseAccountInfo(),
                pager,
                httpUtil);
    }
    
    private void refresh()
    {
        curPage = 1;
        isMore = false;
        getDynamicList(String.valueOf(Constant.PRIVATE_RANGE),
                "",
                curPage,
                pageSize);
        // 网络不可用时直接返回
        //        if (!isNetworkAvailable())
        //        {
        //            closeDialog(refreshDialog);
        //            epContainer.onRefreshComplete();
        //            return;
        //        }
    }
    
}

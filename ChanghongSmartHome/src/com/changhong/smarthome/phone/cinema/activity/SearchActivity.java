package com.changhong.smarthome.phone.cinema.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.HotCinemaAdapter;
import com.changhong.smarthome.phone.cinema.entry.Cinema;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.GetCinemaSearchLogic;
import com.changhong.smarthome.phone.foundation.activity.BaseActivity;
import com.changhong.smarthome.phone.store.entity.StoreConstant;
import com.lidroid.xutils.HttpUtils;

/**
* @ClassName: SearchActivity
* @author yang_jun
* @Description: 搜索界面
*/
public class SearchActivity extends BaseActivity implements OnClickListener
{
    private GridView gridviews;
    
    private TextView titletext;
    
    private ImageView exitImg1;
    
    private GetCinemaSearchLogic getCinemaSearchLogic;
    
    private HotCinemaAdapter hotAdapter;
    
    private HttpUtils httpUtil;
    
    private EditText seacrhing_park_et;
    
    private TextView search_Btn;
    
    private RelativeLayout exitRelative;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        titletext = (TextView) findViewById(R.id.titletext);
        exitImg1 = (ImageView) findViewById(R.id.exitImg1);
        seacrhing_park_et = (EditText) findViewById(R.id.seacrhing_park_et);
        search_Btn = (TextView) findViewById(R.id.search_Btn);
        exitRelative = (RelativeLayout) findViewById(R.id.exitRelative);
        exitRelative.setOnClickListener(this);
        search_Btn.setOnClickListener(this);
        exitImg1.setOnClickListener(this);
        titletext.setText("社区影视");
        gridviews = (GridView) findViewById(R.id.gridviews);
        searchKeyWords(getIntent().getStringExtra(StoreConstant.SEARCHKEY));
        
    }
    
    private void searchKeyWords(String key)
    {
        httpUtil = new HttpUtils();
        showProcessDialog(dismiss);
        getCinemaSearchLogic.setData(mHandler);
        Pager pager = new Pager();
        pager.setPageId(1);
        pager.setPageSize(20);
        
        getCinemaSearchLogic.requestGetCinemaSearchList(key, pager, httpUtil);
    }
    
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.exitImg1 || v.getId() == R.id.exitRelative)
        {
            finish();
        }
        else if (v.getId() == R.id.search_Btn)
        {
            
            String searchWords = seacrhing_park_et.getText().toString();
            httpUtil = new HttpUtils();
            showProcessDialog(dismiss);
            getCinemaSearchLogic.setData(mHandler);
            Pager pager = new Pager();
            pager.setPageId(1);
            pager.setPageSize(20);
            
            getCinemaSearchLogic.requestGetCinemaSearchList(searchWords,
                    pager,
                    httpUtil);
        }
    }
    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.CINEMASEARCH_SUCCESS_GET:
                
                initDataCinemaSearch();
                break;
        }
        super.handleMsg(msg);
        
    }
    
    private void initDataCinemaSearch()
    {
        hotAdapter = new HotCinemaAdapter(this,
                getCinemaSearchLogic.cinemaSearchList);
        gridviews.setAdapter(hotAdapter);
        gridviews.setOnItemClickListener(new OnItemClickListener()
        {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent intent_play = new Intent(SearchActivity.this,
                        PlayerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cinema",
                        (Cinema) hotAdapter.getItem(position));
                intent_play.putExtras(bundle);
                startActivity(intent_play);
                
            }
        });
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // getMenuInflater().inflate(R.menu.activity_cinema_start, menu);
        return true;
    }
    
    @Override
    public void initData()
    {
        if (null != GetCinemaSearchLogic.getInstance())
        {
            getCinemaSearchLogic = GetCinemaSearchLogic.getInstance();
        }
        
    }
    
    @Override
    public void initLayout(Bundle paramBundle)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void clearData()
    {
        // TODO Auto-generated method stub
        
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getCinemaSearchLogic.stopRequest();
        }
    };
}

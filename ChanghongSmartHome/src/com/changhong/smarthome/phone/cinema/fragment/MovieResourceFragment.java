package com.changhong.smarthome.phone.cinema.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.MovieResourceAdapter;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.GetMovieResourceLogic;
import com.lidroid.xutils.HttpUtils;

/**
* @ClassName: MovieResourceFragment
* @author yang_jun
* @date Apr 18, 2014 11:11:03 AM  
* @Description:电影资源，优库 土豆 
*/
public class MovieResourceFragment extends SuperFragment
{
    private View view;
    
    private HttpUtils httpUtil;
    private GetMovieResourceLogic getMovieResourceLogic;
    
    private MovieResourceAdapter movieResourceAdapter;
    
    private GridView gridviews;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.MOVIERESOURCE_SUCCESS_GET:
                
                initDataMovieResource();
                break;
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    private void initDataMovieResource()
    {
  movieResourceAdapter = new MovieResourceAdapter(getActivity(), getMovieResourceLogic.movieSourceList);
  gridviews.setAdapter(movieResourceAdapter);
    
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetMovieResourceLogic.getInstance())
        {
            getMovieResourceLogic = GetMovieResourceLogic.getInstance();
        }
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        long contentId= this.getArguments().getLong("contentId");
        view = inflater.inflate(R.layout.movieresource, container, false);
        gridviews= (GridView) view.findViewById(R.id.gridviews);

        httpUtil = new HttpUtils();
//        showProcessDialog(dismiss);
        getMovieResourceLogic.setData(fHandler);
        getMovieResourceLogic.requestGetMovieSourceList(contentId, httpUtil);
 
        return view;
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getMovieResourceLogic.stopRequest();
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
}

package com.changhong.smarthome.phone.cinema.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.GetMovieDetailLogic;
import com.lidroid.xutils.HttpUtils;

/**
* @ClassName: MovieDetailFragment
* @author yang_jun
* @date Apr 18, 2014 11:10:52 AM  
* @Description: 电影详情
*/
public class MovieDetailFragment extends SuperFragment
{
    private View view;
    
    private GetMovieDetailLogic getMovieDetailLogic;
    
    private HttpUtils httpUtil;
    
    private TextView contentName;
    
    private RatingBar recommendationLevel;
    
    private TextView contentType;
    
    private TextView language;
    
    private TextView director;
    
    private TextView actor;
    
    private TextView publishTime;
    
    private TextView contentDesc;
    
    
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
            case HttpAction.MOVIEDETAIL_SUCCESS_GET:
                
                initDataMovieDetail();
                break;
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    private void initDataMovieDetail()
    {
        contentName.setText(getMovieDetailLogic.movieDetail.getContentName());
        contentType.setText(getMovieDetailLogic.movieDetail.getContentType());
        language.setText(getMovieDetailLogic.movieDetail.getLanguage());
        director.setText(getMovieDetailLogic.movieDetail.getDirector());
        actor.setText(getMovieDetailLogic.movieDetail.getActor());
        publishTime.setText(getMovieDetailLogic.movieDetail.getPublishTime());
        contentDesc.setText("\t\t"+getMovieDetailLogic.movieDetail.getContentDesc());
        recommendationLevel.setRating(getMovieDetailLogic.movieDetail.getRecommendationLevel());
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetMovieDetailLogic.getInstance())
        {
            getMovieDetailLogic = GetMovieDetailLogic.getInstance();
        }
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        long contentId = this.getArguments().getLong("contentId");
        view = inflater.inflate(R.layout.moviedetail, container, false);
        
        contentName = (TextView) view.findViewById(R.id.contentName);
        recommendationLevel = (RatingBar) view.findViewById(R.id.recommendationLevel);
        contentType = (TextView) view.findViewById(R.id.contenttype);
        language = (TextView) view.findViewById(R.id.language);
        director = (TextView) view.findViewById(R.id.director);
        actor = (TextView) view.findViewById(R.id.actor);
        publishTime = (TextView) view.findViewById(R.id.publishTime);
        contentDesc = (TextView) view.findViewById(R.id.contentDesc);

            httpUtil = new HttpUtils();
            //        showProcessDialog(dismiss);
            getMovieDetailLogic.setData(fHandler);
            getMovieDetailLogic.requestGetMovieDetailList(contentId, httpUtil);
   
        
        return view;
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getMovieDetailLogic.stopRequest();
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

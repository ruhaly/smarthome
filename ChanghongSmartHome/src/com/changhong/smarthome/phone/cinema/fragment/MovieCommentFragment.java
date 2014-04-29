package com.changhong.smarthome.phone.cinema.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.changhong.sdk.fragment.SuperFragment;
import com.changhong.smarthome.phone.R;
import com.changhong.smarthome.phone.cinema.adapter.MovieCommentAdapter;
import com.changhong.smarthome.phone.cinema.entry.Pager;
import com.changhong.smarthome.phone.cinema.http.HttpAction;
import com.changhong.smarthome.phone.cinema.logic.GetMovieCommentLogic;
import com.lidroid.xutils.HttpUtils;

/**
* @ClassName: MovieCommentFragment
* @author yang_jun
* @date Apr 18, 2014 11:10:26 AM  
* @Description:电影评论 
*/
public class MovieCommentFragment extends SuperFragment
{
    private View view;
    
    private GetMovieCommentLogic getMovieCommentLogic;
    
    private HttpUtils httpUtil;
    
    private EditText comment_Edit;
    
    private TextView comment_Btn;
    
    private ListView commentListView;
    
    private MovieCommentAdapter movieCommentAdapter;
    
    private long contentId;
    
    private Pager pager;
    
    private String comment;
    

    
    @Override
    public void handleMsg(Message msg)
    {
        switch (msg.what)
        {
            case HttpAction.MOVIECOMMENT_SUCCESS_GET:
                
                initDataMovieComment();
                break;
            case HttpAction.MOVIECOMMENT_SUCCESS_ADD:
              
                addDataMovieComment();
                showToast("发表成功");
                break;
            default:
                break;
        }
        super.handleMsg(msg);
    }
    
    private void addDataMovieComment()
    {
        movieCommentAdapter.notifyDataSetChanged();
        comment_Edit.setText("");
       
        //comment_Edit.setInputType(InputType.TYPE_NULL);
        //getMovieCommentLogic.movieCommentList
        //MovieComment comment1 = new MovieComment();
        
    }
    
    private void initDataMovieComment()
    {

        movieCommentAdapter = new MovieCommentAdapter(getActivity(),
                getMovieCommentLogic.movieCommentList);
        commentListView.setAdapter(movieCommentAdapter);
    }
    
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
    public void onClick(View v)
    {
        if (v.getId() == R.id.comment_Btn)
        {
            
        }
        
    }
    
    @Override
    public void initData()
    {
        if (null != GetMovieCommentLogic.getInstance())
        {
            getMovieCommentLogic = GetMovieCommentLogic.getInstance();
        }
        
    }
    
    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        contentId = this.getArguments().getLong("contentId");
        view = inflater.inflate(R.layout.moviecomment, container, false);
        comment_Edit = (EditText) view.findViewById(R.id.comment_Edit);
        comment_Btn = (TextView) view.findViewById(R.id.comment_Btn);
        commentListView = (ListView) view.findViewById(R.id.commentListView);
        
        comment_Btn.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                comment = comment_Edit.getText().toString();
                if (comment == null || comment.equals(""))
                {
                    showToast("评论不能为空");
                }
                else
                {
                    if (comment.length() > 80)
                    {
                        showToast("评论字数不能超过80");
                    }
                    else
                    {
                        getMovieCommentLogic.requestaddMovieCommentList(contentId,
                                comment,
                                httpUtil);
                    }
                }
                
            }
        });

            httpUtil = new HttpUtils();
            // showProcessDialog(dismiss);
            getMovieCommentLogic.setData(fHandler);
            pager = new Pager();
            pager.setPageId(1);
            pager.setPageSize(20);
            getMovieCommentLogic.requestGetMovieCommentList(contentId,
                    pager,
                    httpUtil);
  
        return view;
    }
    
    DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog)
        {
            getMovieCommentLogic.stopRequest();
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
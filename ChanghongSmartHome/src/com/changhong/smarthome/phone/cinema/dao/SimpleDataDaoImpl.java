package com.changhong.smarthome.phone.cinema.dao;

import java.util.HashMap;
import java.util.List;

import android.database.SQLException;

import com.changhong.smarthome.phone.cinema.entry.VideoFile;
import com.j256.ormlite.dao.Dao;

public class SimpleDataDaoImpl implements SimpleDataDao
{
    /**
     * ��Ӽ�¼
     * 
     * @param simpledatadao
     * @param name
     * @param pwd
     * @throws SQLException
     */
    @Override
    public int addSimpleData(Dao<VideoFile, String> simpledatadao,
            String contentId, String path, String name, long time, String sqlNum,String picUrl)
            throws SQLException
    {
        int x = 0;
        Dao<VideoFile, String> simpledataDao = simpledatadao;
        VideoFile simpleData = new VideoFile(contentId, path, name, time,
                sqlNum,picUrl);
        try
        {
            // Create a new row in the database from an object.
            x = simpledataDao.create(simpleData);
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        return x;
    }
    
    /**
     * @param simpledatadao
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public List<VideoFile> findSimpleDataforid(
            Dao<VideoFile, String> simpledatadao, String contentId,
            String path, String name, long time, String sqlNum)
            throws SQLException
    {
        Dao<VideoFile, String> simpledataDao = simpledatadao;
        HashMap<String, Object> simpledataMap = new HashMap<String, Object>();
        
        simpledataMap.put("contentId", contentId);
        simpledataMap.put("path", path);
        simpledataMap.put("name", name);
        simpledataMap.put("time", time);
        simpledataMap.put("sqlNum", sqlNum);
        
        List<VideoFile> simpledataLists = null;
        try
        {
            simpledataLists = simpledataDao.queryForFieldValues(simpledataMap);
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        return simpledataLists == null ? null : simpledataLists;
    }
    
    /**
     * @param simpledatadao
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int updateSimpleDataforid(Dao<VideoFile, String> simpledatadao,
            VideoFile videoFile) throws SQLException
    {
        Dao<VideoFile, String> simpledataDao = simpledatadao;
        
        List<VideoFile> simpledataLists = null;
        int num = 0;
        try
        {
            num = simpledataDao.update(videoFile);
        }
        catch (java.sql.SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return num;
    }
    
    @Override
    public List<VideoFile> findallSimpleData(
            Dao<VideoFile, String> simpledatadao) throws Exception
    {
        Dao<VideoFile, String> dao = simpledatadao;
        return dao.queryForAll();
        
    }
    
    @Override
    public int deleteSimpleDataforid(Dao<VideoFile, String> simpledatadao,
            String id) throws Exception
    {
        Dao<VideoFile, String> dao = simpledatadao;
        return dao.deleteById(id);
        
    }
    
}

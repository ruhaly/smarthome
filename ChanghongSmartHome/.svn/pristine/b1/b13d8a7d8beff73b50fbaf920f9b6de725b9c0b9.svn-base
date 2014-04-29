package com.changhong.smarthome.phone.property.recording;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

public class AudioRecorder
{
    
    MediaRecorder recorder;
    
    final String path;
    
    public AudioRecorder(String path)
    {
        this.path = sanitizePath(path);
        recorder = new MediaRecorder();
    }
    
    private String sanitizePath(String path)
    {
        if (!path.startsWith("/"))
        {
            path = "/" + path;
        }
        if (!path.contains("."))
        {
            path += ".ram";
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/my" + path;
    }
    
    public void start() throws IOException
    {
        
        String state = android.os.Environment.getExternalStorageState();
        recorder = new MediaRecorder();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED))
        {
            throw new IOException("SD Card is not mounted,It is  " + state
                    + ".");
        }
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs())
        {
            throw new IOException("Path to file could not be created");
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path);
        recorder.prepare();
        recorder.start();
    }
    
    public void stop() throws IOException
    {
        if (recorder != null)
        {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }
    
}
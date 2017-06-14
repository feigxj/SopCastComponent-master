package com.laifeng.sopcastsdk.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaMuxer;
import android.os.Build;

import java.io.IOException;

/**
 * Edit date 2017/6/14
 * Author:lifei
 * Description:
 */
public class MediaMuxerCodec {
    private Context context;
    private static MediaMuxerCodec instance = null;  //多路复用器，用于音视频混合
    private static MediaMuxer mMuxer = null;  //多路复用器，用于音视频混合
    private String mPath;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private MediaMuxerCodec(String path) {
        this.mPath = path;
        try {
            mMuxer = new MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static MediaMuxerCodec getInstance(String path) {
        if (instance == null) {
            synchronized (MediaMuxerCodec.class) {
                if (instance == null) {
                    instance = new MediaMuxerCodec(path);
                }
            }
        }
        return instance;
    }
}

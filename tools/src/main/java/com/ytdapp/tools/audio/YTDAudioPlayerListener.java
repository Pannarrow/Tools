package com.ytdapp.tools.audio;

public interface YTDAudioPlayerListener {

    void onPrepared();

    void onProgress(int progress);

    void onComplete();
}

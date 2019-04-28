package com.dnamedical.player;

import android.net.Uri;

public interface IEasyExoVideoCallback {

  void onStarted(EasyExoVideoPlayer player);

  void onPaused(EasyExoVideoPlayer player);

  void onPreparing(EasyExoVideoPlayer player);

  void onPrepared(EasyExoVideoPlayer player);

  void onBuffering(int percent);

  void onError(EasyExoVideoPlayer player, Exception e);

  void onCompletion(EasyExoVideoPlayer player);

  void onRetry(EasyExoVideoPlayer player, Uri source);

  void onSubmit(EasyExoVideoPlayer player, Uri source);

  void onClickVideoFrame(EasyExoVideoPlayer player);

  void onSeekChange(EasyExoVideoPlayer player, boolean isSeeking);

  void onPauseWhenReady(EasyExoVideoPlayer player);
}

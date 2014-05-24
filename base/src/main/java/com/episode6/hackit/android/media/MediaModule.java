package com.episode6.hackit.android.media;

import android.media.MediaPlayer;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true)
public class MediaModule {

  @Provides
  MediaPlayer provideMediaPlayer() {
    return new MediaPlayer();
  }

}

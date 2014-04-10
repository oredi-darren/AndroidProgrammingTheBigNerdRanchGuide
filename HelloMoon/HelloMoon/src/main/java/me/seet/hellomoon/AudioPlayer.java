package me.seet.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.SurfaceView;

/**
 * Created by dseet on 4/9/2014.
 */
public class AudioPlayer {
    private MediaPlayer mPlayer;
    private int mCurrentPosition;
    private SurfaceView mView;
    public AudioPlayer(SurfaceView view) {
        mView = view;
    }
    public void stop(boolean isResetPosition) {
        if(isResetPosition) {
            mCurrentPosition = 0;
        }
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void pause(){
        mPlayer.pause();
        mCurrentPosition = mPlayer.getCurrentPosition();
    }

    public void play(Context c) {
        stop(false);

        //mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
        mPlayer = MediaPlayer.create(c, R.raw.sample_mpeg4);
        mPlayer.setDisplay(mView.getHolder());
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop(true);
            }
        });
        mPlayer.seekTo(mCurrentPosition);
        mPlayer.start();
    }
}

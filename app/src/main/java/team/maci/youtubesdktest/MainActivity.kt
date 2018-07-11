package team.maci.youtubesdktest

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener{

    lateinit var youtubePlayer : YouTubePlayer
    lateinit var youtubePlayerView : YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        youtubePlayerView = findViewById(R.id.youtube_player)
        youtubePlayerView.initialize("API_KEY", this)
    }

    override fun onInitializationSuccess(youtubePlayerProvider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        youtubePlayer = player ?: throw IllegalArgumentException("Player argument was null when initialized!")
        var flags = youtubePlayer.fullscreenControlFlags and YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION.inv()
        youtubePlayer.fullscreenControlFlags = flags
        youtubePlayer.setOnFullscreenListener(this)

        if(!wasRestored){
            youtubePlayer.cueVideo("8M_vBlWMAiY")
        }
    }
    override fun onInitializationFailure(youtubeProvider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
        result?.getErrorDialog(this, 0)?.show()
        Log.d("MACI","Error when we try to init the YoutubePlayerView ${result?.toString()}")
    }

    override fun onFullscreen(isFullScreen: Boolean) {
        if(isFullScreen){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}

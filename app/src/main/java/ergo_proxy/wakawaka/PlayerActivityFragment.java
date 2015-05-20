package ergo_proxy.wakawaka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import ergo_proxy.wakawaka.Util.DeveloperKey;


/**
 * Заполняющий фрагмент, содержащий простой вид.
 */
public class PlayerActivityFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener {

   /* private YouTubePlayerView playerView;*/
   private YouTubePlayerFragment mYouTubeFragment;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

       /* playerView = (YouTubePlayerView)getView().findViewById(R.id.player_view);
        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);*/
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(getActivity(), getString(R.string.failed), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean restored) {
        if(!restored){
            String videoId=getActivity().getIntent().getStringExtra(VideoListActivityFragment.VIDEO_ID_TAG);
            player.cueVideo(videoId);
        }
    }

    /*public PlayerActivityFragment() {
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }
}

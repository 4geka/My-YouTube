package ergo_proxy.wakawaka;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ergo_proxy.wakawaka.Model.VideoItem;
import ergo_proxy.wakawaka.Util.UtubeDataConnector;

/**
 * Created by Ergo-Proxy on 19.05.2015.
 */

/**
 * Заполняющий фрагмент, содержащий простой вид.
 */
public class VideoListActivityFragment extends Fragment {

    private EditText mSearchInput;
    private ListView mVideosFound;

    private Handler mHandler;
    private String mQueryString;

    public static final String VIDEO_ID_TAG="VideoListActivityFragment.videoID";
    public static final String VIDEO_QUERY_TAG="mQueryString";
    private List<VideoItem> mSearchResults;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mSearchInput = (EditText)getView().findViewById(R.id.search_input);
        mVideosFound = (ListView)getView().findViewById(R.id.videos_found);

        mHandler = new Handler();

        mSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });
        addClickListener();
        if (savedInstanceState != null){
            mQueryString=savedInstanceState.getString(VIDEO_QUERY_TAG);
        }

        searchOnYoutube(mQueryString);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    private void searchOnYoutube(final String keywords){
        final FragmentActivity activity = getActivity();
        new Thread(){
            public void run(){
                UtubeDataConnector tubeCon = new UtubeDataConnector(activity);
                if (keywords==null){
                    mSearchResults = tubeCon.showLastVideo();
                }
                else {
                    mSearchResults = tubeCon.search(keywords);
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getActivity().getApplicationContext(), R.layout.video_item, mSearchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView =getActivity().getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);
                TextView videoDate = (TextView)convertView.findViewById(R.id.video_date);

                VideoItem searchResult = mSearchResults.get(position);
                Picasso.with(getActivity().getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };

        mVideosFound.setAdapter(adapter);
    }
    private void addClickListener(){
       final Context cnxt = getActivity().getApplicationContext();
        mVideosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                PlayerYouTubeFragment myFragment = PlayerYouTubeFragment.newInstance(mSearchResults.get(pos).getId());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.video_container, myFragment).commit();
            }

        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(VIDEO_QUERY_TAG,mQueryString);

    }
}

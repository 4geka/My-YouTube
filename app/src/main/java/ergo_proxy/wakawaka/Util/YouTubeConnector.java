package ergo_proxy.wakawaka.Util;

/**
 * Created by Ergo Proxy on 18.05.2015.
 */

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ergo_proxy.wakawaka.Model.VideoItem;
import ergo_proxy.wakawaka.R;

public class YouTubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;
    private YouTube.Videos.List queryPopular;

    public static final String KEY = "AIzaSyC6pyliWX1fGdlTjk8pBxKCkbX1iNcBPjk";

    public YouTubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {

            }
        }).setApplicationName(context.getString(R.string.app_name)).build();


    }
    public ArrayList<VideoItem> search(String keywords){
        try{
            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setMaxResults(15l);
            query.setFields("items(id/videoId,snippet/publishedAt,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d("YC", "Не удалось инициализировать: " + e);
        }
        query.setQ(keywords);
        try{
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            ArrayList<VideoItem> items = new ArrayList<VideoItem>();
            for(SearchResult result:results){
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                item.setDate(result.getSnippet().getPublishedAt().getValue());
                items.add(item);
            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Не удалось инициализировать: " + e);
            return null;
        }
    }
    public ArrayList<VideoItem> popularvideo(String mostPopular) {
        try {
            queryPopular = youtube.videos().list("id,snippet");
            queryPopular.setKey(KEY);
            queryPopular.setMaxResults(15l);
            queryPopular.setFields("items(id,snippet/publishedAt,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d("YC", "Не удалось инициализировать: " + e);
        }
        queryPopular.setChart(mostPopular);
        try{
            VideoListResponse response = queryPopular.execute();
            List<Video> results = response.getItems();

            ArrayList<VideoItem> items = new ArrayList<VideoItem>();
            for(Video result:results){
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDate(result.getSnippet().getPublishedAt().getValue());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId());
                items.add(item);
            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Не удалось инициализировать: " + e);
            return null;
        }
    }


}

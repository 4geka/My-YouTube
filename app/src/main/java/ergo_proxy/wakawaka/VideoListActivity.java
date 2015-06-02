package ergo_proxy.wakawaka;

/**
 * Created by Ergo Proxy on 18.05.2015.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class VideoListActivity extends AppCompatActivity
{

    private VideoFragment videoFragment;
    private View videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, VideoListActivityFragment.newInstance()).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_video_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

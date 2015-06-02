package ergo_proxy.wakawaka;

/**
 * Created by Ergo Proxy on 18.05.2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import it.sephiroth.android.library.picasso.Picasso;
import ergo_proxy.wakawaka.Model.VideoItem;

public class ListAdapter extends ArrayAdapter<VideoItem>
{

    private ArrayList<VideoItem> mNewslist;
    private final Context mContext;
    private SimpleDateFormat mJUD;

    public ListAdapter(Context context)
    {
        super(context, R.layout.list_element);
        mNewslist = new ArrayList<>();
        mContext = context;
        JUDInit();
    }

    public void addNewslist(ArrayList<VideoItem> parsedNewsList)
    {
        parsedNewsList.removeAll(mNewslist);
        mNewslist.addAll(parsedNewsList);
        notifyDataSetChanged();
    }

    public ArrayList<VideoItem> getNewsList()
    {
        return mNewslist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        }

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.video_thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.video_title);
        TextView description = (TextView) convertView.findViewById(R.id.video_description);
        TextView videoDate = (TextView)convertView.findViewById(R.id.video_date);
        VideoItem searchResult = mNewslist.get(position);

        Picasso.with(getContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
        title.setText(searchResult.getTitle());
        description.setText(searchResult.getDescription());
        Date date = new Date(searchResult.getDate());
        videoDate.setText(mJUD.format(date));
        return convertView;
    }

    @Override
    public void clear()
    {
        super.clear();
        mNewslist.clear();
    }


    @Override
    public int getCount()
    {
        return mNewslist.size();
    }

    @Override
    public VideoItem getItem(int position)
    {
        return mNewslist.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void prependNewsList(ArrayList<VideoItem> newsList)
    {
        for (VideoItem boardNews : newsList)
        {
            mNewslist.add(0, boardNews);
        }
        notifyDataSetChanged();
    }
    private void JUDInit()
    {
        Locale russian = new Locale("ru");
        String[] newMonths =
                {
                "января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
        dfs.setMonths(newMonths);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);
        SimpleDateFormat sdf = (SimpleDateFormat) df;
        sdf.setDateFormatSymbols(dfs);
        mJUD  =  new SimpleDateFormat("d MMMM yyyy, HH:mm", new Locale("ru"));
    }
}

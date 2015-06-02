package ergo_proxy.wakawaka.Model;

/**
 * Created by Ergo Proxy on 18.05.2015.
 */

public class VideoItem
{
    private String title;
    private String description;
    private long date;
    private String thumbnailURL;
    private String id;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getThumbnailURL()
    {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail)
    {
        this.thumbnailURL = thumbnail;
    }
    public void setDate(long date)
    {
        this.date = date;
    }
    public long getDate()
    {
        return date;
    }
}

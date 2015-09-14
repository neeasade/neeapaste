package hello;

import java.rmi.activation.ActivationID;

/**
 * Class to represent a paste.
 */
public class Paste {

    private long mId;
    private String mTitle;
    private String mContent;
    private long mViews;


    public Paste(long aId, String aTitle, String aContent) {
        mId = aId;
        mTitle = aTitle;
        mContent = aContent;
        mViews=0;
    }

    public long getViews()
    {
        return ++mViews;
    }

    public long getId()
    {
        return mId;
    }

    public String getContent()
    {
        return mContent;
    }

    public String getTitle()
    {
        return mTitle;
    }
}

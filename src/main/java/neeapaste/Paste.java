package neeapaste;

import java.rmi.activation.ActivationID;

/**
 * Class to represent a paste.
 */
public class Paste {

    private long mId;
    private String mTitle;
    private String mContent;
    private long mViews;

    public Paste() {
        mId = -1;
        mTitle = "";
        mContent = "";
        mViews = 0;
    }

    public Paste(long aId, String aTitle, String aContent, long aViews) {
        mId = aId;
        mTitle = aTitle;
        mContent = aContent;
        mViews=aViews;
    }

    // Getters

    public long getViews()
    {
        return mViews;
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

    // Setters

    public void setTitle(String aTitle)
    {
        this.mTitle = aTitle;
    }

    public void setContent(String aContent)
    {
        this.mContent = aContent;
    }

    public void setId(Long aId)
    {
        this.mId = aId;
    }

    public void setViews(Long aViews)
    {
        this.mViews = aViews;
    }
}

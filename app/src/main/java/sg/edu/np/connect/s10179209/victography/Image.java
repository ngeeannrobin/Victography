package sg.edu.np.connect.s10179209.victography;

import java.util.Date;

public class Image {
    //Attributes
    protected int imageID;
    protected String memberName;
    protected byte[] image;
    protected String description;
    protected int likes;
    //Get methods
    public int getImageID()
    {
        return imageID;
    }
    public String getMemberID()
    {
        return memberName;
    }
    public byte[] getImage()
    {
        return image;
    }
    public String getDescription()
    {
        return description;
    }
    public int getLikes()
    {
        return likes;
    }
    //Constructors
    public Image()
    {

    }
    public Image(int iid, String mn, byte[] img, String des, int lik)
    {
        imageID=iid;
        memberName=mn;
        image=img;
        description=des;
        likes=lik;
    }

}


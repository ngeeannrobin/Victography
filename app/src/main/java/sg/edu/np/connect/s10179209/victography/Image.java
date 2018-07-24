package sg.edu.np.connect.s10179209.victography;

import java.util.Date;

public class Image {
    //Attributes
    protected int imageID;
    protected int memberID;
    protected byte[] image;
    protected String description;
    protected int likes;
    protected Date datePosted;
    //Get methods
    public Date getDates()
    {
        return datePosted;
    }
    public int getImageID()
    {
        return imageID;
    }
    public int getMemberID()
    {
        return memberID;
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
    public Image(int iid, int mid, byte[] img, String des, int lik, Date dt)
    {
        imageID=iid;
        memberID=mid;
        image=img;
        description=des;
        likes=lik;
        datePosted=dt;
    }
}


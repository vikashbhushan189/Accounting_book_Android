package vikashbhushan.example.accountingbook;

public class Upload {
    private String mDate;
    private String mImageUrl;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String date, String imageUrl) {
        if (date.trim().equals("")) {
            date = "No date";
        }

        mDate = date;
        mImageUrl = imageUrl;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}

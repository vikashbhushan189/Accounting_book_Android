package vikashbhushan.example.accountingbook;


public class content_main {
    public String id;
    public String email;
    public String name;
    public int Dat;
    public String Password;
    public String Phone;
    public String Image;
    public String Credit;
    public String Debit;
    public String Timestamp;
    public String Date;


    public content_main(int Date,String id, String email, String name, String password, String phone, String image, String credit, String debit, String timestamp, String date) {
        this.id = id;
        this.Dat=Date;
        this.email = email;
        this.name = name;
        this.Password = password;
        this.Phone = phone;
        this.Image = image;
        this.Credit = credit;
        this.Debit = debit;
        this.Timestamp = timestamp;
        this.Date = date;
    }

    public int getDat() {
        return Dat;
    }

    public void setDat(int date) {
        this.Dat = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    public String getDebit() {
        return Debit;
    }

    public void setDebit(String debit) {
        Debit = debit;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }



}

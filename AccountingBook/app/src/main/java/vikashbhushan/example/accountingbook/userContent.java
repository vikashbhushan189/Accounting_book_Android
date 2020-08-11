package vikashbhushan.example.accountingbook;


public class userContent {

    private String email;
    private String name;
    private String Password;


    public userContent() {

    }

    public userContent(String id, String email, String name, String password) {

        this.email = email;
        this.name = name;
        this.Password = password;

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

    public String toString() {
        return " User Name: " + this.name + "\n Email: " + email + "\n Password: " + Password;
    }
}




package Model;

public class Users {

    private String name;
    private String email;
    private String password_hash;
    private String api_key;
    private String status;

    public Users(String name, String email, String password_hash, String api_key) {
        this.name = name;
        this.email = email;
        this.password_hash = password_hash;
        this.api_key = api_key;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password_hash;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password_hash = password;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

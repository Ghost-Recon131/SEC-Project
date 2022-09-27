package rmit.sec.webstorepmicroservice.security;

// Custom response when login credentials are invalid.
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


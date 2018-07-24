package sg.edu.np.connect.s10179209.victography;

public class Login {
    private String username;
    private String password;

    public Login(){}

    public Login(String u, String p){
        username = u;
        password = p;
    }

    public String getPassword(){ return password; }
    public String getUsername(){ return username; }

    public void setPassword(String p){ password = p; }
    public void setUsername(String u){ username = u; }

}

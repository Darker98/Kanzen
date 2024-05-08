public class User {
    private int id;
    private String name;
    private String email;

    User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter for id
    public int getID() {
        return id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
}

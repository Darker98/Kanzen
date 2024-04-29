public class Swimlane {
    private int id;
    private String label;
    
    Swimlane(String label) {
        this.label = label;
    }

    // Getter for id
    public int getID() {
        return id;
    }

    // Getter for label
    public String getLabel() {
        return label;
    }

    // Setter for label
    public void setLabel(String label) {
        this.label = label;
    }
}

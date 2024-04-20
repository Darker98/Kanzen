public class Column {
    private String name;
    private ArrayList<Card> cards;
    private ArrayList<Swimlane> swimlanes;
    private int wip;

    // Constructor
    Column(String name, int wip) {
        // Assign variables their values
        this.name = name;
        this.wip = wip;

        // Initialize arrays
        cards = new ArrayList<Card>();
        swimlanes = new ArrayList<Swimlane>();
    }
}

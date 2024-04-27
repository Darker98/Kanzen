public class Column {
    private static int id_tracker;
    private int id;
    private String name;
    private int wip;
    private int cardAmount;
    private ArrayList<Card> cards;
    private ArrayList<Swimlane> swimlanes;

    // Constructor
    Column(String name, int wip) {
        // Initialize values
        id = id_tracker;
        this.name = name;
        this.wip = wip;
        cardAmount = 0;

        // Initialize arrays
        cards = new ArrayList<Card>();
        swimlanes = new ArrayList<Swimlane>();

        id_tracker++;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for wip
    public void setWip(int wip) {
        this.wip = wip;
    }

    // Getter for wip
    public int getWip() {
        return wip;
    }

    // Setter for id
    public void setID(int id) {
        this.id = id;
    }

    // Getter for id
    public int getID() {
        return id;
    }

    // Getter for cardsAmount
    public int getCardAmount() {
        return cardAmount;
    }

    // Add a card
    public void addCard(Card card) {
        cards.add(card);
        cardAmount++;
    }

    // Delete a card
    public void deleteCard(int id) {
        // Loop through array and remove card if id matches
        for (int i = 0; i < cardAmount; i++) {
            if (cards.get(i).getID() == id) {
                cards.remove(i);
                cardAmount--;
                return;
            } 
        }

        throw new Exception("Invalid ID provided to deleteCard...");
    }

    // Get a card
    public Card getCard(int id) {
        // Loop through array and return card if id matches
        for (int i = 0; i < cardAmount; i++) {
            if (cards.get(i).getID() == id) 
                return cards.get(i);
        }

        throw new Exception("Invalid ID provided to getCard...")
    }

    // Add a swimlane
    public void addSwimlane(Swimlane swimlane) {
        swimlanes.add(swimlane);
    }

    // Delete a swimlane
    public void deleteSwimlane(int id) {
        // Loop through array and delete swimlane if id matches
        for (int i = 0; i < swimlanes.size(); i++) {
            if (swimlanes.get(i).getID() == id) {
                swimlanes.remove(i);
                return;
            }
        }

        throw new Exception("Invalid ID provided to deleteSwimlane...");
    }

    // Get a swimlane
    public Swimlane getSwimlane(int id) {
        // Loop through array and return if id matches
        for (int i = 0; i < swimlanes.size(); i++) {
            if (swimlanes.get(i).getID() == id) {
                return swimlanes.get(i);
            }
        }

        throw new Exception("Invalid ID provided to getSwimlane...");
    }

    
    /* // TODO: Create methods
        - updateCard
        - updateSwimlane
    */
}


public class Card {


    private int id;
    private String title;
    private String description;
    private String status;
    private String label;
    private boolean blocked;
    private boolean urgent;
    private Date date;
    private Date dateCreated;
    private ArrayList<Member> assigned;


    public Card(int id, String title, String description, String status,
                String label, boolean blocked, boolean urgent, Date date) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.label = label;
        this.blocked = blocked;
        this.urgent = urgent;
        this.date = date;
        this.dateCreated = new Date(); // Automatically set to current creation date
        this.assigned = new ArrayList<>();
    }


    public void setID(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;

    }

    public void setDescription(String Description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setStatus(String status){
        this.status= status;
    }

    public String getStatus(){
        return status;
    }

    public void setLabel(String label){
        this.label= label;
    }

    public String getLabel(){
        return label;
    }

    public void setBlocked (boolean blocked){
        this.blocked = blocked;
    }

    public boolean getBlocked(){
        return blocked;
    }

    public void setUrgent (boolean urgent){
        this.urgent= urgent;
    }

    public boolean getUrgent(){
        return urgent;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }
    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public ArrayList<Member> getMembers() {
        return assigned;
    }


    public void addMember(Member member) {
        assigned.add(member);
    }

    public void deleteMember(int index) {
        if (assigned == null || index < 0 || index >= assigned.size()) {
            return;  // for invalid index
        }
        assigned.remove(index);
    }


}


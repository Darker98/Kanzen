public class Manager extends User {
    private ArrayList<Member> team;

    Manager(String name, String email) {
        super(name, email);
        team = new ArrayList<Member>;
    }

    // Getter for team
    public ArrayList<Member> getTeam() {
        return team;
    }

    // Add a team member
    public void addMember(Member member) {
        team.add(member);
    }

    // Delete a team member
    public void deleteMember(int id) {
        for (int i = 0; i < team.size(); i++) {
            if (team.get(i).getID() == id)
                team.remove(i);
        }

        throw new Exception("Invalid ID provided to deleteMember...");
    }
}

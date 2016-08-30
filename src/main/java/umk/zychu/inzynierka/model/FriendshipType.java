package umk.zychu.inzynierka.model;

public enum FriendshipType{
    INVITE(1, "INVITE"),
    ACCEPT(2, "ACCEPT"),
    DECLINE(3, "DECLINE"),
    BLOCK(4, "BLOCK"),
    CANCEL(5, "CANCEL"),
    REMOVE(6, "REMOVE");

    private Integer id;

    private String meaning;

    FriendshipType(int id, String meaning){
        this.id = id;
        this.meaning = meaning;
    }

    public static FriendshipType fromId(int id) {
        for (FriendshipType f : values()) {
            if (f.getValue() == id) {
                return f;
            }
        }
        return null;
    }

    public Integer getValue() {
        return id;
    }

    public String getMeaning() {
        return meaning;
    }

}
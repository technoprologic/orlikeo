package umk.zychu.inzynierka.model.enums;


public enum EnumeratedEventRole {
    ORGANIZER(1),
    GUEST(2);

    private final int value;

    EnumeratedEventRole(int value){
        this.value = value;
    }

    public int getValue(EnumeratedEventRole role){
        return value;
    }

}

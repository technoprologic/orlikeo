package umk.zychu.inzynierka.model.enums;


public enum EnumeratedUserEventDecision {

    INVITED(1),
    ACCEPTED(2),
    REJECTED(3),
    //indicates user not invited but can invite
    NOT_INVITED(4);

    private final int value;

    EnumeratedUserEventDecision(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}

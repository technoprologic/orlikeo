package umk.zychu.inzynierka.model.enums;

public enum EnumeratedEventState {

    IN_A_BASKET(1),
    IN_PROGRESS(2),
    READY_TO_ACCEPT(3),
    THREATENED(4),
    APPROVED(5);

    private int value;

    EnumeratedEventState(final int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

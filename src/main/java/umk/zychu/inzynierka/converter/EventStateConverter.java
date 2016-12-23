package umk.zychu.inzynierka.converter;

import umk.zychu.inzynierka.model.enums.EnumeratedEventState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventState.*;

@Converter(autoApply=true)
public class EventStateConverter implements AttributeConverter<EnumeratedEventState, Integer> {


    @Override
    public Integer convertToDatabaseColumn(EnumeratedEventState attribute) {
        switch(attribute){
            case IN_A_BASKET: return 1;
            case IN_PROGRESS: return 2;
            case READY_TO_ACCEPT: return 3;
            case THREATENED: return 4;
            case APPROVED: return 5;
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public EnumeratedEventState convertToEntityAttribute(Integer dbData) {
        return switcher(dbData);
    }

    public static EnumeratedEventState convertToEnum(int value){
        return switcher(value);
    }

    private static EnumeratedEventState switcher(Integer value){
        switch(value){
            case 1: return IN_A_BASKET;
            case 2: return IN_PROGRESS;
            case 3: return READY_TO_ACCEPT;
            case 4: return THREATENED;
            case 5: return APPROVED;
            default:
                throw new IllegalArgumentException("Unknown" + value);
        }
    }
}

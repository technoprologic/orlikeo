package umk.zychu.inzynierka.converter;


import umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.*;

@Converter(autoApply=true)
public class UserEventDecisionConverter implements AttributeConverter<EnumeratedUserEventDecision, Integer>{

    @Override
    public Integer convertToDatabaseColumn(EnumeratedUserEventDecision attribute) {
        switch(attribute){
            case INVITED: return 1;
            case ACCEPTED: return 2;
            case REJECTED: return 3;
            case NOT_INVITED: return 4;
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public EnumeratedUserEventDecision convertToEntityAttribute(Integer dbData) {
        return switcher(dbData);
    }

    public static EnumeratedUserEventDecision convertToEnum(int value){
        return switcher(value);
    }

    private static EnumeratedUserEventDecision switcher(Integer value){
        switch (value){
            case 1 : return INVITED;
            case 2 : return NOT_INVITED;
            case 3 : return REJECTED;
            case 4 : return NOT_INVITED;
            default: throw new IllegalArgumentException("Unknown: " + value);
        }
    }
}

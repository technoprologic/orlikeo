package umk.zychu.inzynierka.converter;

import umk.zychu.inzynierka.model.enums.EnumeratedEventRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.ORGANIZER;

@Converter(autoApply=true)
public class UserEventRoleConverter implements AttributeConverter<EnumeratedEventRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumeratedEventRole attribute) {
        switch (attribute){
            case ORGANIZER: return 1;
            case GUEST: return 2;
            default: throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public EnumeratedEventRole convertToEntityAttribute(Integer dbData) {
        return switcher(dbData);
    }

    private static EnumeratedEventRole switcher(Integer value){
        switch (value){
            case 1 : return ORGANIZER;
            case 2 : return GUEST;
            default: throw new IllegalArgumentException("Unknown" + value);
        }
    }
}

package no.vegvesen.nvdbapi.client.model.datakatalog;

import java.time.LocalDate;

public class IntegerEnumValue extends EnumValue<Integer> {
    public IntegerEnumValue(Integer id,
                            Integer sortNumber,
                            Integer value,
                            String shortName,
                            String description,
                            LocalDate objectListDate,
                            boolean isDefault,
                            boolean isShortValueUsable,
                            Integer shortValueLength,
                            Integer complimentaryId) {
        super(id, sortNumber, value, shortName, description, objectListDate, isDefault,
                isShortValueUsable, shortValueLength, complimentaryId);
    }
}

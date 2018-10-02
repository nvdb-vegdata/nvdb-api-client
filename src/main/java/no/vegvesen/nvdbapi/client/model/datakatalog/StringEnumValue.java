package no.vegvesen.nvdbapi.client.model.datakatalog;

import java.time.LocalDate;

public class StringEnumValue extends EnumValue<String> {
    public StringEnumValue(Integer id,
                           Integer sortNumber,
                           String value,
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

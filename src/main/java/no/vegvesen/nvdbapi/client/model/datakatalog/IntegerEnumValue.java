package no.vegvesen.nvdbapi.client.model.datakatalog;

import java.time.LocalDate;

public class IntegerEnumValue extends EnumValue<Integer> {
    public IntegerEnumValue(Integer id,
                            Integer sortNumber,
                            Integer value,
                            String shortName,
                            String description,
                            LocalDate objectListDate) {
        super(id, sortNumber, value, shortName, description, objectListDate);
    }
}

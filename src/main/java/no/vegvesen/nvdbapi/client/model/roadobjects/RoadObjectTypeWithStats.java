package no.vegvesen.nvdbapi.client.model.roadobjects;

import java.util.Objects;

public class RoadObjectTypeWithStats {
    private final int typeId;
    private final String typeName;
    private final Statistics statistics;

    public RoadObjectTypeWithStats(int typeId, String typeName, Statistics statistics) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.statistics = statistics;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public String toString() {
        return "" +
            "typeId=" + typeId +
            ", name='" + typeName + '\'' +
            ", statistics=" + statistics ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadObjectTypeWithStats that = (RoadObjectTypeWithStats) o;
        return typeId == that.typeId &&
            Objects.equals(typeName, that.typeName) &&
            Objects.equals(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, typeName, statistics);
    }
}

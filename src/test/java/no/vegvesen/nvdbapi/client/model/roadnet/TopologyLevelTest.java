package no.vegvesen.nvdbapi.client.model.roadnet;

import org.junit.Test;

import static no.vegvesen.nvdbapi.client.model.roadnet.TopologyLevel.KJOREBANE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TopologyLevelTest {

    @Test
    public void fromNull() {
        TopologyLevel.from(null);
    }

    @Test
    public void happyDay() {
        TopologyLevel from = TopologyLevel.from(1);
        assertThat(from.getDescription(), is(KJOREBANE.getDescription()));
    }
}
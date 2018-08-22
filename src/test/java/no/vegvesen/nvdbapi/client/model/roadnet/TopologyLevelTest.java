package no.vegvesen.nvdbapi.client.model.roadnet;

import org.junit.Test;

import static no.vegvesen.nvdbapi.client.model.roadnet.TopologyLevel.KJOREBANE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TopologyLevelTest {

    @Test
    public void happyDay() {
        TopologyLevel from = TopologyLevel.fromValue("Kjørebane");
        assertThat(from.getDescription(), is(KJOREBANE.getDescription()));
    }
}
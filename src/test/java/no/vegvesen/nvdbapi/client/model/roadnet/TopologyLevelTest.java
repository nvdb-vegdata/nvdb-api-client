package no.vegvesen.nvdbapi.client.model.roadnet;


import org.junit.jupiter.api.Test;

import static no.vegvesen.nvdbapi.client.model.roadnet.TopologyLevel.KJOREBANE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TopologyLevelTest {

    @Test
    public void happyDay() {
        TopologyLevel from = TopologyLevel.fromValue("Kjørebane");
        assertThat(from.getDescription(), is(KJOREBANE.getDescription()));
    }
}
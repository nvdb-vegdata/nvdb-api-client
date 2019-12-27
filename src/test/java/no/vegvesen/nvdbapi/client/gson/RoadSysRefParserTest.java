package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSystem;
import org.junit.jupiter.api.Test;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class RoadSysRefParserTest {

    @Test
    void parseRoadSystemWithNumber() {
        RoadSysRef roadSysRef = parseObject("vegobjekter/vegsystemreferanse_med_nummer.json", RoadSysRefParser::parse);
        assertThat(roadSysRef, notNullValue());
        assertThat(roadSysRef.shortForm, equalTo("KV17 S1D1 m0-58"));
    }

    @Test
    void parseRoadSystemWithoutNumber() {
        RoadSysRef roadSysRef = parseObject("vegobjekter/vegsystemreferanse_uten_nummer.json", RoadSysRefParser::parse);
        assertThat(roadSysRef, notNullValue());
        assertThat(roadSysRef.shortForm, equalTo("KVnull S1D1 m0-58"));
    }

    @Test
    void roadSystemWithoutNumberOperations() {
        RoadSysRef roadSysRefOk = parseObject("vegobjekter/vegsystemreferanse_med_nummer.json", RoadSysRefParser::parse);
        RoadSystem roadSystemOk = roadSysRefOk.getRoadSystem();
        RoadSysRef roadSysRefMissingNumber = parseObject("vegobjekter/vegsystemreferanse_uten_nummer.json", RoadSysRefParser::parse);
        RoadSystem roadSystemMissingNumber = roadSysRefMissingNumber.getRoadSystem();
        RoadSysRef roadSysRefMissingNumberCopy = parseObject("vegobjekter/vegsystemreferanse_uten_nummer.json", RoadSysRefParser::parse);
        RoadSystem roadSystemMissingNumberCopy = roadSysRefMissingNumberCopy.getRoadSystem();

        assertThat(roadSystemMissingNumber, equalTo(roadSystemMissingNumberCopy));
        assertThat(roadSystemMissingNumber, not(equalTo(roadSystemOk)));

        assertThat(roadSystemMissingNumber.getCategoryPhaseNumberAsString(), equalTo("KVnull"));

        assertThat(roadSystemMissingNumber.hashCode(), equalTo(roadSystemMissingNumberCopy.hashCode()));
        assertThat(roadSystemMissingNumber.hashCode(), not(equalTo(roadSystemOk.hashCode())));

        assertThat(roadSystemMissingNumber.toString(), equalTo("RoadSystem{id=1005921633, version=1, roadNumber=null, roadCategory='K', phase='V'}"));
    }
}
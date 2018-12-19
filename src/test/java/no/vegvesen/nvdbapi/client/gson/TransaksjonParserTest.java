package no.vegvesen.nvdbapi.client.gson;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObjekterList;

public class TransaksjonParserTest {
    @Test
    void parseTransaksjoner() throws IOException {
        parseObjekterList("transaksjoner.json", TransactionParser::parseTransaction);
    }
}

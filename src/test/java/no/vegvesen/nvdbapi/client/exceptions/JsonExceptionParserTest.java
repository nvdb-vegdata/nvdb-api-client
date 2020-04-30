package no.vegvesen.nvdbapi.client.exceptions;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonExceptionParserTest {

    @Test
    public void exceptionParserDoesNotSwallowException() {
        String text = "<html><body><h1>504 Gateway Time-out</h1>\n" +
                "The server didn't respond in time.\n" +
                "</body></html>\n";

        JsonSyntaxException exception = assertThrows(JsonSyntaxException.class, () -> JsonExceptionParser.parse(text));
        assertTrue(exception.getMessage().contains("MalformedJsonException"));
    }

}
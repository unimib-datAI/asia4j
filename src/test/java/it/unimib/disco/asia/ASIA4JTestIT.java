package it.unimib.disco.asia;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ASIA4JTestIT {

    private static final String asiaEndpoint = "http://titan-inside.disco.unimib.it:8088/";
    private static ASIA4J client;

    @BeforeClass
    public static void setUp() {
        client = ASIA4JFactory.getClient(asiaEndpoint);
    }




    @Test
    public void testWeather() {

        Assert.assertEquals("293.3830516582",
                client.extendWeather("2838632", "2018-07-25", "min", "2t", "1"));
    }


    @Test
    public void testClustering(){
        Assert.assertEquals("\"/Haus/Sicherheit zu Hause,/Haus/Sicherheit zu Hause/In-Home-Gefahrenmelder," +
                        "/Haus/Sicherheit zu Hause/In-Home-Gefahrenmelder/Kohlenmonoxid-Detektoren," +
                        "/Haus/Sicherheit zu Hause/Feuerlöscher,/Haus/Sicherheit zu Hause/In-Home-Gefahrenmelder/Rauchmelder," +
                        "/Gesundheit/Gesundheitsbedingungen & Bedenken/Fußgesundheit/Anti-Pilz-Heilmittel zu Fuß," +
                        "/Haus/Sicherheit zu Hause/Schließfächer & Hide-A-Keys\"",
                client.keywordClustering("1 zu 4 demultiplexer"));
    }



}

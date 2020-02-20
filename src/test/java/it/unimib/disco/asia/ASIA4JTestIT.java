package it.unimib.disco.asia;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ASIA4JTestIT {

    private static final String asiaEndpoint = "//put a real enpoint here";
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





}

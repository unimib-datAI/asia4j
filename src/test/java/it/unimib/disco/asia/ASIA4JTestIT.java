package it.unimib.disco.asia;

import it.unimib.disco.asia.model.request.CustomEventMatchCondition;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ASIA4JTestIT {

    private static final String asiaEndpoint = "//add here ASIA address";
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

    @Test
    public void testCustomEventMatch(){

       String tr = "{\n" +
                "    \"key\": [\n" +
                "      \"20190514\",\n" +
                "      \"9577242\"\n" +
                "    ],\n" +
                "    \"filters\": [\n" +
                "      {\n" +
                "        \"propertyID\": \"startDate\",\n" +
                "        \"operator\": \"==\",\n" +
                "        \"value\": \"20190514\",\n" +
                "        \"isColumn\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"propertyID\": \"product.identifier\",\n" +
                "        \"operator\": \"==\",\n" +
                "        \"value\": \"9577242\",\n" +
                "        \"isColumn\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"propertyID\": \"measure.priceChanged\",\n" +
                "        \"operator\": \"==\",\n" +
                "        \"value\": \"True\",\n" +
                "        \"isColumn\": false\n" +
                "      }\n" +
                "    ]\n" +
                "  },";


        List<CustomEventMatchCondition> conditions = new ArrayList<>();
        CustomEventMatchCondition c1 = new CustomEventMatchCondition();
        c1.setPropertyID("startDate");
        c1.setOperator("==");
        c1.setValue("20170514");
        c1.setColumn(true);
        conditions.add(c1);

        Assert.assertEquals("",
                client.customEventMatcher(conditions));

    }


    @Test
    public void testCustomEventSelect(){
        String ids = "CustomEvents/458739";
        String propIds = "product.sku";
        Assert.assertEquals("13541152",
                client.customEventSelect(ids,propIds));
    }



}

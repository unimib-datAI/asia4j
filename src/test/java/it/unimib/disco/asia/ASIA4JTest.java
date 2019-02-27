package it.unimib.disco.asia;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ASIA4JTest {

    private static final String asiaEndpoint = "http://localhost:9999/";
    private static ASIA4J client;

    @Rule
    public WireMockRule asiaService = new WireMockRule(9999);

    @BeforeClass
    public static void setUp() {
        client = ASIA4JFactory.build(asiaEndpoint);
    }

    @Test
    public void testReconciliation() {

        asiaService.stubFor(get(urlMatching("/reconcile?.*"))
                .withQueryParam("queries", equalToJson("{\"q0\": {\"query\": \"Berlin\"}}"))
                .withQueryParam("conciliator", equalTo("geonames"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("{\n" +
                                "    \"q0\": {\n" +
                                "        \"result\": [\n" +
                                "            {\n" +
                                "                \"id\": \"6547539\",\n" +
                                "                \"name\": \"Berlin\",\n" +
                                "                \"type\": [\n" +
                                "                    {\n" +
                                "                        \"id\": \"A.ADM4\",\n" +
                                "                        \"name\": \"A.ADM4\"\n" +
                                "                    }\n" +
                                "                ],\n" +
                                "                \"score\": 55.95427703857422,\n" +
                                "                \"match\": false\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"id\": \"10332205\",\n" +
                                "                \"name\": \"Berlin\",\n" +
                                "                \"type\": [\n" +
                                "                    {\n" +
                                "                        \"id\": \"P.PPL\",\n" +
                                "                        \"name\": \"P.PPL\"\n" +
                                "                    }\n" +
                                "                ],\n" +
                                "                \"score\": 55.2410774230957,\n" +
                                "                \"match\": false\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"id\": \"10332842\",\n" +
                                "                \"name\": \"Berlin\",\n" +
                                "                \"type\": [\n" +
                                "                    {\n" +
                                "                        \"id\": \"P.PPL\",\n" +
                                "                        \"name\": \"P.PPL\"\n" +
                                "                    }\n" +
                                "                ],\n" +
                                "                \"score\": 55.2410774230957,\n" +
                                "                \"match\": false\n" +
                                "            }\n" +
                                "        ]\n" +
                                "    }\n" +
                                "}")));

        asiaService.stubFor(get(urlMatching("/reconcile?.*"))
                .withQueryParam("queries", equalToJson("{\"q0\":{\"query\":\"Berlin\",  \"type\":\"A.ADM1\", \"type_strict\":\"should\"}}"))
                .withQueryParam("conciliator", equalTo("geonames"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("{\n" +
                                "    \"q0\": {\n" +
                                "        \"result\": [\n" +
                                "            {\n" +
                                "                \"id\": \"2950157\",\n" +
                                "                \"name\": \"Land Berlin\",\n" +
                                "                \"type\": [\n" +
                                "                    {\n" +
                                "                        \"id\": \"A.ADM1\",\n" +
                                "                        \"name\": \"A.ADM1\"\n" +
                                "                    }\n" +
                                "                ],\n" +
                                "                \"score\": 36.59111785888672,\n" +
                                "                \"match\": false\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"id\": \"9611689\",\n" +
                                "                \"name\": \"Mariehamns stad\",\n" +
                                "                \"type\": [\n" +
                                "                    {\n" +
                                "                        \"id\": \"A.ADM1\",\n" +
                                "                        \"name\": \"A.ADM1\"\n" +
                                "                    }\n" +
                                "                ],\n" +
                                "                \"score\": 0,\n" +
                                "                \"match\": false\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"id\": \"9611692\",\n" +
                                "                \"name\": \"Ålands landsbygd\",\n" +
                                "                \"type\": [\n" +
                                "                    {\n" +
                                "                        \"id\": \"A.ADM1\",\n" +
                                "                        \"name\": \"A.ADM1\"\n" +
                                "                    }\n" +
                                "                ],\n" +
                                "                \"score\": 0,\n" +
                                "                \"match\": false\n" +
                                "            }\n" +
                                "        ]\n" +
                                "    }\n" +
                                "}")));

        asiaService.stubFor(get(urlMatching("/reconcile?.*"))
                .withQueryParam("queries", equalTo("{\"q0\": {\"query\": \"null\"}}"))
                .willReturn(badRequest()));

        asiaService.stubFor(get(urlMatching("/reconcile?.*"))
                .withQueryParam("conciliator", equalTo("null"))
                .willReturn(badRequest()));

        Assert.assertEquals("", client.reconcile(null, null, 0., null));
        Assert.assertEquals("", client.reconcile(null, null, 0.1, "geonames"));
        Assert.assertEquals("", client.reconcile("Berlin", null, 0.1, null));
        Assert.assertEquals("6547539",
                client.reconcile("Berlin", null, 0.1, "geonames"));
        Assert.assertEquals("2950157",
                client.reconcile("Berlin", "A.ADM1", 0.1, "geonames"));
    }


    @Test
    public void testExtension() {

        asiaService.stubFor(get(urlMatching("/extend?.*"))
                .withQueryParam("extend",
                        equalToJson("{\"ids\":[\"6554818\"],\"properties\":[{\"id\":\"parentADM1\"}]}"))
                .withQueryParam("conciliator", equalTo("geonames"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("{\n" +
                                "    \"meta\": [\n" +
                                "        {\n" +
                                "            \"id\": \"parentADM1\",\n" +
                                "            \"name\": \"parentADM1\",\n" +
                                "            \"type\": {\n" +
                                "                \"id\": \"A.ADM1\",\n" +
                                "                \"name\": \"A.ADM1\"\n" +
                                "            }\n" +
                                "        }\n" +
                                "    ],\n" +
                                "    \"rows\": {\n" +
                                "        \"6554818\": {\n" +
                                "            \"parentADM1\": [\n" +
                                "                {\n" +
                                "                    \"id\": \"2847618\",\n" +
                                "                    \"name\": \"Rheinland-Pfalz\"\n" +
                                "                }\n" +
                                "            ]\n" +
                                "        }\n" +
                                "    }\n" +
                                "}\n")));


        asiaService.stubFor(get(urlMatching("/extend?.*"))
                .withQueryParam("extend",
                        equalToJson("{\"ids\":[\"null\"],\"properties\":[{\"id\":\"null\"}]}"))
                .willReturn(aResponse().withBody("{\n" +
                        "    \"meta\": [\n" +
                        "        null\n" +
                        "    ],\n" +
                        "    \"rows\": {\n" +
                        "        \"null\": {\n" +
                        "            \"null\": []\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")));

        asiaService.stubFor(get(urlMatching("/extend?.*"))
                .withQueryParam("extend",
                        equalToJson("{\"ids\":[\"null\"],\"properties\":[{\"id\":\"parentADM1\"}]}"))
                .willReturn(aResponse().withBody("{\n" +
                        "    \"meta\": [\n" +
                        "        {\n" +
                        "            \"id\": \"parentADM1\",\n" +
                        "            \"name\": \"parentADM1\",\n" +
                        "            \"type\": {\n" +
                        "                \"id\": \"A.ADM1\",\n" +
                        "                \"name\": \"A.ADM1\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"rows\": {\n" +
                        "        \"null\": {\n" +
                        "            \"parentADM1\": []\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")));

        asiaService.stubFor(get(urlMatching("/extend?.*"))
                .withQueryParam("extend",
                        equalToJson("{\"ids\":[\"6554818\"],\"properties\":[{\"id\":\"null\"}]}"))
                .willReturn(aResponse().withBody("{\n" +
                        "    \"meta\": [\n" +
                        "        null\n" +
                        "    ],\n" +
                        "    \"rows\": {\n" +
                        "        \"6554818\": {\n" +
                        "            \"null\": []\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")));

        asiaService.stubFor(get(urlMatching("/extend?.*"))
                .withQueryParam("conciliator", equalTo("null"))
                .willReturn(serverError()));

        Assert.assertEquals("", client.extend(null, null, null));
        Assert.assertEquals("", client.extend(null, null, "geonames"));
        Assert.assertEquals("", client.extend("6554818", null, null));
        Assert.assertEquals("", client.extend(null, "parentADM1", null));
        Assert.assertEquals("", client.extend("6554818", "parentADM1", null));
        Assert.assertEquals("2847618",
                client.extend("6554818", "parentADM1", "geonames"));
    }

    @Test
    public void testWeather() {
        asiaService.stubFor(get(urlMatching("/weather?.*"))
                .withQueryParam("ids", equalTo("null"))
                .willReturn(serverError()));

        asiaService.stubFor(get(urlMatching("/weather?.*"))
                .withQueryParam("dates", equalTo("null"))
                .willReturn(serverError()));

        asiaService.stubFor(get(urlMatching("/weather?.*"))
                .withQueryParam("weatherParams", equalTo("null"))
                .willReturn(serverError()));

        asiaService.stubFor(get(urlMatching("/weather?.*"))
                .withQueryParam("offsets", equalTo("null"))
                .willReturn(serverError()));

        asiaService.stubFor(get(urlMatching("/weather?.*"))
                .withQueryParam("ids", equalTo("2953481"))
                .withQueryParam("dates", equalTo("2018-07-25"))
                .withQueryParam("aggregators", equalTo("null"))
                .withQueryParam("weatherParams", equalTo("2t"))
                .withQueryParam("offsets", equalTo("1"))
                .willReturn(aResponse().withBody("[\n" +
                        "    {\n" +
                        "        \"geonamesId\": \"2953481\",\n" +
                        "        \"validTime\": \"2018-07-25T00:00:00Z\",\n" +
                        "        \"validityDateTime\": \"2018-07-26T00:00:00Z\",\n" +
                        "        \"weatherParameters\": [\n" +
                        "            {\n" +
                        "                \"id\": \"2t\",\n" +
                        "                \"value\": \"294.006742227\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"offset\": 1\n" +
                        "    }\n" +
                        "]")));

        Assert.assertEquals("",
                client.extendWeather(null, null, null, null, null));
        Assert.assertEquals("",
                client.extendWeather("2953481", null, null, "2t", "1"));
        Assert.assertEquals("",
                client.extendWeather("2953481", "2018-07-25", null, null, "1"));
        Assert.assertEquals("294.006742227",
                client.extendWeather("2953481", "2018-07-25", null, "2t", "1"));
    }

    @Test
    public void testGeoMatch() {
        asiaService.stubFor(get(urlMatching("/geoExactMatch?.*"))
                .withQueryParam("ids", equalTo("Milan"))
                .withQueryParam("source", equalTo("dbpedia"))
                .withQueryParam("target", equalTo("wikidata"))
                .willReturn(aResponse().withBody("{\n" +
                        "    \"meta\": {\n" +
                        "        \"id\": \"http://www.w3.org/2004/02/skos/core#exactMatch\",\n" +
                        "        \"name\": \"exactMatch\"\n" +
                        "    },\n" +
                        "    \"rows\": {\n" +
                        "        \"Milan\": {\n" +
                        "            \"exactMatch\": [\n" +
                        "                {\n" +
                        "                    \"str\": \"http://www.wikidata.org/entity/Q49295412\"\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")));
        asiaService.stubFor(get(urlMatching("/geoExactMatch?.*"))
                .withQueryParam("source", equalTo("null"))
                .willReturn(serverError()));
        asiaService.stubFor(get(urlMatching("/geoExactMatch?.*"))
                .withQueryParam("target", equalTo("null"))
                .willReturn(serverError()));
        asiaService.stubFor(get(urlMatching("/geoExactMatch?.*"))
                .withQueryParam("ids", equalTo("null"))
                .willReturn(serverError()));

        Assert.assertEquals("",
                client.geoExactMatch(null, null, null));
        Assert.assertEquals("",
                client.geoExactMatch("Milan", "dbpedia", null));
        Assert.assertEquals("",
                client.geoExactMatch("Milan", null, "wikidata"));
        Assert.assertEquals("",
                client.geoExactMatch(null, "dbpedia", "wikidata"));
        Assert.assertEquals("http://www.wikidata.org/entity/Q49295412",
                client.geoExactMatch("Milan", "dbpedia", "wikidata"));
    }

}

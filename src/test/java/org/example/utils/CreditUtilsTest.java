package org.example.utils;

import org.example.core.entities.Request;
import org.example.core.utils.CreditUtils;
import org.junit.Assert;
import org.junit.Test;

public class CreditUtilsTest {

    @Test
    public void readRequestJSONTest() {
        String json = "{\"id\": 49002010976,\n" +
                "    \"amount\": 5000,\n" +
                "    \"period\": 30}";
        Request actual = CreditUtils.readJson(json, Request.class);
        Assert.assertEquals(49002010976L, (long) actual.getPersonalCode());
        Assert.assertEquals(5000, (double) actual.getAmount(), 0);
        Assert.assertEquals(30, (long) actual.getPeriod());
    }

    @Test
    public void readRequestJSONWithoutFieldTest() {
        String json = "{\"id\": 49002010976,\n" +
                "    \"amount\": 5000}";
        Request actual = CreditUtils.readJson(json, Request.class);
        Assert.assertEquals(49002010976L, (long) actual.getPersonalCode());
        Assert.assertEquals(5000, (double) actual.getAmount(), 0);
        Assert.assertNull(actual.getPeriod());
    }

    @Test
    public void readRequestJSONExtraFieldTest() {
        String json = "{\"id\": 49002010976,\n" +
                "    \"amount\": 5000,\n" +
                "    \"period\": 30," +
                "   \"extraField\": \"value\"}";
        Request actual = CreditUtils.readJson(json, Request.class);
        Assert.assertEquals(49002010976L, (long) actual.getPersonalCode());
        Assert.assertEquals(5000, (double) actual.getAmount(), 0);
        Assert.assertEquals(30, (long) actual.getPeriod());
    }

    @Test
    public void readWrongJSONTest() {
        String json = "{id\": 49002010976,\n" +
                "    \"amount\": 5000,\n" +
                "    \"period\": 30," +
                "   \"extraField\": \"value\"}";
        Request actual = CreditUtils.readJson(json, Request.class);
        Assert.assertNull(actual);
    }
}

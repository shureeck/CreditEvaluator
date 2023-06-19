package org.example.entites;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.core.entities.Request;
import org.example.core.entities.Response;
import org.junit.Assert;
import org.junit.Test;

public class RequestTest {
    @Test
    public void emptyRequestTest() {
        Request request = new Request();
        Assert.assertNull(request.getAmount());
        Assert.assertNull(request.getPersonalCode());
        Assert.assertNull(request.getPeriod());
    }

    @Test
    public void setAmountTest() {
        Request request = new Request();
        request.setAmount(1000D);
        Assert.assertEquals(1000D, request.getAmount(), 0);
    }

    @Test
    public void setPeriodTest() {
        Request request = new Request();
        request.setPeriod(60L);
        Assert.assertEquals(60, (long) request.getPeriod());
    }

    @Test
    public void setPersonalCodeTest() {
        Request request = new Request();
        request.setPersonalCode(4444444444444L);
        Assert.assertEquals(4444444444444L, request.getPersonalCode(), 0);
    }
}

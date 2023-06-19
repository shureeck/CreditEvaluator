package org.example.entites;

import org.example.core.entities.Response;
import org.example.core.enums.Decision;
import org.junit.Assert;
import org.junit.Test;

public class ResponseTest {
    @Test
    public void emptyResponseTest() {
        Response response = new Response();
        Assert.assertEquals(0, response.getAmount(), 0);
        Assert.assertEquals(0, response.getPeriod(), 0);
        Assert.assertNull(response.getDecision());

    }

    @Test
    public void responseTest() {
        Response response = new Response(Decision.APPROVED, 1000, 12);
        Assert.assertEquals(1000, response.getAmount(), 0);
        Assert.assertEquals(Decision.APPROVED, response.getDecision());
        Assert.assertEquals(12, response.getPeriod());

    }

    @Test
    public void setAmountTest() {
        Response response = new Response(Decision.APPROVED, 1000, 12);
        response.setAmount(500);
        Assert.assertEquals(500, response.getAmount(), 0);
    }

    @Test
    public void setDecisionTest() {
        Response response = new Response(Decision.APPROVED, 1000, 12);
        response.setDecision(Decision.DECLINED);
        Assert.assertEquals(Decision.DECLINED, response.getDecision());

    }

    @Test
    public void setPeriodTest() {
        Response response = new Response(Decision.APPROVED, 1000, 12);
        response.setPeriod(60);
        Assert.assertEquals(60, response.getPeriod());

    }
}

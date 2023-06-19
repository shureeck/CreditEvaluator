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
        Assert.assertNull(response.getDecision());

    }

    @Test
    public void responseTest() {
        Response response = new Response(Decision.APPROVED, 1000);
        Assert.assertEquals(1000, response.getAmount(), 0);
        Assert.assertEquals(Decision.APPROVED, response.getDecision());

    }

    @Test
    public void setAmountTest() {
        Response response = new Response(Decision.APPROVED, 1000);
        response.setAmount(500);
        Assert.assertEquals(500, response.getAmount(), 0);
    }

    @Test
    public void setDecisionTest() {
        Response response = new Response(Decision.APPROVED, 1000);
        response.setDecision(Decision.DECLINED);
        Assert.assertEquals(Decision.DECLINED, response.getDecision());

    }
}

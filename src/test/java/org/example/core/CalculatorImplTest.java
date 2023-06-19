package org.example.core;

import org.example.core.dao.Dao;
import org.example.core.dao.SegmentsDao;
import org.example.core.entities.Response;
import org.example.core.enums.Decision;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CalculatorImplTest {
    private final static Dao segmentDao = Mockito.mock(SegmentsDao.class);

    @Test
    public void calculatorTestDeclinedAmountToLowTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 2000, 12, segmentDao);
        Mockito.doReturn(100L).when(segmentDao).getCreditModifier(300L);
        Response actual = calculator.calculate();
        Assert.assertEquals(Decision.DECLINED, actual.getDecision());
        Assert.assertEquals(0, actual.getAmount(), 0);

    }

    @Test
    public void calculatorTestDeclinedDebtTest() {

        CalculatorImpl calculator = new CalculatorImpl(300, 2000, 12, segmentDao);
        Mockito.doReturn(0L).when(segmentDao).getCreditModifier(300L);
        Response actual = calculator.calculate();
        Assert.assertEquals(Decision.DECLINED, actual.getDecision());
        Assert.assertEquals(0, actual.getAmount(), 0);

    }

    @Test
    public void calculatorTestApproved100Test() {
        CalculatorImpl calculator = new CalculatorImpl(300, 2000, 20, segmentDao);
        Mockito.doReturn(100L).when(segmentDao).getCreditModifier(300L);
        Response actual = calculator.calculate();
        Assert.assertEquals(Decision.APPROVED, actual.getDecision());
        Assert.assertEquals(2000, actual.getAmount(), 0);
    }

    @Test
    public void calculatorTestApproved300AmountMoreRequestedTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 2000, 20, segmentDao);
        Mockito.doReturn(300L).when(segmentDao).getCreditModifier(300L);
        Response actual = calculator.calculate();
        Assert.assertEquals(Decision.APPROVED, actual.getDecision());
        Assert.assertEquals(6000, actual.getAmount(), 0);
    }

    @Test
    public void calculatorTestApproved100AmountLessRequestedTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 4000, 20, segmentDao);
        Mockito.doReturn(100L).when(segmentDao).getCreditModifier(300L);
        Response actual = calculator.calculate();
        Assert.assertEquals(Decision.APPROVED, actual.getDecision());
        Assert.assertEquals(2000, actual.getAmount(), 0);
    }

    @Test
    public void calculatorTestApproved300AmountMoreThresholdTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 4000, 60, segmentDao);
        Mockito.doReturn(300L).when(segmentDao).getCreditModifier(300L);
        Response actual = calculator.calculate();
        Assert.assertEquals(Decision.APPROVED, actual.getDecision());
        Assert.assertEquals(10000, actual.getAmount(), 0);
    }

    @Test
    public void validateTestTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 4000, 60, segmentDao);
        boolean actual = calculator.validate();
        Assert.assertTrue(actual);
    }

    @Test
    public void validateTestAmountLessThresholdTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 1900, 60, segmentDao);
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> calculator.calculate());
        Assert.assertEquals(exception.getMessage(),"Amount should be more than 2000.0 and less than 10000.0");
    }

    @Test
    public void validateTestAmountMoreThresholdTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 11000, 60, segmentDao);
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> calculator.calculate());
        Assert.assertEquals(exception.getMessage(),"Amount should be more than 2000.0 and less than 10000.0");
    }

    @Test
    public void validateTestPeriodMoreThresholdTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 9000, 61, segmentDao);
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> calculator.calculate());
        Assert.assertEquals(exception.getMessage(),"Period should be more than 12.0 and less than 60.0");
    }
    @Test
    public void validateTestPeriodLessThresholdTest() {
        CalculatorImpl calculator = new CalculatorImpl(300, 9000, 11, segmentDao);
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> calculator.calculate());
        Assert.assertEquals(exception.getMessage(),"Period should be more than 12.0 and less than 60.0");
    }
}

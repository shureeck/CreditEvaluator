package org.example.utils;

import org.example.core.utils.LoggerMessages;
import org.junit.Assert;
import org.junit.Test;

public class LoggerMessagesTest {
    @Test
    public void getMessageTest() {
        String msg = LoggerMessages.getMessage("SegmentsDao.getCreditModifier.connect");
        Assert.assertEquals("Get connection to the database [%s]", msg);
    }

    @Test
    public void getMessageWithArgumentsTest() {
        String msg = LoggerMessages.getMessage("SegmentsDao.getCreditModifier.connect", "resources/evaluator.db");
        Assert.assertEquals("Get connection to the database [resources/evaluator.db]", msg);
    }

    @Test
    public void getNotExistentMessageWithArgumentsTest() {
        String msg = LoggerMessages.getMessage("not.exist", "resources/evaluator.db");
        Assert.assertEquals("Can't find appropriate message resource for property not.exist in the ResourceBundle", msg);
    }

    @Test
    public void getNotExistentMessageTest() {
        String msg = LoggerMessages.getMessage("not.exist");
        Assert.assertEquals("Can't find appropriate message resource for property not.exist in the ResourceBundle", msg);
    }
}

package org.example.dao;

import org.example.core.dao.Dao;
import org.example.core.dao.SegmentsDao;
import org.junit.Assert;
import org.junit.Test;

public class SegmentDaoTest {
    @Test
    public void getCreditModifier() {
        SegmentsDao dao = new SegmentsDao();
        long modifier = dao.getCreditModifier(49002010976L);
        Assert.assertEquals(100, modifier);
    }

    @Test
    public void creditModifierNotFound() {
        SegmentsDao dao = new SegmentsDao();
        Exception e = Assert.assertThrows(RuntimeException.class, () -> dao.getCreditModifier(11111));
        Assert.assertEquals("Customer with Personal code [11111] not found", e.getMessage());
    }
}

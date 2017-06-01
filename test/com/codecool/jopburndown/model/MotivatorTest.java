package com.codecool.jopburndown.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by petya on 2017.06.01..
 */
class MotivatorTest {

    @Test
    void testGetInstance_NotCreatesNewInstance() {
        Motivator motivator = Motivator.getInstance();
        Motivator secondMotivator = Motivator.getInstance();
        assertEquals(motivator, secondMotivator);
    }

    @Test
    void testGetMotivationMessage_AssertNotNull() {
        Motivator motivator = Motivator.getInstance();
        String firstElement = motivator.getMotivationMessage();
        assertNotNull(firstElement);
    }

    @Test
    void testSetMotivationMessage_FirstElementNotEqualNewFirstElement() {
        Motivator motivator = Motivator.getInstance();
        String firstElement = motivator.getMotivationMessage();
        motivator.setMotivationMessages("test message");
        String newFirstElement = motivator.getMotivationMessage();
        assertNotEquals(firstElement, newFirstElement);
    }
}
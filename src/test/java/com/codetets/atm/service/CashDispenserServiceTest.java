package com.codetets.atm.service;

import com.codetets.atm.exception.CashDispenserException;
import com.codetets.atm.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CashDispenserServiceTest {

    private CashDispenserService cashDispenserService;
    private CashDispenser cashDispenser;
    private Logger logger;

    @BeforeEach
    public void setup() {
        cashDispenser = Mockito.mock(CashDispenser.class);
        logger = Mockito.mock(Logger.class);
        cashDispenserService = new CashDispenserService();
        cashDispenserService.setCashDispenser(cashDispenser);
        cashDispenserService.setLogger(logger);
    }

    @Test
    public void testGetAvailableCash() {

        Map<NoteType, Integer> availableNotes = new HashMap<>();
        availableNotes.put(NoteType.TWENTY, 10);
        availableNotes.put(NoteType.FIFTY, 5);
        Mockito.when(cashDispenser.getAvailableNotes()).thenReturn(availableNotes);

        CashAvailabilityResponse response = cashDispenserService.getAvailableCash();

        Assertions.assertEquals(availableNotes, response.getAvailableNotes());
    }

    @Test
    public void testInitializeCash() {

        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(5);
        request.setFiftyNoteCount(3);

        cashDispenserService.initializeCash(request);

        Mockito.verify(cashDispenser, Mockito.times(1)).setInitialNoteCount(NoteType.TWENTY, 5);
        Mockito.verify(cashDispenser, Mockito.times(1)).setInitialNoteCount(NoteType.FIFTY, 3);
        Mockito.verify(logger, Mockito.times(1)).info("Initializing cash");
        Mockito.verify(logger, Mockito.times(1)).info("Cash initialized");
    }

    @Test
    public void testAddCash() {

        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(5);
        request.setFiftyNoteCount(3);

        cashDispenserService.addCash(request);

        Mockito.verify(cashDispenser, Mockito.times(1)).addNotes(NoteType.TWENTY, 5);
        Mockito.verify(cashDispenser, Mockito.times(1)).addNotes(NoteType.FIFTY, 3);
        Mockito.verify(logger, Mockito.times(1)).info("Adding cash");
        Mockito.verify(logger, Mockito.times(1)).info("Cash added");
    }

    @Test
    public void testRemoveCash() {

        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(2);
        request.setFiftyNoteCount(1);

        cashDispenserService.removeCash(request);

        Mockito.verify(cashDispenser, Mockito.times(1)).removeNotes(NoteType.TWENTY, 2);
        Mockito.verify(cashDispenser, Mockito.times(1)).removeNotes(NoteType.FIFTY, 1);
        Mockito.verify(logger, Mockito.times(1)).info("Removing cash");
        Mockito.verify(logger, Mockito.times(1)).info("Cash removed");
    }

    @Test
    public void testRemoveCash_ThrowsException() {
        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(2);
        request.setFiftyNoteCount(1);

        Mockito.doThrow(IllegalArgumentException.class).when(cashDispenser).removeNotes(Mockito.any(NoteType.class),
                Mockito.anyInt());

        Assertions.assertThrows(CashDispenserException.class, () -> cashDispenserService.removeCash(request));
    }

    @Test
    public void testDispenseCash() {

        double amount = 100.0;

        DispenseResponse response = cashDispenserService.dispenseCash(amount);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Cash dispensed successfully", response.getMessage());
        Mockito.verify(cashDispenser, Mockito.times(1)).validateAmount(amount);
        Mockito.verify(cashDispenser, Mockito.times(1)).reduceAvailableCash(amount);
        Mockito.verify(logger, Mockito.times(1)).info("Dispensing cash");
        Mockito.verify(logger, Mockito.times(1)).info("Cash dispensed");
    }

    @Test
    public void testDispenseCash_ThrowsException() {

        double amount = 100.0;

        Mockito.doThrow(CashDispenserException.class).when(cashDispenser).validateAmount(Mockito.anyDouble());

        DispenseResponse response = cashDispenserService.dispenseCash(amount);

        Assertions.assertFalse(response.isSuccess());
    }

}

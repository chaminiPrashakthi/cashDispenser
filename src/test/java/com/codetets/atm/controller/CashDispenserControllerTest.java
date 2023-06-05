package com.codetets.atm.controller;

import com.codetets.atm.exception.CashDispenserException;
import com.codetets.atm.model.CashAvailabilityResponse;
import com.codetets.atm.model.CashRequest;
import com.codetets.atm.model.DispenseResponse;
import com.codetets.atm.model.NoteType;
import com.codetets.atm.service.CashDispenserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

public class CashDispenserControllerTest {

    private CashDispenserController cashDispenserController;

    @Mock
    private CashDispenserService cashDispenserService;

    @BeforeEach
    void setUp() {
        cashDispenserService = Mockito.mock(CashDispenserService.class);
        cashDispenserController = new CashDispenserController(cashDispenserService);
    }

    @Test
    void testInitializeCashDispenser_Success() throws CashDispenserException {
        // Mock data
        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(10);
        request.setFiftyNoteCount(5);

        Mockito.doNothing().when(cashDispenserService).initializeCash(any(CashRequest.class));
        ResponseEntity<String> response = cashDispenserController.initializeCashDispenser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cash dispenser initialized successfully", response.getBody());
    }

    @Test
    void testInitializeCashDispenser_AlreadyInitialized() throws CashDispenserException {
        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(10);
        request.setFiftyNoteCount(5);

        cashDispenserController.setInitialized(true);

        ResponseEntity<String> response = cashDispenserController.initializeCashDispenser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cash dispenser has already been initialized.", response.getBody());
    }

    @Test
    void testInitializeCashDispenser_InvalidRequest() throws CashDispenserException {
        CashRequest request = new CashRequest();
        request.setTwentyNoteCount(0);
        request.setFiftyNoteCount(5);

        Mockito.doThrow(new IllegalArgumentException("Invalid request")).when(cashDispenserService).initializeCash(any(CashRequest.class));

        ResponseEntity<String> response = cashDispenserController.initializeCashDispenser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody());
    }

    @Test
    void testGetAvailableNotes() {
        // Mock data
        Map<NoteType, Integer> availableNotes = new HashMap<>();
        availableNotes.put(NoteType.TWENTY, 10);
        availableNotes.put(NoteType.FIFTY, 5);
        CashAvailabilityResponse expectedResponse = new CashAvailabilityResponse(availableNotes);
        when(cashDispenserService.getAvailableCash()).thenReturn(expectedResponse);

        // Call the method
        ResponseEntity<CashAvailabilityResponse> responseEntity = cashDispenserController.getAvailableNotes();

        // Assertion
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(cashDispenserService).getAvailableCash();
    }

    @Test
    void testAddNotes() {
        // Mock data
        CashRequest request = new CashRequest();

        // Call the method
        ResponseEntity<String> responseEntity = cashDispenserController.addNotes(request);

        // Assertion
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Notes added successfully", responseEntity.getBody());
        verify(cashDispenserService).addCash(request);
    }

    @Test
    void testRemoveNotes_Success() {
        // Mock data
        CashRequest request = new CashRequest();

        // Call the method
        ResponseEntity<String> responseEntity = cashDispenserController.removeNotes(request);

        // Assertion
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Notes removed successfully", responseEntity.getBody());
        verify(cashDispenserService).removeCash(request);
    }

    @Test
    void testRemoveNotes_Failure() {
        // Mock data
        CashRequest request = new CashRequest();
        String errorMessage = "Invalid request";

        // Mock behavior
        doThrow(new IllegalArgumentException(errorMessage)).when(cashDispenserService).removeCash(request);

        // Call the method
        ResponseEntity<String> responseEntity = cashDispenserController.removeNotes(request);

        // Assertion
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
        verify(cashDispenserService).removeCash(request);
    }

    @Test
    void testDispenseCash() {
        // Mock data
        double amount = 100.0;
        DispenseResponse expectedResponse = new DispenseResponse(true, "Cash dispensed successfully");
        when(cashDispenserService.dispenseCash(amount)).thenReturn(expectedResponse);

        // Call the method
        ResponseEntity<DispenseResponse> responseEntity = cashDispenserController.dispenseCash(amount);

        // Assertion
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(expectedResponse, responseEntity.getBody());
        verify(cashDispenserService).dispenseCash(amount);
    }
}

package com.codetets.atm.service;

import com.codetets.atm.exception.CashDispenserException;
import com.codetets.atm.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CashDispenserService {

    private final Logger logger = LoggerFactory.getLogger(CashDispenserService.class);

    private final CashDispenser cashDispenser;

    public CashDispenserService() {
        this.cashDispenser = new CashDispenser();
    }

    public CashAvailabilityResponse getAvailableCash() {
        logger.info("Retrieving available cash");
        Map<NoteType, Integer> availableNotes = cashDispenser.getAvailableNotes();
        return new CashAvailabilityResponse(availableNotes);
    }

    public void initializeCash(CashRequest request) {
        logger.info("Initializing cash");
        cashDispenser.setInitialNoteCount(NoteType.TWENTY, request.getTwentyNoteCount());
        cashDispenser.setInitialNoteCount(NoteType.FIFTY, request.getFiftyNoteCount());
        logger.info("Cash initialized");
    }

    public void addCash(CashRequest request) {
        logger.info("Adding cash");
        cashDispenser.addNotes(NoteType.TWENTY, request.getTwentyNoteCount());
        cashDispenser.addNotes(NoteType.FIFTY, request.getFiftyNoteCount());
        logger.info("Cash added");
    }

    public void removeCash(CashRequest request) {
        logger.info("Removing cash");
        try {
            Map<NoteType, Integer> notesToRemove = new HashMap<>();
            notesToRemove.put(NoteType.TWENTY, request.getTwentyNoteCount());
            notesToRemove.put(NoteType.FIFTY, request.getFiftyNoteCount());
            
            for (Map.Entry<NoteType, Integer> entry : notesToRemove.entrySet()) {
                NoteType noteType = entry.getKey();
                int count = entry.getValue();
                cashDispenser.removeNotes(noteType, count);
            }
            
            logger.info("Cash removed");
        } catch (IllegalArgumentException e) {
            logger.error("Failed to remove cash: {}", e.getMessage());
            throw new CashDispenserException("Failed to remove cash", e);
        }
    }
    
    public DispenseResponse dispenseCash(double amount) {
        logger.info("Dispensing cash");
        try {
            cashDispenser.validateAmount(amount);
            cashDispenser.reduceAvailableCash(amount);
            logger.info("Cash dispensed");
            return new DispenseResponse(true, "Cash dispensed successfully");
        } catch (CashDispenserException e) {
            logger.error("Failed to dispense cash: {}", e.getMessage());
            return new DispenseResponse(false, e.getMessage());
        }
    }
}

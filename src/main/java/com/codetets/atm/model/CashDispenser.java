package com.codetets.atm.model;

import com.codetets.atm.exception.CashDispenserException;

import java.util.HashMap;
import java.util.Map;

public class CashDispenser {

    private final Map<NoteType, Integer> availableNotes;

    public CashDispenser() {
        this.availableNotes = new HashMap<>();
    }

    public void setInitialNoteCount(NoteType noteType, int count) {
        availableNotes.put(noteType, count);
    }

    public Map<NoteType, Integer> getAvailableNotes() {
        return new HashMap<>(availableNotes);
    }

    public void addNotes(NoteType noteType, int count) {
        int currentCount = availableNotes.getOrDefault(noteType, 0);
        availableNotes.put(noteType, currentCount + count);
    }

    public void removeNotes(NoteType noteType, int count) {
        int currentCount = availableNotes.getOrDefault(noteType, 0);
        if (currentCount < count) {
            throw new IllegalArgumentException("Insufficient notes available");
        }
        availableNotes.put(noteType, currentCount - count);
    }

    public void validateAmount(double amount) {
        if (amount < 0) {
            throw new CashDispenserException("Invalid amount");
        }
        if (amount % NoteType.FIFTY.getValue() % NoteType.TWENTY.getValue() != 0) {
            throw new CashDispenserException("Invalid amount - cannot dispense exact change");
        }
        int fiftyCount = availableNotes.getOrDefault(NoteType.FIFTY, 0);
        int twentyCount = availableNotes.getOrDefault(NoteType.TWENTY, 0);
        int amountInFiftyNotes = (int) (amount / NoteType.FIFTY.getValue());
        int amountInTwentyNotes = (int) ((amount % NoteType.FIFTY.getValue()) / NoteType.TWENTY.getValue());
        if (fiftyCount < amountInFiftyNotes || twentyCount < amountInTwentyNotes) {
            throw new CashDispenserException("Insufficient cash available");
        }
    }

    public void reduceAvailableCash(double amount) {
        int fiftyCount = availableNotes.getOrDefault(NoteType.FIFTY, 0);
        int twentyCount = availableNotes.getOrDefault(NoteType.TWENTY, 0);
    
        int totalAmount = (int) (fiftyCount * NoteType.FIFTY.getValue() + twentyCount * NoteType.TWENTY.getValue());
    
        if (amount > totalAmount) {
            throw new CashDispenserException("Insufficient cash available");
        }
    
        int amountInFiftyNotes = Math.min(fiftyCount, (int) (amount / NoteType.FIFTY.getValue()));
        int remainingAmount = (int) (amount - amountInFiftyNotes * NoteType.FIFTY.getValue());
        int amountInTwentyNotes = Math.min(twentyCount, (int) (remainingAmount / NoteType.TWENTY.getValue()));
    
        availableNotes.put(NoteType.FIFTY, fiftyCount - amountInFiftyNotes);
        availableNotes.put(NoteType.TWENTY, twentyCount - amountInTwentyNotes);
    }
    
}

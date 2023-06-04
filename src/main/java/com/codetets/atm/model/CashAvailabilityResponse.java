package com.codetets.atm.model;

import java.util.Map;

public class CashAvailabilityResponse {
    private Map<NoteType, Integer> availableNotes;

    public CashAvailabilityResponse(Map<NoteType, Integer> availableNotes) {
        this.availableNotes = availableNotes;
    }

    public Map<NoteType, Integer> getAvailableNotes() {
        return availableNotes;
    }

    public void setAvailableNotes(Map<NoteType, Integer> availableNotes) {
        this.availableNotes = availableNotes;
    }
}

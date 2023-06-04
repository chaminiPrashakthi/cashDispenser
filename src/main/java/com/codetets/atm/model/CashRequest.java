package com.codetets.atm.model;


import javax.validation.constraints.Min;

public class CashRequest {
    @Min(value = 0, message = "Twenty note count must be non-negative")
    private int twentyNoteCount;

    @Min(value = 0, message = "Fifty note count must be non-negative")
    private int fiftyNoteCount;

    public int getTwentyNoteCount() {
        return twentyNoteCount;
    }

    public void setTwentyNoteCount(int twentyNoteCount) {
        this.twentyNoteCount = twentyNoteCount;
    }

    public int getFiftyNoteCount() {
        return fiftyNoteCount;
    }

    public void setFiftyNoteCount(int fiftyNoteCount) {
        this.fiftyNoteCount = fiftyNoteCount;
    }

    
}

package com.codetets.atm.controller;

import com.codetets.atm.exception.CashDispenserException;
import com.codetets.atm.model.*;
import com.codetets.atm .service.CashDispenserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/cashdispenser")
public class CashDispenserController {

    private final CashDispenserService cashDispenserService;
    private boolean initialized;

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public CashDispenserController(CashDispenserService cashDispenserService) {
        this.cashDispenserService = cashDispenserService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeCashDispenser(@RequestBody @Valid CashRequest request) throws CashDispenserException{
        try {
            String message;
            if (initialized) {
                message ="Cash dispenser has already been initialized.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            cashDispenserService.initializeCash(request);
            initialized = true;
            message = "Cash dispenser initialized successfully";
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<CashAvailabilityResponse> getAvailableNotes() {
        CashAvailabilityResponse response = cashDispenserService.getAvailableCash();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNotes(@RequestBody @Valid CashRequest request) {
        cashDispenserService.addCash(request);
        return ResponseEntity.ok("Notes added successfully");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeNotes(@RequestBody @Valid CashRequest request) {
        try {
            cashDispenserService.removeCash(request);
            return ResponseEntity.ok("Notes removed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/dispense")
    public ResponseEntity<DispenseResponse> dispenseCash(@RequestParam @Positive @Min(1) double amount) {
        DispenseResponse response = cashDispenserService.dispenseCash(amount);
        return ResponseEntity.ok(response);
    }
}

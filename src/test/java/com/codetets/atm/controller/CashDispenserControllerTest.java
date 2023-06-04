package com.codetets.atm.controller;

import com.codetets.atm.service.CashDispenserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class CashDispenserControllerTest {

    private CashDispenserService cashDispenserService;
    @BeforeEach
    public void setup() {
        cashDispenserService = Mockito.mock(CashDispenserService.class);
        new CashDispenserController(cashDispenserService);
    }
}

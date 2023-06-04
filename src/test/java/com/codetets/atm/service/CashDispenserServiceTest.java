package com.codetets.atm.service;

import com.codetets.atm.model.CashDispenser;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CashDispenserServiceTest {

    @Mock
    private CashDispenser cashDispenser;

    @InjectMocks
    private CashDispenserService cashDispenserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}

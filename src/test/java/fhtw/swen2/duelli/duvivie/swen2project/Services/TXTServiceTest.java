package fhtw.swen2.duelli.duvivie.swen2project.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class TXTServiceTest {

    @BeforeEach
    void setUp() {
        TXTService txtService = Mockito.spy(TXTService.class);
    }
}
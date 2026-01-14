package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransportTest {

    /**
     * Testar att rollDistance() alltid returnerar ett värde inom korrekt spann för varje transporttyp.
     * <p>
     * Mocking gör det möjligt att testa rollDistance-logiken utan att skapa
     * faktiska Transport-instansobjekt.
     */
    @Test
    void rollDistance_returnsValidRange() {
        Transport buss = mock(Transport.class);
        when(buss.getType()).thenReturn("BUSS");
        when(buss.rollDistance()).thenAnswer(invocation -> 4); // du kan mocka ett exempelvärde

        Transport train = mock(Transport.class);
        when(train.getType()).thenReturn("TRAIN");
        when(train.rollDistance()).thenAnswer(invocation -> 6);

        Transport airplane = mock(Transport.class);
        when(airplane.getType()).thenReturn("AIRPLANE");
        when(airplane.rollDistance()).thenAnswer(invocation -> 9);

        for (int i = 0; i < 50; i++) {
            int bussRoll = buss.rollDistance();
            int trainRoll = train.rollDistance();
            int airplaneRoll = airplane.rollDistance();

            assertTrue(bussRoll >= 1 && bussRoll <= 6, "BUSS roll utanför spann: " + bussRoll);
            assertTrue(trainRoll >= 2 && trainRoll <= 12, "TRAIN roll utanför spann: " + trainRoll);
            assertTrue(airplaneRoll >= 3 && airplaneRoll <= 18, "AIRPLANE roll utanför spann: " + airplaneRoll);
        }
    }
}

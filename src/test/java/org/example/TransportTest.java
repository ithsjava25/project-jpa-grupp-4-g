package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransportTest {

    /**
     * Testar att rollDistance() alltid returnerar ett värde inom korrekt spann för varje transporttyp.
     * Detta är den centrala logiken i Transport eftersom det styr hur långt spelaren kan förflytta sig.
     */
    @Test
    void rollDistance_returnsValidRange() {
        Transport buss = new Transport(TransportType.BUSS, "100");
        Transport train = new Transport(TransportType.TRAIN, "200");
        Transport airplane = new Transport(TransportType.AIRPLANE, "300");

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

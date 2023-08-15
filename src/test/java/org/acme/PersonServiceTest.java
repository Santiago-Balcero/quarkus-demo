package org.acme;

import org.acme.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class PersonServiceTest {

    @Inject
    PersonService personService;

    @Test
    public void testCashWithdrawalMethod() {
        Assertions.assertEquals(Long.valueOf(250000), personService.cashWithdrawal(Long.valueOf(1), Long.valueOf(250000)));
    }

}
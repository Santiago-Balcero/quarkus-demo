package org.acme.services;

import org.acme.models.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonService {

    public Long cashWithdrawal(Long id, Long money) {
        Person person = Person.findById(id);
        person.accountBalance -= money;
        return person.accountBalance;
    }
    
}

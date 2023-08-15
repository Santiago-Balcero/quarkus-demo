package org.acme.resources;

import java.util.List;

import org.acme.models.Person;
import org.acme.services.PersonService;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonService personService;
    
    @GET
    public List<Person> getAll() throws Exception {
        return Person.findAll(Sort.ascending("lastName")).list();
    }

    @POST
    @Transactional
    public Response create(Person p) {
        if (p == null || p.id != null)
            throw new WebApplicationException("id != null");
        p.persist();
        return Response.ok(p).status(200).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Person update(@PathParam("id") Long id, Person p) {
        Person entity = Person.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        if(p.accountBalance != null ) entity.accountBalance = p.accountBalance;
        if(p.firstName != null ) entity.firstName = p.firstName;
        if(p.lastName != null) entity.lastName = p.lastName;
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Person entity = Person.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }

    @PUT
    @Path("cash/{id}/{money}")
    @Transactional
    public Response cashWithdrawal(@PathParam("id") Long id, @PathParam("money") Long money) {
        if (id == null || money == null || money < 1) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        Long newBalance = personService.cashWithdrawal(id, money);
        String result = String.format(
            "Success! Person with id %d withdrew %d from the bank. New account balance is $%d",
            id,
            money,
            newBalance
        );
        return Response.ok(result).status(200).build();
    }
}


package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.PersonDTO;
import com.mongodb.starter.usecases.interfaces.PersonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@Tag(name = "persons", description = "An example for Swagger documentation")
@SecurityScheme(
    name = "api_key",
    type = SecuritySchemeType.APIKEY,
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY",
    description = "API key for authentication. Add 'X-API-KEY' header with your API key."
)
public class PersonController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Create a new person",
               description = "Creates a single person with the provided information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO postPerson(
            @Parameter(description = "Person information for creation")
            @RequestBody PersonDTO PersonDTO) {
        return personService.save(PersonDTO);
    }

    @Operation(summary = "Create multiple persons",
               description = "Creates multiple persons in a single batch operation",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Persons successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("persons")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PersonDTO> postPersons(
            @Parameter(description = "List of person information for batch creation")
            @RequestBody List<PersonDTO> personEntities) {
        return personService.saveAll(personEntities);
    }

    @Operation(summary = "Get all persons",
               description = "Retrieves a list of all persons in the system",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all persons")
    @GetMapping("persons")
    public List<PersonDTO> getPersons() {
        return personService.findAll();
    }

    @Operation(summary = "Get person by ID",
               description = "Retrieves a specific person using their ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the person"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("person/{id}")
    public ResponseEntity<PersonDTO> getPerson(
            @Parameter(description = "ID of the person to retrieve")
            @PathVariable String id) {
        PersonDTO PersonDTO = personService.findOne(id);
        if (PersonDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(PersonDTO);
    }

    @Operation(summary = "Get multiple persons by IDs",
               description = "Retrieves multiple persons using a comma-separated list of IDs",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the persons"),
        @ApiResponse(responseCode = "400", description = "Invalid ID format")
    })
    @GetMapping("persons/{ids}")
    public List<PersonDTO> getPersons(
            @Parameter(description = "Comma-separated list of person IDs", 
                      example = "id1,id2,id3")
            @PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return personService.findAll(listIds);
    }

    @Operation(summary = "Get total count of persons",
               description = "Returns the total number of persons in the system",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the count")
    @GetMapping("persons/count")
    public Long getCount() {
        return personService.count();
    }

    @Operation(summary = "Delete a person",
               description = "Deletes a single person by their ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the person"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @DeleteMapping("person/{id}")
    public Long deletePerson(
            @Parameter(description = "ID of the person to delete")
            @PathVariable String id) {
        return personService.delete(id);
    }

    @Operation(summary = "Delete multiple persons",
               description = "Deletes multiple persons using a comma-separated list of IDs",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the persons"),
        @ApiResponse(responseCode = "400", description = "Invalid ID format")
    })
    @DeleteMapping("persons/{ids}")
    public Long deletePersons(
            @Parameter(description = "Comma-separated list of person IDs to delete",
                      example = "id1,id2,id3")
            @PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return personService.delete(listIds);
    }

    @Operation(summary = "Delete all persons",
               description = "Deletes all persons from the system",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully deleted all persons")
    @DeleteMapping("persons")
    public Long deletePersons() {
        return personService.deleteAll();
    }

    @Operation(summary = "Update a person",
               description = "Updates a single person's information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @PutMapping("person")
    public PersonDTO putPerson(
            @Parameter(description = "Updated person information")
            @RequestBody PersonDTO PersonDTO) {
        return personService.update(PersonDTO);
    }

    @Operation(summary = "Update multiple persons",
               description = "Updates multiple persons in a single batch operation",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the persons"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("persons")
    public Long putPerson(
            @Parameter(description = "List of updated person information")
            @RequestBody List<PersonDTO> personEntities) {
        return personService.update(personEntities);
    }

    @Operation(summary = "Get average age",
               description = "Calculates and returns the average age of all persons",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully calculated average age")
    @GetMapping("persons/averageAge")
    public Double averageAge() {
        return personService.getAverageAge();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
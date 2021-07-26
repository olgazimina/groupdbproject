package cst8276.groupassignmentdb.controller;

import cst8276.groupassignmentdb.dao.PersonDao;
import cst8276.groupassignmentdb.dto.Person;
import cst8276.groupassignmentdb.dto.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    @Autowired
    PersonDao personDao;

    @GetMapping("/api/v1/persons")
    public ResponseEntity<Map> getAllPersons() {
        return prepareResponse(personDao.getAllPersons());
    }

    @GetMapping("/api/v1/persons/person/{id}")
    public ResponseEntity<Map> getPersonById(@PathVariable Long id) {
        return prepareResponse(personDao.getPersonById(id));
    }

    @GetMapping("/api/v1/persons/course/{id}")
    public ResponseEntity<Map> getAllPersonsByCourseId(@PathVariable String id) {
        return prepareResponse(personDao.getAllPersonsByCourseId(id));
    }
    @GetMapping("/api/v1/persons/program/{id}")
    public ResponseEntity<Map> getAllPersonsByProgramId(@PathVariable String id) {
        return prepareResponse(personDao.getAllPersonsByProgramId(id));
    }

    @PostMapping("/api/v1/persons/person")
    public ResponseEntity<Map> createPerson(@RequestBody Persons person) {
        return prepareResponse(personDao.createPerson(person));
    }

    @PostMapping("/api/v1/persons/persons")
    public ResponseEntity<Map> createAllPersons(@RequestBody List<Persons> persons) {
        return prepareResponse(personDao.createManyPersons(persons));
    }

    @PutMapping("/api/v1/persons/person")
    public ResponseEntity<Map> updatePerson(@RequestBody Persons person) {
        return prepareResponse(personDao.updatePerson(person));
    }

    @DeleteMapping("/api/v1/persons/person/{id}")
    public ResponseEntity<Map> deletePerson(@PathVariable Long id) {
        return prepareResponse(personDao.deletePersonById(id));
    }

    @DeleteMapping("/api/v1/persons/course/{id}")
    public ResponseEntity<Map> deleteAllPersonsByCourseId(@PathVariable String id) {
        return prepareResponse(personDao.deleteAllPersonsByCourseId(id));
    }

    @DeleteMapping("/api/v1/persons/program/{id}")
    public ResponseEntity<Map> deleteAllPersonsByProgramId(@PathVariable String id) {
        return prepareResponse(personDao.deleteAllPersonsByProgramId(id));
    }

    private ResponseEntity<Map> prepareResponse(Map response) {
        if ((Integer) response.get("status") == 200) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .body(response);
        } else {
            return ResponseEntity
                    .badRequest()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .body(response);
        }
    }
}


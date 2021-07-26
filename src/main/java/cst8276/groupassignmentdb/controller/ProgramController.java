package cst8276.groupassignmentdb.controller;

import cst8276.groupassignmentdb.dao.ProgramDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProgramController {
    @Autowired
    ProgramDao programDao;

    @GetMapping("/api/v1/programs")
    public ResponseEntity<Map> getAllPersons() {
        return prepareResponse(programDao.getAllPrograms());
    }

    @GetMapping("/api/v1/programs/program/{id}")
    public ResponseEntity<Map> getPersonById(@PathVariable String id) {
        return prepareResponse(programDao.getProgramsById(id));
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

package cst8276.groupassignmentdb.controller;

import cst8276.groupassignmentdb.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    CourseDao courseDao;

    @GetMapping("/api/v1/courses")
    public ResponseEntity<Map> getAllCourses() {
        return prepareResponse(courseDao.getAllCourses());
    }

    @GetMapping("/api/v1/courses/course/{id}")
    public ResponseEntity<Map> getCoursesById(@PathVariable String id) {
        return prepareResponse(courseDao.getCoursesById(id));
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

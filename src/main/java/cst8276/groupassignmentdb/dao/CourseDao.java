package cst8276.groupassignmentdb.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cst8276.groupassignmentdb.dto.Course;
import cst8276.groupassignmentdb.dto.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map getAllCourses() {
        Map data = new HashMap();
        String query = "select courseId, courses, instructor, credits from PERSONS, json_table(persons.person, '$.courseList[*]' columns(courseId path '$.courseId', courses path '$.name', instructor path '$.instructor', credits path '$.credits')) group by courseId, courses, instructor, credits";

        List<Course> persons = jdbcTemplate.query(query, (rs, rowNum) ->
                new Course(rs.getString("courseId"),
                        rs.getString("courses"),
                        rs.getString("instructor"),
                        rs.getString("credits")));

        data.put("message", "All persons were retrieved");
        data.put("status", 200);
        data.put("data", persons);

        return data;
    }

    public Map getCoursesById(String courseId) {
        Map data = new HashMap();
        String query = "select courseId, courses, instructor, credits from PERSONS, json_table(persons.person, '$.courseList[*]' columns(courseId path '$.courseId', courses path '$.name', instructor path '$.instructor', credits path '$.credits')) where courseId='"+courseId+"' group by courseId, courses, instructor, credits";

        List<Course> persons = jdbcTemplate.query(query, (rs, rowNum) ->
                new Course(rs.getString("courseId"),
                        rs.getString("courses"),
                        rs.getString("instructor"),
                        rs.getString("credits")));

        data.put("message", "All persons were retrieved");
        data.put("status", 200);
        data.put("data", persons);

        return data;
    }
}

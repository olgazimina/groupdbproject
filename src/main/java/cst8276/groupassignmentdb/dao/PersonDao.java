package cst8276.groupassignmentdb.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cst8276.groupassignmentdb.dto.Person;
import cst8276.groupassignmentdb.dto.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PersonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map getAllPersons() {
        Map data = new HashMap();
        String query = "select * from system.persons";
        List<String> persons = jdbcTemplate.query(query, (rs, rowNum) -> new String(
                rs.getBlob("person").getBytes(1, (int) rs.getBlob("person").length())));
        List<Persons> retList = new ArrayList<>();

        for (String person : persons) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                retList.add(mapper.readValue(person, Persons.class));
            } catch (JsonProcessingException e) {
                data.put("message", e.toString());
                data.put("status", 500);
                data.put("data", "{}");
                return data;
            }
        }

        data.put("message", "All persons were retrieved");
        data.put("status", 200);
        data.put("data", retList);

        return data;
    }

    public Map createPerson(Persons persons) {
        long personId = persons.getPerson().getPersonId();
        ObjectMapper mapper = new ObjectMapper();

        Map data = new HashMap();
        data.put("message", "Person with ID = " + personId + " was created");
        data.put("status", 200);
        data.put("data", "{}");

        String personAsJson = "{}";
        try {
            personAsJson = mapper.writeValueAsString(persons);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String createQuery = "insert into system.persons values (" + personId + ", utl_raw.cast_to_raw('" + personAsJson + "'))";
        try {
            jdbcTemplate.update(createQuery);
        } catch (Exception e) {
            data.put("message", e.toString());
            data.put("status", 500);
            data.put("data", "{}");
        }
        return data;
    }

    public Map createManyPersons(List<Persons> persons) {
        Map data = new HashMap();
        data.put("message", "All Persons were created");
        data.put("status", 200);
        data.put("data", "{}");

        for (Persons person : persons) {
            long personId = person.getPerson().getPersonId();
            ObjectMapper mapper = new ObjectMapper();

            String personAsJson = "{}";
            try {
                personAsJson = mapper.writeValueAsString(person);
            } catch (JsonProcessingException e) {
                data.put("message", e.toString());
                data.put("status", 400);
                data.put("data", "{}");
                return data;
            }
            String createQuery = "insert into system.persons values (" + personId + ", utl_raw.cast_to_raw('" + personAsJson + "'))";
            try {
                jdbcTemplate.update(createQuery);
            } catch (Exception e) {
                data.put("message", e.toString());
                data.put("status", 500);
                data.put("data", "{}");
            }
        }
        return data;
    }

    public Map getPersonById(Long personId) {
        Map data = new HashMap();
        String query = "select person from PERSONS, json_table(persons.person, '$.person' columns(value path '$.personId')) where value = '" + personId + "'";
        List<String> persons = jdbcTemplate.query(query, (rs, rowNum) -> new String(
                rs.getBlob("person").getBytes(1, (int) rs.getBlob("person").length())));
        List<Persons> retList = new ArrayList<>();

        for (String person : persons) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                retList.add(mapper.readValue(person, Persons.class));
            } catch (JsonProcessingException e) {
                data.put("message", e.toString());
                data.put("status", 500);
                data.put("data", "{}");
            }
        }

        data.put("message", "A Person with ID " + personId + " were retrieved");
        data.put("status", 200);
        data.put("data", retList);

        return data;
    }

    public Map getAllPersonsByCourseId(String courseId) {
        Map data = new HashMap();
        String query = "select person from PERSONS, json_table(persons.person, '$.courseList[*]' columns(value path '$.courseId')) where value = '" + courseId + "'";
        List<String> persons = jdbcTemplate.query(query, (rs, rowNum) -> new String(
                rs.getBlob("person").getBytes(1, (int) rs.getBlob("person").length())));
        List<Persons> retList = new ArrayList<>();

        for (String person : persons) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                retList.add(mapper.readValue(person, Persons.class));
            } catch (JsonProcessingException e) {
                data.put("message", e.toString());
                data.put("status", 500);
                data.put("data", "{}");
            }
        }

        data.put("message", "All persons enrolled to the course with ID = "+courseId+" were retrieved");
        data.put("status", 200);
        data.put("data", retList);

        return data;
    }

    public Map getAllPersonsByProgramId(String programId) {
        Map data = new HashMap();
        String query = "select person from PERSONS, json_table(persons.person, '$.program' columns(value path '$.programId')) where value = '" + programId + "'";
        List<String> persons = jdbcTemplate.query(query, (rs, rowNum) -> new String(
                rs.getBlob("person").getBytes(1, (int) rs.getBlob("person").length())));
        List<Persons> retList = new ArrayList<>();

        for (String person : persons) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                retList.add(mapper.readValue(person, Persons.class));
            } catch (JsonProcessingException e) {
                data.put("message", e.toString());
                data.put("status", 500);
                data.put("data", "{}");
            }
        }

        data.put("message", "All persons enrolled to the program with ID = "+programId+" were retrieved");
        data.put("status", 200);
        data.put("data", retList);

        return data;
    }

    public Map deletePersonById(Long personId) {
        Map data = new HashMap();
        data.put("message", "Person with ID = " + personId + " was deleted");
        data.put("status", 200);
        data.put("data", "{}");

        String query = "delete from PERSONS where person_id = " + personId;

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            data.put("message", e.toString());
            data.put("status", 500);
            data.put("data", "{}");
        }
        return data;
    }

    public Map deleteAllPersonsByProgramId(String programId) {
        Map data = new HashMap();
        data.put("message", "Persons enrolled to program with ID = " + programId + " were deleted");
        data.put("status", 200);
        data.put("data", "{}");

        String query = "delete from PERSONS where person_id in (select person_id from persons, json_table(persons.person, '$.program' columns(value path '$.programId')) where value = '" + programId + "')";

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            data.put("message", e.toString());
            data.put("status", 500);
            data.put("data", "{}");
        }
        return data;
    }

    public Map deleteAllPersonsByCourseId(String courseId) {
        Map data = new HashMap();
        data.put("message", "Persons enrolled to course with ID = " + courseId + " were deleted");
        data.put("status", 200);
        data.put("data", "{}");

        String query = "delete from PERSONS where person_id in (select person_id from persons, json_table(persons.person, '$.courseList[*]' columns(value path '$.courseId')) where value = '" + courseId + "')";

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            data.put("message", e.toString());
            data.put("status", 500);
            data.put("data", "{}");
        }
        return data;
    }

    public Map updatePerson(Persons person) {
        Long personId = person.getPerson().getPersonId();
        Map data = new HashMap();
        data.put("message", "Persons with ID = " + personId + " were updated");
        data.put("status", 200);

        ObjectMapper mapper = new ObjectMapper();

        String personAsJson = "{}";
        try {
            personAsJson = mapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            data.put("message", e.toString());
            data.put("status", 400);
            data.put("data", "{}");
            return data;
        }

        String query = "update system.persons set person = (utl_raw.cast_to_raw('" + personAsJson + "')) where person_id = " + personId;

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            data.put("message", e.toString());
            data.put("status", 500);
            data.put("data", "{}");
        }

        Map check = this.getPersonById(personId);
        data.put("data", check.get("data"));

        return data;
    }
}

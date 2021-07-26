package cst8276.groupassignmentdb.dao;

import cst8276.groupassignmentdb.dto.Course;
import cst8276.groupassignmentdb.dto.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProgramDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map getAllPrograms() {
        Map data = new HashMap();
        String query = "select programId, name, areaOfInterest, campus, credential, length from PERSONS, " +
                "json_table(persons.person, '$.program' columns(programId path '$.programId', " +
                "name path '$.name', " +
                "areaOfInterest path '$.areaOfInterest', " +
                "campus path '$.campus'," +
                "credential path '$.credential'," +
                "length path '$.length')) " +
                "group by programId, name, areaOfInterest, campus, credential, length";

        List<Program> persons = jdbcTemplate.query(query, (rs, rowNum) ->
                new Program(rs.getString("programId"),
                        rs.getString("name"),
                        rs.getString("areaOfInterest"),
                        rs.getString("campus"),
                        rs.getString("credential"),
                        rs.getString("length")));

        data.put("message", "All programs were retrieved");
        data.put("status", 200);
        data.put("data", persons);

        return data;
    }

    public Map getProgramsById(String id) {
        Map data = new HashMap();
        String query = "select programId, name, areaOfInterest, campus, credential, length from PERSONS, " +
                "json_table(persons.person, '$.program' columns(programId path '$.programId', " +
                "name path '$.name', " +
                "areaOfInterest path '$.areaOfInterest', " +
                "campus path '$.campus'," +
                "credential path '$.credential'," +
                "length path '$.length')) where programId='" + id + "'" +
                "group by programId, name, areaOfInterest, campus, credential, length";

        List<Program> persons = jdbcTemplate.query(query, (rs, rowNum) ->
                new Program(rs.getString("programId"),
                        rs.getString("name"),
                        rs.getString("areaOfInterest"),
                        rs.getString("campus"),
                        rs.getString("credential"),
                        rs.getString("length")));

        data.put("message", "All programs were retrieved");
        data.put("status", 200);
        data.put("data", persons);

        return data;
    }
}

package com.example.demo1.service.implemintation;

import com.example.demo1.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Map<String, Object>> getUsersList() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForList("SELECT p.id,p.name FROM people p");
    }

    @Override
    public Map<String, Object> getUserInfo(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForMap("SELECT p.id, p.name, p.phone, a.country, a.post_index ,a.city, a.street, a.house, wp.company_name, wp.role FROM people p LEFT JOIN addresses a ON p.addressId = a.address_id LEFT JOIN work_place wp ON p.workId = wp.work_place_id WHERE p.id =" + id);
    }

    @Override
    public void updateUser(Map<String, String> values) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("UPDATE people SET name =" + "'" +  values.get("name") + "'" + ", phone =" + "'" + values.get("phone") + "'" + "WHERE id=" + values.get("userId"));
        jdbcTemplate.execute("UPDATE addresses SET country =" + "'" + values.get("country") + "'" + ", post_index =" + "'" + values.get("post_index") + "'" + ", city =" + "'" + values.get("city") + "'" + ", street =" + "'" + values.get("street") + "'" + ", house =" + "'" + values.get("house") + "'" + " WHERE address_id =" + values.get("userId"));
        jdbcTemplate.execute("UPDATE work_place SET company_name =" + "'" + values.get("workPlace") + "'" +", role =" + "'" + values.get("role") + "'" + "WHERE work_place_id=" + values.get("userId"));
    }

    @Override
    public void addUser(Map<String, String> values) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("INSERT INTO work_place  (company_name, role) VALUES (" + "'" + values.get("newWorkPlace") + "'" + "," + "'" + values.get("newRole") + "'" + ")");
        Map<String, Object> workData = jdbcTemplate.queryForMap("SELECT work_place_id FROM work_place WHERE company_name=" + "'" + values.get("newWorkPlace")+ "'");
        jdbcTemplate.execute("INSERT INTO addresses  (country, post_index, city, street, house) VALUES (" + "'" + values.get("newCountry") + "'" + "," + "'" + values.get("newIndex") + "'" + "," + "'" + values.get("newCity") + "'" + "," + "'" + values.get("newStreet") + "'" + "," + "'" + values.get("newHouse") + "'" + ")");
        Map<String, Object> addrData = jdbcTemplate.queryForMap("SELECT address_id FROM addresses WHERE post_index=" + "'" + values.get("newIndex") + "'");
        jdbcTemplate.execute("INSERT INTO people  (name, phone,addressId,workId ) VALUES (" + "'" +values.get("newName") + "'" + "," + "'" + values.get("newPhone") + "'" + "," + addrData.get("address_id") + "," + workData.get("work_place_id") + ")" );
    }

    @Override
    public void deleteUser(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DELETE FROM people WHERE id = " + id);
    }
}

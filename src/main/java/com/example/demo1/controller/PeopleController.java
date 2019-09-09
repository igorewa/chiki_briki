package com.example.demo1.controller;


import com.example.demo1.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping(value = "/user_list")
    @ResponseBody
    public List<Map<String, Object>> getUserList() throws SQLException {
        return peopleService.getUsersList();
    }

    @GetMapping(value = "/user_info/{id}")
    @ResponseBody
    public Map<String, Object> getUserInfo(@PathVariable(value = "id") int id) throws Exception {
        return peopleService.getUserInfo(id);
    }


    @PutMapping("/update_user/")
    @ResponseBody
    public void updateUser(@RequestBody Map<String, String> data) {
        peopleService.updateUser(data);
    }

    @PutMapping(value = "/add_user/")
    @ResponseBody
    public void addUser(@RequestBody Map<String, String> data) {
        peopleService.addUser(data);
    }

    @RequestMapping(value = "/delete_user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@PathVariable(value = "id") int id) {
        peopleService.deleteUser(id);
    }
}

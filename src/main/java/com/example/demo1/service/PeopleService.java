package com.example.demo1.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PeopleService {
    List<Map<String, Object>> getUsersList();
    Map<String, Object> getUserInfo(int id);
    void updateUser (Map<String, String> values);
    void addUser(Map<String, String> values);
    void deleteUser (int id);
}

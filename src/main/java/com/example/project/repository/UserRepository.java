package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String INSERT_USER = "INSERT INTO users(name, email, password, role, created_at) VALUES (?, ?, ?, ?, NOW())";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    private static final String UPDATE_USER = "UPDATE users SET name = ?, email = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE email = ?";



    public int save(User user) {
        return jdbcTemplate.update(INSERT_USER, user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getString("created_at"));
        return user;
    }

    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, new Object[]{email}, this::mapRowToUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<User> findAll() {
        return jdbcTemplate.query(GET_ALL_USERS, this::mapRowToUser);
    }

    public int updateUser(User user) {
        return jdbcTemplate.update(UPDATE_USER, user.getName(), user.getEmail(), user.getId());
    }
    public int updatePassword(String email, String newPassword) {
        return jdbcTemplate.update(UPDATE_PASSWORD, newPassword, email);
    }
}

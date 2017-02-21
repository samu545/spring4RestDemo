package com.ing.training.dao;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ing.training.domain.User;

@Repository("userManagementDao")
public class UserManagementDaoImpl implements UserManagementDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public User createUser(User user){
		String insertUserQuery="INSERT INTO T_USERS VALUES(?,?,?,?,?,?)";
		int userId=getIdForUser();
		jdbcTemplate.update(insertUserQuery, new Object[]{userId, user.getFirstname(), user.getLastname(), user.getCity(), user.getEmail(),user.getPhone()});
		return populateUserWithId(user, userId);
	}
	
	@Override
	public User updateUser(User user) {
		String insertUserQuery="UPDATE T_USERS set phone=? WHERE ID=?";
		jdbcTemplate.update(insertUserQuery, new Object[]{user.getPhone(), user.getId()});
		return populateUserWithId(user, user.getId());
	}

	private User populateUserWithId(User user, int userId){
		User createdUser=new User();
		BeanUtils.copyProperties(user, createdUser);
		createdUser.setId(userId);
		return createdUser;
	}

	private int getIdForUser(){
		String highestUserIdQuery="SELECT MAX(ID) FROM T_USERS";
		Integer id=jdbcTemplate.queryForObject(highestUserIdQuery, Integer.class);
		return id+1;
	}

	@Override
	public User getUserById(int id){
		String getUserByIdQuery="SELECT * FROM T_USERS WHERE ID=?";
		return jdbcTemplate.queryForObject(getUserByIdQuery, new Object[]{id}, (rs, rowNum)->{
			User user=new User();
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("firstname"));
			user.setLastname(rs.getString("lastname"));
			user.setCity(rs.getString("city"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			return user;
		});

	}

	@Override
	public List<User> listUsers(){
		String getUserByIdQuery="SELECT * FROM T_USERS";
		return jdbcTemplate.query(getUserByIdQuery, (rs, rowNum)->{
			User user=new User();
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("firstname"));
			user.setLastname(rs.getString("lastname"));
			user.setCity(rs.getString("city"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			return user;

		});
	}

}

package edu.uzz.springboot.mapper.admin;

import edu.uzz.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper1 {
    public User selectByUserTel(String phone);
    public User selectByUsername(String username);
    public int insertUser(String nickname,String email, String telephone, String password, String time,int role);
    public List<User> selectAll();
    public int deleteByid(int id);
    public List<User> selectByUserId(int id);
    public List<User> selectByname(String username);
    public List<User> selectBytel(String telephone);
    public List<User> selectByemail(String email);
    public List<User> selectBysex(int sex);
    public int insertUser2(String nickname,String email, String telephone, String password, String
            time,int sex,String filename,int role);
    public int insertUser3(String nickname,String email, String telephone, String password, String
            time,int sex,int role);
    public User selectByUserId2(int id);
    public int updateUser(String username,String email,String time,int sex,String filename,int id);
    public int updateUser2(String username,String email,String time,int sex,String filename,int id);
    public int updateUser3(String password,int id);
}
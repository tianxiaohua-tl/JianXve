package edu.uzz.springboot.mapper.index;

import edu.uzz.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public User selectByUserTel(String telephone);
    public int insertUser(String username,String email, String telephone, String password, String time,int role,int sex);
    public User selectByUserId(int id);
}

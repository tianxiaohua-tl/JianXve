package edu.uzz.springboot.mapper.admin;

import edu.uzz.springboot.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommentMapper {

    public List<Comment> selectAll();
    public int deleteByid(int CommentId);
    public List<Comment> selectByname(String username);
    public List<Comment> selectByCourseName(String CourseName);
    public List<Comment> selectBy12(String username,String CourseName);
    public List<Comment> selectBy12345(String username);

}

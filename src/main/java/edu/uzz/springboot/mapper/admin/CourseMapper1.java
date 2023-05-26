package edu.uzz.springboot.mapper.admin;

import edu.uzz.springboot.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CourseMapper1 {
    public Course selectByCourseISBN(int ISBN);
    public Course selectByCourseNameadd(String CourseName);
    public List<Course> selectAll();
    public int deleteByISBN(int ISBN);
    public List<Course> selectByISBN(int ISBN);
    public List<Course> selectByCourseName(String CourseName);
    public List<Course> selectByCreatorName(String CreatorName);
    public List<Course> selectByCourse12(String CreatorName,String CourseName);
    public int insertCourse2(int ISBN,String CourseName,String CreatorName,String Decription,String time);
    public int insertCourse3(int ISBN,String CourseName,String CreatorName,String Decription,String time);
    public Course selectByISBN2(int ISBN);
    public int updateCourse2(String CourseName,String CreatorName,String time,String Decription,String filename,int ISBN);
}
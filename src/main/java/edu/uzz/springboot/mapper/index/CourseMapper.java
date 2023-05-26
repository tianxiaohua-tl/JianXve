package edu.uzz.springboot.mapper.index;

import edu.uzz.springboot.domain.Comment;
import edu.uzz.springboot.domain.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CourseMapper {
    public List<Course> selectBySpecialCourse();
    public List<Course> selectAllCourse();
    public List<Course> selectByNameAsc();
    public List<Course> selectByNameDesc();
    public List<Course> selectByPriceAsc();
    public List<Course> selectByPriceDesc();
    public List<Course> selectRateAsc();
    public List<Course> selectRateDesc();
    public List<Course> selectByCategoryId(int Category);
    public List<Course> selectByCategoryIdNameAsc(int Category);
    public List<Course> selectByCategoryIdNameDesc(int Category);
    public List<Course> selectByCategoryIdPriceAsc(int Category);
    public List<Course> selectByCategoryIdPriceDesc(int Category);
    public List<Course> selectByAllPrice0();
    public List<Course> selectByAllSpecial();
    public List<Course> selectByAllNew();
    public List<Course> selectByAllPrice100();
    public List<Course> selectByAllPrice300();
    public List<Course> selectByAllPrice300d();
    public Course selectByISBN(int CourseISBN);
    public List<Course> selectByCourseName(String CourseName);
    public List<Comment> selectByCourseISBN(int CourseISBN);
    public int addcomment(int UserISBN,int CourseISBN,float Grade,String Comment,String Entrytime);
    public List<Comment> selectComment(int UserISBN);
    public int DelComment(int CommentId);
    public List<Comment> selectGrade(int CourseISBN);
    public List<Course> selectStudySpace(int UserISBN);
    public List<Course> selectStudySpaceByNameASC(int UserISBN);
    public List<Course> selectStudySpaceByNameDESC(int UserISBN);

}

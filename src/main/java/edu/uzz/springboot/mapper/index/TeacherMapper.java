package edu.uzz.springboot.mapper.index;

import edu.uzz.springboot.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper {
    public List<Course> selectcreator(int UserISBN);
    public List<Course> selectcreatorasc(int UserISBN);
    public List<Course> selectcreatordesc(int UserISBN);
    public List<Category> selectAllCategory();
    public int insertcourse(String CourseName,int Price,String Decription, String Img,int CategoryId,
                            int SpecialCourse,String Entrytime,String CreatorName,String CreatorTel);
    public List<Course> selectCreatorId(String userISBN);
    public int insertchapter(int CourseISBN,int ChapterHao,String chapter,String kaoshitime);
    public List<Chapter> selectChapterHao(int CourseISBN,int ChapterHao);
    public int UpdateChapter(String chapter,int CourseISBN,int ChapterHao,String kaoshitime);
    public int insertsmallchapter(int ChapterHao1,int ChapterHao2,int CourseISBN,String SmallChapter,int grade);
    public List<Chapter> selectSmallChapter(int ChapterHao1,int ChapterHao2,int CourseISBN);
    public int UpdateSmallChapter(String SmallChapter,int ChapterHao1,int ChapterHao2,int CourseISBN);
    public int DeleteChapter(int ChapterHao,int CourseISBN);
    public int DeleteSmallChapter(int ChapterHao1,int ChapterHao2,int CourseISBN);
    public int UpdateSmallChapterVideo1(String video1Name,String video1,int SmallChapterId,int CourseISBN);
    public int UpdateSmallChapterVideo2(String video2Name,String video2,int SmallChapterId,int CourseISBN);
    public List<Chapter> selectcreatortel(int UserISBN,int CourseISBN);
    public int UpdateSmallChapterwork1(String work1Name,String work1,String work1time,int SmallChapterId,int CourseISBN);
    public int UpdateSmallChapterwork2(String work2Name,String work2,String work2time,int SmallChapterId,int CourseISBN);
    public List<Question> selectQuestion(int CourseISBN);
    public int insertQuestion(String subject,String type,String optiona,String optionb,String optionc,String optiond,String answer,int courseisbn,int chapterid);
    public int DeleteExam(int id);
    public Question SelectQuestionById(int id);
    public int UpdateQuestion(String subject,String type,String optiona,String optionb,String optionc,String optiond,String answer,int id);
    public int UpdateChapterTest(int ChpaterId);
    public List<ExamResult> SelectAllExam(int CourseISBN);
    public List<Map<String,Object>> SelectAllExam1(int CourseISBN);
    public List<TestResult> SelectAllTest(int CourseISBN);
    public int UpdateStugrade(int grade,int testid);
    public int UpdateExam(int CoueseISBN,int chapterid);
}

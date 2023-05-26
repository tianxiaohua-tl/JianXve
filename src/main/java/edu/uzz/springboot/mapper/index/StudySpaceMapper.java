package edu.uzz.springboot.mapper.index;

import edu.uzz.springboot.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudySpaceMapper {
    public List<Chapter> selectChapter(int CourseISBN);
    public List<Chapter> selectSmallChapter(int CourseISBN);
    public List<Chapter> selectSmallChapter2(int CourseISBN);
    public Chapter selectBySmallChapter(int SmallChapterId);
    public List<Chapter> selectByChapterHao1(int CourseISBN,int ChapterHao1);
    public Test selectIsEmpty(int CourseISBN,int UserISBN,int ChapterId,int SmallChapterId);
    public int InsertTest1(int UserISBN,String UserName,int CourseISBN, int ChapterId,
                               int SmallChapterId,String Work1Name,String Work1File,String Work1Time);
    public int InsertTest2(String Work2Name,String Work2File,String WorkTime,int UserISBN,int CourseISBN, int ChapterId,
                           int SmallChapterId);
    public List<Test> selectIsSub(int UserISBN,int CourseISBN, int ChapterId,int SmallChapterId);
    public List<Map<String,Object>> findExamRadioQuestions(int chapterid,int courseisbn);
    public List<Map<String,Object>> findExamCheckboxQuestions(int chapterid,int courseisbn);
    public Question findExamAnswerById(String id);
    //添加学生考试成绩记录
    public int addStuExamResult(ExamResult examResult);
    public User SelectUserBYid(int id);
    public Course SelectCourseByisbn(int CourseISBN);
    public List<ExamResult> SelectExam(int UserISBN, int CourseISBN);
    public List<ExamResult> SelectExam3(int CourseISBN);
    public ExamResult IsExam(int UserISBN, int CourseISBN,int ChapterHao);
    public List<TestResult> IsTest(int UserISBN,int CourseISBN);
    public List<Map<String,Object>> SelectExam1(int UserISBN, int CourseISBN);
    void export(HttpServletResponse response, Workbook workbook, String fileName)throws Exception;


}

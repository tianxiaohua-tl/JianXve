package edu.uzz.springboot.domain;

public class Test {
    private int TestId,CourseISBN,UserISBN,ChapterId,SmallChapterId,grade;
    private String UserName,Work1Name,Work1File,Work1Time,Work2Name,Work2File,Work2Time;

    public int getUserISBN() {
        return UserISBN;
    }

    public void setUserISBN(int userISBN) {
        UserISBN = userISBN;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getTestId() {
        return TestId;
    }

    public void setTestId(int testId) {
        TestId = testId;
    }

    public int getCourseISBN() {
        return CourseISBN;
    }

    public void setCourseISBN(int courseISBN) {
        CourseISBN = courseISBN;
    }

    public int getChapterId() {
        return ChapterId;
    }

    public void setChapterId(int chapterId) {
        ChapterId = chapterId;
    }

    public int getSmallChapterId() {
        return SmallChapterId;
    }

    public void setSmallChapterId(int smallChapterId) {
        SmallChapterId = smallChapterId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getWork1Name() {
        return Work1Name;
    }

    public void setWork1Name(String work1Name) {
        Work1Name = work1Name;
    }

    public String getWork1File() {
        return Work1File;
    }

    public void setWork1File(String work1File) {
        Work1File = work1File;
    }

    public String getWork1Time() {
        return Work1Time;
    }

    public void setWork1Time(String work1Time) {
        Work1Time = work1Time;
    }

    public String getWork2Name() {
        return Work2Name;
    }

    public void setWork2Name(String work2Name) {
        Work2Name = work2Name;
    }

    public String getWork2File() {
        return Work2File;
    }

    public void setWork2File(String work2File) {
        Work2File = work2File;
    }

    public String getWork2Time() {
        return Work2Time;
    }

    public void setWork2Time(String work2Time) {
        Work2Time = work2Time;
    }
}

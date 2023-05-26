package edu.uzz.springboot.domain;

public class TestResult {
    private int SmallChapterId,ChapterHao1,ChapterHao2,grade,stugrade,UserISBN,TestId;
    private String SmallChapter,work1Name,work2Name,work1,work2,work1file,work2file,work1time,work2time,sub1time,sub2time,UserName;

    public int getTestId() {
        return TestId;
    }

    public void setTestId(int testId) {
        TestId = testId;
    }

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

    public int getStugrade() {
        return stugrade;
    }

    public void setStugrade(int stugrade) {
        this.stugrade = stugrade;
    }

    public String getSub1time() {
        return sub1time;
    }

    public void setSub1time(String sub1time) {
        this.sub1time = sub1time;
    }

    public String getSub2time() {
        return sub2time;
    }

    public void setSub2time(String sub2time) {
        this.sub2time = sub2time;
    }

    public String getWork1time() {
        return work1time;
    }

    public void setWork1time(String work1time) {
        this.work1time = work1time;
    }

    public String getWork2time() {
        return work2time;
    }

    public void setWork2time(String work2time) {
        this.work2time = work2time;
    }

    public int getSmallChapterId() {
        return SmallChapterId;
    }

    public void setSmallChapterId(int smallChapterId) {
        SmallChapterId = smallChapterId;
    }

    public int getChapterHao1() {
        return ChapterHao1;
    }

    public void setChapterHao1(int chapterHao1) {
        ChapterHao1 = chapterHao1;
    }

    public int getChapterHao2() {
        return ChapterHao2;
    }

    public void setChapterHao2(int chapterHao2) {
        ChapterHao2 = chapterHao2;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSmallChapter() {
        return SmallChapter;
    }

    public void setSmallChapter(String smallChapter) {
        SmallChapter = smallChapter;
    }

    public String getWork1Name() {
        return work1Name;
    }

    public void setWork1Name(String work1Name) {
        this.work1Name = work1Name;
    }

    public String getWork2Name() {
        return work2Name;
    }

    public void setWork2Name(String work2Name) {
        this.work2Name = work2Name;
    }

    public String getWork1() {
        return work1;
    }

    public void setWork1(String work1) {
        this.work1 = work1;
    }

    public String getWork2() {
        return work2;
    }

    public void setWork2(String work2) {
        this.work2 = work2;
    }

    public String getWork1file() {
        return work1file;
    }

    public void setWork1file(String work1file) {
        this.work1file = work1file;
    }

    public String getWork2file() {
        return work2file;
    }

    public void setWork2file(String work2file) {
        this.work2file = work2file;
    }
}

package edu.uzz.springboot.domain;


import java.sql.Timestamp;

public class ExamResult {
    private int id,courseisbn,chapterid,userisbn,radioscores,checkscores,total;
    private Timestamp createtime;

    private int ChapterId,CourseISBN,ChapterHao,test;
    private String chapter,kaoshitime;

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getKaoshitime() {
        return kaoshitime;
    }

    public void setKaoshitime(String kaoshitime) {
        this.kaoshitime = kaoshitime;
    }

    public int getChapterId() {
        return ChapterId;
    }

    public void setChapterId(int chapterId) {
        ChapterId = chapterId;
    }

    public int getCourseISBN() {
        return CourseISBN;
    }

    public void setCourseISBN(int courseISBN) {
        CourseISBN = courseISBN;
    }

    public int getChapterHao() {
        return ChapterHao;
    }

    public void setChapterHao(int chapterHao) {
        ChapterHao = chapterHao;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseisbn() {
        return courseisbn;
    }

    public void setCourseisbn(int courseisbn) {
        this.courseisbn = courseisbn;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getUserisbn() {
        return userisbn;
    }

    public void setUserisbn(int userisbn) {
        this.userisbn = userisbn;
    }

    public int getRadioscores() {
        return radioscores;
    }

    public void setRadioscores(int radioscores) {
        this.radioscores = radioscores;
    }

    public int getCheckscores() {
        return checkscores;
    }

    public void setCheckscores(int checkscores) {
        this.checkscores = checkscores;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}

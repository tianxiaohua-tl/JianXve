package edu.uzz.springboot.domain;

public class Studyspace {
    private int id,UserISBN,UserTel,CourseISBN;
    private String Entrytime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserISBN() {
        return UserISBN;
    }

    public void setUserISBN(int userISBN) {
        UserISBN = userISBN;
    }

    public int getUserTel() {
        return UserTel;
    }

    public void setUserTel(int userTel) {
        UserTel = userTel;
    }

    public int getCourseISBN() {
        return CourseISBN;
    }

    public void setCourseISBN(int courseISBN) {
        CourseISBN = courseISBN;
    }

    public String getEntrytime() {
        return Entrytime;
    }

    public void setEntrytime(String entrytime) {
        Entrytime = entrytime;
    }
}

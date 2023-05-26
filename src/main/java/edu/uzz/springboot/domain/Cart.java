package edu.uzz.springboot.domain;

public class Cart {
    private int UserISBN,CourseISBN,id;
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

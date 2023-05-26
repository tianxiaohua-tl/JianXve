package edu.uzz.springboot.domain;

public class Comment {
    private int CommentId,UserISBN,CourseISBN;
    private float Grade;
    private String username,img,Comment,Entrytime,CourseName;

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
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

    public float getGrade() {
        return Grade;
    }

    public void setGrade(float grade) {
        Grade = grade;
    }

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getEntrytime() {
        return Entrytime;
    }

    public void setEntrytime(String entrytime) {
        Entrytime = entrytime;
    }
}

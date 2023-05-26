package edu.uzz.springboot.domain;

public class Course {
    private int ISBN,CourseISBN,CategoryId,SpecialCourse,Price,orderid,orderPrice;
    private String CourseName,CreatorName,Decription,Entrytime,Img,shijian,note,CreatorTel;

    public String getCreatorTel() {
        return CreatorTel;
    }

    public void setCreatorTel(String creatorTel) {
        CreatorTel = creatorTel;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getCourseISBN() {
        return CourseISBN;
    }

    public void setCourseISBN(int courseISBN) {
        CourseISBN = courseISBN;
    }


    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getSpecialCourse() {
        return SpecialCourse;
    }

    public void setSpecialCourse(int specialCourse) {
        SpecialCourse = specialCourse;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String creatorName) {
        CreatorName = creatorName;
    }

    public String getDecription() {
        return Decription;
    }

    public void setDecription(String decription) {
        Decription = decription;
    }

    public String getEntrytime() {
        return Entrytime;
    }

    public void setEntrytime(String entrytime) {
        Entrytime = entrytime;
    }
}

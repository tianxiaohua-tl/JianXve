package edu.uzz.springboot.mapper.index;

import edu.uzz.springboot.domain.Cart;
import edu.uzz.springboot.domain.Course;
import edu.uzz.springboot.domain.Wish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    public int addCart(int UserISBN, int CourseISBN, String Entrytime);
    public int addWish(int UserISBN, int CourseISBN, String Entrytime);
    public List<Course> selectByUserISBN(int UserISBN);
    public List<Course> selectByUserISBN1(int UserISBN);
    public List<Course> selectByUserISBN2(int UserISBN);
    public List<Wish> selectByUCWISBN(int UserISBN, int CourseISBN);
    public List<Cart> selectByUCCISBN(int UserISBN, int CourseISBN);
    public Wish selectforwishid(int UserISBN,int CourseISBN);
    public Cart selectforcartid(int UserISBN,int CourseISBN);
    public int deletebywishid(int id);
    public int deletebycartid(int id);
}

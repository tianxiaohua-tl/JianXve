package edu.uzz.springboot.mapper.index;

import edu.uzz.springboot.domain.Course;
import edu.uzz.springboot.domain.order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    public Boolean addorder(int UserISBN,String UserTel,int ISBN,int CoursePrice,String Entrytime);
    public Boolean addorder1(int UserISBN,String UserTel,int ISBN,int CoursePrice,String note,String Entrytime);
    public Boolean addstudyspace(int UserISBN,String UserTel,int ISBN,String Entrytime);
    public Boolean deletecart();
    public List<order> selectfororderid(int UserISBN, int CourseISBN);
    public order selectorder(int UserISBN, int CourseISBN);
    public List<Course> selectByUserISBN(int UserISBN);
    public int DeleteStudyspace(int UserISBN,int CourseISBN);
}

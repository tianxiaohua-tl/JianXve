package edu.uzz.springboot.mapper.admin;

import edu.uzz.springboot.domain.order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderListMapper {
    public List<order> selectAll();
    public int deleteByid(int orderid);
    public List<order> selectAll1(String username);
    public List<order> selectAll2(String CourseName);
    public List<order> selectAll3(int orderid);
    public List<order> selectAll4(String username,String CourseName);
    public List<order> selectByUserId(int orderid);
    public order selectByUserId2(int orderid);
}
package edu.uzz.springboot.controller.admin;

import edu.uzz.springboot.domain.Comment;
import edu.uzz.springboot.mapper.admin.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ConmentController {


    @Autowired
    private CommentMapper conmentMapper;
    private String admin = "admin/";

    @GetMapping("/admin/comment/list")
    public String list(@RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum, @RequestParam(value =
            "num",required = false,defaultValue ="5")String num, Model model){
        List<Comment> List= conmentMapper.selectAll();
        int pagesize=Integer.parseInt(num);//每页显示的书的个数
        int totalnum=List.size();//书的总数量
        int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
        if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
            pageNum=totalpage;
        }
        int begin=(pageNum-1)*pagesize;
        int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
        List=List.subList(begin,end);
        model.addAttribute("sw",1);
        model.addAttribute("list",List);
        model.addAttribute("pagesize",pagesize);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalpage",totalpage);
        model.addAttribute("totalnum",totalnum);
        return admin + "comment/list";
    }
    @GetMapping("/admin/deleteById2")
    public String deleteUser(int CommentId, RedirectAttributes attributes){
        int isdelete;int icon=1;
        String msg;
        isdelete= conmentMapper.deleteByid(CommentId);
        if (isdelete == 0) {
            msg = "删除失败";
            icon=2;
        }else{
            msg = "删除成功";
        }
        attributes.addFlashAttribute("ms",msg);
        attributes.addFlashAttribute("icon",icon);
        return "redirect:/admin/comment/list";
    }
    @PostMapping("/user/search2")
    public String search(String username,String CourseName,@RequestParam(value =
            "pageNum",required = false,defaultValue ="1")int pageNum,@RequestParam(value =
            "num",required = false,defaultValue ="5")String num,Model model,RedirectAttributes attributes) {
        String msg=" ";String url="/admin/user/list2";;int icon=1;
        if(CourseName!=null&&!CourseName.equals("")&&CourseName.length()>0&&(username==null||username.equals(""))){
            List<Comment> List= conmentMapper.selectByCourseName(CourseName);
            int totalnum=List.size();
            if(totalnum==0){
                msg="无此信息";
                icon=2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/comment/list";
            }else{
                model.addAttribute("list",List);
                model.addAttribute("totalnum",totalnum);
                model.addAttribute("sw",2);}
        }

else if(username!=null&&username.length()>0&&(CourseName==null||CourseName.equals(""))){
            List<Comment> List= conmentMapper.selectByname(username);
            int totalnum=List.size();
            if(totalnum==0){
                msg="无此信息";
                icon=2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/comment/list";
            }else{
                model.addAttribute("list",List);
                model.addAttribute("totalnum",totalnum);
                model.addAttribute("sw",2);}
        }
        else{
            if(username!=null&&username.length()>0&&(CourseName!=null&&!CourseName.equals("")&&CourseName.length()>0)) {
                List<Comment> List= conmentMapper.selectBy12(username,CourseName);
                int totalnum=List.size();
                if(totalnum==0){
                    msg="无此信息";
                    icon=2;
                    attributes.addFlashAttribute("ms", msg);
                    attributes.addFlashAttribute("icon", icon);
                    return "redirect:/admin/comment/list";
                }else{
                    model.addAttribute("list",List);
                    model.addAttribute("totalnum",totalnum);
                    model.addAttribute("sw",2);
                }
            }else if(CourseName==null||CourseName.equals("")&&(username==null||username.equals(""))){
                List<Comment> List= conmentMapper.selectAll();
                if(List==null&&List.size()==0){
                    msg="无此信息";
                    icon=2;
                    attributes.addFlashAttribute("ms", msg);
                    attributes.addFlashAttribute("icon", icon);
                    return "redirect:/admin/comment/list";
                }else{
                    return "redirect:/admin/comment/list";
                }
            }
        }
            return admin + "comment/list";
    }
}

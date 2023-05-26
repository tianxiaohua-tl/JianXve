package edu.uzz.springboot.controller.admin;

import edu.uzz.springboot.domain.Course;
import edu.uzz.springboot.mapper.admin.CourseMapper1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseMapper1 CourseMapper;
    private String admin = "admin/";


    @GetMapping("/admin/course/list")
    public String list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum, @RequestParam(value =
            "num", required = false, defaultValue = "5") String num, Model model) {
        List<Course> List = CourseMapper.selectAll();
        int pagesize = Integer.parseInt(num);//每页显示的书的个数
        int totalnum = List.size();//书的总数量
        int totalpage = totalnum % pagesize == 0 ? (totalnum / pagesize) : (totalnum / pagesize + 1);// 总页数
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageNum > totalpage) {
            pageNum = totalpage;
        }
        int begin = (pageNum - 1) * pagesize;
        int end = pagesize * pageNum > totalnum ? totalnum : (pageNum * pagesize);
        List = List.subList(begin, end);
        model.addAttribute("sw", 1);
        model.addAttribute("list", List);
        model.addAttribute("pagesize", pagesize);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("totalnum", totalnum);
        return admin + "/course/list";
    }


    @PostMapping("/course/search")
    public String search(String ISBN, String CourseName, String CreatorName, @RequestParam(value =
            "pageNum", required = false, defaultValue = "1") int pageNum, @RequestParam(value =
            "num", required = false, defaultValue = "5") String num, Model model, RedirectAttributes attributes) {
        String msg = " ";
        String url = "/admin/course/list";
        ;
        int icon = 1;
        if (ISBN != null && !ISBN.equals("") && ISBN.length() > 0) {
            List<Course> List = CourseMapper.selectByISBN(new Integer(ISBN));
            int totalnum = List.size();
            if (totalnum == 0) {
                msg = "无此信息";
                icon = 2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/course/list";
            } else {
                model.addAttribute("list", List);
                model.addAttribute("totalnum", totalnum);
                model.addAttribute("sw", 2);
            }
        } else if(CourseName != null && CourseName.length() > 0 &&(CreatorName != null && CreatorName.length() > 0 )){

            List<Course> List = CourseMapper.selectByCourse12(CreatorName,CourseName);
            int totalnum = List.size();
            if (totalnum == 0) {
                msg = "无此信息";
                icon = 2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/course/list";
            } else {
                model.addAttribute("list", List);
                model.addAttribute("totalnum", totalnum);
                model.addAttribute("sw", 2);
            }



        }












        else if (CourseName != null && CourseName.length() > 0 && (CreatorName == null || CreatorName.equals(""))) {
            List<Course> List = CourseMapper.selectByCourseName(CourseName);
            int totalnum = List.size();
            if (totalnum == 0) {
                msg = "无此信息";
                icon = 2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/course/list";
            } else {
                model.addAttribute("list", List);
                model.addAttribute("totalnum", totalnum);
                model.addAttribute("sw", 2);
            }
        } else if (CreatorName != null && CreatorName.length() > 0 && (CourseName == null || CourseName.equals(""))) {
            List<Course> List = CourseMapper.selectByCreatorName(CreatorName);
            int totalnum = List.size();
            if (totalnum == 0) {
                msg = "无此信息";
                icon = 2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/course/list";
            } else {
                model.addAttribute("list", List);
                model.addAttribute("totalnum", totalnum);
                model.addAttribute("sw", 2);
            }
        } else if ((ISBN == null || ISBN.equals("") || ISBN.length() == 0) && (CourseName == null || CourseName.equals("")) && (CreatorName == null || CreatorName.equals(""))) {
            List<Course> List = CourseMapper.selectAll();
            if (List==null&&List.size()==0) {
                msg = "无此信息";
                icon = 2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/course/list";
            } else {
                return "redirect:/admin/course/list";
            }
        }
        return admin + "/course/list";
    }

    @GetMapping("/admin/deleteByISBN")
    public String deleteCourse(int ISBN, RedirectAttributes attributes){
        int isdelete;int icon=1;
        String msg;
        isdelete= CourseMapper.deleteByISBN(ISBN);
        if (isdelete == 0) {
            msg = "删除失败";
            icon=2;
        }else{
            msg = "删除成功";
        }
        attributes.addFlashAttribute("ms",msg);
        attributes.addFlashAttribute("icon",icon);
        return "redirect:/admin/course/list";
    }

    @GetMapping("/admin/course/addCourse")
    public String addCourse(){
        return admin + "course/courseform";
    }

    @PostMapping(value = "/addcourse")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, int ISBN,
                             String CourseName,String CreatorName,String Decription,RedirectAttributes
                                     attributes) throws FileNotFoundException {
        String msg = null;int role=0;int icon=1;
        int isRegister=0;
        Course course = CourseMapper.selectByCourseNameadd(CourseName);
        if (course == null) {
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            if (file.isEmpty()) {
                msg="添加成功，为默认图片";
                isRegister = CourseMapper.insertCourse3(ISBN,CourseName,CreatorName,Decription,time);
            }else {
                String fileName = file.getOriginalFilename(); // 文件名
                String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
                String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/course//";
//                String filePath =
//                        "D://springboot//match//springboot//src//main//resources//static//img//"; // 上传后的路径
                fileName=System.currentTimeMillis()+suffixName;//新文件名
                File dest = new File(filePath + fileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String filename = "/img/" + fileName;
                isRegister =
                        CourseMapper.insertCourse2(ISBN,CourseName,CreatorName,Decription,time);
                msg="添加成功";
            }if (course!=null&&isRegister ==0) {
                msg = "请重新添加";
                icon=2;
            }}
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/course/addCourse";
    }
//


    @GetMapping("/admin/course/updateCourse")
    public String updateCourse(String ISBN,Model model){
        Course course= CourseMapper.selectByISBN2(new Integer(ISBN));
        model.addAttribute("ISBN",course.getISBN());
        model.addAttribute("Price",course.getPrice());
        model.addAttribute("CourseName",course.getCourseName());
        model.addAttribute("img",course.getImg());
        model.addAttribute("Decription",course.getDecription());
        model.addAttribute("CreatorName",course.getCreatorName());
        return admin + "course/update";
    }
    @PostMapping("/updatecourse")
    public String updatecourse(@RequestParam(value = "file") MultipartFile file, String
            ISBN,String CourseName,String CreatorName,String Decription,RedirectAttributes attributes) throws FileNotFoundException {
        String msg = null;int icon=1;int isUpdate=0;
        if (file.isEmpty()) {
            msg="图片为空";
            icon=2;
        }else {
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            String fileName = file.getOriginalFilename(); // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/course//";
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//img//course//"; // 上传后的路径
            fileName=System.currentTimeMillis()+suffixName;//新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename = "/img/course/" + fileName;
                isUpdate = CourseMapper.updateCourse2(CourseName, CreatorName, time, Decription,filename, new
                        Integer(ISBN));
            if(isUpdate==1){
                msg="更新成功";
            }else {
                msg="更新失败";icon=2;
            }}
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/course/updateCourse?ISBN="+new Integer(ISBN);
    }



    @GetMapping("/admin/course/info")
    public String courseinfo(String ISBN,Model model){
        Course course= CourseMapper.selectByCourseISBN(new Integer(ISBN));
        model.addAttribute("ISBN",course.getISBN());
        model.addAttribute("Price",course.getPrice());
        model.addAttribute("CourseName",course.getCourseName());
        model.addAttribute("img",course.getImg());
        model.addAttribute("Decription",course.getDecription());
        model.addAttribute("CreatorName",course.getCreatorName());
        return admin + "/set/course/info";
    }
    @PostMapping("/updatecourse2")
    public String updatecourse2(@RequestParam(value = "file") MultipartFile file, String
            ISBN,String CourseName,String CreatorName,String Decription,RedirectAttributes attributes) throws FileNotFoundException {
        String msg = null;int icon=1;int isUpdate=0;
        if (file.isEmpty()) {
            msg="图片为空";
            icon=2;
        }else {
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            String fileName = file.getOriginalFilename(); // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/course//";
//            String filePath =
//                    "D://liuxiaofan//match//springboot//src//main//resources//static//img//"; // 上传后的路径
            fileName=System.currentTimeMillis()+suffixName;//新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename = "/img/" + fileName;
            isUpdate = CourseMapper.updateCourse2(CourseName, CreatorName, time, Decription,filename, new
                    Integer(ISBN));
            if(isUpdate==1){
                msg="更新成功";
            }else {
                msg="更新失败";icon=2;
            }}
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/course/info?ISBN="+new Integer(ISBN);
    }

//    @GetMapping("/admin/user/updateUser")
//    public String updateUser(String id,Model model){
//        User user= userMapper.selectByUserId2(new Integer(id));
//        model.addAttribute("id",user.getId());
//        model.addAttribute("password",user.getPassword());
//        model.addAttribute("username",user.getUsername());
//        model.addAttribute("img",user.getImg());
//        model.addAttribute("sex",user.getSex());
//        model.addAttribute("email",user.getEmail());
//        return admin + "user/user/update";
//    }
//    @PostMapping("/updateuser")
//    public String updateuser(@RequestParam(value = "file") MultipartFile file, String
//            id,String username,String email,int pass,int sex,RedirectAttributes attributes){
//        String msg = null;int icon=1;int isUpdate=0;
//        if (file.isEmpty()) {
//            msg="头像为空";
//            icon=2;
//        }else {
//            Date date=new Date();
//            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String time=sd.format(date);
//            String fileName = file.getOriginalFilename(); // 文件名
//            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//img//user//"; // 上传后的路径
//            fileName=System.currentTimeMillis()+suffixName;//新文件名
//            File dest = new File(filePath + fileName);
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }
//            try {
//                file.transferTo(dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String filename = "img/user/" + fileName;
//            if(pass==1) {
//                isUpdate = userMapper.updateUser(username, email, time, sex, filename, new Integer(id));
//            }else {
//                isUpdate = userMapper.updateUser2(username, email, time, sex, filename, new
//                        Integer(id));
//            }
//            if(isUpdate==1){
//                msg="更新成功";
//            }else {
//                msg="更新失败";icon=2;
//            }}
//        attributes.addFlashAttribute("ms", msg);
//        attributes.addFlashAttribute("icon", icon);
//        return "redirect:/admin/user/updateUser?id="+new Integer(id);
//    }
//
//    @GetMapping("/admin/user/info")
//    public String userinfo(String id,Model model){
//        User user= userMapper.selectByUserId2(new Integer(id));
//        model.addAttribute("id",user.getId());
//        model.addAttribute("password",user.getPassword());
//        model.addAttribute("username",user.getUsername());
//        model.addAttribute("img",user.getImg());
//        model.addAttribute("sex",user.getSex());
//        model.addAttribute("email",user.getEmail());
//        model.addAttribute("telephone",user.getTelephone());
//        return admin + "/set/user/info";
//    }
//    @PostMapping("/updateuser2")
//    public String updateuser2(@RequestParam(value = "file") MultipartFile file, String
//            id,String username,String email,int sex,RedirectAttributes attributes){
//        String msg = null;int icon=1;int isUpdate=0;
//        if (file.isEmpty()) {
//            msg="头像为空";
//            icon=2;
//        }else {
//            Date date=new Date();
//            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String time=sd.format(date);
//            String fileName = file.getOriginalFilename(); // 文件名
//            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
//            String filePath =
//                    "D://liuxiaofan//match//springboot//src//main//resources//static//img//"; // 上传后的路径
//            fileName=System.currentTimeMillis()+suffixName;//新文件名
//            File dest = new File(filePath + fileName);
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }
//            try {
//                file.transferTo(dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String filename = "img/" + fileName;
//            isUpdate = userMapper.updateUser2(username, email, time, sex, filename, new
//                    Integer(id));
//            if(isUpdate==1){
//                msg="更新成功";
//            }else {
//                msg="更新失败";icon=2;
//            }}
//        attributes.addFlashAttribute("ms", msg);
//        attributes.addFlashAttribute("icon", icon);
//        return "redirect:/admin/user/info?id="+new Integer(id);
//    }
//
//    @GetMapping("/admin/user/password")
//    public String userpassword(String id,Model model){
//        model.addAttribute("id",new Integer(id));
//        return admin + "/set/user/password";
//    }
//
//    @PostMapping("/dopassword")
//    public String dopassword(String id,String oldpassword,String password,String
//            repassword,RedirectAttributes attributes){
//        String msg;int icon=2;int isUpdate=0;
//        User user= userMapper.selectByUserId2(new Integer(id));
//        if (oldpassword.equals(user.getPassword())) {
//            if(!password.equals(repassword)){
//                msg = "两次密码输入不一致";
//            }else {
//                isUpdate = userMapper.updateUser3(password, new Integer(id));
//                msg = "修改成功";
//                icon=1;
//            }
//        } else {
//            msg = "原始密码错误";
//        }
//        attributes.addFlashAttribute("ms", msg);
//        attributes.addFlashAttribute("icon", icon);
//        return "redirect:/admin/user/password?id="+new Integer(id);
//    }
}
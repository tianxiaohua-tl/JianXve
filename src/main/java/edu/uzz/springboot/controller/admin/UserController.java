package edu.uzz.springboot.controller.admin;

import edu.uzz.springboot.domain.User;
import edu.uzz.springboot.mapper.admin.UserMapper1;
import edu.uzz.springboot.tool.MailTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    private UserMapper1 userMapper;
    private String admin = "admin/";

    @GetMapping("/admin/index")
    public String index(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int id= (int) session.getAttribute("id");
        if(username!=null&&!username.equals("")){
            model.addAttribute("username", username);
            model.addAttribute("id", id);
        }
        return admin + "index";
    }

    @RequestMapping("/getCode")
    @ResponseBody
    public boolean getCode(String email , HttpServletRequest request){
        //随机生成一个验证码
//        Integer code=new Random().nextInt(3000);

        String code1= String.format("%05d",new Random().nextInt(99999));
        Integer  code=Integer.parseInt(code1);

        request.getSession().setAttribute("code",code.toString());
        request.getSession().setAttribute("email",email);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        MailTool.send(email,code);
                    }
                }
        ).start();
        return true;
    }
    @GetMapping("/reg")
    public String register(){
        return admin + "user/reg";
    }
    @PostMapping("/doregister")
    public String doRegister(String telephone,String email,String password,String repass,String agreement,String
            nickname,Model model, HttpServletRequest request) {
        int isRegister = 0;int isUrl=0;
        String msg = "注册成功,正在跳转页面...";
        String url = "/reg";
        int agree=0;int pass=0;int role=0;int icon=7;int ti=0000;
        String codeId = (String) request.getSession().getAttribute("code");
        String emailcode=request.getParameter("emailcode");//取出输入的验证码
        if(agreement!=null&&agreement.equals("on")) {
            agree=1;}
        if(!password.equals(repass)){
            pass=1;
            msg = "两次密码输入不一致";
        }
        if(agree!=1) {
            msg= "你必须同意用户协议才能注册";
        }
        if(emailcode.equals(codeId)) {
            if (agree == 1 && pass != 1) {
                User user = userMapper.selectByUserTel(telephone);
                if (user == null) {
                    Date date = new Date();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sd.format(date);
                    isRegister = userMapper.insertUser(nickname, email, telephone, password, time, role);
                    url = "/login";
                    icon = 1;
                    ti = 2000;
                }
                if (user != null && isRegister == 0) {
                    msg = "此手机号已被注册,请登录或重新注册";
                }
            }
        }else{
            msg="验证码不准确";
        }
        model.addAttribute("isUrl",isUrl);
        model.addAttribute("ms", msg);
        model.addAttribute("url", url);
        model.addAttribute("icon", icon);
        model.addAttribute("ti", ti);
        return admin + "user/reg";
    }
// 注册的另一种方法----重定向！
// RedirectAttributes attributes
// attributes.addFlashAttribute("aa", msg);
// return "redirect:/user/login";

    @GetMapping("/login")
    public String login(){
        return admin + "user/login";
    }
    @PostMapping("/dologin")
    public String dologin(String telephone,String password,Model model,HttpServletResponse response,HttpServletRequest
            request) throws IOException {
        User user = userMapper.selectByUserTel(telephone);
        int islogin = 0;int icon=1;int ti=1000;
        String msg = "登陆成功,正在跳转...";
        String url = "/admin/index";
// 已注册
        String inputvalcode=request.getParameter("verifyCode");//取出输入的验证码
        HttpSession session=request.getSession();
        String valcode=(String)session.getAttribute("verifyCode");//取出生成的验证码
        if(inputvalcode.equals(valcode)) {
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    if(user.getRole()==1) {
                        islogin = 1;
                        Cookie cookiename = new Cookie("username", user.getUsername());
                        Cookie cookiepass = new Cookie("userpass", user.getPassword());
                        cookiename.setMaxAge(24 * 60 * 60);
                        cookiepass.setMaxAge(24 * 60 * 60);
                        response.addCookie(cookiename);
                        response.addCookie(cookiepass);
//                HttpSession session=request.getSession();
                        session.setAttribute("username", user.getUsername());
                        session.setAttribute("id", user.getId());
                        response.sendRedirect("/admin/index");//重定向
                    }else {
                        msg = "权限不足";
                        icon=2;
                        url= "/login";
                        ti=0000;
                    }
                } else {
                    msg = "密码错误";
                    icon=2;
                    url= "/login";
                    ti=0000;
                }
            } else {
                msg = "此用户名未注册，正在跳转注册界面...";
                icon=7;
                url = "/admin/user/reg";
            }
        }else{
            msg="验证码错误";
            icon=2;
            url= "/login";
            ti=0000;
        }
        model.addAttribute("ms", msg);
        model.addAttribute("url", url);
        model.addAttribute("icon", icon);
        model.addAttribute("ti", ti);
        return admin + "user/login";
    }
    @GetMapping("/admin/user/forget")
    public String forget(){
        return admin + "user/forget";
    }

    @GetMapping("/admin/user/list")
    public String list(@RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum,@RequestParam(value =
            "num",required = false,defaultValue ="5")String num,Model model){
        List<User> List= userMapper.selectAll();
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
        return admin + "/user/user/list";
    }

    @PostMapping("/user/search")
    public String search(String id,String username,String telephone,String email,int sex,@RequestParam(value =
            "pageNum",required = false,defaultValue ="1")int pageNum,@RequestParam(value =
            "num",required = false,defaultValue ="5")String num,Model model,RedirectAttributes attributes) {
        String msg=" ";String url="/admin/user/list";;int icon=1;
        if(id!=null&&!id.equals("")&&id.length()>0){
            List<User> List= userMapper.selectByUserId(new Integer(id));
            int totalnum=List.size();
            if(totalnum==0){
                msg="无此信息";
                icon=2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/user/list";
            }else{
                model.addAttribute("list",List);
                model.addAttribute("totalnum",totalnum);
                model.addAttribute("sw",2);}
        }
        else if(telephone!=null&&!telephone.equals("")&&telephone.length()>0) {
            List<User> List= userMapper.selectBytel(telephone);
            int totalnum=List.size();
            if(totalnum==0){
                msg="无此信息";
                icon=2;
                attributes.addFlashAttribute("ms", msg);
                attributes.addFlashAttribute("icon", icon);
                return "redirect:/admin/user/list";
            }else{

                model.addAttribute("list",List);
                model.addAttribute("totalnum",totalnum);
                model.addAttribute("sw",2);}
        }else{
            if(username!=null&&username.length()>0&&(email==null||email.equals(""))) {
                List<User> List= userMapper.selectByname(username);
                int totalnum=List.size();
                if(totalnum==0){
                    msg="无此信息";
                    icon=2;
                    attributes.addFlashAttribute("ms", msg);
                    attributes.addFlashAttribute("icon", icon);
                    return "redirect:/admin/user/list";
                }else{
                    model.addAttribute("list",List);
                    model.addAttribute("totalnum",totalnum);
                    model.addAttribute("sw",2);
                }
            }else if(email!=null&&email.length()>0&&(username==null||username.equals(""))) {
                List<User> List= userMapper.selectByemail(email);
                int totalnum=List.size();
                if(totalnum==0){
                    msg="无此信息";
                    icon=2;
                    attributes.addFlashAttribute("ms", msg);
                    attributes.addFlashAttribute("icon", icon);
                    return "redirect:/admin/user/list";
                }else{
                    model.addAttribute("list",List);
                    model.addAttribute("totalnum",totalnum);
                    model.addAttribute("sw",2);
                }
            }else if(sex!=0&&(username==null||username.equals(""))&&(email==null||email.equals(""))) {
                List<User> List= userMapper.selectBysex(sex);
                int totalnum=List.size();
                if(totalnum==0){
                    msg="无此信息";
                    icon=2;
                    attributes.addFlashAttribute("ms", msg);
                    attributes.addFlashAttribute("icon", icon);
                    return "redirect:/admin/user/list";
                }else{
                    model.addAttribute("list",List);
                    model.addAttribute("totalnum",totalnum);
                    model.addAttribute("sw",2);
                }
            }else if(sex==0&&(username==null||username.equals(""))&&(email==null||email.equals(""))){
                List<User> List= userMapper.selectAll();
                if(List==null&&List.size()==0){
                    msg="无此信息";
                    icon=2;
                    attributes.addFlashAttribute("ms", msg);
                    attributes.addFlashAttribute("icon", icon);
                    return "redirect:/admin/user/list";
                }else{
                    return "redirect:/admin/user/list";
                }
            }
        }
        return admin + "user/user/list";
    }

    @GetMapping("/admin/deleteById")
    public String deleteUser(int id, RedirectAttributes attributes){
        int isdelete;int icon=1;
        String msg;
        isdelete= userMapper.deleteByid(id);
        if (isdelete == 0) {
            msg = "删除失败";
            icon=2;
        }else{
            msg = "删除成功";
        }
        attributes.addFlashAttribute("ms",msg);
        attributes.addFlashAttribute("icon",icon);
        return "redirect:/admin/user/list";
    }

    @GetMapping("/admin/user/addUser")
    public String addUser(){
        return admin + "user/user/userform";
    }

    @PostMapping(value = "/adduser")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, String
            username,String telephone,String email,String password,int sex,RedirectAttributes
                                     attributes) throws FileNotFoundException {
        String msg = null;int role=0;int icon=1;
        int isRegister=0;
        User user = userMapper.selectByUserTel(telephone);
        if (user == null) {
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            if (file.isEmpty()) {
                msg="添加成功，为默认头像";
                isRegister = userMapper.insertUser3(username,email,telephone,password,time,sex,role);
            }else {
                String fileName = file.getOriginalFilename(); // 文件名
                String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
                String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/user//"; // 上传后的路径
//                        "D://liuxiaofan//match//springboot//src//main//resources//static//img//"; // 上传后的路径
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
                        userMapper.insertUser2(username,email,telephone,password,time,sex,filename,role);
                msg="添加成功";
            }if (user!=null&&isRegister ==0) {
                msg = "此手机号已被添加,请重新添加";
                icon=2;
            }}
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/user/addUser";
    }

    @GetMapping("/admin/user/updateUser")
    public String updateUser(String id,Model model){
        User user= userMapper.selectByUserId2(new Integer(id));
        model.addAttribute("id",user.getId());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("img",user.getImg());
        model.addAttribute("sex",user.getSex());
        model.addAttribute("email",user.getEmail());
        return admin + "user/user/update";
    }
    @PostMapping("/updateuser")
    public String updateuser(@RequestParam(value = "file") MultipartFile file, String
            id,String username,String email,int pass,int sex,RedirectAttributes attributes) throws FileNotFoundException {
        String msg = null;int icon=1;int isUpdate=0;
        if (file.isEmpty()) {
            msg="头像为空";
            icon=2;
        }else {
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            String fileName = file.getOriginalFilename(); // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/user//";
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//img//user//"; // 上传后的路径
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
            String filename = "/img/user/" + fileName;
            if(pass==1) {
                isUpdate = userMapper.updateUser(username, email, time, sex, filename, new Integer(id));
            }else {
                isUpdate = userMapper.updateUser2(username, email, time, sex, filename, new
                        Integer(id));
            }
            if(isUpdate==1){
                msg="更新成功";
            }else {
                msg="更新失败";icon=2;
            }}
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/user/updateUser?id="+new Integer(id);
    }

    @GetMapping("/admin/user/info")
    public String userinfo(String id,Model model){
        User user= userMapper.selectByUserId2(new Integer(id));
        model.addAttribute("id",user.getId());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("img",user.getImg());
        model.addAttribute("sex",user.getSex());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("telephone",user.getTelephone());
        return admin + "/set/user/info";
    }
    @PostMapping("/updateuser2")
    public String updateuser2(@RequestParam(value = "file") MultipartFile file, String
            id,String username,String email,int sex,RedirectAttributes attributes) throws FileNotFoundException {
        String msg = null;int icon=1;int isUpdate=0;
        if (file.isEmpty()) {
            msg="头像为空";
            icon=2;
        }else {
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            String fileName = file.getOriginalFilename(); // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/user//";
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
            isUpdate = userMapper.updateUser2(username, email, time, sex, filename, new
                    Integer(id));
            if(isUpdate==1){
                msg="更新成功";
            }else {
                msg="更新失败";icon=2;
            }}
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/user/info?id="+new Integer(id);
    }

    @GetMapping("/admin/user/password")
    public String userpassword(String id,Model model){
        model.addAttribute("id",new Integer(id));
        return admin + "/set/user/password";
    }

    @PostMapping("/dopassword")
    public String dopassword(String id,String oldpassword,String password,String
            repassword,RedirectAttributes attributes){
        String msg;int icon=2;int isUpdate=0;
        User user= userMapper.selectByUserId2(new Integer(id));
        if (oldpassword.equals(user.getPassword())) {
            if(!password.equals(repassword)){
                msg = "两次密码输入不一致";
            }else {
                isUpdate = userMapper.updateUser3(password, new Integer(id));
                msg = "修改成功";
                icon=1;
            }
        } else {
            msg = "原始密码错误";
        }
        attributes.addFlashAttribute("ms", msg);
        attributes.addFlashAttribute("icon", icon);
        return "redirect:/admin/user/password?id="+new Integer(id);
    }
}
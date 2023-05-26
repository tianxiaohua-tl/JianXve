package edu.uzz.springboot.controller.index;

import edu.uzz.springboot.domain.*;
import edu.uzz.springboot.mapper.index.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    private String index = "index/";
    @GetMapping("/")
    public  String index1(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        List<Course> list = courseMapper.selectBySpecialCourse();
        session.setAttribute("speciallist", list);
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            islogin=1;
            List<Course> cartlist =cartMapper.selectByUserISBN2(id);
            List<Course> cartlist1 =cartMapper.selectByUserISBN(id);
            Integer sum= cartlist .stream().collect(Collectors.summingInt(Course::getPrice));
            int num=cartlist.size();
            session.setAttribute("cartnum", num);
            session.setAttribute("cartlist", cartlist1);
            session.setAttribute("cartsum", sum);
            model.addAttribute("cartlist", cartlist1);
            model.addAttribute("cartsum", sum);
            model.addAttribute("cartnum", num);
            model.addAttribute("username", username);
            model.addAttribute("id", id);
            model.addAttribute("list", list);
            model.addAttribute("islogin", islogin);

            return index + "index";
        }
        model.addAttribute("list", list);
        model.addAttribute("islogin", islogin);
        return index + "index";
    }

    @GetMapping("/index")
    public  String index(Model model,HttpServletRequest request){


        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        List<Course> list = courseMapper.selectBySpecialCourse();
        session.setAttribute("speciallist", list);
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            islogin=1;
            List<Course> cartlist =cartMapper.selectByUserISBN2(id);
            List<Course> cartlist1 =cartMapper.selectByUserISBN(id);
            Integer sum= cartlist .stream().collect(Collectors.summingInt(Course::getPrice));
            int num=cartlist.size();
            session.setAttribute("cartnum", num);
            session.setAttribute("cartlist", cartlist1);
            session.setAttribute("cartsum", sum);
            model.addAttribute("cartlist", cartlist1);
            model.addAttribute("cartsum", sum);
            model.addAttribute("cartnum", num);
            model.addAttribute("username", username);
            model.addAttribute("id", id);
            model.addAttribute("list", list);
            model.addAttribute("islogin", islogin);

            return index + "index";
        }
        model.addAttribute("list", list);
        model.addAttribute("islogin", islogin);
        return index + "index";
    }

    @PostMapping("/login1")
    public String dologin(String telephone,String password,Model model, HttpServletResponse response,HttpServletRequest
            request,RedirectAttributes attributes) throws IOException {
        User user = userMapper.selectByUserTel(telephone);
        int islogin = 0;
        String msg = "登陆成功";
        String inputvalcode=request.getParameter("verifyCode");//取出输入的验证码
        HttpSession session=request.getSession();
        String valcode=(String)session.getAttribute("verifyCode");//取出生成的验证码
        if(inputvalcode.equals(valcode)) {
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    islogin = 1;
                    Cookie cookiename = new Cookie("username", user.getUsername());
                    Cookie cookiepass = new Cookie("userpass", user.getPassword());
                    cookiename.setMaxAge(24 * 60 * 60);
                    cookiepass.setMaxAge(24 * 60 * 60);
                    response.addCookie(cookiename);
                    response.addCookie(cookiepass);
//                    HttpSession session=request.getSession();
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("id", user.getId());
                    response.sendRedirect("/index");//重定向
                } else {
                    msg = "密码错误";
                    attributes.addFlashAttribute("ms",msg);
                    return "redirect:/index";
                }

            } else {
                msg = "此用户名未注册，请注册...";
                attributes.addFlashAttribute("ms",msg);
                return "redirect:/index";
            }
        }else{
            msg="验证码错误";
            attributes.addFlashAttribute("ms",msg);
            return "redirect:/index";
        }
        model.addAttribute("ms",msg);
        return index+"index";
    }

    @PostMapping("/reg")
    public String doRegister(String telephone, String email, int sex, String password, String repass, String
            username, RedirectAttributes attributes, HttpServletRequest request) {
        int isRegister = 0;
        String msg = "注册成功,请登录";
        int pass = 0;
        int role = 0;
        if (!password.equals(repass)) {
            pass = 1;
            msg = "两次密码输入不一致";
        }
        String codeId = (String) request.getSession().getAttribute("code");
        String emailcode=request.getParameter("emailcode");//取出输入的验证码

        if(emailcode.equals(codeId)) {
            if (pass != 1) {
                User user = userMapper.selectByUserTel(telephone);
                if (user == null) {
                    Date date = new Date();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sd.format(date);
                    isRegister = userMapper.insertUser(username, email, telephone, password, time, role,sex);
                }
                if (user != null && isRegister == 0) {
                    msg = "此手机号已被注册,请登录或重新注册";
                }
            }
        }else{
            msg="验证码不准确";

        }
        attributes.addFlashAttribute("ms",msg);
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        if(username!=null&&!username.equals("")) {
            session.removeAttribute("username");
            session.removeAttribute("id");
        }
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/detail")
    public String detail(Model model,HttpServletRequest request,int ISBN,@RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum,@RequestParam(value = "a",required = false,defaultValue ="1")int a){
        HttpSession session=request.getSession();int iscomment=0;
        String username=(String)session.getAttribute("username");
        String iscomment1=(String) session.getAttribute("iscomment");
        List<Comment> gradelist=courseMapper.selectGrade(ISBN);
        Double sum= gradelist .stream().collect(Collectors.summingDouble(Comment::getGrade));
        long aa=Math.round(sum/gradelist.size());
        int Gradeavg= (int) aa;
        if(iscomment1!=null&&!iscomment1.equals("")||pageNum>1||a==2||pageNum==0){
            iscomment=1;
        }
        int islogin=0;int commentnum=0;int last = 0;
        Course course =courseMapper.selectByISBN(ISBN);
        List<Comment> commentlist=courseMapper.selectByCourseISBN(ISBN);
        List<Course> list = courseMapper.selectBySpecialCourse();
        if(commentlist.size()!=0){
            commentnum=1;
            int pagesize=4;//每页显示的书的个数
            int totalnum=commentlist.size();//书的总数量
            int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
            if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
                pageNum=totalpage;
            }
            int begin=(pageNum-1)*pagesize;
            int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
            if(pageNum==totalpage){last=1;}
            commentlist=commentlist.subList(begin,end);
        }
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            islogin=1;
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("course",course);
            model.addAttribute("username", username);
            model.addAttribute("id", id);
            model.addAttribute("Gradeavg", Gradeavg);
            model.addAttribute("commentlist", commentlist);
            model.addAttribute("commentnum", commentnum);
            model.addAttribute("last", last);
            model.addAttribute("iscomment", iscomment);
            model.addAttribute("list", list);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("islogin", islogin);
            return index + "detail";
        }
        model.addAttribute("list", list);
        model.addAttribute("commentlist", commentlist);
        model.addAttribute("course",course);
        model.addAttribute("islogin", islogin);
        model.addAttribute("last", last);
        model.addAttribute("commentnum", commentnum);
        model.addAttribute("iscomment", iscomment);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("Gradeavg", Gradeavg);
        return index + "detail";
    }

    @PostMapping("/docomment")
    public String DoComment(HttpServletRequest request,int ISBN,float star,String comment, RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            List<order> orderlist=orderMapper.selectfororderid(id,ISBN);
            if(orderlist.size()!=0){
                User user=userMapper.selectByUserId(id);
                Date date=new Date();
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sd.format(date);
                if(comment!=null&&!comment.equals("")){
                int isaddcomment=courseMapper.addcomment(id,ISBN,star,comment,time);
                if(isaddcomment==1){
                    attributes.addFlashAttribute("ms","评论成功！");
                    session.setAttribute("iscomment","1");
                    return "redirect:/detail?ISBN="+ISBN+"#tab-3";
                }else {
                    attributes.addFlashAttribute("ms","评论失败！请重新评论");
                    session.setAttribute("iscomment","1");
                    return "redirect:/detail?ISBN="+ISBN+"#tab-3";
                }
                }else {
                    attributes.addFlashAttribute("ms","评论为空！请重新评论");
                    session.setAttribute("iscomment","1");
                    return "redirect:/detail?ISBN="+ISBN+"#tab-3";
                }
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+ISBN;
            }

        }else {
            attributes.addFlashAttribute("ms","请登录进行评论！");
            return "redirect:/detail?ISBN="+ISBN;
        }
    }

    @GetMapping("/AllCourse")
    public String AllCourse(Model model, HttpServletRequest request, @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum, @RequestParam(value =
            "num",required = false,defaultValue ="8")String num,@RequestParam(value ="pattern",required = false,defaultValue ="0")String pattern,@RequestParam(value =
            "fenlei",required = false,defaultValue ="0")String fenlei,@RequestParam(value =
            "Price",required = false,defaultValue ="0")int Price){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        List<Course> searchlist= (List<Course>) session.getAttribute("searchlist");
        List<Category> categoryList =teacherMapper.selectAllCategory();
        int islogin=0;int search=0;
        //排列方式
        int pattern1=Integer.parseInt(pattern);
        int fenlei1=Integer.parseInt(fenlei);
        List<Course> List = null;
        int ss=1;
        //fenlei 0是默认 1是计算机 2是文学 3是人工智能 4是外国语
        if(searchlist==null||searchlist.size()==0){
        if(fenlei1!=0){
            if(pattern1==0){List = courseMapper.selectByCategoryId(fenlei1);}
            if(pattern1==1){List = courseMapper.selectByCategoryIdNameAsc(fenlei1);}
            if(pattern1==2){List = courseMapper.selectByCategoryIdNameDesc(fenlei1);}
            if(pattern1==3){List = courseMapper.selectByCategoryIdPriceAsc(fenlei1);}
            if(pattern1==4){List = courseMapper.selectByCategoryIdPriceDesc(fenlei1);}
        }else {
            if(Price!=0){
                ss=0;
                if(Price==1){List = courseMapper.selectByAllPrice0();}
                if(Price==2){List = courseMapper.selectByAllSpecial();}
                if(Price==3){List = courseMapper.selectByAllNew();}
                if(Price==4){List = courseMapper.selectByAllPrice100();}
                if(Price==5){List = courseMapper.selectByAllPrice300();}
                if(Price==6){List = courseMapper.selectByAllPrice300d();}
            }else {
                //pattern 0是全部 1是按照名字顺序 2是按照名字倒序 3是按照价格顺序 4是按照价格倒序
                if (pattern1 == 0) {List = courseMapper.selectAllCourse();}
                if (pattern1 == 1) {List = courseMapper.selectByNameAsc();}
                if (pattern1 == 2) {List = courseMapper.selectByNameDesc();}
                if (pattern1 == 3) {List = courseMapper.selectByPriceAsc();}
                if (pattern1 == 4) {List = courseMapper.selectByPriceDesc();}
            }
        }}else {
            search=1;
            List=searchlist;
        }
        int pagesize=Integer.parseInt(num);//每页显示的书的个数
        int totalnum=List.size();//书的总数量
        int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
        if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
            pageNum=totalpage;
        }
        int begin=(pageNum-1)*pagesize;
        int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
        List=List.subList(begin,end);
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            islogin=1;
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("Price", Price);
            model.addAttribute("ss",ss);
            model.addAttribute("fenlei1",fenlei1);
            model.addAttribute("pagesize",pagesize);
            model.addAttribute("pattern",pattern);
            model.addAttribute("pageNum",pageNum);
            model.addAttribute("islogin", islogin);
            model.addAttribute("list", List);
            model.addAttribute("search",search);
            session.removeAttribute("searchlist");
            return index + "list";
        }
        model.addAttribute("Price", Price);
        model.addAttribute("ss",ss);
        model.addAttribute("fenlei1",fenlei1);
        model.addAttribute("pagesize",pagesize);
        model.addAttribute("pattern",pattern);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("islogin", islogin);
        model.addAttribute("list", List);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("search",search);
        session.removeAttribute("searchlist");
        return index + "list";
    }
    @GetMapping("/docart")
    public String doCart(RedirectAttributes attributes,HttpServletRequest request,int ISBN){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int isInsertCart = 0;
        List<Course> list = courseMapper.selectBySpecialCourse();
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            List<Cart> list1=cartMapper.selectByUCCISBN(id,ISBN);
            if(list1.size()==0){
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            isInsertCart=cartMapper.addCart(id,ISBN,time);
            islogin=1;
            if (isInsertCart ==0) {
                attributes.addFlashAttribute("ms", "添加失败！");
                return "redirect:/detail?ISBN="+ ISBN;
            }}else{
                attributes.addFlashAttribute("ms", "已添加，请勿重复添加");
                return "redirect:/detail?ISBN="+ ISBN;
            }
            attributes.addFlashAttribute("islogin", islogin);
            attributes.addFlashAttribute("ms", "添加成功！");
            return "redirect:/cart";
        }else {
            attributes.addFlashAttribute("ms", "请登录进行此操作！");
            return "redirect:/detail?ISBN="+ ISBN;
        }
    }

    @GetMapping("/cart")
    public String Cart(RedirectAttributes attributes,Model model,HttpServletRequest request,
                       @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        List<Course> speciallist= (List<Course>) session.getAttribute("speciallist");
        int islogin=0;int cart=1;int last=0;
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            islogin=1;
            List<Course> cartlist =cartMapper.selectByUserISBN2(id);
            List<Course> cartlist1 =cartMapper.selectByUserISBN(id);
            Integer sum= cartlist .stream().collect(Collectors.summingInt(Course::getPrice));
            int num=cartlist.size();
            session.setAttribute("cartnum", num);
            session.setAttribute("cartlist", cartlist1);
            session.setAttribute("cartsum", sum);
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            islogin=1;
            List<Course> list =cartMapper.selectByUserISBN2(id);
            Integer sum1= list .stream().collect(Collectors.summingInt(Course::getPrice));
            if(list.size()==0){
                cart=0;
                model.addAttribute("cartnum",cartnum);
                model.addAttribute("cartsum",cartsum);
                model.addAttribute("cartlist",cartlist);
                model.addAttribute("sum",sum1);
                model.addAttribute("cart",cart);
                model.addAttribute("last", last);
                model.addAttribute("pageNum", pageNum);
                model.addAttribute("list", list);
                model.addAttribute("list1", speciallist);
                model.addAttribute("cart", cart);
                model.addAttribute("islogin", islogin);
                return index + "cart";
            }else{
                int pagesize=4;//每页显示的书的个数
                int totalnum=list.size();//书的总数量
                int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
                if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
                    pageNum=totalpage;
                }
                int begin=(pageNum-1)*pagesize;
                int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
                if(pageNum==totalpage){last=1;}
                list=list.subList(begin,end);}
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("sum",sum1);
            model.addAttribute("cart",cart);
            model.addAttribute("last", last);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("list", list);
            model.addAttribute("list1", speciallist);
            model.addAttribute("islogin", islogin);
            return index + "cart";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }
    @GetMapping("/cartout")
    public String cartout(HttpServletRequest request,int ISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        int UserISBN= (int) session.getAttribute("id");
        Cart cart=cartMapper.selectforcartid(UserISBN,ISBN);
        int isDelCart=cartMapper.deletebycartid(cart.getId());
        if(isDelCart==1){
            attributes.addFlashAttribute("ms", "移除成功！");
            return "redirect:/cart";
        }else {
            attributes.addFlashAttribute("ms", "移除失败！");
            return "redirect:/cart";
        }
    }

    @GetMapping("/dowish")
    public String doWish(RedirectAttributes attributes,HttpServletRequest request,int ISBN){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int isInsertWish = 0;
        if(username!=null&&!username.equals("")){
            int id= (int) session.getAttribute("id");
            List<Wish> list1=cartMapper.selectByUCWISBN(id,ISBN);
            if(list1.size()==0){
            Date date=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sd.format(date);
            isInsertWish=cartMapper.addWish(id,ISBN,time);
            islogin=1;
            if (isInsertWish ==0) {
                attributes.addFlashAttribute("ms", "添加失败！请重试");
                return "redirect:/detail?ISBN="+ ISBN;
            }}else{
                attributes.addFlashAttribute("ms", "已添加，请勿重复添加");
                return "redirect:/detail?ISBN="+ ISBN;
            }
            attributes.addFlashAttribute("islogin", islogin);
            attributes.addFlashAttribute("ms", "添加成功！");
            return "redirect:/wish";
        }else {
            attributes.addFlashAttribute("ms", "请登录进行此操作！");
            return "redirect:/detail?ISBN="+ ISBN;
        }
    }

    @GetMapping("/wish")
    public String Wish(RedirectAttributes attributes,Model model,HttpServletRequest request,
                       @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int wish=1;int last=0;int totalnum;
        if(username!=null&&!username.equals("")){
            int UserISBN= (int) session.getAttribute("id");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            islogin=1;
            List<Course> list =cartMapper.selectByUserISBN1(UserISBN);
            if(list.size()==0){
                wish=0;
                model.addAttribute("wish", wish);
                model.addAttribute("cartnum",cartnum);
                model.addAttribute("cartsum",cartsum);
                model.addAttribute("cartlist",cartlist);
                model.addAttribute("islogin", islogin);
                return index + "wish";
            }else{
            int pagesize=4;//每页显示的书的个数
            totalnum=list.size();//书的总数量
            int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
            if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
                pageNum=totalpage;
            }
            int begin=(pageNum-1)*pagesize;
            int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
            if(pageNum==totalpage){last=1;}
            list=list.subList(begin,end);}
            model.addAttribute("wishnum",totalnum);
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("wish", wish);
            model.addAttribute("last", last);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("list", list);
            model.addAttribute("islogin", islogin);
            return index + "wish";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @GetMapping("/wishout")
    public String wishout(HttpServletRequest request,int ISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        int UserISBN= (int) session.getAttribute("id");
        Wish wish=cartMapper.selectforwishid(UserISBN,ISBN);
        int isDelWish=cartMapper.deletebywishid(wish.getId());
        if(isDelWish==1){
            attributes.addFlashAttribute("ms", "移除成功！");
            return "redirect:/wish";
        }else {
            attributes.addFlashAttribute("ms", "移除失败！");
            return "redirect:/wish";
        }
    }

    @PostMapping("/dosearch")
    public String Search(RedirectAttributes attributes,HttpServletRequest request,Model model,String CourseName){
        HttpSession session=request.getSession();
        int islogin=0;int search=0;
        List<Course> List = null;
        if(CourseName!=null&&!CourseName.equals("")){
             List=courseMapper.selectByCourseName(CourseName);
             if(List.size()==0){
                 attributes.addFlashAttribute("ms", "未找到类似课程！");
                 return "redirect:/AllCourse"; }
        }else {return "redirect:/AllCourse";}
        session.setAttribute("searchlist", List);
        return "redirect:/AllCourse";
    }

    @GetMapping("/check")
    public String Check(Model model,HttpServletRequest request,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int complete=0;
        if(username!=null&&!username.equals("")){
            int UserISBN= (int) session.getAttribute("id");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> cartlist1=cartMapper.selectByUserISBN2(UserISBN);
            session.setAttribute("lastcartlist", cartlist1);
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            islogin=1;
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("islogin", islogin);
            model.addAttribute("user",user);
            model.addAttribute("cartlist1",cartlist1);
            return index + "check";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("/docheck")
    public String DoCheckout(Model model,HttpServletRequest request,RedirectAttributes attributes,String note,String agree){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int complete=0;int n = 0;Boolean isaddorder = null;
        if(username!=null&&!username.equals("")){
            int UserISBN= (int) session.getAttribute("id");
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> cartlist1=cartMapper.selectByUserISBN2(UserISBN);
            islogin=1;
            if(agree!=null&&agree.equals("on")){
                Date date=new Date();
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String Entrytime=sd.format(date);
                for (int i = 0; i <cartlist1.size(); i++) { Course course=cartlist1.get(i);
                    if(i==0){String CourseISBN=Integer.toString(course.getISBN());
                    session.setAttribute("CourseISBN",CourseISBN);}List<order>
                            isexit=orderMapper.selectfororderid(UserISBN,course.getISBN());
                    if(isexit.size()>=1){
                        attributes.addFlashAttribute("ms", "已购买该课程，请勿重复购买!");
                        return "redirect:/check";
                    }else {
                        if(note.equals("")){
                            isaddorder=orderMapper.addorder(user.getId(),user.getTelephone(),course.getISBN(),course.getPrice(),Entrytime);
                        }else {
                            isaddorder=orderMapper.addorder1(user.getId(),user.getTelephone(),course.getISBN(),course.getPrice(),note,Entrytime);
                        }
                        Boolean isInsertStudySpace=orderMapper.addstudyspace(user.getId(),user.getTelephone(),course.getISBN(),Entrytime);
                        if(isaddorder){
                            n=n+1;
                        }
                    }
                }
                if(n==cartlist1.size()){
                    Boolean isDelCart=orderMapper.deletecart();
                    if(isDelCart){
                        attributes.addFlashAttribute("ms", "结算成功");
                        return "redirect:/checkout";
                    }else {
                        attributes.addFlashAttribute("ms", "购物车失败！请重新结算");
                        return "redirect:/check";
                    }
                }else {
                    attributes.addFlashAttribute("ms", "结算失败！请重新结算");
                    return "redirect:/check";
                }
            }else{
                attributes.addFlashAttribute("ms", "未同意相关政策！");
                return "redirect:/check";
            }
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            return "redirect:/index";
        }
    }
    @GetMapping("/checkout")
    public String Checkout(Model model,HttpServletRequest request,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")){
            int UserISBN= (int) session.getAttribute("id");
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> lastcartlist= (List<Course>) session.getAttribute("lastcartlist");
            List<Course> cartlistnew=cartMapper.selectByUserISBN(UserISBN);
            String CourseId= (String) session.getAttribute("CourseISBN");
            if(CourseId!=null&&!CourseId.equals("")){
                int CourseISBN=Integer.parseInt(CourseId);
                session.removeAttribute("CourseISBN");
                order order = orderMapper.selectorder(UserISBN,CourseISBN);
                Integer lastsum= lastcartlist .stream().collect(Collectors.summingInt(Course::getPrice));
                Integer sum= cartlistnew .stream().collect(Collectors.summingInt(Course::getPrice));
                int lastnum=lastcartlist.size();
                int num=cartlistnew.size();
                session.setAttribute("cartnum", num);
                session.setAttribute("cartlist", cartlistnew);
                session.setAttribute("cartsum", sum);
                islogin=1;
                model.addAttribute("lastsum",lastsum);
                model.addAttribute("lastnum",lastnum);
                model.addAttribute("cartnum",num);
                model.addAttribute("cartsum",sum);
                model.addAttribute("cartlist",cartlistnew);
                model.addAttribute("islogin", islogin);
                model.addAttribute("user",user);
                model.addAttribute("order",order);
                model.addAttribute("cartlist1",lastcartlist);
                session.removeAttribute("lastcartlist");
                return index + "checkout";
            }else {
                attributes.addFlashAttribute("ms", "结算完成！");
                return "redirect:/cart";
            }
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            return "redirect:/index";
        }
    }
    @GetMapping("/orderhistory")
    public String OrderHistory(Model model,HttpServletRequest request,RedirectAttributes attributes,
                               @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int ordernum;int totalnum;int last = 0;
        if(username!=null&&!username.equals("")){
            islogin=1;
            int UserISBN= (int) session.getAttribute("id");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
//            List<Course>
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            List<Course> orderlist=orderMapper.selectByUserISBN(UserISBN);
            if(orderlist.size()==0){
                ordernum=0;
                model.addAttribute("cartsum",cartsum);
                model.addAttribute("cartnum",cartnum);
                model.addAttribute("cartlist",cartlist);
                model.addAttribute("islogin", islogin);
                model.addAttribute("ordernum", ordernum);
                return index + "orderhistory";
            }else{
                ordernum=1;
                int pagesize=4;//每页显示的书的个数
                totalnum=orderlist.size();//书的总数量
                int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
                if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
                    pageNum=totalpage;
                }
                int begin=(pageNum-1)*pagesize;
                int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
                if(pageNum==totalpage){last=1;}
                orderlist=orderlist.subList(begin,end);}
            model.addAttribute("ordernum", ordernum);
            model.addAttribute("totalnum", totalnum);
            model.addAttribute("last", last);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("orderlist",orderlist);
            model.addAttribute("islogin", islogin);
            return index + "orderhistory";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @GetMapping("/comment")
    public String Comment(Model model,HttpServletRequest request,RedirectAttributes attributes,
                               @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int commentnum;int totalnum;int last = 0;
        if(username!=null&&!username.equals("")){
            islogin=1;
            int UserISBN= (int) session.getAttribute("id");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
//            List<Course>
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            List<Comment> commentlist=courseMapper.selectComment(UserISBN);
            if(commentlist.size()==0){
                commentnum=0;
                model.addAttribute("cartnum",cartnum);
                model.addAttribute("cartsum",cartsum);
                model.addAttribute("cartlist",cartlist);
                model.addAttribute("islogin", islogin);
                model.addAttribute("commentnum", commentnum);
                return index + "comment";
            }else{
                commentnum=1;
                int pagesize=4;//每页显示的书的个数
                totalnum=commentlist.size();//书的总数量
                int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
                if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
                    pageNum=totalpage;
                }
                int begin=(pageNum-1)*pagesize;
                int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
                if(pageNum==totalpage){last=1;}
                commentlist=commentlist.subList(begin,end);}
            model.addAttribute("commentnum", commentnum);
            model.addAttribute("totalnum", totalnum);
            model.addAttribute("last", last);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("commentlist",commentlist);
            model.addAttribute("islogin", islogin);
            return index + "comment";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }
    @GetMapping("/delcomment")
    public String Delcomment(int CommentId,RedirectAttributes attributes){
        int isDelComment=courseMapper.DelComment(CommentId);
        if(isDelComment==1){
            attributes.addFlashAttribute("ms", "移除成功！");
            return "redirect:/comment";
        }else {
            attributes.addFlashAttribute("ms", "移除失败！");
            return "redirect:/comment";
        }
    }

    @GetMapping("/studyspace")
    public String Studyspace(Model model, HttpServletRequest request, @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum, @RequestParam(value =
            "num",required = false,defaultValue ="8")String num,@RequestParam(value ="pattern",required = false,defaultValue ="0")String pattern,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int last=0;
        if(username!=null&&!username.equals("")) {
            //排列方式
            islogin = 1;
            int UserISBN = (int) session.getAttribute("id");
            List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
            int cartnum = (int) session.getAttribute("cartnum");
            int cartsum = (int) session.getAttribute("cartsum");
            int pattern1=Integer.parseInt(pattern);
            List<Course> List = null;
            if (pattern1 == 0) {List=courseMapper.selectStudySpace(UserISBN);}
            if (pattern1 == 1) {List = courseMapper.selectStudySpaceByNameASC(UserISBN);}
            if (pattern1 == 2) {List = courseMapper.selectStudySpaceByNameDESC(UserISBN);}
            if(List.size()==0){
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("ishave", 0);
                model.addAttribute("islogin", islogin);
                return index + "studyspace";
            }else {
                int pagesize=Integer.parseInt(num);//每页显示的书的个数
                int totalnum=List.size();//书的总数量
                int totalpage=totalnum%pagesize==0?(totalnum/pagesize):(totalnum/pagesize+1);// 总页数
                if(pageNum<1){ pageNum=1; } if(pageNum>totalpage){
                    pageNum=totalpage;
                }
                if(pageNum==totalpage){last=1;}
                int begin=(pageNum-1)*pagesize;
                int end= pagesize*pageNum>totalnum?totalnum:(pageNum*pagesize);
                List=List.subList(begin,end);
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("pagesize", pagesize);
                model.addAttribute("pattern", pattern);
                model.addAttribute("pageNum", pageNum);
                model.addAttribute("last", last);
                model.addAttribute("ishave", 1);
                model.addAttribute("islogin", islogin);
                model.addAttribute("list", List);
                return index + "studyspace";
            }
        }else {
                attributes.addFlashAttribute("ms", "请登录执行此操作！");
                attributes.addFlashAttribute("islogin", islogin);
                return "redirect:/index";
            }
    }

    @GetMapping("/doDeleteStudySpace")
    public String DoDeleteStudySpace( HttpServletRequest request,int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        int UserISBN = (int) session.getAttribute("id");
        int IsDelete=orderMapper.DeleteStudyspace(UserISBN,CourseISBN);
        if(IsDelete!=0){
            attributes.addFlashAttribute("ms", "删除成功！");
            return "redirect:/studyspace";
        }else {
            attributes.addFlashAttribute("ms", "删除失败！");
            return "redirect:/studyspace";
        }
    }
    @GetMapping("/problem")
    public String Problem(Model model,HttpServletRequest request,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")){
            int UserISBN= (int) session.getAttribute("id");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> cartlist1=cartMapper.selectByUserISBN2(UserISBN);
            session.setAttribute("lastcartlist", cartlist1);
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            islogin=1;
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("islogin", islogin);

            return index + "problem";
        }else {
            model.addAttribute("islogin", islogin);
            return index + "problem";
        }
    }

    @GetMapping("/about")
    public String About(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")){
            int UserISBN= (int) session.getAttribute("id");
            int cartnum= (int) session.getAttribute("cartnum");
            int cartsum= (int) session.getAttribute("cartsum");
            User user = userMapper.selectByUserId(UserISBN);
            List<Course> cartlist1=cartMapper.selectByUserISBN2(UserISBN);
            session.setAttribute("lastcartlist", cartlist1);
            List<Course> cartlist= (List<Course>) session.getAttribute("cartlist");
            islogin=1;
            model.addAttribute("cartnum",cartnum);
            model.addAttribute("cartsum",cartsum);
            model.addAttribute("cartlist",cartlist);
            model.addAttribute("islogin", islogin);

            return index + "about";
        }else {
            model.addAttribute("islogin", islogin);
            return index + "about";
        }
    }
}

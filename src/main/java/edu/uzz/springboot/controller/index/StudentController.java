package edu.uzz.springboot.controller.index;

import edu.uzz.springboot.controller.common.StuExamResultService;
import edu.uzz.springboot.domain.*;
import edu.uzz.springboot.mapper.index.CourseMapper;
import edu.uzz.springboot.mapper.index.OrderMapper;
import edu.uzz.springboot.mapper.index.StudySpaceMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudySpaceMapper studySpaceMapper;
    private String student = "student/";

    @GetMapping("/studycourse")
    public String StudyCourse(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, CourseISBN);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);
                //大目录
                List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
                if(list.size()==0){
                    molu=0;
                }
                model.addAttribute("molu", molu);
                model.addAttribute("list", list);
                //小目录
                List<Chapter> list1=studySpaceMapper.selectSmallChapter(CourseISBN);
                model.addAttribute("list1", list1);

                return student+"studycourse";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+CourseISBN;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }

    @GetMapping("/chapter")
    public String Chapter(Model model, HttpServletRequest request, int CourseISBN, int SmallChapterId,
                          @RequestParam(value ="ChapterHao",required = false,defaultValue ="0")int ChapterHao,
                          @RequestParam(value ="work3",required = false,defaultValue ="1")int work3,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, CourseISBN);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);
                //大目录
                List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
                model.addAttribute("list", list);
                //小目录
                List<Chapter> list1;
                if(ChapterHao==0){
                    Chapter chapter=studySpaceMapper.selectBySmallChapter(SmallChapterId);
                    list1=studySpaceMapper.selectByChapterHao1(CourseISBN,chapter.getChapterHao1());
                }else list1=studySpaceMapper.selectByChapterHao1(CourseISBN,ChapterHao);
                model.addAttribute("list1", list1);

                Chapter chapter=studySpaceMapper.selectBySmallChapter(SmallChapterId);
                model.addAttribute("chapter", chapter);
                model.addAttribute("work3", work3);
                session.setAttribute("work1",chapter.getWork1());
                session.setAttribute("work2",chapter.getWork2());
                return student+"chapter";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+CourseISBN;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }
    @GetMapping("/test")
    public String Test(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, CourseISBN);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);

                //小目录
                List<TestResult> list=studySpaceMapper.IsTest(UserISBN,CourseISBN);
                if(list.size()==0){
                    molu=0;
                }
                model.addAttribute("molu", molu);
                model.addAttribute("list", list);
                return student+"test";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+CourseISBN;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }


    @PostMapping("SubTest")
    public String SubTest(@RequestParam(value = "Work1File") MultipartFile Work1File,
                          int CourseISBN,int ChapterId,int SmallChapterId,String Work1Name,
                          RedirectAttributes attributes,HttpServletRequest request) throws FileNotFoundException {
        int isSubTest = 0;
        HttpSession session = request.getSession();
        String UserName = (String) session.getAttribute("username");
// int islogin=0;int molu=1;
        int UserISBN = (int) session.getAttribute("id");
        Test test = studySpaceMapper.selectIsEmpty(CourseISBN, UserISBN, ChapterId, SmallChapterId);
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String Work1Time = sd.format(date);
            if (Work1File.isEmpty()) {
                attributes.addFlashAttribute("ms", "未选择作业文件！");
                return "redirect:/test?CourseISBN=" + CourseISBN;
            } else {
                String Work1FileName = Work1File.getOriginalFilename(); // 文件名
                String suffixName = Work1FileName.substring(Work1FileName.lastIndexOf(".")); // 后缀名
                String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//";
//                        "D://springboot//match//springboot//src//main//resources//static//file//test//"; // 上传后的路径
                Work1FileName = System.currentTimeMillis() + suffixName;//新文件名
                File dest = new File(filePath + Work1FileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    Work1File.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String Work1Filename = Work1FileName;
                isSubTest = studySpaceMapper.InsertTest1(UserISBN,UserName,CourseISBN,ChapterId,SmallChapterId,Work1Name,Work1Filename,Work1Time);
                if(isSubTest!=0){

                    attributes.addFlashAttribute("ms", "提交成功！");
                    return "redirect:/test?CourseISBN=" + CourseISBN;
                }else {
                    attributes.addFlashAttribute("ms", "提交失败！");
                    return "redirect:/test?CourseISBN=" + CourseISBN;
                }
            }
    }
    @PostMapping("SubTest2")
    public String SubTest2(@RequestParam(value = "Work2File") MultipartFile Work2File,
                          int CourseISBN,int ChapterId,int SmallChapterId,String Work2Name,
                           RedirectAttributes attributes,HttpServletRequest request) throws FileNotFoundException {
        int isSubTest = 0;
        HttpSession session = request.getSession();
        String UserName = (String) session.getAttribute("username");
// int islogin=0;int molu=1;
        int UserISBN = (int) session.getAttribute("id");
        Test test = studySpaceMapper.selectIsEmpty(CourseISBN, UserISBN, ChapterId, SmallChapterId);
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String WorkTime = sd.format(date);
            if (Work2File.isEmpty()) {
                attributes.addFlashAttribute("ms", "未选择作业文件！");
                return "redirect:/test?CourseISBN=" + CourseISBN;
            } else {
                String Work2FileName = Work2File.getOriginalFilename(); // 文件名
                String suffixName = Work2FileName.substring(Work2FileName.lastIndexOf(".")); // 后缀名
                String file2Path = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//";
//                        "D://springboot//match//springboot//src//main//resources//static//file//test//"; // 上传后的路径
                Work2FileName = System.currentTimeMillis() + suffixName;//新文件名
                File dest = new File(file2Path + Work2FileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    Work2File.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String Work2Filename = Work2FileName;
                isSubTest = studySpaceMapper.InsertTest2(Work2Name,Work2Filename, WorkTime,UserISBN,CourseISBN, ChapterId, SmallChapterId);
                if(isSubTest!=0){
                    attributes.addFlashAttribute("ms", "提交成功！");
                    return "redirect:/test?CourseISBN=" + CourseISBN;
                }else {
                    attributes.addFlashAttribute("ms", "提交失败！请优先提交作业1");
                    return "redirect:/test?CourseISBN=" + CourseISBN;
                }
            }
    }

    @GetMapping("/exam")
    public String Exam(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, CourseISBN);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);
                //大目录
//                List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
//                if(list.size()==0){
//                    molu=0;
//                }
//                model.addAttribute("molu", molu);
//                model.addAttribute("list", list);
//                //小目录
//                List<Chapter> list1=studySpaceMapper.selectSmallChapter(CourseISBN);
//                model.addAttribute("list1", list1);

                List<ExamResult> examResultList=studySpaceMapper.SelectExam(UserISBN,CourseISBN);
                if(examResultList.size()==0){
                    molu=0;
                }
                model.addAttribute("molu", molu);
                model.addAttribute("examResultList", examResultList);
                model.addAttribute("UserISBN",UserISBN);

                return student+"exam";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+CourseISBN;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException {
        //PDF文件地址
        HttpSession session=request.getSession();
        String work1=(String)session.getAttribute("work1");
        String filepath=work1;
        String path = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//"+filepath ;
//        String path="D:\\Springboot\\match\\springboot\\src\\main\\resources\\static\\file\\test\\"+filepath;
        File file = new File(path);
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input=null;
            try {
                input= new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            }finally{
                try {
                    if(input!=null){
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/preview1", method = RequestMethod.GET)
    public void pdfStreamHandler1(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException {
        //PDF文件地址
        HttpSession session=request.getSession();
        String work2=(String)session.getAttribute("work2");
        String filepath=work2;
        String path = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//"+filepath ;
//        String path="D:\\Springboot\\match\\springboot\\src\\main\\resources\\static\\file\\test\\"+filepath;
        File file = new File(path);
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input=null;
            try {
                input= new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            }finally{
                try {
                    if(input!=null){
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/fileDown")
    public String filedown(HttpServletResponse response,String filename) throws UnsupportedEncodingException {
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            String path = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//";
            File file = new File(path+filename);
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @GetMapping("/kaoshi")
    public String Kaoshi(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, CourseISBN);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);



                return student+"kaoshi";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+CourseISBN;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }


    @GetMapping("/toExamPageList")
    public String toExamPage(int chapterid,int courseisbn, Model model, HttpSession session) {
        //存储选择的考试课程id号
        session.setAttribute("taotiid",chapterid);
//        List<Map<String,Object>> questionsList  = exampleQuestionsService.findExamQuestions(3);
        //返回单选题目
        List<Map<String,Object>> questionsList  = studySpaceMapper.findExamRadioQuestions(chapterid,courseisbn);
        model.addAttribute("radioQuestionsList", questionsList);
        //返回多选题目
        List<Map<String,Object>> questionsList2  = studySpaceMapper.findExamCheckboxQuestions(chapterid,courseisbn);
        model.addAttribute("checkboxQuestionsList", questionsList2);
        return "student/exampage";
    }


    @GetMapping("/exampage")
    public String ExamPage(Model model, HttpServletRequest request,RedirectAttributes attributes,int courseisbn,int chapterid){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, courseisbn);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(courseisbn);
                model.addAttribute("course", course);

                //存储选择的考试课程id号
                session.setAttribute("taotiid",chapterid);
//        List<Map<String,Object>> questionsList  = exampleQuestionsService.findExamQuestions(3);
                //返回单选题目
                List<Map<String,Object>> questionsList  = studySpaceMapper.findExamRadioQuestions(chapterid,courseisbn);
                model.addAttribute("radioQuestionsList", questionsList);
                //返回多选题目
                List<Map<String,Object>> questionsList2  = studySpaceMapper.findExamCheckboxQuestions(chapterid,courseisbn);
                model.addAttribute("checkboxQuestionsList", questionsList2);

                return student+"exampage";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+courseisbn;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @RequestMapping("/postExam")
    public String postExam(@RequestParam Map<String,String> map, Model model, HttpSession session,RedirectAttributes attributes){
        String username= (String) session.getAttribute("username");
        int UserISBN = (int) session.getAttribute("id");
        int chapterid = 0;int courseisbn=0;
        //String abc=null;
        Question question=new Question();
        Set<String> keySet=map.keySet();
        Iterator<String> it=keySet.iterator();
        Integer radioscores=0;     //单选分
        Integer checkscores=0;    //多选分
        Integer total=0;         //总得分
        while (it.hasNext()){
            String key=it.next();
            String value=map.get(key);
            question = studySpaceMapper.findExamAnswerById(key);
            chapterid=question.getChapterid();
            courseisbn=question.getCourseisbn();
            if(question.getType().equals("单选")) {

                if(question.getAnswer().equals(value)) {
                    radioscores+=10;
                }
            }
            if(question.getType().equals("多选")) {

                if(question.getAnswer().equals(value)) {
                    checkscores+=20;
                }
            }
        }

        total=radioscores+checkscores;
        //存储数据到数据库

        ExamResult examResult=new ExamResult();
        examResult.setRadioscores(radioscores);
        examResult.setCheckscores(checkscores);
        examResult.setTotal(total);
        examResult.setCreatetime(new Timestamp(new Date().getTime()));
        examResult.setChapterid(chapterid);
        examResult.setCourseisbn(courseisbn);
        examResult.setUserisbn(UserISBN);

        ExamResult IsExam=studySpaceMapper.IsExam(UserISBN,courseisbn,chapterid);
        if(IsExam.getId()!=0){
            attributes.addFlashAttribute("ms", "已考完，请勿重复考试！");
            return "redirect:/exam?CourseISBN="+courseisbn;
        }
        else {
        int addStuExamResult= studySpaceMapper.addStuExamResult(examResult);

        //返回参数给前端显示
        User user=studySpaceMapper.SelectUserBYid(UserISBN);
        Course course =studySpaceMapper.SelectCourseByisbn(courseisbn);
        model.addAttribute("course",course);
        int stuid1=user.getId();
        String stuid=Integer.toString(stuid1);
        String stuname=user.getUsername();
        String lessonname=course.getCourseName();
        Map<String, Integer> resultmap1 = new HashMap<String, Integer>();
        resultmap1.put("rdioscores",radioscores);
        resultmap1.put("checkscores", checkscores);
        resultmap1.put("total", total);
        model.addAttribute("resultmap1", resultmap1);

        Map<String, String> resultmap2 = new HashMap<String,String>();
        resultmap2.put("lessonname",lessonname);
        resultmap2.put("stuname", stuname);

        resultmap2.put("stuid", stuid);
        model.addAttribute("resultmap2", resultmap2);
        return "student/examresult";
        }
    }

    @GetMapping("/lookanswer")
    public String LookAnswer(Model model, HttpServletRequest request,RedirectAttributes attributes,int courseisbn,int chapterid){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            ExamResult IsExam=studySpaceMapper.IsExam(UserISBN,courseisbn,chapterid);
            if(IsExam.getId()==0){
                attributes.addFlashAttribute("ms", "您未考试该章节，请考试！");
                return "redirect:/exam?CourseISBN="+courseisbn;
            }
            else {
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, courseisbn);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(courseisbn);
                model.addAttribute("course", course);

                //返回单选题目
                List<Map<String,Object>> questionsList  = studySpaceMapper.findExamRadioQuestions(chapterid,courseisbn);
                model.addAttribute("radioQuestionsList", questionsList);
                //返回多选题目
                List<Map<String,Object>> questionsList2  = studySpaceMapper.findExamCheckboxQuestions(chapterid,courseisbn);
                model.addAttribute("checkboxQuestionsList", questionsList2);
                }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+courseisbn;
            }
                return student+"lookanswer";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @GetMapping("/myexam")
    public String Myexam(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            List<order> orderlist = orderMapper.selectfororderid(UserISBN, CourseISBN);
            if (orderlist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);

                List<ExamResult> examResultList=studySpaceMapper.SelectExam(UserISBN,CourseISBN);
//                //大目录
//                List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
//                if(list.size()==0){
//                    molu=0;
//                }
//                model.addAttribute("molu", molu);
//                model.addAttribute("list", list);
//                //小目录
//                List<Chapter> list1=studySpaceMapper.selectSmallChapter(CourseISBN);
                model.addAttribute("examResultList",examResultList);


                return student+"myexam";
            }else {
                attributes.addFlashAttribute("ms","您未学习该课程！请购买学习");
                return "redirect:/detail?ISBN="+CourseISBN;
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }

    @Autowired
    private StuExamResultService stuExamResultService;
    @GetMapping("/saveExcel")
    public void saveExcel(HttpSession session, HttpServletResponse response,int CourseISBN)throws Exception{
        String username= (String) session.getAttribute("username");
        int UserISBN = (int) session.getAttribute("id");
        //返回用户查询所有考试记录
        List<Map<String, Object>> listMap =studySpaceMapper.SelectExam1(UserISBN,CourseISBN);
        Workbook workbook=new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("您的考试成绩单");
        Row row=sheet.createRow(0);
        Cell cell00=row.createCell(0);
        cell00.setCellValue("考试章节");
        Cell cell01=row.createCell(1);
        cell01.setCellValue("单选题成绩");
        Cell cell02=row.createCell(2);
        cell02.setCellValue("多选题成绩");
        Cell cell03=row.createCell(3);
        cell03.setCellValue("总成绩");
        Cell cell04=row.createCell(4);
        cell04.setCellValue("考试时间");
        for(int i = 0;i<listMap.size();i++){
            row=sheet.createRow(i+1);
            Cell cell0=row.createCell(0);
            cell0.setCellValue((String)listMap.get(i).get("chapter"));
            Cell cell1=row.createCell(1);
            cell1.setCellValue((Integer)listMap.get(i).get("radioscores"));
            Cell cell2=row.createCell(2);
            cell2.setCellValue((Integer)listMap.get(i).get("checkscores"));
            Cell cell3=row.createCell(3);
            cell3.setCellValue((Integer)listMap.get(i).get("total"));
            Cell cell4=row.createCell(4);
            cell4.setCellValue((Timestamp)listMap.get(i).get("createtime"));
            //获取单元格样式
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle cellStyle = workbook.createCellStyle();
            //定义单元格日期样式
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
            //设置单元格样式
            cell4.setCellStyle(cellStyle);
        }
        stuExamResultService.export(response, workbook, "课程测验结果.xls");
    }


}

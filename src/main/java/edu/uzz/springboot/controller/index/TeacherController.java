package edu.uzz.springboot.controller.index;

import edu.uzz.springboot.controller.common.StuExamResultService;
import edu.uzz.springboot.domain.*;
import edu.uzz.springboot.mapper.index.CourseMapper;
import edu.uzz.springboot.mapper.index.StudySpaceMapper;
import edu.uzz.springboot.mapper.index.TeacherMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.omg.CosNaming.BindingIterator;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudySpaceMapper studySpaceMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    private String teacher= "teacher/";

    @GetMapping("/createcourse")
    public String CreateCourse(Model model, HttpServletRequest request, @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum,
                               @RequestParam(value = "num",required = false,defaultValue ="8")String num,
                               @RequestParam(value ="pattern",required = false,defaultValue ="0")String pattern,RedirectAttributes attributes){
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
            if (pattern1 == 0) {List=teacherMapper.selectcreator(UserISBN);}
            if (pattern1 == 1) {List = teacherMapper.selectcreatorasc(UserISBN);}
            if (pattern1 == 2) {List = teacherMapper.selectcreatordesc(UserISBN);}
            if(List.size()==0){
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("ishave", 0);
                model.addAttribute("islogin", islogin);
                return teacher + "createcourse";
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
                return teacher + "createcourse";
            }
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @GetMapping("/createform")
    public String CreateForm(Model model, HttpServletRequest request, RedirectAttributes attributes){
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
            List<Category> categoryList =teacherMapper.selectAllCategory();
            model.addAttribute("cartnum", cartnum);
            model.addAttribute("cartsum", cartsum);
            model.addAttribute("cartlist", cartlist);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("islogin", islogin);
            return teacher + "createform";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("/docreatecourse")
    public String DoCreateCourse(RedirectAttributes attributes, String CourseName, String Price, int categoryid,
                                 int specialcourse, @RequestParam(value = "img") MultipartFile img, String decription,HttpServletRequest request) throws FileNotFoundException {
        if(CourseName!=null&&!CourseName.equals("")&&Price!=null&&!Price.equals("")&&!img.isEmpty()&&decription!=null&&!decription.equals("")&&specialcourse!=0){
            HttpSession session=request.getSession();
            String username=(String)session.getAttribute("username");
            int UserISBN = (int) session.getAttribute("id");
            String userISBN=Integer.toString(UserISBN);
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sd.format(date);
            String imgName = img.getOriginalFilename(); // 文件名
            String suffixName = imgName.substring(imgName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/img/course//";
//                    "D://springboot//match//springboot//src//main//resources//static//img//course//"; // 上传后的路径
            imgName = System.currentTimeMillis() + suffixName;//新文件名
            File dest = new File(filePath + imgName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                img.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int Price1=Integer.parseInt(Price);
            String imgName1 = "/img/course/"+imgName;
            int isInert;
            isInert= teacherMapper.insertcourse(CourseName,Price1,decription,imgName1,categoryid,specialcourse,time,username,userISBN);
            if(isInert==1){
                attributes.addFlashAttribute("ms", "创建成功！");
                return "redirect:/createcourse";
            }else {
                attributes.addFlashAttribute("ms", "创建失败！");
                return "redirect:/createform";
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/createform";
        }
    }

    @GetMapping("/managecourse")
    public String ManageCourse(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            String userISBN=Integer.toString(UserISBN);
            List<Course> courselist = teacherMapper.selectCreatorId(userISBN);
            if (courselist.size() != 0) {
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
                int xiaomulu=list1.size();
                model.addAttribute("xiaomulu", xiaomulu);
                model.addAttribute("list1", list1);

                return teacher+"managecourse";
            }else {
                attributes.addFlashAttribute("ms","您未创建该课程！请创建");
                return "redirect:/createcourse";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @GetMapping("/chapterform")
    public String ChapterForm(Model model, HttpServletRequest request, RedirectAttributes attributes,int CourseISBN){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int mulu=1;
        if(username!=null&&!username.equals("")) {
            //排列方式
            islogin = 1;
            int UserISBN = (int) session.getAttribute("id");
            List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
            int cartnum = (int) session.getAttribute("cartnum");
            int cartsum = (int) session.getAttribute("cartsum");

            //课程信息
            Course course=courseMapper.selectByISBN(CourseISBN);
            model.addAttribute("course", course);

            List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
            if(list.size()==0){
                mulu=0;
            }
            model.addAttribute("mulu", mulu);
            model.addAttribute("list", list);
            model.addAttribute("cartnum", cartnum);
            model.addAttribute("cartsum", cartsum);
            model.addAttribute("cartlist", cartlist);
            model.addAttribute("course", course);
            model.addAttribute("islogin", islogin);

            return teacher + "chapterform";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("dochapterform")
    public String DoChapterForm(RedirectAttributes attributes,String ChapterName,String ChapterHao,int CourseISBN,String kaoshitime){
        if(ChapterName!=null&&!ChapterName.equals("")&&ChapterHao!=null&&!ChapterHao.equals("")){
            int ChapterHao1=Integer.parseInt(ChapterHao);
            List<Chapter> list=teacherMapper.selectChapterHao(CourseISBN,ChapterHao1);
            if(list.size()==0) {
                int isInsertChapter;
                isInsertChapter = teacherMapper.insertchapter(CourseISBN, ChapterHao1, ChapterName,kaoshitime);
                if (isInsertChapter != 0) {
                    attributes.addFlashAttribute("ms", "创建成功！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                } else {
                    attributes.addFlashAttribute("ms", "创建失败！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                }
            }else {
                int isUpdateChapter;
                isUpdateChapter=teacherMapper.UpdateChapter(ChapterName,CourseISBN,ChapterHao1,kaoshitime);
                if(isUpdateChapter!=0){
                    attributes.addFlashAttribute("ms", "更新成功！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                }else {
                    attributes.addFlashAttribute("ms", "更新失败！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                }
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/chapterform?CourseISBN="+CourseISBN;
        }

    }

    @PostMapping("dosmallchapterform")
    public String DoSmallChapterForm(RedirectAttributes attributes,String SmallChapter,String ChapterHao21,int CourseISBN,int ChapterHao1,String Grade){
        if(SmallChapter!=null&&!SmallChapter.equals("")&&ChapterHao21!=null&&!ChapterHao21.equals("")&&ChapterHao1!=0){
            int ChapterHao2=Integer.parseInt(ChapterHao21);
            int grade=Integer.parseInt(Grade);
            List<Chapter> list=teacherMapper.selectSmallChapter(ChapterHao1,ChapterHao2,CourseISBN);
            if(list.size()==0) {
                int isInsertChapter;
                isInsertChapter = teacherMapper.insertsmallchapter(ChapterHao1,ChapterHao2,CourseISBN,SmallChapter,grade);
                if (isInsertChapter != 0) {
                    attributes.addFlashAttribute("ms", "创建成功！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                } else {
                    attributes.addFlashAttribute("ms", "创建失败！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                }
            }else {
                int isUpdateChapter;
                isUpdateChapter=teacherMapper.UpdateSmallChapter(SmallChapter,ChapterHao1,ChapterHao2,CourseISBN);
                if(isUpdateChapter!=0){
                    attributes.addFlashAttribute("ms", "更新成功！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                }else {
                    attributes.addFlashAttribute("ms", "更新失败！");
                    return "redirect:/managecourse?CourseISBN=" + CourseISBN;
                }
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/chapterform?CourseISBN="+CourseISBN;
        }
    }

    @GetMapping("doDeleteChapter")
    public String DoDeleteChapter(RedirectAttributes attributes,int CourseISBN,String ChapterHao){
        int ChapterHao1=Integer.parseInt(ChapterHao);
        int isDeleteChapter=teacherMapper.DeleteChapter(ChapterHao1,CourseISBN);
        if(isDeleteChapter==1){
            attributes.addFlashAttribute("ms", "删除成功！");
            return "redirect:/managecourse?CourseISBN=" + CourseISBN;
        }else {
            attributes.addFlashAttribute("ms", "删除失败！");
            return "redirect:/managecourse?CourseISBN=" + CourseISBN;
        }
    }

    @GetMapping("doDeleteSmallChapter")
    public String DoDeleteSmallChapter(RedirectAttributes attributes,int CourseISBN,int ChapterHao1,int ChapterHao2){
        int isDeleteSmallChapter=teacherMapper.DeleteSmallChapter(ChapterHao1,ChapterHao2,CourseISBN);
        if(isDeleteSmallChapter==1){
            attributes.addFlashAttribute("ms", "删除成功！");
            return "redirect:/managecourse?CourseISBN=" + CourseISBN;
        }else {
            attributes.addFlashAttribute("ms", "删除失败！");
            return "redirect:/managecourse?CourseISBN=" + CourseISBN;
        }
    }

    @GetMapping("/managechapter")
    public String ManageChapter(Model model, HttpServletRequest request, int CourseISBN, int SmallChapterId,
                          @RequestParam(value ="ChapterHao",required = false,defaultValue ="0")int ChapterHao,
                          @RequestParam(value ="work",required = false,defaultValue ="1")int work,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
                //顶端导航栏
            List<Chapter> isCreator=teacherMapper.selectcreatortel(UserISBN,CourseISBN);
            if(isCreator.size()!=0){
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
                model.addAttribute("work", work);
                session.setAttribute("work1",chapter.getWork1());
                session.setAttribute("work2",chapter.getWork2());
                return teacher+"managechapter";
            }else {
                attributes.addFlashAttribute("ms", "该课程不是您创建，请创建课程！");
                attributes.addFlashAttribute("islogin", islogin);
                return "redirect:/createcourse";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("/doUpdateSmallChapterVideo1")
    public String DoUpdateSmallChapterVideo1(RedirectAttributes attributes,@RequestParam(value = "video") MultipartFile  video,String video1Name,int CourseISBN,int SmallChapterId) throws FileNotFoundException {
        if(!video.isEmpty()&&video1Name!=null&&!video1Name.equals(""))
        {
            String videoName = video.getOriginalFilename(); // 文件名
            String suffixName = videoName.substring(videoName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/video//" ;
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//file//video//"; // 上传后的路径
            videoName = System.currentTimeMillis() + suffixName;//新文件名
            File dest = new File(filePath + videoName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                video.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String video1 = videoName;
            int isUpdate=teacherMapper.UpdateSmallChapterVideo1(video1Name,video1,SmallChapterId,CourseISBN);
            if(isUpdate!=0){
                attributes.addFlashAttribute("ms", "提交成功");
                return "redirect:/managecourse?CourseISBN="+CourseISBN;
            }else {
                attributes.addFlashAttribute("ms", "提交失败！");
                return "redirect:/managechapter?SmallChapterId="+SmallChapterId+"&CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/managechapter?SmallChapterId="+SmallChapterId+"&CourseISBN="+CourseISBN;
        }
    }

    @PostMapping("/doUpdateSmallChapterVideo2")
    public String DoUpdateSmallChapterVideo2(RedirectAttributes attributes,@RequestParam(value = "video") MultipartFile  video,String video2Name,int CourseISBN,int SmallChapterId) throws FileNotFoundException {
        if(!video.isEmpty()&&video2Name!=null&&!video2Name.equals(""))
        {
            String videoName = video.getOriginalFilename(); // 文件名
            String suffixName = videoName.substring(videoName.lastIndexOf(".")); // 后缀名
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/video//" ;
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//file//video//"; // 上传后的路径
            videoName = System.currentTimeMillis() + suffixName;//新文件名
            File dest = new File(filePath + videoName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                video.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String video1 = videoName;
            int isUpdate=teacherMapper.UpdateSmallChapterVideo2(video2Name,video1,SmallChapterId,CourseISBN);
            if(isUpdate!=0){
                attributes.addFlashAttribute("ms", "提交成功");
                return "redirect:/managecourse?CourseISBN="+CourseISBN;
            }else {
                attributes.addFlashAttribute("ms", "提交失败！");
                return "redirect:/managechapter?SmallChapterId="+SmallChapterId+"&CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/managechapter?SmallChapterId="+SmallChapterId+"&CourseISBN="+CourseISBN;
        }
    }

    @GetMapping(value = "/preview11")
    public void pdfStreamHandler1(HttpServletRequest request, HttpServletResponse response,String work) throws FileNotFoundException {
        //PDF文件地址
        String path1 = ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//" ;
        String path=path1+work;
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

    @PostMapping("/doUpdateSmallChapterWork1")
    public String DoUpdateSmallChapterWork1(RedirectAttributes attributes,@RequestParam(value = "work1") MultipartFile work1,String work1Name,int CourseISBN,int SmallChapterId) throws FileNotFoundException {
        if(!work1.isEmpty()&&work1Name!=null&&!work1Name.equals(""))
        {
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sd.format(date);
            String workName = work1.getOriginalFilename(); // 文件名
            String suffixName = workName.substring(workName.lastIndexOf(".")); // 后缀名
            String filePath= ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//" ;
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//file//test//"; // 上传后的路径
            workName = System.currentTimeMillis() + suffixName;//新文件名
            File dest = new File(filePath + workName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                work1.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String work = workName;
            int isUpdate=teacherMapper.UpdateSmallChapterwork1(work1Name,work,time,SmallChapterId,CourseISBN);
            if(isUpdate!=0){
                attributes.addFlashAttribute("ms", "提交成功");
                return "redirect:/managetest?CourseISBN="+CourseISBN;
            }else {
                attributes.addFlashAttribute("ms", "提交失败！");
                return "redirect:/managetest?CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/managetest?CourseISBN="+CourseISBN;
        }
    }

    @PostMapping("/doUpdateSmallChapterWork2")
    public String DoUpdateSmallChapterWork2(RedirectAttributes attributes,@RequestParam(value = "work2") MultipartFile work2,String work2Name,int CourseISBN,int SmallChapterId) throws FileNotFoundException {
        if(!work2.isEmpty()&&work2Name!=null&&!work2Name.equals(""))
        {
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sd.format(date);
            String workName = work2.getOriginalFilename(); // 文件名
            String suffixName = workName.substring(workName.lastIndexOf(".")); // 后缀名
            String filePath= ResourceUtils.getURL("classpath:").getPath() + "static"+"/file/test//" ;
//            String filePath =
//                    "D://springboot//match//springboot//src//main//resources//static//file//test//"; // 上传后的路径
            workName = System.currentTimeMillis() + suffixName;//新文件名
            File dest = new File(filePath + workName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                work2.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String work = workName;
            int isUpdate=teacherMapper.UpdateSmallChapterwork2(work2Name,work,time,SmallChapterId,CourseISBN);
            if(isUpdate!=0){
                attributes.addFlashAttribute("ms", "提交成功");
                return "redirect:managetest?CourseISBN="+CourseISBN;
            }else {
                attributes.addFlashAttribute("ms", "提交失败！");
                return "redirect:/managetest?CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/managetest?CourseISBN="+CourseISBN;
        }
    }

    @GetMapping("/manageexam")
    public String ManageExam(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes,
                             @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int question=0;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            String userISBN=Integer.toString(UserISBN);
            List<Course> courselist = teacherMapper.selectCreatorId(userISBN);
            if (courselist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;int totalnum;int last=0;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);

                List<Question> questionlist =teacherMapper.selectQuestion(CourseISBN);
                if(questionlist.size()!=0) {
                    question = 1;
                    int pagesize = 8;//每页显示的书的个数
                    totalnum = questionlist.size();//书的总数量
                    int totalpage = totalnum % pagesize == 0 ? (totalnum / pagesize) : (totalnum / pagesize + 1);// 总页数
                    if (pageNum < 1) {
                        pageNum = 1;
                    }
                    if (pageNum > totalpage) {
                        pageNum = totalpage;
                    }
                    int begin = (pageNum - 1) * pagesize;
                    int end = pagesize * pageNum > totalnum ? totalnum : (pageNum * pagesize);
                    if (pageNum == totalpage) {
                        last = 1;
                    }
                    questionlist = questionlist.subList(begin, end);
                    model.addAttribute("last", last);
                    model.addAttribute("pageNum", pageNum);
                    model.addAttribute("question", question);
                    model.addAttribute("questionlist", questionlist);
                    return teacher + "manageexam";
                }else {
                    model.addAttribute("last", last);
                    model.addAttribute("pageNum", pageNum);
                    model.addAttribute("question", question);
                    model.addAttribute("questionlist", questionlist);
                    return teacher + "manageexam";
                }
            }else {
                attributes.addFlashAttribute("ms","您未创建该课程！请创建");
                return "redirect:/createcourse";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @GetMapping("/examform")
    public String ExamForm(Model model, HttpServletRequest request, RedirectAttributes attributes,int CourseISBN){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int mulu=1;
        if(username!=null&&!username.equals("")) {
            //排列方式
            islogin = 1;
            int UserISBN = (int) session.getAttribute("id");
            List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
            int cartnum = (int) session.getAttribute("cartnum");
            int cartsum = (int) session.getAttribute("cartsum");

            //课程信息
            Course course=courseMapper.selectByISBN(CourseISBN);
            model.addAttribute("course", course);

            List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
            if(list.size()==0){
                mulu=0;
            }
            model.addAttribute("mulu", mulu);
            model.addAttribute("list", list);
            model.addAttribute("cartnum", cartnum);
            model.addAttribute("cartsum", cartsum);
            model.addAttribute("cartlist", cartlist);
            model.addAttribute("course", course);
            model.addAttribute("islogin", islogin);

            return teacher + "examform";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("/doexamform")
    public String DoExamForm(RedirectAttributes attributes,String subject,String optiona,String optionb,String optionc,String optiond,
                             String answer,String type,int chapterid,int CourseISBN){
        if(subject!=null&&!subject.equals("")&&optiona!=null&&optionb!=null&&optionc!=null&&optiond!=null&&!optiona.equals("")&&!optionb.equals("")
                &&!optionc.equals("")&&!optiond.equals("")&&answer!=null&&!answer.equals("")&&type!=null&&!type.equals(""))
        {
            int UpdateChapterTest=teacherMapper.UpdateChapterTest(chapterid);
            int isInsert=teacherMapper.insertQuestion(subject,type,optiona,optionb,optionc,optiond,answer,CourseISBN,chapterid);
            if(isInsert!=0){
                int a= teacherMapper.UpdateExam(CourseISBN,chapterid);
                if(a!=0){
                    attributes.addFlashAttribute("ms", "提交成功");
                    return "redirect:manageexam?CourseISBN="+CourseISBN;
                }else {
                    attributes.addFlashAttribute("ms", "提交失败");
                    return "redirect:manageexam?CourseISBN="+CourseISBN;
                }
            }else {
                attributes.addFlashAttribute("ms", "提交失败！");
                return "redirect:/examform?CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/examform?CourseISBN="+CourseISBN;
        }
    }
    @GetMapping("doDeleteexam")
    public String DoDeleteExam(RedirectAttributes attributes,int id,int CourseISBN){
        int isDelete=teacherMapper.DeleteExam(id);
        if(isDelete==1){
            attributes.addFlashAttribute("ms", "删除成功！");
            return "redirect:/manageexam?CourseISBN=" + CourseISBN;
        }else {
            attributes.addFlashAttribute("ms", "删除失败！");
            return "redirect:/manageexam?CourseISBN=" + CourseISBN;
        }
    }

    @GetMapping("/updateexamform")
    public String UpdateExamForm(Model model, HttpServletRequest request, RedirectAttributes attributes,int CourseISBN,int id){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;
        if(username!=null&&!username.equals("")) {
            //排列方式
            islogin = 1;
            int UserISBN = (int) session.getAttribute("id");
            List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
            int cartnum = (int) session.getAttribute("cartnum");
            int cartsum = (int) session.getAttribute("cartsum");
            model.addAttribute("cartnum", cartnum);
            model.addAttribute("cartsum", cartsum);
            model.addAttribute("cartlist", cartlist);
            model.addAttribute("islogin", islogin);
            //课程信息
            Course course=courseMapper.selectByISBN(CourseISBN);
            model.addAttribute("course", course);
            //章节目录
            List<Chapter> list = studySpaceMapper.selectChapter(CourseISBN);
            model.addAttribute("list", list);

            Question question = teacherMapper.SelectQuestionById(id);
            model.addAttribute("question", question);


            return teacher + "updateexamform";
        }else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("/doupdateexam")
    public String DoUpdateExam(RedirectAttributes attributes,String subject,String optiona,String optionb,String optionc,String optiond,
                             String answer,String type,int id,int CourseISBN){
        if(subject!=null&&!subject.equals("")&&optiona!=null&&optionb!=null&&optionc!=null&&optiond!=null&&!optiona.equals("")&&!optionb.equals("")
                &&!optionc.equals("")&&!optiond.equals("")&&answer!=null&&!answer.equals("")&&type!=null&&!type.equals(""))
        {
            int isUpdate=teacherMapper.UpdateQuestion(subject,type,optiona,optionb,optionc,optiond,answer,id);
            if(isUpdate!=0){
                attributes.addFlashAttribute("ms", "更新成功");
                return "redirect:manageexam?CourseISBN="+CourseISBN;
            }else {
                attributes.addFlashAttribute("ms", "更新失败！");
                return "redirect:/updateexamform?CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "请填写完整信息！");
            return "redirect:/updateexamform?CourseISBN="+CourseISBN;
        }
    }

    @GetMapping("/managetest")
    public String ManageTest(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int molu=1;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            String userISBN=Integer.toString(UserISBN);
            List<Course> courselist = teacherMapper.selectCreatorId(userISBN);
            if (courselist.size() != 0) {
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
                List<Chapter> list=studySpaceMapper.selectSmallChapter2(CourseISBN);
                if(list.size()==0){
                    molu=0;
                }
                model.addAttribute("molu", molu);

                model.addAttribute("list", list);
                return teacher+"managetest";
            }else {
                attributes.addFlashAttribute("ms","您未创建该课程！请创建");
                return "redirect:/createcourse";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }

    }

    @GetMapping("/lookexam")
    public String LookExam(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes,
                             @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int question=0;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            String userISBN=Integer.toString(UserISBN);
            List<Course> courselist = teacherMapper.selectCreatorId(userISBN);
            if (courselist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;int totalnum;int last=0;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);

                List<ExamResult> examResultList=teacherMapper.SelectAllExam(CourseISBN);
                if(examResultList.size()!=0) {
                    question = 1;
                    int pagesize = 8;//每页显示的书的个数
                    totalnum = examResultList.size();//书的总数量
                    int totalpage = totalnum % pagesize == 0 ? (totalnum / pagesize) : (totalnum / pagesize + 1);// 总页数
                    if (pageNum < 1) {
                        pageNum = 1;
                    }
                    if (pageNum > totalpage) {
                        pageNum = totalpage;
                    }
                    int begin = (pageNum - 1) * pagesize;
                    int end = pagesize * pageNum > totalnum ? totalnum : (pageNum * pagesize);
                    if (pageNum == totalpage) {
                        last = 1;
                    }
                    examResultList = examResultList.subList(begin, end);
                    model.addAttribute("last", last);
                    model.addAttribute("pageNum", pageNum);
                    model.addAttribute("question", question);
                    model.addAttribute("examResultList", examResultList);
                    return teacher + "lookexam";
                }else {
                    model.addAttribute("last", last);
                    model.addAttribute("pageNum", pageNum);
                    model.addAttribute("question", question);
                    model.addAttribute("questionlist", examResultList);
                    return teacher + "lookexam";
                }
            }else {
                attributes.addFlashAttribute("ms","您未创建该课程！请创建");
                return "redirect:/createcourse";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @Autowired
    private StuExamResultService stuExamResultService;
    @GetMapping("/saveExcel2")
    public void saveExcel2(HttpSession session, HttpServletResponse response,int CourseISBN)throws Exception{
        String username= (String) session.getAttribute("username");
        int UserISBN = (int) session.getAttribute("id");
        //返回用户查询所有考试记录
        List<Map<String, Object>> listMap =teacherMapper.SelectAllExam1(CourseISBN);
        Workbook workbook=new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("您的课程成绩单");
        Row row=sheet.createRow(0);
        Cell cell00=row.createCell(0);
        cell00.setCellValue("课程号");
        Cell cell01=row.createCell(1);
        cell01.setCellValue("章节号");
        Cell cell02=row.createCell(2);
        cell02.setCellValue("章节名");
        Cell cell03=row.createCell(3);
        cell03.setCellValue("单选题");
        Cell cell04=row.createCell(4);
        cell04.setCellValue("多选题");
        Cell cell05=row.createCell(5);
        cell05.setCellValue("总分");
        Cell cell06=row.createCell(6);
        cell06.setCellValue("考试时间");
        for(int i = 0;i<listMap.size();i++){
            row=sheet.createRow(i+1);
            Cell cell0=row.createCell(0);
            cell0.setCellValue((Integer)listMap.get(i).get("courseisbn"));
            Cell cell1=row.createCell(1);
            cell1.setCellValue((Integer)listMap.get(i).get("chapterid"));
            Cell cell2=row.createCell(2);
            cell2.setCellValue((String) listMap.get(i).get("chapter"));
            Cell cell3=row.createCell(3);
            cell3.setCellValue((Integer)listMap.get(i).get("radioscores"));
            Cell cell4=row.createCell(4);
            cell4.setCellValue((Integer)listMap.get(i).get("checkscores"));
            Cell cell5=row.createCell(5);
            cell5.setCellValue((Integer)listMap.get(i).get("total"));
            Cell cell6=row.createCell(6);
            cell6.setCellValue((Timestamp)listMap.get(i).get("createtime"));
            //获取单元格样式
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle cellStyle = workbook.createCellStyle();
            //定义单元格日期样式
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
            //设置单元格样式
            cell6.setCellStyle(cellStyle);
        }
        stuExamResultService.export(response, workbook, "课程考试结果.xls");
    }


    @GetMapping("/looktest")
    public String LooTest(Model model, HttpServletRequest request, int CourseISBN,RedirectAttributes attributes,
                           @RequestParam(value = "pageNum",required = false,defaultValue ="1")int pageNum){
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        int islogin=0;int question=0;
        if(username!=null&&!username.equals("")) {
            int UserISBN = (int) session.getAttribute("id");
            String userISBN=Integer.toString(UserISBN);
            List<Course> courselist = teacherMapper.selectCreatorId(userISBN);
            if (courselist.size() != 0) {
                //顶端导航栏
                List<Course> cartlist = (List<Course>) session.getAttribute("cartlist");
                int cartnum = (int) session.getAttribute("cartnum");
                int cartsum = (int) session.getAttribute("cartsum");
                islogin = 1;int totalnum;int last=0;
                model.addAttribute("cartnum", cartnum);
                model.addAttribute("cartsum", cartsum);
                model.addAttribute("cartlist", cartlist);
                model.addAttribute("islogin", islogin);
                //课程信息
                Course course=courseMapper.selectByISBN(CourseISBN);
                model.addAttribute("course", course);

                List<TestResult> testResultList=teacherMapper.SelectAllTest(CourseISBN);
                if(testResultList.size()!=0) {
                    question = 1;
                    int pagesize = 8;//每页显示的书的个数
                    totalnum = testResultList.size();//书的总数量
                    int totalpage = totalnum % pagesize == 0 ? (totalnum / pagesize) : (totalnum / pagesize + 1);// 总页数
                    if (pageNum < 1) {
                        pageNum = 1;
                    }
                    if (pageNum > totalpage) {
                        pageNum = totalpage;
                    }
                    int begin = (pageNum - 1) * pagesize;
                    int end = pagesize * pageNum > totalnum ? totalnum : (pageNum * pagesize);
                    if (pageNum == totalpage) {
                        last = 1;
                    }
                    testResultList = testResultList.subList(begin, end);
                    model.addAttribute("last", last);
                    model.addAttribute("pageNum", pageNum);
                    model.addAttribute("question", question);
                    model.addAttribute("testResultList", testResultList);
                    return teacher + "looktest";
                }else {
                    model.addAttribute("last", last);
                    model.addAttribute("pageNum", pageNum);
                    model.addAttribute("question", question);
                    model.addAttribute("testResultList", testResultList);
                    return teacher + "looktest";
                }
            }else {
                attributes.addFlashAttribute("ms","您未创建该课程！请创建");
                return "redirect:/createcourse";
            }
        } else {
            attributes.addFlashAttribute("ms", "请登录执行此操作！");
            attributes.addFlashAttribute("islogin", islogin);
            return "redirect:/index";
        }
    }

    @PostMapping("doStugrade")
    public String DoStugrade(int TestId,String stugrade,int CourseISBN,RedirectAttributes attributes){
        if(!stugrade.equals("")&&stugrade!=null){
            int grade=Integer.parseInt(stugrade);
            int IsUpdate=teacherMapper.UpdateStugrade(grade,TestId);
            if(IsUpdate!=0){
                attributes.addFlashAttribute("ms", "打分成功！");
                return "redirect:/looktest?CourseISBN="+CourseISBN;
            }else{
                attributes.addFlashAttribute("ms", "打分失败！");
                return "redirect:/looktest?CourseISBN="+CourseISBN;
            }
        }else {
            attributes.addFlashAttribute("ms", "分数不能为空！");
            return "redirect:/looktest?CourseISBN="+CourseISBN;
        }
    }
}

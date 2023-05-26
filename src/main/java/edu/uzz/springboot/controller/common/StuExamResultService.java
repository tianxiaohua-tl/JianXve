package edu.uzz.springboot.controller.common;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StuExamResultService {

    //保存考试信息到Excel
    void export(HttpServletResponse response, Workbook workbook, String fileName)throws Exception;
}

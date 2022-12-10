package com.example.student_portal.controller;

import com.example.student_portal.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.student_portal.controller.mainCont.CurrA;

// 4000 lines back-end code,and 20 front-end pages, completed by Steven Chen @
//Charles W. Davidson College of Engineering, SJSU


@Controller()
public class AdminController {
    @Autowired
    public JdbcTemplate jdbcTemplate;

//    public User CurrA;
    classPersonDataRow [] applicationArr;
    @RequestMapping(value = "/adminMain",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adminMain()
    {
        ModelAndView mv = new ModelAndView("admin_main.html");
        mv.addObject("aUser",CurrA);

        return mv;

    }

    @RequestMapping(value = "/absentHandler",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adminAbesent()
    {
        ModelAndView mv  = new ModelAndView("adminAbsent.html");
        //mv.addObject()
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        absentData[] al = sqlClass.absentArray();
        mv.addObject("absentList",al);
        return mv;

    }

    @RequestMapping(value = "/createClass",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView createClass()
    {
        ModelAndView mv = new ModelAndView("newClass.html");
        mv.addObject("Course",new Course());
        return mv;

    }


    @RequestMapping(value = "/newClassFeedback",method = RequestMethod.GET)
    public ModelAndView newClassFeedBack(Course newCourse)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result = sqlClass.addClass(newCourse);
        if(result>0)
        {
           ModelAndView mv = new ModelAndView("adminSuccess.html");

           return mv;
        }
        else {
            ModelAndView mv = new ModelAndView("error.html");
            return mv;
        }


    }
    @RequestMapping(value = "/registrations" ,method = RequestMethod.GET)
    public ModelAndView registerHandling()
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        List<classPersonDataRow> lc = sqlClass.applicationsShow();
        ModelAndView mv = new ModelAndView("applicationHandler.html");


        if(lc.size()==0)
        {
            mv.addObject("classList", new classPersonDataRow[0]);

        }
        else {
            classPersonDataRow[] cArr = listToArray(lc);
            applicationArr = cArr;
            mv.addObject("classList", cArr);




        }
        return mv;

    }
    @RequestMapping(value = "/appAprove", method = RequestMethod.GET)
    public String appAprove(int index)
    {
        classPersonDataRow cpdr = applicationArr[index];
        //primary key is the combo of user_id and c_id
        //command 1 = aprove, -1 = decline;
//        Map<String,Integer> mCommand = new HashMap<>();
//        mCommand.put("class_id", cpdr.getClass_id());
//        mCommand.put("user_id", cpdr.getUser_id());
//        mCommand.put("command", 1);
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result = sqlClass.appOperater(cpdr,1);
        if(result<1)
        {
            return "error";
        }


        return "adminSuccess";

    }
    @RequestMapping(value = "/appDecline", method = RequestMethod.GET)
    public String appDecline(int index)
    {
        classPersonDataRow cpdr = applicationArr[index];
        //primary key is the combo of user_id and c_id
        //command 1 = aprove, -1 = decline;
//        Map<String,Integer> mCommand = new HashMap<>();
//        mCommand.put("class_id", cpdr.getClass_id());
//        mCommand.put("user_id", cpdr.getUser_id());
//        mCommand.put("command", 1);
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result = sqlClass.appOperater(cpdr,-1);
        if(result<1)
        {
            return "error";
        }


        return "adminSuccess";
    }

@RequestMapping("/dropHandler")
    public ModelAndView dropHandle(String classId)
    {
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        if(classId == null)
        {
        ModelAndView mv = new ModelAndView("dropHandler.html");

        Course[] cArr = sqlClass.loadClassIntoArray();

        classPersonDataRow dr = new classPersonDataRow();
        classPersonDataRow []cpdrs = new classPersonDataRow[0];
        mv.addObject("classList",cArr);
        mv.addObject("cpdrs",cpdrs);
        mv.addObject("dr",dr);
        return mv;
        }
        else {
            ModelAndView mv = new ModelAndView("dropHandler.html");
            classPersonDataRow []cpdrs = listToArray(sqlClass.getStudentsInClass(Integer.parseInt(classId)));
            classPersonDataRow dr;
            if(cpdrs.length>0)
            {
                dr = cpdrs[0];
            }
            else {
                dr = new classPersonDataRow(Integer.parseInt(classId), "no students found");
            }

            Course[] cArr = sqlClass.loadClassIntoArray();
            mv.addObject("classList",cArr);
            mv.addObject("cpdrs",cpdrs);
            mv.addObject("dr",dr);



            return mv;
        }

    }

    @RequestMapping(value = "/adDropExute")
    public ModelAndView adDropExute( int user_id, int class_id,String comment) throws ParseException {
        classOfaCourse coc = new classOfaCourse(class_id,user_id,getDateString(),getDateString(),"admin: "+comment);
        List<classOfaCourse> cl = new ArrayList<>();
        cl.add(coc);
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        sqlClass.userDrop(cl,user_id,comment);

        ModelAndView mv = new ModelAndView("dropHandler.html");

        Course[] cArr = sqlClass.loadClassIntoArray();
        classPersonDataRow []cpdrs = listToArray(sqlClass.getStudentsInClass(class_id));
        classPersonDataRow dr;
        if (cpdrs.length>0)
        {
            dr = cpdrs[0];
        }
        else {
            dr = new classPersonDataRow(class_id, "no students found");
        }
        mv.addObject("classList",cArr);
        mv.addObject("cpdrs",cpdrs);
        mv.addObject("dr",dr);
        mv.addObject("reminder","Success drop a student "+ Integer.toString(user_id));

        return mv;
    }
    @RequestMapping(value = "/classRank")
    public ModelAndView classRank()
    {
        ModelAndView mv = new ModelAndView("Statistics.html");
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        Course[] cArr = sqlClass.orderByEnrollment();
        instructorWithSnum []  iArr= new instructorWithSnum[0];
        UserConsumption [] uArr = new UserConsumption[0];
        mv.addObject("classList",cArr);
        mv.addObject("instList",iArr);
        mv.addObject("userList",uArr);
        return mv;




    }
    @RequestMapping(value = "/instructorRank")
    public ModelAndView instRank()
    {
        ModelAndView mv = new ModelAndView("Statistics.html");
        SqlClass sqlClass =new SqlClass(jdbcTemplate);

        instructorWithSnum []  iArr= sqlClass.instructorList();

        Course[] cArr = new Course[0];
        UserConsumption [] uArr = new UserConsumption[0];
        mv.addObject("classList",cArr);
        mv.addObject("instList",iArr);
        mv.addObject("userList",uArr);
        return mv;

    }

    @RequestMapping(value = "/adStatic")
    public ModelAndView adStatic()
    {
        ModelAndView mv = new ModelAndView("Statistics.html");
        Course [] cArr = new Course[0];
        instructorWithSnum []  iArr= new instructorWithSnum[0];
        UserConsumption [] uArr = new UserConsumption[0];

        mv.addObject("classList",cArr);
        mv.addObject("instList",iArr);
        mv.addObject("userList",uArr);

        return mv;

    }
    @RequestMapping(value = "/adSetting")
    public ModelAndView adSetting()
    {
        ModelAndView mv = new ModelAndView("adSetting.html");
        return mv;
    }

    @RequestMapping(value = "consumptionRank")
    public ModelAndView consumptionRank(String sDate, String eDate) throws ParseException {
        ModelAndView mv = new ModelAndView("Statistics.html");
        if(sDate== null&&eDate==null)
        {
            SqlClass sqlClass =new SqlClass(jdbcTemplate);
            Course [] cArr = new Course[0];
            instructorWithSnum []  iArr= new instructorWithSnum[0];
            UserConsumption [] uArr = new UserConsumption[0];

            mv.addObject("classList",cArr);
            mv.addObject("instList",iArr);
            mv.addObject("userList",uArr);
            return mv;
        }
        else {
            SqlClass sqlClass =new SqlClass(jdbcTemplate);
            Course [] cArr = new Course[0];
            instructorWithSnum []  iArr= new instructorWithSnum[0];
            UserConsumption [] uArr = sqlClass.userRank(sDate,eDate);

            mv.addObject("classList",cArr);
            mv.addObject("instList",iArr);
            mv.addObject("userList",uArr);
            return mv;
        }
//        return mv;
    }

    public String getDateString()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String refeDate = dateFormat.format(date);
        return refeDate;
    }
    public classPersonDataRow[] listToArray (List<classPersonDataRow> l)
    {
        classPersonDataRow []  arr = new classPersonDataRow[l.size()];
        int i =0;
        for (classPersonDataRow c :l)
        {
            arr[i] = c;
            i++;
        }
        return arr;
    }



}

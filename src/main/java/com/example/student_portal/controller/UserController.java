package com.example.student_portal.controller;

import com.example.student_portal.Service.UserService;
import com.example.student_portal.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//import mainCont;
@Controller()
public class UserController extends mainCont {

    //
    @Autowired
    public JdbcTemplate jdbcTemplate;
    List<Map<String,Object>> cl;

    private User currStu;




    @RequestMapping(value="/registClass",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView userRegister(@RequestParam("selectIds") int[] selectIds, String comment, HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("userRegist.html");
        List<Course> l= loadClass(0);

        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");

        if(selectIds==null)
        {

            //load all class from chosen;
            mv.addObject("classList",convertListToArry(l));             //this template can only take array
            mv.addObject("aUser",u);
            mv.addObject("reminder", "selected none");

            return mv;
        }
        else {
            //user already selected;
            SqlClass classSql = new SqlClass(jdbcTemplate);
            boolean s = false;

            for(int i : selectIds)
            {

                s= classSql.writeAnApplication(u.getId(),i,comment);

                if(!s)
                    break;;
            }
            if(!s)
            {
                mv.addObject("reminder","error!");
            }
            else {
                mv.addObject("reminder", "success thank you, waiting for the admin to approve");
            }
            mv.addObject("aUser",u);
            //this template can only take array
            mv.addObject("classList",convertListToArry(l));

        }
        return mv;


    }
    @RequestMapping(value="/addClass",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView preAdd(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");


        ModelAndView mv = new ModelAndView("userRegist.html");
        List<Course> l= loadClass(0);


            //load all class from chosen;
        mv.addObject("classList",convertListToArry(l));
        mv.addObject("aUser",u);
        mv.addObject("reminder", "please selecct");

        return mv;
    }
    @RequestMapping(value = "/finance",method = RequestMethod.GET)
    public ModelAndView showFinance(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");

        ModelAndView mv = new ModelAndView("userFinance");
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        Order[] oArr = sqlClass.checkUserOrders(u.getId());
        double b = sqlClass.checkUserBalance(u.getId());
        mv.addObject("balance",b);
        mv.addObject("aUser",u);
        mv.addObject("classList",oArr);
        return mv;


    }

    //this function use sql statement to load all classes of a school from database; and return a List of classes;
    //the school is defaulted right now
    public List<Course> loadClass(int schoolCode)
    {
       // Set<Course> sc = new LinkedHashSet<Course>();
        String sqlLoad = " select class_id, location,classes.time,classes.price,classes.startDate, classes.classCount, classes.class_name,teacher from classes  order by class_id;";
        //String sqlLoad = "select classes.class_id, catalogue_courses.course_abbreviation from classes join student_portal.is using(class_id) join catalogue_courses using(course_abbreviation)";
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        Map<String,Object> courseMap =new HashMap<String,Object>();
        l =jdbcTemplate.queryForList(sqlLoad);
        List<Course> courseList = new ArrayList<Course>();
        for(Map<String,Object> m : l) {

            Course aCourse = new Course((Integer) m.get("class_id"), (String) m.get("teacher"), (String) m.get("location"), (String) m.get("time"), (String) m.get("class_name"), (Double) m.get("price"));

            aCourse.setNc((Integer) m.get("classCount"));
            if(m.get("startDate")!= null)
            {
                aCourse.setsTime(m.get("startDate").toString());
            }
            courseList.add(aCourse);

        }

        return courseList;


    }
    @RequestMapping(value="/stMain",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView showUserTokeAndMain(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u= (User) session.getAttribute("currentU");

        List<Course> lc = new ArrayList<Course>();
        if(u.getRoleCode()==1)
        {
            SqlClass temp = new SqlClass(jdbcTemplate);
            lc= temp.showtoke(1, u.getId());

        }


        ModelAndView mv = new ModelAndView("studmainWithCourse.html");
        Course[] cArr = new Course[lc.size()];
        for(int i = 0;i<lc.size();i++)
        {
            cArr[i] = lc.get(i);
        }

        mv.addObject("aUser",u);
        mv.addObject("classList",cArr);             //this template can only take array

        return mv;

    }

//    @RequestMapping(value = "/addResult",method = RequestMethod.GET)
//    @ResponseBody
//    public void addpage(@RequestParam("c_id") int[] idArry)
//    {
//
//
//
//    }
    //@RequestParam("dates") String []dates,
    @RequestMapping(value="/absentExcute",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView userAbsent(HttpServletRequest request,@RequestParam("selectIds") int[]selectIds,@RequestParam("dates") String []dates,String []mDates, String comment) throws ParseException {

        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");

        ModelAndView mv = new ModelAndView("userAbsent.html");
        List<classOfaCourse> coc = new ArrayList<classOfaCourse>();
        List<String> ls = new <String> LinkedList();
        List<String> md = new <String> LinkedList();
        int index=0;
        for(String date : dates)
        {
            //vaild input
            if (!date.equals("null"))
            {
                ls.add(date);

            }
            index++;
        }
        index = 0;
        for(String date : mDates)
        {
            //vaild input
            if (!date.equals("makeUp date"))
            {
                md.add(date);

            }
            index++;
        }

        for(int i = 0;i<selectIds.length;i++)
        {

            Date date = new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String refeDate = dateFormat.format(date);

            classOfaCourse cc = new classOfaCourse(selectIds[i],u.getId(),ls.get(i),refeDate,comment);
            cc.setMd(md.get(i));

            coc.add(cc);
        }

        //excution:
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        sqlClass.userAbsent(coc);

        mv.addObject("aUser", u);
        mv.addObject("classList",convertListToArry(new SqlClass(jdbcTemplate).showtoke(u.getRoleCode(),u.getId())));
        mv.addObject("reminder", "absent form submitted, thank you");





        return mv;
    }
    @RequestMapping(value="/absent",method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView preUserAbsent(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");

        SqlClass temp = new SqlClass(jdbcTemplate);
        List<Course> l = temp.showtoke(1, u.getId());
        ModelAndView mv = new ModelAndView("userAbsent.html");
        mv.addObject("classList",convertListToArry(l));
        mv.addObject("aUser", u);
        mv.addObject("reminder", "please selecct");
        return mv;
    }
//================================user drop course pre and excution============================
    @RequestMapping(value="/drop",method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView preUserDrop(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");
        SqlClass temp = new SqlClass(jdbcTemplate);
        List<Course> l = temp.showtoke(1, u.getId());
        ModelAndView mv = new ModelAndView("userDrop.html");
        mv.addObject("classList",convertListToArry(l));
        mv.addObject("aUser", u);
        mv.addObject("reminder", "please selecct");


        return mv;
    }
    @RequestMapping(value="/dropExcute",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView userDrop(HttpServletRequest request,@RequestParam("selectIds") int []selectIds,@RequestParam("dates") String[] dates, String comment) throws ParseException {

        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");

        ModelAndView mv = new ModelAndView("userDrop.html");
        List<classOfaCourse> coc = new ArrayList<classOfaCourse>();
        List<String> ls = new <String> LinkedList();
        int index=0;
        for(String date : dates)
        {
            //vaild input
            if (!date.equals("null"))
            {
                ls.add(date);

            }
            index++;
        }

        for(int i = 0;i<selectIds.length;i++)
        {
            Date date = new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String refeDate = dateFormat.format(date);
            if(ls.get(i).equals("null"))
            {
                return new ModelAndView("error.html");
            }

            classOfaCourse cc = new classOfaCourse(selectIds[i],u.getId(),ls.get(i),refeDate,comment);
            coc.add(cc);
        }

        //excution:
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        sqlClass.userDrop(coc,u.getId(),comment);

        mv.addObject("aUser", u);
        mv.addObject("classList",convertListToArry(new SqlClass(jdbcTemplate).showtoke(u.getRoleCode(),u.getId())));
        mv.addObject("reminder", "drop form submitted, thank you");
//        Selection session[] = new Selection()[];
//
//        mv.addObject("selections", session);





        return mv;
    }
    @RequestMapping(value = "/makeUp",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView userMethod()
    {
        ModelAndView mv = new ModelAndView("usermq.html");
        return mv;
    }


    @RequestMapping(value="/userSet",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView preSetting(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");

        ModelAndView mv = new ModelAndView("userSeetingPage.html");

        //system haven't loaded the zip now


        String sql = "select* from users where users.user_id= " + Integer.toString(u.getId()) + ";";
        List<Map<String, Object>> lc = new ArrayList<>();
        lc = jdbcTemplate.queryForList(sql);

        u.setZip((String) (lc.get(0).get("zip")));
        u.setNumber((String) (lc.get(0).get("phone")));
        u.setEmail((String) (lc.get(0).get("email")));
        mv.addObject("reminder", "you can change personal info here");
        mv.addObject("aUser",u);
        return mv;
    }
    @RequestMapping(value="/userSetExcute")

    public String preSetting(User aUser,HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentU");
        //the ID and role should inherient CurrUser;
        aUser.setId(u.getId());
        aUser.setRoleCode(u.getRoleCode());

        SqlClass sql = new SqlClass(jdbcTemplate);
        if(sql.checkDuplicate(aUser,u))
        {
            return "/error invalid userName";
        }
        else
        {
            sql.userUpdate(aUser);
            session.setAttribute("currentU",aUser);
            //u = aUser;
        }
        return "redirect:/userSet";
    }


    public Course[] convertListToArry (List<Course> lc)
    {
        Course[] cArr = new Course[lc.size()];
        for(int i = 0;i<lc.size();i++)
        {
            cArr[i] = lc.get(i);
        }
        return cArr;
    }


}

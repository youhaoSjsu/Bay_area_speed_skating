package com.example.student_portal.controller;


import com.example.student_portal.Service.HpBoardService;
import com.example.student_portal.apiModule.RestClassRequest;
import com.example.student_portal.apiModule.RestClassRespond;
import com.example.student_portal.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//@Controller()
public class mainCont {


    @Autowired
    public JdbcTemplate jdbcTemplate;


    @Autowired
    public EmailService emailService;

    protected static User CurrA;
    protected static User CurrUser;



    @RequestMapping(value="/signIn",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView signIn(HttpServletRequest request, String username,String password) throws ClassNotFoundException,NullPointerException
    {
        String sqlSignIn = "select * from users where username = '"+username+"'";

        if(username == null ||username.equals("null"))
        {
            return new ModelAndView("signIn.html");
        }
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //ModelAndView mv = new ModelAndView("signIn.html");
       // List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        Map<String,Object> m = new HashMap<String,Object>();

        m = jdbcTemplate.queryForMap(sqlSignIn);
        if(m.get("password").equals(password))
        {
            m.get("user_id");
            String roleSql = "select role_id from has where user_id = "+m.get("user_id")+";";
            User aUser = new User((Integer) (m.get("user_id")),(String)(m.get("username")));

            int role_id = jdbcTemplate.queryForObject(roleSql, Integer.class);
            aUser.setRoleCode(role_id);
            //user completed
            ModelAndView mv = null;

            HttpSession session = request.getSession();
            //session.setAttribute("currentUser", aUser);

            switch (aUser.getRoleCode())
            {
                case 1:
                     mv= new ModelAndView("student_main.html");
                    CurrUser = aUser;
                    session.setAttribute("currentU", aUser);
                    mv.addObject("aUser",aUser);

                     break;

                case 3:
                    mv =new ModelAndView("admin_main.html");
                    CurrA = aUser;

                    session.setAttribute("currentA", aUser);
                    mv.addObject("aUser",CurrA);

                    break;
                case 2:
                    mv = new ModelAndView("instor_main.html");
                    break;
                default:
                    System.out.println("no such role resource");
            }

            assert mv != null;

            return mv;




        }


        return new ModelAndView("signIn.html");


    }
    @RequestMapping(value="/newSignIn",method = RequestMethod.GET)
    public ModelAndView newSignIn()
    {
        return new ModelAndView("signIn.html");

    }




    public List<Course> mainLoadClass(int schoolCode)
    {
        // Set<Course> sc = new LinkedHashSet<Course>();
        String sqlLoad = " select class_id, location,classes.time,classes.price,classes.startDate, classes.classCount,classes.showEnable, classes.class_name,teacher,description from classes  order by class_id;";
        //String sqlLoad = "select classes.class_id, catalogue_courses.course_abbreviation from classes join student_portal.is using(class_id) join catalogue_courses using(course_abbreviation)";
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        Map<String,Object> courseMap =new HashMap<String,Object>();
        l =jdbcTemplate.queryForList(sqlLoad);
        List<Course> courseList = new ArrayList<Course>();
        for(Map<String,Object> m : l) {

            Course aCourse = new Course((Integer) m.get("class_id"), (String) m.get("teacher"), (String) m.get("location"), (String) m.get("time"), (String) m.get("class_name"), (Double) m.get("price"),(Integer)m.get("showEnable"));
            aCourse.setTime((String)m.get("time"));
            aCourse.setNc((Integer) m.get("classCount"));
            try{
                aCourse.setDescription(m.get("description").toString());
            }catch (Exception e)
            {

            }

            if(m.get("startDate")!= null)
            {
                aCourse.setsTime(m.get("startDate").toString());
            }
            courseList.add(aCourse);

        }

        return courseList;


    }
    @GetMapping("/api/classList")
    public ResponseEntity<Course[]> passClasses()
    {
        UserController uc = new UserController();
        List<Course> lc = mainLoadClass(0);
        Course[] cArray = uc.convertListToArry(lc);
        return ResponseEntity.ok(cArray);
    }

    @GetMapping("/api/showAbleCourses")
    public ResponseEntity<Course[]> showAbleCourses()
    {
        UserController uc = new UserController();
        List<Course> lc = mainLoadClass(0);

        for(int i =0;i<lc.size();i++)
        {
            if(lc.get(i).getShowEnable() != 1)
            {
                lc.remove(i);
                i=i-1;
            }
        }
        Course[] cArray = uc.convertListToArry(lc);
        return ResponseEntity.ok(cArray);
    }
    @RequestMapping(value="/index",method = RequestMethod.GET)
    public ModelAndView mainPage()
    {
        ModelAndView mv  =new ModelAndView("index.html");

        return mv;
    }

    @RequestMapping(value="/signUp",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView signUp()
    {
        ModelAndView mv = new ModelAndView("signUp.html");
        return mv;
    }
    @RequestMapping(value="/signUpCheck",method = RequestMethod.GET)
    public ModelAndView signUpCheck(String username, String number, String password, String zip, String email, String repeat)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        ModelAndView mv = new ModelAndView("signUp.html");
        String reminder = "error";
        if(!password.equals(repeat))
        {
            reminder = "password error";
            mv.addObject("reminder",reminder);
            return mv;
        }
        else if(!sqlClass.invalidName(username)){

            reminder = "used username error please try with other names";
            mv.addObject("reminder",reminder);
            return mv;
        } else {
            sqlClass.newUsers(username,number,email,zip,password);
            reminder ="success";
            mv.addObject("reminder",reminder);
            return mv;

        }
    }

//    public Map<String,File> loadFile(int role)
//    {
//        Map<String,File> imgeFile= new HashMap<Sting,File>();
//
//
//    }
@GetMapping("/api/getStuImage")
public void getStuImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //method of get a static image
        response.setContentType("image/png");
    ClassPathResource resource = new ClassPathResource("pics/s_test.png", this.getClass().getClassLoader());
    //File file = new File("s_test.png");
    File file = resource.getFile();
    InputStream in;
    try {
        in = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        while (in.read(b) != -1) {
            os.write(b);
        }
        in.close();
        os.flush();
        os.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



//    @RequestMapping(value = "/index",method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView index() throws ClassNotFoundException
//    {
//
//        int test = 0;
//
//        if(test == 0)
//        {
//            //return signIn("null","null");
//        }
//
//        return new ModelAndView();
//
//
//    }



    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getUsers(){
        String sql="select * from users";//SQL查询语句
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        l = jdbcTemplate.queryForList(sql);
        Map<String ,Object>item = l.get(0);
        return l;
    }


    @RequestMapping(value ="/ourAward", method = RequestMethod.GET)
    public ModelAndView ourAward()
    {
        ModelAndView mv = new ModelAndView("ourAward.html");

        return mv;

    }
    @RequestMapping(value = "/ourCoaches", method = RequestMethod.GET)
    public ModelAndView ourCoaches()
    {
        ModelAndView mv = new ModelAndView("OurCoaches.html");
        return  mv;
    }
    @RequestMapping(value ="/whyUs", method = RequestMethod.GET)
    public ModelAndView whyUs()
    {
        ModelAndView mv = new ModelAndView("whyUs.html");
        return  mv;
    }

    @RequestMapping(value = "/microservice", method = RequestMethod.GET)
    public ModelAndView microservice()
    {
        ModelAndView mv = new ModelAndView("microservice.html");
        return mv;
    }
    @RequestMapping(value = "/classLeft", method = RequestMethod.GET)
    public ModelAndView classLeft()
    {
        ModelAndView mv = new ModelAndView("classLeft.html");
        return mv;
    }


    @GetMapping("/sendEmail")
    public String sendEmail(String to,String subject,String content) {
        try {
            emailService.sendEmail(to, subject, content);
            return "Email sent successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email";
        }
    }
    @RequestMapping("/trailClassForm")
    public ModelAndView TrailClassForm(@RequestParam("id") int id)
    {
        ModelAndView mv =new ModelAndView("TrailClassForm.html");
        mv.addObject("id",id);
        return mv;

    }
    @PostMapping("/api/TrailClassForm")
    public String trailClassForm(@RequestParam int id) {
        return "redirect:/trailClassForm?id=" + id;
    }

    @PostMapping("/api/postTrailApp")
    public ResponseEntity<String> postTrailApp(@RequestBody TrailClassAppRequest trailClassAppRequest)
    {
        String subject="";
        String content ="";
        String to="";
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        Course target = sqlClass.searchAClass( Integer.parseInt(trailClassAppRequest.getClass_id()),0);
        subject = "You have ordered a Trail Class";
        content ="Thank You for registering our class, please read below text before you come. <br> address: <br> "+target.getLocation()+" <br>time:<br>"+target.getTime()+"("+trailClassAppRequest.getDate()+")"+ "<br> Notes: <br>"+target.getDescription();
        content += "Thank you.<br>";
        if(!target.getMode().equals("unknown"))
        {
            content+="<br> please note that the class is a <h2>"+target.getMode()+"</h2>";
        }
        to = trailClassAppRequest.getTrailEmail();
        String result = sendEmail(to,subject,content);
        subject = "You have recieved a new trail student";
        content = "You have recieved a new trial class <br> date: <br>"+ trailClassAppRequest.getDate()+"<br> student name:"+trailClassAppRequest.getApplicant()+"<br> number <br>"+trailClassAppRequest.getNumber()+"<br> on class:"+ target.getName();
        to = "bayarea.skatingclub@gmail.com";
        String adminNotice = sendEmail(to,subject,content);
        if(result.equals("Email sent successfully"))
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("success registered the trailClass " + target.getName(), headers, HttpStatus.OK);
        }
        else
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("Fail to register current class:  " + target.getName()+" please contact our sales: 4088889452", headers, HttpStatus.OK);
        }

    }

//    @PostMapping("/api/getRestClass")
//    public ResponseEntity<RestClassRespond> getRestClass(@RequestBody RestClassRequest rr)
//    {
//        SqlClass sqlClass =new SqlClass(jdbcTemplate);
//        //sqlClass.getCanceledClassNum()
//
//    }

    @PostMapping("/testClassLeft")
    public ResponseEntity<RestClassRespond> getCanceledClass(@RequestBody RestClassRequest rr)
    {
        try {
            int class_id = rr.getClass_id();
            Calendar calendar = Calendar.getInstance();

            // Set the year, month, and day
//        calendar.set(Calendar.YEAR, 2023);
//        calendar.set(Calendar.MONTH, Calendar.JULY); // Note: Month is zero-based, so July is represented as 6
//        calendar.set(Calendar.DAY_OF_MONTH, 20);

            // Get the Date object representing July 20, 2023
            Date start = rr.getStart();

            Calendar c = Calendar.getInstance();

            Date end =c.getTime();


            String sql = "select c.time, cc.date from classcanceled as cc join classes as c on c.class_id = cc.class_id  where cc.class_id = (?) and date>= (?) and date <= (?)";
            List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, class_id,start,end);
            Date []dArray = new Date[list.size()];
            for(int i = 0;i<list.size();i++)
            {
                dArray[i] = (Date) list.get(i).get("Date");

            }
//if the list is empty we get day by the jdbc
            String day;
            if(list.size()==0)
            {
                String sqlTime ="select time from classes where class_id=(?);";
                day = jdbcTemplate.queryForObject(sqlTime,String.class,class_id);
                day = day.substring(0,3);
            }
            else {
                day = list.get(0).get("time").toString();
                day = day.substring(0, 3);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String ss = sdf.format(start);
            String se = sdf.format(end);


            List<String> classes = generateDateSchedule(ss,rr.getPaidClasses(), day, dArray);
            List<String > classLeft = filterDates(classes,se);
            String [] arr = classLeft.toArray(new String[0]);
            RestClassRespond respond = new RestClassRespond(class_id,classLeft.size(),classLeft.get(classLeft.size()-1),arr,classes.toArray(new String[0]));
            return ResponseEntity.ok(respond);

        }catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestClassRespond());
        }



    }
    public static List<String> filterDates(List<String> dateStrings, String currentDate) {
        List<String> filteredDates = new ArrayList<>();

        // Define a date format for parsing and comparing the dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            // Parse the current date
            Date currentDateParsed = dateFormat.parse(currentDate);

            // Iterate through the list of date strings
            for (String dateString : dateStrings) {
                // Parse each date string
                Date date = dateFormat.parse(dateString);

                // Compare the parsed date with the current date
                if (date.after(currentDateParsed)) {
                    filteredDates.add(dateString);
                }
            }
        } catch (ParseException e) {
            // Handle parsing errors if needed
            e.printStackTrace();
        }

        return filteredDates;
    }



    public  List<String> generateDateSchedule(String startDateString, int courseLengthInWeeks, String dayOfWeek,Date[] canceledClass) {
        List<String> schedule = new ArrayList<>();
        courseLengthInWeeks += canceledClass.length;


        List<String> dw = Arrays.asList(
                "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"
        );
//        Map weekDayMap= new HashMap<>();
//        weekDayMap.put("Mon","Monday");
        Map<String, String> weekDayMap = new HashMap<>();
        weekDayMap.put("mon", "Monday");
        weekDayMap.put("tue", "Tuesday");
        weekDayMap.put("wed", "Wednesday");
        weekDayMap.put("thu", "Thursday");
        weekDayMap.put("fri", "Friday");
        weekDayMap.put("sat", "Saturday");
        weekDayMap.put("sun", "Sunday");
        if(dayOfWeek.length() <=3)
        {
            dayOfWeek = weekDayMap.getOrDefault(dayOfWeek.toLowerCase(),"N/A");
            if(dayOfWeek.equals("N/A"))
            {
                return schedule;
            }
        }
        else {
            if(!dw.contains(dayOfWeek.toLowerCase())){
                return  schedule;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            Date startDate = sdf.parse(startDateString);
            calendar.setTime(startDate);

            for (int i = 0; i < courseLengthInWeeks; i++) {
                while (!calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, java.util.Locale.US).equalsIgnoreCase(dayOfWeek)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                schedule.add(sdf.format(calendar.getTime()));

                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
// check if the schedule time contain canceled class
        for(Date d: canceledClass)
        {
            String formatD = sdf.format(d);
            if(schedule.contains(formatD))
            {
                schedule.remove(formatD);
            }
        }

        return schedule;
    }





}
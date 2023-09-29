package com.example.student_portal.controller;

import com.example.student_portal.apiModule.CancelClassRequest;
import com.example.student_portal.apiModule.RestClassRespond;
import com.example.student_portal.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import static com.example.student_portal.controller.mainCont.CurrA;
import static com.example.student_portal.controller.mainCont.CurrUser;

// 4000 lines back-end code,and 20 front-end pages, completed by Steven Chen @
//Charles W. Davidson College of Engineering, SJSU


@Controller()
public class AdminController {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    //public SqlClass sqlClass = new SqlClass(jdbcTemplate);

//    public User CurrA;
    classPersonDataRow [] applicationArr;
    @RequestMapping(value = "/adminMain",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adminMain(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");

        ModelAndView mv = new ModelAndView("admin_main.html");
        mv.addObject("aUser",u);

        return mv;

    }


    @PostMapping("/api/enableShow")
    public ResponseEntity<String> enableShow(@RequestBody int respondId)
    {
        SqlClass sc = new SqlClass(jdbcTemplate);
        int result = sc.enableClass(respondId);
        if(result ==1)
        {

            //return a http headers rather than string directly
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("success enabled the class " + respondId, headers, HttpStatus.OK);
        }
        else{
            return ResponseEntity.ok("error");
        }

    }
    @PostMapping("/api/deleteClass")
    public ResponseEntity<String> deleteClass(@RequestBody int respondId)
    {
        SqlClass sc = new SqlClass(jdbcTemplate);
        int result = sc.deleteClass(respondId);
        if(result ==1)
        {

            //return a http headers rather than string directly
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("success deleted the class " + respondId, headers, HttpStatus.OK);
        }
        else{
            return ResponseEntity.ok("error");
        }

    }

    @GetMapping("/api/getClassBoards")
    public  ResponseEntity<ClassDescription[]> getClassBoards()
    {
        ClassDescription[] bArr = new ClassDescription[3];
        SqlClass sc = new SqlClass(jdbcTemplate);
        bArr = sc.getClassBoards();
        return  ResponseEntity.ok(bArr);
    }
    @PostMapping("/api/updateClassBoards")
    public ResponseEntity<String> updateClassBoards( @RequestBody ClassDescription[] cdArr)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result = sqlClass.postClassBoards(cdArr);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        if(result==1)
        {
            return new ResponseEntity<>("class boards update successfully",headers,HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("error",headers,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/absentHandler",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adminAbesent(HttpServletRequest request)
    {


        ModelAndView mv  = new ModelAndView("adminAbsent.html");
        //mv.addObject()
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        absentData[] al = sqlClass.absentArray();
        mv.addObject("absentList",al);
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);

        return mv;

    }

    @RequestMapping(value = "/createClass",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView createClass(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("newClass.html");
        mv.addObject("Course",new Course());
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
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
    @RequestMapping(value = "/videoMp", method = RequestMethod.GET)
    public ModelAndView videoMp(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentA");
        ModelAndView mv = new ModelAndView("videoMp.html");
        mv.addObject("aUser",user);
        return  mv;
    }
    @RequestMapping(value = "/adCancelCLass", method = RequestMethod.GET)
    public ModelAndView adCancelClass(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentA");
        ModelAndView mv = new ModelAndView("adCancelClass.html");
        mv.addObject("aUser",user);
        return  mv;
    }

    @RequestMapping(value = "/registrations" ,method = RequestMethod.GET)
    public ModelAndView registerHandling(HttpServletRequest request)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        List<classPersonDataRow> lc = sqlClass.applicationsShow();
        ModelAndView mv = new ModelAndView("applicationHandler.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);


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
    public ModelAndView dropHandle(String classId ,HttpServletRequest request)
    {
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        if(classId == null)
        {
        ModelAndView mv = new ModelAndView("dropHandler.html");
            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("currentA");
            mv.addObject("aUser",u);

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
            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("currentA");
            mv.addObject("aUser",u);



            return mv;
        }

    }

    @RequestMapping(value = "/adDropExute")
    public ModelAndView adDropExute( int user_id, int class_id,String comment,HttpServletRequest request) throws ParseException {
        classOfaCourse coc = new classOfaCourse(class_id,user_id,getDateString(),getDateString(),"admin: "+comment);
        List<classOfaCourse> cl = new ArrayList<>();
        cl.add(coc);
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        sqlClass.userDrop(cl,user_id,comment);

        ModelAndView mv = new ModelAndView("dropHandler.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);

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
    public ModelAndView classRank(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("Statistics.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
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
    public ModelAndView instRank(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("Statistics.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
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
    public ModelAndView adStatic(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("Statistics.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
        Course [] cArr = new Course[0];
        instructorWithSnum []  iArr= new instructorWithSnum[0];
        UserConsumption [] uArr = new UserConsumption[0];

        mv.addObject("classList",cArr);
        mv.addObject("instList",iArr);
        mv.addObject("userList",uArr);

        return mv;

    }
    @RequestMapping(value = "/adSetting")
    public ModelAndView adSetting(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("adSetting.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
        return mv;
    }

    @RequestMapping(value = "consumptionRank")
    public ModelAndView consumptionRank(String sDate, String eDate,HttpServletRequest request) throws ParseException {
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
            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("currentA");
            mv.addObject("aUser",u);
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
            HttpSession session = request.getSession();
            User u = (User)session.getAttribute("currentA");
            mv.addObject("aUser",u);
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

    @RequestMapping(value = "/adHp", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adHomePageManager(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("adHp.html");
        //mv.addObject("aUser",CurrA);
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
        return mv;
    }
    @RequestMapping(value = "/classTypesMan", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView classTypesMan(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("classTypesMan.html");
//        mv.addObject("aUser",CurrA);
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);

        return mv;
    }

    @RequestMapping(value = "/awardsMan",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView awardsMan(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("awardsMan.html");
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("currentA");
        mv.addObject("aUser",u);
        return mv;

    }
    @RequestMapping(value = "/coachMp", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView coachMp(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("coachMp.html");
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("currentA");
        mv.addObject("aUser",u);
        return  mv;
    }
    @RequestMapping(value = "/whyUsMp",method = RequestMethod.GET)
    public ModelAndView  whyUsMp(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("whyUsMp.html");
        HttpSession session = request.getSession();
        User u=(User) session.getAttribute("currentA");
        mv.addObject("aUser",u);
        return  mv;
    }

    @RequestMapping(value = "/hpBoardMp",method = RequestMethod.GET)
    public ModelAndView  hpBoardMp(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("HpBoardMp.html");
        HttpSession session = request.getSession();
        User u=(User) session.getAttribute("currentA");
        mv.addObject("aUser",u);
        return  mv;
    }
//api
    @GetMapping("/api/getAwards")
    public ResponseEntity<Award[]> getAwards(){
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        Award[] awards = sqlClass.getAward();
        return ResponseEntity.ok(awards);
    }

    @PostMapping("/api/addNewAward")
    public ResponseEntity<String> addaWard(@RequestBody Award award)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result= sqlClass.addAward(award);
        if(result==1)
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("success add award  " + award.getName(), headers, HttpStatus.OK);

        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("add award error  " + award.getName(), headers, HttpStatus.OK);
        }
    }
    @PostMapping("/api/updateAward")
    public ResponseEntity<String> updateAward(@RequestBody Award award)
    {
        SqlClass sql = new SqlClass(jdbcTemplate);
        int result= sql.updateAward(award.getId(),award);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        if( result==1 )
        {
            return new ResponseEntity<>("success update award  " + award.getName(), headers, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("update award error  " + award.getName(), headers, HttpStatus.OK);
        }
    }
    @PostMapping("/api/removeAward")
    public  ResponseEntity<String> removeAward(@RequestBody Award award)
    {
        SqlClass sql = new SqlClass(jdbcTemplate);
        int result= sql.removeAward(award.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        if( result==1 )
        {
            return new ResponseEntity<>("success removed award  " + award.getName(), headers, HttpStatus.OK);
        }
        else {

            return new ResponseEntity<>("removed award error  " + award.getName(), headers, HttpStatus.OK);
        }

    }


    @PostMapping("/api/disableShow")
    public ResponseEntity<String> disableShow(@RequestBody int respondId)
    {
        SqlClass sc = new SqlClass(jdbcTemplate);
        int result = sc.disableClass(respondId);
        if(result ==1)
        {

            //return a http headers rather than string directly
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("success disabled the class " + respondId, headers, HttpStatus.OK);
        }
        else{
            return ResponseEntity.ok("error");
        }

    }
    @GetMapping ("/api/getCoaches")
    public ResponseEntity<Coach []> getCoaches()
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        return ResponseEntity.ok(sqlClass.getCoaches());
    }
    @PostMapping("/api/updateCoach")
    public ResponseEntity<String> updateCoach(@RequestBody Coach coach)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result = sqlClass.updateCoach(coach);
        if(result ==1)
        {
            return new ResponseEntity<>("Success updated the coach "+coach.getId(),headers,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("failed to updated the coach "+coach.getId(),headers,HttpStatus.OK);
        }

    }
    @PostMapping("/api/removeCoach")
    public ResponseEntity<String> removeCoach(@RequestBody int id)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        int result= sqlClass.deleteCoach(id);
        if(result==1)
        {
            return new ResponseEntity<>("Success delete the coach "+id ,headers,HttpStatus.OK);
        }
        else {
           return new ResponseEntity<>("failed to delete coach"+id,headers,HttpStatus.OK);
        }
    }

    @PostMapping("/api/getBoards")
    public ResponseEntity<BoardInfo[]> getBoards(@RequestBody int type)
    {
        SqlClass sqlClass =new SqlClass(jdbcTemplate);
        BoardInfo[] boards= sqlClass.getBoards(type);
        return ResponseEntity.ok(boards);

    }

    @PostMapping("/api/addCoach")
    public ResponseEntity<String> addCoach(@RequestBody Coach coach)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        int result = sqlClass.addCoach(coach);
        if(result ==1)
        {
            return new ResponseEntity<>("Success updated the coach "+coach.getName(),headers,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("failed to updated the coach "+coach.getName(),headers,HttpStatus.OK);
        }

    }
    @PostMapping("/api/updateBoard")
    public ResponseEntity<String> updateBoard(@RequestBody BoardInfo b)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        int type = Integer.parseInt(b.getType());

        if(type==0)
        {
            SqlClass sqlClass =new SqlClass(jdbcTemplate);
            int result= sqlClass.updateBoard(type,b);
            if(result==1)
            {
                return new ResponseEntity<>("Success Update the board "+b.getId() ,headers,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("failed to update board"+b.getId(),headers,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("failed to do this job "+b.getId(),headers,HttpStatus.OK);
    }

    @PostMapping("/api/addBoard")
    public ResponseEntity<String> addBoard(@RequestBody BoardInfo b)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        int type = Integer.parseInt(b.getType());

        if(type==0)
        {
            SqlClass sqlClass = new SqlClass(jdbcTemplate);
            int result = sqlClass.addBoard(type,b);
            if(result ==1)
            {
                return new ResponseEntity<>("Success add the board "+b.getName(),headers,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("failed to add the board "+b.getName(),headers,HttpStatus.OK);
            }
        }

         return new ResponseEntity<>("failed to add the board "+b.getName(),headers,HttpStatus.OK);

    }
    class rmInstructor
    {
        private int id;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public rmInstructor() {
        }
    }
    @PostMapping("/api/removeBoard")
    public ResponseEntity<String> removeBoard(@RequestBody BoardInfo b)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        int type = Integer.parseInt(b.getType());
        if(type==0)
        {
            SqlClass sqlClass =new SqlClass(jdbcTemplate);
            int result= sqlClass.deleteBoard(Integer.parseInt(b.getType()),b);

            if(result==1)
            {
                return new ResponseEntity<>("Success delete the board "+ b.getId() ,headers,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("failed to delete board"+ b.getId(),headers,HttpStatus.OK);
            }

        }


        return new ResponseEntity<>("failed to delete board" + b.getId(),headers,HttpStatus.OK);

    }

    @PostMapping("/api/formClassData")
    public ResponseEntity<String> removeBoard(@RequestBody MutiDurations[] durations)
    {
        return new ResponseEntity<>("failed to delete board" ,HttpStatus.OK);
    }
    @PostMapping("/createCancel")
    public ResponseEntity<String> recordCanceled(@RequestBody CancelClassRequest cr)
    {
        try {
            String sqlCheck = "Select class_id from classes where class_id=(?)";
            List<Map<String,Object>> l = jdbcTemplate.queryForList(sqlCheck,cr.getClass_id());
            if(l.size() == 0)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error class id");
            }


            java.sql.Date sqlDate = new java.sql.Date(cr.getDate().getTime());

            String sql = "INSERT INTO classcanceled (class_id,date,reason) VALUES (?,?,?);";
            int result =jdbcTemplate.update(sql,cr.getClass_id(),sqlDate,cr.getReason());
            if(result!=1)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error insert");

            }
            HttpHeaders headers =new HttpHeaders();

            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("canceled a class",headers,HttpStatus.OK);
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }



    }



}

package com.example.student_portal.module;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import static jdk.internal.org.jline.utils.Colors.s;

public class SqlClass {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public SqlClass(JdbcTemplate t)
    {
        this.jdbcTemplate = t;
    }
    //a method that load all classes, Classes as map<String,Object> are added into a List.
    public List<Map<String,Object>> loadClass(int schoolCode)
    {
        Set<Course> sc = new LinkedHashSet<Course>();
        String sqlLoad = "select class_id, location,classes.time, classes.enrollment,classes.price,classes.startDate, classes.endDate,classes.classCount,class_name,teacher,description,showEnable " +
                "from classes  " +
                "order by class_id;";

        //String sqlLoad = "select classes.class_id, catalogue_courses.course_abbreviation from classes join student_portal.is using(class_id) join catalogue_courses using(course_abbreviation)";
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        Map<String,Object> courseMap =new HashMap<String,Object>();
        l =jdbcTemplate.queryForList(sqlLoad);
        //courseMap = l.get(0);
        return l;
    }
    public Course[] loadClassIntoArray()
    {
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        l =  loadClass(0);
        List<Course> lc = new ArrayList<>();
        for(Map<String,Object> m :l)
        {
            Course c = new Course();
            c.setC_id((Integer)m.get("class_id"));
            c.setName(m.get("class_name").toString());
            c.setLocation(m.get("location").toString());
            c.setPrice((double)m.get("price"));
            c.setTime(m.get("time").toString());
            if(m.get("startDate") !=null) {
                c.setsTime(m.get("startDate").toString());
            }
            c.setEnrollment((Integer) m.get("enrollment"));

            c.setInstructor(m.get("teacher").toString());
            c.setDescription(m.get("description").toString());
            lc.add(c);

        }
        Course[] cArr = new Course[lc.size()];
        int i = 0;
        for(Course c : lc)
        {
            cArr[i] = c;
            i++;
        }
        return cArr;

    }
    public Map<String,Object> loadSpeClass(int class_id)
    {
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        l=loadClass(0);
        Map<String,Object> target = new HashMap<>();
        for(Map<String,Object>elment : l)
        {

           if(((Integer)elment.get("class_id")) == class_id)
           {
               target = elment;
               return target;
           }
        }
        return target;
    }


    //get class with class_id and the course have classCount;
    public Course loadSpeClassByCourse(int class_id)
    {
        Map<String,Object> m = new HashMap<String,Object>();
        m = loadSpeClass(class_id);
        Course aCourse = new Course((Integer) m.get("class_id"),(String) m.get("username"),(String) m.get("location"),(String) m.get("time"),(String) m.get("course_name"),(Double)m.get("price"));
        //value the number of classhour;
        aCourse.setNc((Integer) m.get("classCount"));

        return aCourse;

    }

    public List<Course> userTokeClass(int user_id)
    {  String id = String.valueOf(user_id);
        String sql = "";
        List<Map<String,Object>> l= new ArrayList<Map<String,Object>>();
        sql = "select class_id, location, time,classes.startDate,classes.endDate, classes.classCount,classes.price, classes.class_name, teacher " +
                "from classes join  " +
                "register using(class_id) where register.user_id = " +
                id +
                ";";
        l = jdbcTemplate.queryForList(sql);
        LinkedList<Course> lc = new LinkedList<>();
        for(Map<String,Object> m: l)
        {
            Course c = new Course((Integer) m.get("class_id"),(String) m.get("teacher"),(String) m.get("location"),(String) m.get("time"),(String) m.get("class_name"),(Double)m.get("price"));
            lc.add(c);
        }
        return lc;
    }

    public List<Course> showtoke(int role,int user_id)
    {
        String id = String.valueOf(user_id);
        String sql = "";
        List<Map<String,Object>> l= new ArrayList<Map<String,Object>>();
        if(role ==1 )
        {
//            sql = "select class_id, location, classes.time, classes.price, course_name from classes " +
//                    "join student_portal.is using(class_id) join catalogue_courses using (course_abbreviation) join register using(class_id) where user_id = " +
//                    id +
//                    ";";
            sql = "select class_id, location, time,classes.startDate,classes.endDate, classes.classCount,classes.price, classes.class_name, teacher " +
                    "from classes join  " +
                    "register using(class_id) where register.user_id = " +
                    id +
                    ";";
            l = jdbcTemplate.queryForList(sql);
            return classListConvert(l);


        }
        else {

        }
        return null;

    }

    public List<Course> classListConvert(List<Map<String,Object>> list)
    {
        List<Course> lc = new ArrayList<Course>();
        for(Map<String,Object>m : list)
        {
            if(m.get("price") == null)
            {
                m.replace("price",0.0);
            }
            Course aCourse = new Course((Integer) m.get("class_id"),(String) m.get("teacher"),(String) m.get("location"),(String) m.get("time"),(String) m.get("class_name"),(Double)m.get("price"));
            aCourse.setNc((Integer) m.get("classCount"));
            if(m.get("startDate")!= null)
            {
                aCourse.setsTime(m.get("startDate").toString());
            }



            lc.add(aCourse);
        }
        return lc;
    }
//write a row of application into the application table
    public boolean writeAnApplication( int u_id, int c_id,String comment)
    {
        boolean success = false;
        String uId = Integer.toString(u_id);
        String cId = Integer.toString(c_id);
        //String rC = Integer.toString(cont);
        Date d = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(dateFormat.format(d));
        String sql = "Insert Into application (user_id,class_id, comment,registerDate,active)" +
                "values" +
                "("+uId+","+c_id+",'"+comment+"','"+dateFormat.format(d)+"',false);";
        jdbcTemplate.execute(sql);

        //user balance add
        String classCheck = "Select price,classCount from classes where class_id ="+c_id+";";
        List<Map<String,Object>> l = new ArrayList<>();
        l = jdbcTemplate.queryForList(classCheck);
        double price = (Double)l.get(0).get("price");
        int count = (Integer)l.get(0).get("classCount");
        double balance = price*count;

        String sqlBalance = "UPDATE users SET balance = balance+ "+balance+" where user_id = "+u_id+";";
        jdbcTemplate.update(sqlBalance);

        return true;

    }

    public boolean userAbsent(List<classOfaCourse> lc)
    {
        boolean success = false;
        for(int i = 0; i<lc.size();i++)
        {
            String sql = "UPDATE register SET absent = absent+ 1 where user_id = "+Integer.toString(lc.get(i).user_id)+" and class_id ="+Integer.toString(lc.get(i).getC_id())+";";
            jdbcTemplate.execute(sql);
            //String m = "+\""+lc.get(i).getMd()+"\"";
            sql = "Insert Into absent (user_id,class_id, referDate,absentDate,comment,makeUp) values ("+Integer.toString(lc.get(i).getUser_id())+","+lc.get(i).getC_id()+",'"+lc.get(i).getsReferDate()+"','"+lc.get(i).sDateOfAbsent+"','"+lc.get(i).comment+"','"+lc.get(i).getMd()+"');";
            jdbcTemplate.execute(sql);


        }

        //absent table:


       return true;


    }
    public boolean userDrop(List<classOfaCourse> courses,int user_id,String comment)
    {
        boolean success = false;
        for(int i = 0;i<courses.size();i++)
        {
            String sql = "delete from register where user_id ="+Integer.toString(user_id)+" and class_id ="+courses.get(i).getC_id()+";";
            jdbcTemplate.execute(sql);

           // sql = "Insert Into dropTable (user_id,class_id, dropDate, comment) values ("+Integer.toString(user_id)+","+Integer.toString(courses.get(i).getC_id())+",'"+courses.get(i).sReferDate+"','"+comment+"');";
           // jdbcTemplate.execute(sql);

        }

        return true;

    }

    public void userUpdate(User aUser)
    {
        String sql = "UPDATE users SET userName = '"+aUser.getName()+"', phone = '"+aUser.getNumber()+"', email = '"+aUser.getEmail()+"', zip='"+aUser.getZip()+"' where user_id = "+Integer.toString(aUser.getId())+";";
        jdbcTemplate.execute(sql);

    }

    public boolean checkDuplicate(User u,User Curr)
    {
        //name never changed
        if(u.getName().equals(Curr.getName()))
        {
            return false;
        }
        String name = u.getName();
        String sql = "select count(userName) from users where username = '"+name+"'";
        int count =  jdbcTemplate.queryForObject(sql, Integer.class);
        if(count!=0)
        {
            System.out.println("duplicate name");
            return true;
        }
        return false;
    }
    public int totalSizeOfClass ()
    {
        String sql = "select count(class_id) from classes;";
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count;
    }

    public int addClass(Course course)
    {
        int id = idGeneration(0);
        course.setC_id(id);
        int result= 0;
        String s = ",\""+course.getName()+"\",\""+course.getLocation() + "\",\"" + course.getTime() + "\"," + course.getPrice() + "," + course.getNc() + ",\"" + course.getsTime() + "\",\"" + course.geteTime() + "\");";
        String sql ="Insert Into classes (class_id,teacher,enrollment, capacity,class_name,location,time, price, classCount,startDate,endDate) values " +
                "("+Integer.toString(course.c_id)+","+"'"+course.getInstructor()+"',"+Integer.toString(0)+","+Integer.toString(course.getMaxStudent())+s;
        String sql1 = "Insert Into classes (class_id,teacher,enrollment, capacity,class_name,location,time, price, classCount,startDate,endDate,description,mode_of_instruction) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        result=jdbcTemplate.update(sql1,course.getC_id(),course.getInstructor(),course.getEnrollment(),course.getMaxStudent(),course.getName(),course.getLocation(),course.getTime(),course.getPrice(),course.getNc(),course.getsTime(),course.geteTime(),course.getDescription(),course.getMode());


        return result;

    }
    public int idGeneration(int type)
    {
        String sql;
        String title;
        if(type==0)
        {
            //class id
            sql = "Select class_id from classes";
            title = "class_id";
        }
        else if(type==1){
            //user_id
            sql = "Select user_id from users";
            title = "user_id";
        }
        else if(type==2)
        {
            sql = "Select id from awards";
            title = "id";
        }else if(type==3) {
            sql = "Select coach_id from coach_info";
            title = "coach_id";
        }
        else if(type==4){
            sql = "Select id from board";
            title = "id";
        }
        else if(type==5)
        {
            sql = "select id from videolinks;";
            title = "id";
        }
        else {
            sql = "error";
            title="error";
        }
        List<Map<String,Object>> reList = new ArrayList<Map<String,Object>>();
        reList = jdbcTemplate.queryForList(sql);
        List<Integer> idList = new LinkedList<Integer>();
        for(Map<String,Object> m : reList)
        {
            idList.add((Integer) m.get(title));
        }
        int randomNumber = new Random().nextInt(9000) + 1000;
        //check repeat:
        Collections.sort(idList);
        int low = 0;
        int high = idList.size()-1;
        int mid=0;
        while(low<high)
        {
            mid = (low+high)/2;
            if(randomNumber<idList.get(mid))
            {
                high = mid-1;
            }else if(randomNumber>idList.get(mid))
            {
                low = mid + 1;
            }
            else {
                randomNumber = new Random().nextInt(9000) + 1000;
                low = 0;
                high = idList.size()-1;
            }

        }
        return  randomNumber;

    }
    public int disableClass(int id)
    {
        String sql = "update classes SET showEnable = 0 where class_id = '"+id+"';";
        int result = jdbcTemplate.update(sql);
        return result;
    }

    public int enableClass(int id)
    {
        String sql = "update classes SET showEnable = 1 where class_id = '"+id+"';";
        int result = jdbcTemplate.update(sql);
        return result;
    }
    public int deleteClass(int id)
    {
        String sql = "delete from classes where class_id = "+id+";";
        int result = jdbcTemplate.update(sql);
        return result;
    }



    //return a list of class of Course
    public List<classPersonDataRow> applicationsShow()
    {

        List<classPersonDataRow> lc = new ArrayList<>();
        String sql = "SELECT * FROM student_portal.application;";
        List <Map<String,Object>>  l = new ArrayList<Map<String,Object>>();

        l = jdbcTemplate.queryForList(sql);


        for(Map<String,Object> m: l)
        {
            classPersonDataRow cpdr = new classPersonDataRow();
            cpdr.setClass_id((Integer)m.get("class_id"));
            if(m.get("orderCount") == null)
            {
                double price = (Double) getPriceAndCount(cpdr.getClass_id()).get("price");
                int count= (Integer) getPriceAndCount(cpdr.getClass_id()).get("classCount");
                double priceTotal = price*count;
                cpdr.setPrice(priceTotal);
                cpdr.setOrderCount(count);
            }
            else {
                double price = (Double) getPriceAndCount(cpdr.getClass_id()).get("price");
                int count= (Integer) m.get("orderCount");
                double priceTotal = price*count;
                cpdr.setPrice(priceTotal);
                cpdr.setOrderCount(count);

            }

            cpdr.setUser_id((Integer) m.get("user_id"));
            cpdr.setUsername(getUserNameById(cpdr.getUser_id()));

            if(m.get("comment") ==null)
            {
                cpdr.setComment("null");
            }
            else
            {
                cpdr.setComment(m.get("comment").toString());
            }

            cpdr.setApp_date(m.get("registerDate").toString());
            lc.add(cpdr);
        }
        return lc;

    }

    public Map<String,Object> getPriceAndCount(int class_id)
    {
        String sql = "select price,classCount from classes where class_id = "+Integer.toString(class_id)+";";
        List<Map<String,Object>> l = jdbcTemplate.queryForList(sql);
        return l.get(0);

    }
    public void updatePrice(int class_id)
    {
        String sql = "select price,classCount from classes where class_id = "+Integer.toString(class_id)+";";
        List<Map<String,Object>> l = jdbcTemplate.queryForList(sql);
        double newPrice = (Double) l.get(0).get("price")* 0.2;
        sql = "update classes set price ="+Double.toString(newPrice)+"where class_id ="+Integer.toString(class_id)+";";
        jdbcTemplate.execute(sql);
    }
    public void balanceDecs(int user_id, double balance)
    {
        String sql = "update users set balance = balance-"+balance+"where user_id ="+user_id+";";
        jdbcTemplate.execute(sql);
    }
    public int appOperater(classPersonDataRow cpdr, int command)
    {
        //approve
        String sql;
        String usql = null;
        int uResult =0;
        if(command == 1)
        {
            if (checkRegister(cpdr.getClass_id(), cpdr.getUser_id()))
            {
                sql = "update register set registerCount = registerCount + "+cpdr.getOrderCount()+",price paidCount = paidCount+"+(int)cpdr.getPrice()+" where class_id = "+cpdr.getClass_id()+" and user_id = "+cpdr.getUser_id()+";";
                orderBuilder(cpdr,1);
                //decrease balance
                balanceDecs(cpdr.getUser_id(), cpdr.getPrice());

            }
            else
            {
                String s ="\""+cpdr.getApp_date() +"\"";
                sql = "Insert Into register (class_id, user_id,paidCount ,registerCount , registerDate,absent) values (" +
                        cpdr.getClass_id() + "," + cpdr.getUser_id() + "," + (int) cpdr.getPrice() + "," + cpdr.getOrderCount() + "," +s+ "," + 0 + ");";
                //enrollment ++
                usql="update classes set enrollment = enrollment+1 where class_id = "+cpdr.getClass_id()+";";

                //new student order
                orderBuilder(cpdr,0);
                //decs balance
                balanceDecs(cpdr.getUser_id(), cpdr.getPrice());
                uResult = jdbcTemplate.update(usql);
            }
            int result = jdbcTemplate.update(sql);

            if (result>0 && uResult>0)
            {
                moneyReceived(cpdr);
                sql = "delete from application where class_id = "+cpdr.getClass_id()+" and user_id = "+cpdr.getUser_id()+";";


                return jdbcTemplate.update(sql);
            }
            else {
                return -1;
            }


        }
        if(command == -1)
        {
            // operator
            sql = "delete from application where class_id = "+cpdr.getClass_id()+" and user_id = "+cpdr.getUser_id()+";";
            balanceDecs(cpdr.getUser_id(), cpdr.getPrice());
            return jdbcTemplate.update(sql);
        }
        return  0;
    }
    public int orderBuilder(classPersonDataRow cpdr, int command){
        String sql;
        //command =1 continuous student
        if(command == 1)
        {
            cpdr.setContinuous(true);
        }
        else {
            cpdr.setContinuous(false);
        }
        String countSql = "select count(order_id) from orders";
        int id = jdbcTemplate.queryForObject(countSql, Integer.class)+1;
        String s ="\""+cpdr.getApp_date() +"\"";
        sql = "Insert Into orders (order_id, class_id, user_id, paid, registerDate,continuous_order) values ("+id+", " +
                cpdr.getClass_id() + "," + cpdr.getUser_id() + "," + cpdr.getPrice() + "," +s+ ","+cpdr.isContinuous()+" );";

        return jdbcTemplate.update(sql);



    }
    public Course[] orderByEnrollment()
    {
        String sql = "Select * from classes order by enrollment desc";
        List<Map<String,Object>> l = jdbcTemplate.queryForList(sql);
        List<Course> lc = new ArrayList<>();
        for(Map<String ,Object> m:l)
        {
            Course c= new Course();
            c.setC_id((Integer) m.get("class_id"));
            c.setName(m.get("class_name").toString());
            c.setEnrollment((Integer) m.get("enrollment"));
            lc.add(c);
        }
        return listToCourseArr(lc);


    }
//    class instructorWithSnum
//    {
//
//
//    }
    public  instructorWithSnum [] instructorList()
    {
        String getInstor = "select user_id from users join has using(user_id) where role_id = 2;";
        String sql ="select count(teach.class_id) as class_count, sum(classes.enrollment) as student_count ,users.username,teach.user_id from teach join classes using(class_id) join users using(user_id)group by user_id order by student_count desc;";
//        List<Map<String,Object>> l = new ArrayList<>();
//
//        l = jdbcTemplate.queryForList(getInstor);
//        List<Integer> idList = new LinkedList<>();
//        for(Map<String,Object> m : l)
//        {
//            int i= (Integer)m.get("user_id");
//            idList.add(i);
//        }
//        //idList is a list full of instructor id;
//        for(int i : idList)
//        {
//
//        }
        List<Map<String ,Object>> l = jdbcTemplate.queryForList(sql);
        List<instructorWithSnum> il = new ArrayList<>();

        for(Map<String,Object> m : l)
        {
            instructorWithSnum iws = new instructorWithSnum();
            Long li = (Long) m.get("class_count");

            iws.setClassCount(li.intValue());
            BigDecimal b = (BigDecimal) m.get("student_count");
//            iws.classCount = (Integer) m.get("class_count");
            iws.studentCount= b.intValue();
            iws.user_id = (Integer) m.get("user_id");
            iws.username = m.get("username").toString();
            iws.setAve();
            il.add(iws);
        }

        return listToInArr(il);
    }
    public Order[] getOrdersFromPeriod(String sDate, String eDate) throws ParseException {
        Date s = strToDate(sDate);
        Date e = strToDate(eDate);
        String sql = "select * from orders";
        List<Map<String,Object>> l =  new ArrayList<>();
        l = jdbcTemplate.queryForList(sql);
        List<Order> ol = new ArrayList<>();
        for (Map<String,Object>m :l)
        {
            Order o= new Order((Integer)m.get("order_id"),(Integer)m.get("class_id"),(Integer)m.get("user_id"),m.get("registerDate").toString(),(Double) m.get("paid"));
            Date d = strToDate(o.date);

            if(d.after(s) && d.before(e))
            {
                ol.add(o);
            }

        }

        return oListToArr(ol);
    }

    public UserConsumption[] userRank(String sDate, String eDate) throws ParseException {
        Order[] oArr = getOrdersFromPeriod(sDate,eDate);
        List<UserConsumption> ul = new ArrayList<>();

        Map<Integer,Double> m = new HashMap<>();

        for(Order o :oArr)
        {
            //iterate a new user
            if(!m.containsKey(o.user_id))
            {
                m.put(o.user_id,o.paid);
            }
            else {
                //Map already has such people, get old value and add new value;
                double oldValue = m.get(o.user_id);
                m.replace(o.user_id,o.paid+oldValue);
            }

        }
        //traverse map
        for(int id : m.keySet())
        {
            String sql = "select username from users where user_id = "+id+";";
            String name = jdbcTemplate.queryForObject(sql,String.class);
            UserConsumption u = new UserConsumption(id,name,m.get(id));
            ul.add(u);

        }
        return ucListToArr(ul);

    }


    //return true if student is already in that class; otherwise return false
    public boolean checkRegister(int class_id, int user_id)
    {


        List<Course> lc = showtoke(1, user_id);
        for(Course c : lc)
        {
            if(c.getC_id() == class_id)
            {
                return true;
            }
        }
        return false;

    }



    public List<classPersonDataRow> getStudentsInClass(int class_id)
    {
        String sql = "select register.user_id, paidCount, registerCount, registerDate, users.username, class_name from register join classes using(class_id) join users using(user_id) where class_id = "+class_id+";";
        List<classPersonDataRow> cl = new ArrayList<>();
        List<Map<String,Object>> l = new ArrayList<>();
        l= jdbcTemplate.queryForList(sql);
        for(Map<String,Object> m:l)
        {
           classPersonDataRow cpdr = new classPersonDataRow();
           cpdr.setClass_id(class_id);
           cpdr.setClass_name(m.get("class_name").toString());
           cpdr.setUsername(m.get("username").toString());
           cpdr.setUser_id((Integer) m.get("user_id"));

           if(m.get("registerDate")!=null)
           {
               cpdr.setApp_date(m.get("registerDate").toString());
           }else {
               cpdr.setApp_date("unknown");
           }
           if(m.get("paidCount")!=null)
           {
               cpdr.setPrice((Integer) m.get("paidCount"));
           }
           else {
               cpdr.setPrice(0);
           }
            if(m.get("registerCount")!=null)
            {
                cpdr.setOrderCount((Integer) m.get("registerCount"));
            }
            else {
                cpdr.setOrderCount(0);
            }
            cl.add(cpdr);
        }
        return cl;
    }

    public String getUserNameById(int id)
    {
        String sql = "select username from users where user_id = "+Integer.toString(id)+";";
        String username = jdbcTemplate.queryForObject(sql,String.class);

        return username;
    }
    //under construction;
    public int moneyReceived(classPersonDataRow cpdr)
    {



        return 0;
    }

    public boolean invalidName(String un)
    {
        String sql ="Select count(username) from users where username = '"+un+"';";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        if(count == 0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    //this method find a new id for newuser, and check the if usernames are duplicate
    //if duplicated username are found return 0, if failed to operate return -1
    public int newUsers(String username, String number, String email,  String zip, String password)
    {
        String getIds = "select user_id from users";
        List<Map<String, Object>> idList = new ArrayList<>();
        idList = jdbcTemplate.queryForList(getIds);
        int newId = findAnid(idList);
        String checkName = "select count(username) from users where username = '"+username+"';";
        int count = jdbcTemplate.queryForObject(checkName, Integer.class);
        if(count != 0)
        {
            return 0;
        }

        String s = "\""+username+"\",\""+number+"\","+"\""+email+"\",\""+zip+"\",\""+password+"\");";
        String sql = "Insert Into users (user_id,username,phone, email,zip,password) values ("+newId+","+s;

        int signal1 = jdbcTemplate.update(sql);
        //currently we only allow students to login
        String roleModi = "Insert into has(user_id,role_id) values("+newId+","+1+")";
        int signal2 = jdbcTemplate.update(roleModi);

        return signal2*signal1;

    }

    public int findAnid(List<Map<String, Object>> l)
    {
        int randomId = (int) ((Math.random() * 9 + 1) * 100000);
//        int randomId = randomInt();
        for(Map<String,Object> m:l)
        {
            if((Integer)m.get("user_id") == randomId)
            {

                findAnid(l);
            }
        }

    return randomId;
    }
//    public int randomInt()
//    {
//
//        return randomId;
//    }

//    public boolean getLeftClass()
//    {
//
//    }


//this method convert a List of course to an array, so that the front end can itetor it;
    public Course[] listToCourseArr(List<Course> lc)
    {

        if(lc.size()==0)
        {
            Course[] cArr = new Course[1];
            cArr[0] = new Course(-1);
            return cArr;
        }
        else {
            int i = 0;
            Course cArr[] = new Course[lc.size()];
            for(Course c: lc)
            {
                cArr[i] = c;
                i++;
            }
            return cArr;
        }

    }


    public instructorWithSnum[] listToInArr(List<instructorWithSnum> l)
    {
        if(l.size()==0)
        {
            return new instructorWithSnum[0];
        }
        instructorWithSnum[] instructors = new instructorWithSnum[l.size()];
        int i =0;
        for(instructorWithSnum is: l)
        {
            instructors[i] = is;
            i++;
        }

        return instructors;
    }

    public Date strToDate(String d) throws ParseException {
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse(d);
        return date;
    }

    public Order[] oListToArr(List<Order> ol)
    {
        if(ol.size() < 1)
        {
            return new Order[0];
        }
        Order[] oArr = new Order[ol.size()];

        int i = 0;
        for(Order o : ol)
        {
            oArr[i] = o;
            i++;
        }
        return oArr;

    }

    public  UserConsumption[] ucListToArr(List<UserConsumption> ul)
    {
        if(ul.size()<1)
        {
            return new UserConsumption[0];
        }
        UserConsumption [] uArr = new UserConsumption[ul.size()];
        int i = 0;
        for(UserConsumption u : ul)
        {
            uArr[i] = u;
            i++;
        }
        return uArr;
    }

    public Order[] checkUserOrders(int userId)
    {
        String sql = "Select * from orders where user_id = "+userId+";";
        List<Map<String,Object>> l = new ArrayList<>();
        l = jdbcTemplate.queryForList(sql);
        if(l.size()==0)
        {
            return new Order[0];
        }
        List<Order> orderList = new ArrayList<>();
        for(Map <String,Object>m:l)
        {
            Order o = new Order((Integer)m.get("order_id"),(Integer)m.get("class_id"),(Integer) m.get("user_id"),m.get("registerDate").toString(),(Double)m.get("paid"));
            orderList.add(o);

        }
        return oListToArr(orderList);
    }
    public double checkUserBalance(int user_id)
    {
        String sql = "select balance from users where user_id = "+user_id+";";
        double b = jdbcTemplate.queryForObject(sql, Integer.class);
        return b;
    }
    public absentData[] absentArray()
    {
        String sql = "select * from absent";
        List<Map<String,Object>> l = new ArrayList<>();
        l = jdbcTemplate.queryForList(sql);
        int index= 0;
        absentData[] aArr = new absentData[l.size()];

        for(Map<String,Object> m : l)
        {
            absentData ab = new absentData();
            ab.setUser_id((Integer) m.get("user_id"));
            ab.setClass_id((Integer)m.get("class_id"));
            ab.setMakeUpDate(m.get("makeUp").toString());
            ab.setReferDate(m.get("referDate").toString());
            ab.setAbsentDate(m.get("absentDate").toString());
            aArr[index] = ab;
            index++;
        }
        return aArr;

    }
    public Course searchAClass(int id, int school_code)
    {
        String sql = "select class_id, location,classes.time, classes.enrollment,classes.price,classes.startDate, classes.endDate,classes.classCount,mode_of_instruction,class_name,teacher,description,showEnable,description " +
                "from classes  " +
                "where class_id="+Integer.toString(id)+";";
        List<Map<String,Object>> l = jdbcTemplate.queryForList(sql);
        Map<String ,Object> m = l.get(0);
        Course c = new Course();

        c.setC_id((Integer)m.get("class_id"));
        c.setName(m.get("class_name").toString());
        c.setLocation(m.get("location").toString());
        c.setPrice((double)m.get("price"));
        c.setTime(m.get("time").toString());
        c.setDescription(m.get("description").toString());
        c.setMode(m.get("mode_of_instruction").toString());
        if(m.get("startDate") !=null) {
            c.setsTime(m.get("startDate").toString());
        }
        c.setEnrollment((Integer) m.get("enrollment"));

        c.setMode(m.get("mode_of_instruction").toString());
        c.setInstructor(m.get("teacher").toString());
        c.setDescription(m.get("description").toString());

        return c;

    }
//this function get an array which consist of mutiple class description. It get data from the mysql table classboard.
    public ClassDescription[] getClassBoards()
    {
        String sql ="select classBoard_id,name, description,imageLink from classboard order by classboard_id;";
        List<Map<String,Object>> l = jdbcTemplate.queryForList(sql);
        ClassDescription [] cdArr = new ClassDescription[l.size()];
        int index =0;
        for(Map<String,Object> m: l)
        {
            ClassDescription cd = new ClassDescription((Integer) m.get("classBoard_id"),m.get("name").toString(),m.get("description").toString(),
                    m.get("imageLink").toString());
            cdArr[index] = cd;
            index++;
        }

        return cdArr;

    }

    public int postClassBoards(ClassDescription[] cdArr)
    {
        for(ClassDescription cd: cdArr)
        {
            String sql = "update classboard set name = '"+cd.getName()+"' , description = '"+cd.getDescription()+"',imageLink = '"+cd.getPicLink()+"' where classBoard_id = "+cd.getId()+";";
            int result = jdbcTemplate.update(sql);
            if(result!=1)
            {
                return 0;
            }

        }
        return 1;
    }

    public int addAward(Award award)
    {
        String sql = "Insert Into awards(id,number,name,description,images) values(?,?,?,?,?);";
        int id = idGeneration(2);
        Gson json = new Gson();
        String arrayJson = json.toJson(award.getImageLinks());
        int result = jdbcTemplate.update(sql,idGeneration(2),award.getNumber(),award.getName(),award.getDescription(),arrayJson);
        return result;

    }

    public int updateAward(int id,Award award)
    {
        String sql = "update awards set name = (?) ,description = (?),number = (?),images = (?) where id = (?);";
        Gson json = new Gson();
        String arrayJson = json.toJson(award.getImageLinks());
        int result = jdbcTemplate.update(sql,award.getName(),award.getDescription(),award.getNumber(),arrayJson, id);

        return result;
    }

    public int removeAward(int id)
    {
        String sql = "delete from awards where id = (?);";
        int result = jdbcTemplate.update(sql,id);
        return result;
    }
    public Award[] getAward()
    {


        String sql = "select id,number,name,description,images from awards order by number";
        List<Map<String,Object>> al = jdbcTemplate.queryForList(sql);
        Award[] awards = new Award[al.size()];
        for(int i=0;i<al.size();i++)
        {
            Award award = new Award();
            award.setName(al.get(i).get("name").toString());
            award.setId((int)al.get(i).get("id"));
            award.setDescription(al.get(i).get("description").toString());
            award.setNumber((int)al.get(i).get("number"));
            String json = al.get(i).get("images").toString();
            Gson gson =new Gson();

            try {
                award.setImageLinks(gson.fromJson(json,String[].class));
            }catch (Exception e){
                String errorLinks[] = new String[]{"error","errors"};
                award.setImageLinks(errorLinks);
            }



            awards[i] = award;

        }
        return awards;


    }

    public Coach[] getCoaches(){
        String sql = "select * from coach_info order by coach_info.rank";
        List<Map<String,Object>> l = new LinkedList<>();
        l = jdbcTemplate.queryForList(sql);
        Coach[] users = new Coach[l.size()];

        for(int i=0;i<l.size();i++)
        {
            Coach c = new Coach();
            c.setId((Integer) l.get(i).get("coach_id"));
            c.setName(l.get(i).get("name").toString());
            c.setEmail(l.get(i).get("email").toString());
            c.setDescription(l.get(i).get("description").toString());
            c.setImageLink(l.get(i).get("imageLink").toString());
            c.setRank((Integer)l.get(i).get("rank"));
            users[i] = c;
        }
        return users;
    }
    //this method update the info of a coach return 0 if success;
    public int updateCoach(Coach c){
        String sql = "update coach_info set name = (?), email=(?),description= (?),imageLink =(?) ,coach_info.rank =(?) where coach_id = (?)";
        int result =jdbcTemplate.update(sql,c.getName(),c.getEmail(),c.getDescription(),c.getImageLink(),c.getRank(),c.getId());
        return  result;
    }
//this method delete the info of a coach return 0 if success
    public int deleteCoach(int id)
    {
        String sql = "delete from coach_info where coach_id = (?);";
        int result = jdbcTemplate.update(sql,id);
        return  result;
    }
    public  int addCoach(Coach c)
    {
        //have not set id yet;

        String sql = "Insert into coach_info (coach_id,name,email,description,imageLink,coach_info.rank) values (?,?,?,?,?,?)";
        int result = jdbcTemplate.update(sql,idGeneration(3),c.getName(),c.getEmail(),c.getDescription(),c.getImageLink(),c.getRank());
        return result;
    }
//for all boards CRUD method takes a type parameter which means:<0,regular text board with one image possible video>,<>
    public BoardInfo[] getBoards(int type)
    {
        String sql = "SELECT * FROM student_portal.board where board.type = (?) order by number";

        //return all why skate with us board
        if(type==0)
        {
            List<Map<String ,Object>> l = new ArrayList<>();
            l = jdbcTemplate.queryForList(sql,type);
            BoardInfo boards[] = new BoardInfo[l.size()];
            for(int i =0;i<l.size();i++)
            {
                Map <String,Object>m = l.get(i);

                BoardInfo b = new BoardInfo();
                b.setType(Integer.toString(type));
                b.setId((Integer)m.get("id"));
                b.setName(m.get("name").toString());
                b.setDescription(m.get("description").toString());
                String [] links = new  String[]{m.get("imageLinks").toString()};
                b.setImageLinks(links);
                b.setVideoLink(m.get("videoLink").toString());
                b.setNumber((Integer) m.get("number"));
                boards[i] = b;

            }
            return boards;
        }
        return new BoardInfo[1];

    }
    public int addBoard(int type,BoardInfo b)
    {

        String sql = "insert board (id, name, number,description, board.type,imageLinks,videoLink) values (?,?,?,?,?,?,?)";
        if(type==0)
        {
            int result = jdbcTemplate.update(sql,idGeneration(4),b.getName(),b.getNumber(),b.getDescription(),b.getType(),b.getImageLinks()[0],b.getVideoLink());
            return result;
        }

        return 0;
    }

    public int updateBoard(int type,BoardInfo b)
    {
        String sql = "update board set name = (?), number =(?), description=(?),board.type=(?),imageLinks = (?), videoLink =(?) where id = (?)";
        if(type ==0 )
        {

            int reult= jdbcTemplate.update(sql,b.getName(),b.getNumber(),b.getDescription(),b.getType(),b.getImageLinks()[0],b.getVideoLink(),b.getId());
            return reult;
        }
        return 0;
    }
    public int deleteBoard(int type,BoardInfo b)
    {
        String sql = "delete from board where id =(?)";
        if(type ==0 )
        {
            int reult = jdbcTemplate.update(sql,b.getId());
            return reult;
        }
        return 0;
    }





}

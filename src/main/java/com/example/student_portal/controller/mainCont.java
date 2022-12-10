package com.example.student_portal.controller;


import com.example.student_portal.module.SqlClass;
import com.example.student_portal.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller()
public class mainCont {

//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
    @Autowired
    public JdbcTemplate jdbcTemplate;
    protected static User CurrUser;

    protected static User CurrA;

    @RequestMapping(value="/signIn",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView signIn(String username,String password) throws ClassNotFoundException,NullPointerException
    {
        String sqlSignIn = "select * from users where username = '"+username+"'";

        if(username == null ||username.equals("null"))
        {
            return new ModelAndView("signIn.html");
        }

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
            switch (aUser.getRoleCode())
            {
                case 1:
                     mv= new ModelAndView("student_main.html");
                    CurrUser = aUser;
                    mv.addObject("aUser",aUser);
                     break;

                case 3:
                    mv =new ModelAndView("admin_main.html");
                    CurrA = aUser;
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

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public ModelAndView mainPage()
    {
        ModelAndView mv  =new ModelAndView("index.html");
        return mv;
    }
    @RequestMapping(value="/signUp",method = RequestMethod.GET)
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




}
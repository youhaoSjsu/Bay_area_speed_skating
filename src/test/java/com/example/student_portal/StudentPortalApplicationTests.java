package com.example.student_portal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class StudentPortalApplicationTests {


@Autowired
static JdbcTemplate jdbcTemplate;
    static class Task {
        String name;
        int priority;

        public Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
    }
    public class MyClass {

        private Map<String, Integer> map;

        public MyClass() {
            map = new HashMap<>();
            map.put("foo", 1);
            map.put("bar", 3);
        }

        public int getValue(String input, int numRetries) throws Exception {
            try {
                return map.get(input);
            }
            catch (Exception e) {
                if (numRetries > 3) {
                    throw e;
                }
                return getValue(input, numRetries + 1);
            }
        }
    }
    @Test
    void test() throws Exception {
        MyClass m =new MyClass();
        m.getValue("fubar",1);

    }
    @Test
    public static void main(String[] args){
//        Calendar calendar = Calendar.getInstance();
//
//        // Set the year, month, and day
//        calendar.set(Calendar.YEAR, 2023);
//        calendar.set(Calendar.MONTH, Calendar.JULY); // Note: Month is zero-based, so July is represented as 6
//        calendar.set(Calendar.DAY_OF_MONTH, 20);
//
//        // Get the Date object representing July 20, 2023
//        Date start = calendar.getTime();
//
//        Calendar c = Calendar.getInstance();
//
//        Date end =c.getTime();
//
//        getCanceledClass( 2931,start,end);

    }
//    public static Date[] getCanceledClass(int class_id, Date start, Date end)
//    {
//        String sql = "select Date from classcanceled where class_id = (?) and date>= (?) and date >= (?)";
//        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, class_id,start,end);
//        Date []dArray = new Date[list.size()];
//        for(int i = 0;i<list.size();i++)
//        {
//            dArray[i] = (Date) list.get(i).get("Date");
//        }
//        return null;
//    }

    public static List<String> generateDateSchedule(String startDateString, int courseLengthInWeeks, String dayOfWeek) {
        List<String> schedule = new ArrayList<>();
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

        return schedule;
    }



}

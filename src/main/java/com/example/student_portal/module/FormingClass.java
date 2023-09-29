package com.example.student_portal.module;

import org.hibernate.annotations.Parent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.*;
class OptionalDuration implements Serializable{
    String day;
    Duration duration;
    int numStudent;
    public OptionalDuration(String day,Duration duration ){
        this.day = day;
        this.duration = duration;
    }
}

public class FormingClass extends Course implements Serializable {
    private DayOfWeek dayOfWeek;
    private String time;
    private List<String> dateList;
    private int minStudent,maxStudent;
    private String level;

    private MutiDurations optionDurations;
    public FormingClass (MutiDurations durations ,String name ,String level , String location , int minStudent ,int maxStudent)
    {
        this.optionDurations = durations;
        this.level= level;
        this.setLocation(location);
        this.setName(name);
        this.minStudent =minStudent;
        this.maxStudent = maxStudent;
    }


//    private Time
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

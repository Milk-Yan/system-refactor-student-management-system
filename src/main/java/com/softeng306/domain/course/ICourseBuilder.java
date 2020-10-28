package com.softeng306.domain.course;

import java.util.HashMap;

public interface ICourseBuilder {
    void setCourseID(String id);

    void setCourseName(String name);

    void setProfInCharge(String profInCharge);

    void setTotalSeats(int totalSeats);

    void setLectureGroups(HashMap<String, Double> lectureGroups);

    void setTutorialGroups(HashMap<String, Double> tutorialGroups);

    void setLabGroups(HashMap<String, Double> labGroups);

    void setAU(int AU);

    void setCourseDepartment(String department);

    void setCourseType(String Type);

    void setLecWeeklyHour(int lecWeeklyHour);

    void setTutWeeklyHour(int tutWeeklyHour);

    void setLabWeeklyHour(int labWeeklyHour);

    Course build();
}

package com.softeng306.builders.interfaces;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.professor.Professor;

import java.util.List;

public interface ICourseBuilder {
    void setID(String id);
    void setName(String name);

    void setProfInCharge(Professor profInCharge);

    void setTotalSeats(int totalSeats);

    void setLectureGroups(List<Group> lectureGroups);
    void setTutorialGroups(List<Group> tutorialGroups);
    void setLabGroups(List<Group> labGroups);

    void setAU(int AU);
    void setDepartment(String department);
    void setType(String Type);

    void setLecWeeklyHour(int lecWeeklyHour);
    void setTutWeeklyHour(int tutWeeklyHour);
    void setLabWeeklyHour(int labWeeklyHour);

    Course getCourse();
}

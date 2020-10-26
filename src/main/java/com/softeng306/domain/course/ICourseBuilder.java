package com.softeng306.domain.course;

import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.professor.Professor;

import java.util.List;

public interface ICourseBuilder {
    void setCourseID(String id);
    void setCourseName(String name);

    void setProfInCharge(Professor profInCharge);

    void setTotalSeats(int totalSeats);

    void setLectureGroups(List<Group> lectureGroups);
    void setTutorialGroups(List<Group> tutorialGroups);
    void setLabGroups(List<Group> labGroups);

    void setAU(int AU);
    void setCourseDepartment(Department department);
    void setCourseType(CourseType Type);

    void setLecWeeklyHour(int lecWeeklyHour);
    void setTutWeeklyHour(int tutWeeklyHour);
    void setLabWeeklyHour(int labWeeklyHour);

    Course build();
}

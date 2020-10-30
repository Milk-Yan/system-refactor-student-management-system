package com.softeng306.domain.course;

import com.softeng306.domain.exceptions.ProfessorNotFoundException;

import java.util.Map;

public interface ICourseBuilder {
    void setCourseID(String id);

    void setCourseName(String name);

    void setProfInCharge(String profInCharge) throws ProfessorNotFoundException;

    void setTotalSeats(int totalSeats);

    void setLectureGroups(Map<String, Double> lectureGroups);

    void setTutorialGroups(Map<String, Double> tutorialGroups);

    void setLabGroups(Map<String, Double> labGroups);

    void setAcademicUnits(int academicUnits);

    void setCourseDepartment(String department);

    void setCourseType(String Type);

    void setLecWeeklyHour(int lecWeeklyHour);

    void setTutWeeklyHour(int tutWeeklyHour);

    void setLabWeeklyHour(int labWeeklyHour);

    ICourse build();
}

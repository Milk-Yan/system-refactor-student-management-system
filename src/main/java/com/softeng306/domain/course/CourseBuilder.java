package com.softeng306.domain.course;

import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.professor.Professor;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;
import com.softeng306.enums.GroupType;
import com.softeng306.managers.ProfessorMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseBuilder implements ICourseBuilder {
    private Course course;

    public CourseBuilder() {
        course = new Course();
    }

    @Override
    public void setCourseID(String id) {
        course.setID(id);
    }

    @Override
    public void setCourseName(String name) {
        course.setName(name);
    }

    @Override
    public void setProfInCharge(String profID) {
        ProfessorMgr profMgr = ProfessorMgr.getInstance();
        //Guaranteed at this point that the ID is valid
        Professor profInCharge = profMgr.getProfessorFromID(profID);
        course.setProfInCharge(profInCharge);
    }

    @Override
    public void setTotalSeats(int totalSeats) {
        course.setTotalSeat(totalSeats);
        course.setVacancies(totalSeats);
    }

    @Override
    public void setLectureGroups(HashMap<String, Double> lectureGroups) {
        List<Group> groupsOfLectures = new ArrayList<>();
        for(String name : lectureGroups.keySet()){
            Group g = new Group(name, lectureGroups.get(name).intValue(), lectureGroups.get(name).intValue(), GroupType.LECTURE_GROUP);
            groupsOfLectures.add(g);
        }
        course.setLectureGroups(groupsOfLectures);
    }

    @Override
    public void setTutorialGroups(HashMap<String, Double> tutorialGroups) {
        List<Group> groupsOfLectures = new ArrayList<>();
        for(String name : tutorialGroups.keySet()){
            Group g = new Group(name, tutorialGroups.get(name).intValue(), tutorialGroups.get(name).intValue(), GroupType.LECTURE_GROUP);
            groupsOfLectures.add(g);
        }
        course.setLectureGroups(groupsOfLectures);
    }

    @Override
    public void setLabGroups(HashMap<String, Double> labGroups) {
        List<Group> groupsOfLectures = new ArrayList<>();
        for(String name : labGroups.keySet()){
            Group g = new Group(name, labGroups.get(name).intValue(), labGroups.get(name).intValue(), GroupType.LECTURE_GROUP);
            groupsOfLectures.add(g);
        }
        course.setLectureGroups(groupsOfLectures);
    }

    @Override
    public void setAU(int AU) {
        course.setAU(AU);
    }

    @Override
    public void setCourseDepartment(String department) {
        course.setCourseDepartment(Department.valueOf(department));
    }

    @Override
    public void setCourseType(String type) {
        course.setType(CourseType.valueOf(type));
    }

    @Override
    public void setLecWeeklyHour(int lecWeeklyHour) {
        course.setLecWeeklyHour(lecWeeklyHour);
    }

    @Override
    public void setTutWeeklyHour(int tutWeeklyHour) {
        course.setTutWeeklyHour(tutWeeklyHour);
    }

    @Override
    public void setLabWeeklyHour(int labWeeklyHour) {
        course.setLabWeeklyHour(labWeeklyHour);
    }

    @Override
    public Course build() {
        return course;
    }
}

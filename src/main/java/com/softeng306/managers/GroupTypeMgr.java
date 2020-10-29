package com.softeng306.managers;

import com.softeng306.enums.GroupType;

public class GroupTypeMgr {



    public String getLectureGroupTypeString(){
        return GroupType.LECTURE_GROUP.toString();
    }

    public String getLabGroupTypeString(){
        return GroupType.LAB_GROUP.toString();
    }

    public String getTutorialGroupTypeString(){
        return GroupType.TUTORIAL_GROUP.toString();
    }


}

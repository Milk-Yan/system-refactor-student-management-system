# CBO Calculations

## MainComponent

- SubComponent
- Course
- CourseMgr*
- FILEMgr*
- Mark
- MarkMgr*
- CourseworkComponent

Total: 7

## Mark

- Student
- Course
- CourseworkComponent
- MainComponent
- SubComponent
- FILEMgr*
- Main*
- MarkMgr*
- CourseRegistrationMgr

Total: 9

## MarkMgr*

- CourseworkComponent
- MainComponent
- SubComponent
- Mark
- FILEMgr
- ValidationMgr
- Course
- Student
- Main
- CourseRegistrationMgr

Total: 10

## Professor

- ValidationMgr*
- Course
- CourseMgr*
- CourseRegistrationMgr*
- FILEMgr*
- Main* 
- ProfessorMgr*
- HelpInfoMgr*

Total: 8

## ProfessorMgr*

- ValidationMgr
- Professor
- HelpInfoMgr

Total: 3

??? ProfessorMgr isnt used by anything???

## Student

- Mark
- CourseRegistration
- CourseRegistrationMgr*
- FILEMgr*
- HelpInfoMgr*
- Main*
- MarkMgr*
- StudentMgr*
- ValidationMgr*

Total: 9

## StudentMgr*

- ValidationMgr
- Student
- HelpInfoMgr
- FILEMgr
- Main

Total: 5

## SubComponent

- Mark*
- CourseMgr*
- FILEMgr*
- MainComponent
- MarkMgr*
- CourseworkComponent

Total: 6

## TutorialGroup

- CourseMgr*
- Course
- CourseRegistrationMgr*
- FILEMgr*
- Group

Total: 5

## ValidationMgr*

- HelpInfoMgr*
- Student
- Main*
- Course
- Professor
- CourseRegistration
- CourseRegistrationMgr*
- CourseMgr*
- MarkMgr*
- ProfessorMgr*
- StudentMgr*

Total: 11

---

# Checking Max's Stuff

## Course

- Professor
- LectureGroup
- TutorialGroup
- LabGroup
- MainComponent
- Main
- CourseRegistration
- CourseRegistrationMgr
- FILEMgr
- HelpInfoMgr
- Mark
- MarkMgr
- ValidationMgr
- CourseMgr

Total: 14

## CourseMgr

- ValidationMgr
- HelpInfoMgr
- LectureGroup
- TutorialGroup
- LabGroup
- Professor
- Course
- FILEMgr
- Main
- MainComponent
- SubComponent

Total: 11

## CourseRegistration

- Student
- Course
- CourseRegistrationMgr
- FILEMgr
- Main
- ValidationMgr

Total: 6

## FILEMgr

- Student
- Group
- LectureGroup
- TutorialGroup
- LabGroup
- MainComponent
- SubComponent
- CourseworkComponent
- Course
- Professor
- CourseRegistration
- Mark
- MarkMgr
- CourseMgr
- CourseRegistrationMgr
- Main
- StudentMgr
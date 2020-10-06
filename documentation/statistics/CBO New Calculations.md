# CBO Calculations

## Course

- FILEMgr
- CourseMgr
- CourseRegistrationMgr
- HelpInfoMgr
- MarkMgr
- ValidationMgr

Total: 6

## CourseMgr

- Course
- Main
- CourseworkComponent
- FILEMgr
- HelpInfoMgr
- Professor
- ValidationMgr
- Group

Total: 8

## CourseRegistration

- CourseRegistrationMgr
- FILEMgr
- ValidationMgr

Total: 3

## CourseRegistrationMgr

- Course
- CourseRegistration
- Main
- FILEMgr
- HelpInfoMgr
- MarkMgr
- Professor
- Student
- ValidationMgr

Total: 9

## CourseworkComponent

- CourseMgr
- FILEMgr
- Mark
- MarkMgr

Total: 4

## FILEMgr

- Course
- CourseRegistration
- CourseworkComponent
- StudentMgr
- Main
- CourseMgr
- CourseRegistrationMgr
- MarkMgr
- Group
- Mark
- Professor
- Student

Total: 12

## Group

- CourseMgr
- FILEMgr
- HelpInfoMgr

Total: 3

## HelpInfoMgr

- Course
- Group
- CourseMgr
- ValidationMgr
- StudentMgr
- ProfessorMgr
- CourseRegistrationMgr
- Main
- Professor
- Student

Total: 10

## LabGroup

Parent (Group): 3

Total: 3

## LectureGroup

Parent (Group): 3

Total: 3

## Main

- CourseMgr
- CourseRegistrationMgr
- FILEMgr
- HelpInfoMgr
- Student
- StudentMgr
- ValidationMgr
- MarkMgr

Total: 8

## MainComponent

Parent (CourseworkComponent): 4

Total: 4

## Mark

- CourseworkComponent
- MainComponent
- FILEMgr
- MarkMgr

Total: 4

## MarkMgr*

- Course
- CourseworkComponent
- FILEMgr
- Main
- Mark
- CourseRegistrationMgr
- Student
- ValidationMgr

Total: 8

## Professor

- FILEMgr
- ProfessorMgr
- HelpInfoMgr
- ValidationMgr
- CourseMgr
- CourseRegistrationMgr

Total: 6

## ProfessorMgr*

- HelpInfoMgr
- Professor
- ValidationMgr

Total: 3

## Student

- Main
- StudentMgr
- FILEMgr
- CourseRegistrationMgr
- HelpInfoMgr
- MarkMgr
- ValidationMgr

Total: 7

## StudentMgr*

- FILEMgr
- HelpInfoMgr
- Main
- Student
- ValidationMgr

Total: 5

## SubComponent

Parent (CourseworkComponent): 4

Total: 4

## TutorialGroup

Parent (Group): 3

Total: 3

## ValidationMgr*

- Course
- CourseRegistration
- HelpInfoMgr
- Main
- Professor
- Student
- CourseMgr
- ProfessorMgr
- StudentMgr
- CourseRegistrationMgr
- MarkMgr

Total: 11


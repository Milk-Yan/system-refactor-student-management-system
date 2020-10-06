# WMC_Fan-in Calculations

## MainComponent

- ```java
  public MainComponent(String componentName, int componentWeight, ArrayList<SubComponent> subComponents)
  ```

  - CourseMgr.enterCourseWorkComponentWeightage()
  - FILEMgr.loadCourses()
  - FILEMgr.loadStudentMarks()

- ```java
  public ArrayList<SubComponent> getSubComponents()
  ```

  - CourseMgr.enterCourseWorkComponentWeightage()
  - FILEMgr.writeCourseIntoFile()
  - FILEMgr.backUpCourse()
  - FILEMgr.updateStudentMarks()
  - FILEMgr.backUpMarks()
  - Mark.setMainCourseWorkMarks()
  - Mark.setSubCourseWorkMarks()
  - MarkMgr.initializeMark()
  - MarkMgr.setCourseWorkMark()
  - MarkMgr.printCourseStatistics()
  - MarkMgr.printStudentTranscript()

- ```java
  public void printComponentType()
  ```




Total: 3 + 11 + 0 = 14

## Mark

- ```java
  public Mark(Student student, Course course, HashMap<CourseworkComponent, Double> courseWorkMarks, double totalMark)
  ```

  - FILEMgr.loadStudentMarks()
  - MarkMgr.initializeMark()

- ```java
  public Student getStudent()
  ```

  - FILEMgr.updateStudentMarks()
  - FILEMgr.backUpMarks()
  - MarkMgr.setCourseWorkMark()
  - MarkMgr.printStudentTranscript()

- ```java
  public Course getCourse()
  ```

  - FILEMgr.updateStudentMarks()
  - FILEMgr.backUpMarks()
  - MarkMgr.setCourseWorkMark()
  - MarkMgr.printCourseStatistics()
  - MarkMgr.printStudentTranscript()

- ```java
  public HashMap<CourseworkComponent, Double> getCourseWorkMarks()
  ```

  - FILEMgr.updateStudentMarks()
  - FILEMgr.backUpMarks()
  - MarkMgr.setCourseWorkMark()
  - MarkMgr.computeMark()
  - MarkMgr.printCourseStatistics()
  - MarkMgr.printStudentTranscript()

- ```java
  public double getTotalMark()
  ```

  - FILEMgr.updateStudentMarks()
  - FILEMgr.backUpMarks()
  - MarkMgr.printCourseStatistics()
  - MarkMgr.printStudentTranscript()

- ```java
  public void setMainCourseWorkMarks(String courseWorkName, double result)
  ```

  - MarkMgr.setCourseWorkMark()

- ```java
  public void setSubCourseWorkMarks(String courseWorkName, double result)
  ```

  - MarkMgr.setCourseWorkMark()

Total: 2 + 4 + 5 + 6 + 4 + 1 + 1= 23

## MarkMgr*

- ```java
  public static Mark initializeMark(Student student, Course course)
  ```

  - CourseRegistrationMgr.registerCourse()

- ```java
  public static void setCourseWorkMark(boolean isExam)
  ```

  - Main.main()

- ```java
  public static double computeMark(ArrayList<Mark> thisCourseMark, String thisComponentName)
  ```

  - MarkMgr.printCourseStatistics()

- ```java
  public static void printCourseStatistics()
  ```

  - Main.main()

- ```java
  public static void  printStudentTranscript()
  ```

  - Main.main()

- ```java
  public static double gpaCalcualtor(double result)
  ```

  - MarkMgr.printStudentTranscript()

Total: 1 + 1 + 1 + 1 + 1 + 1 = 6

## Professor

- ```java
  public Professor(String profID, String profName)
  ```

  - FILEMgr.loadProfessors()
  - ProfessorMgr.addProfessor()

- ```java
  public String getProfID()
  ```

  - FILEMgr.writeCourseIntoFile()
  - FILEMgr.loadCourses()
  - FILEMgr.backUpCourse()
  - FILEMgr.writeProfIntoFile()
  - HelpInfoMgr.printProfInDepartment()
  - ValidationMgr.checkProfExists()

- ```java
  public String getProfName()
  ```

  - CourseMgr.printCourses()
  - CourseRegistrationMgr.registerCourse()
  - FILEMgr.writeProfIntoFile()

- ```java
  public String getProfDepartment()
  ```

  - FILEMgr.writeProfIntoFile()
  - HelpInfoMgr.printProfInDepartment()

- ```java
  public void setProfDepartment(String profDepartment)
  ```

  - FILEMgr.loadProfessors()
  - ProfessorMgr.addProfessor()

Total: 2 + 6 + 3 + 2 + 2 = 15

## ProfessorMgr*

- ```java
  public Professor addProfessor()
  ```

Total: 0

??? ProfessorMgr isnt used by anything???

## Student

- ```java
  public Student(String studentName)
  ```

  - StudentMgr.addStudent()

- ```java
  public Student(String studentID, String studentName)
  ```

  - FILEMgr.loadStudents()

- ```java
  public String getStudentID()
  ```

  - CourseRegistrationMgr
    registerCourse()
    printStudents()
  - FILEMgr
    writeStudentsIntoFile(Student)
    writeCourseRegistrationIntoFile(CourseRegistration)
    loadCourseRegistration()
    updateStudentMarks(Mark)
    loadStudentMarks()
    backUpMarks(ArrayList<Mark>)
  - HelpInfoMgr
    printAllStudents()
  - MarkMgr
    setCourseWorkMark(boolean)
    printStudentTranscript()
  - Student
    generateStudentID()
  - StudentMgr
    addStudent()
  - ValidationMgr
    checkStudentExists(String)
    checkCourseRegistrationExists(String, String)

- ```java
  public String getStudentName()
  ```

  - CourseRegistrationMgr
    registerCourse()
    printStudents()
  - FILEMgr
    writeStudentsIntoFile(Student)
  - MarkMgr
    printStudentTranscript()
  - StudentMgr
    addStudent()

- ```java
  public String getStudentSchool()
  ```

  - FILEMgr
    writeStudentsIntoFile(Student)
  - StudentMgr
    addStudent()

- ```java
  public String getGender()
  ```

  - FILEMgr
    writeStudentsIntoFile(Student)
  - StudentMgr
    addStudent()

- ```java
  public double getGPA()
  ```

  - FILEMgr
    writeStudentsIntoFile(Student)
  - StudentMgr
    addStudent()

- ```java
  public int getStudentYear()
  ```

  - FILEMgr
    writeStudentsIntoFile(Student)
  - StudentMgr
    addStudent()

- ```java
  public static void setIdNumber(int idNumber)
  ```

  - FILEMgr
    loadStudents()

- ```java
  public void setStudentID(String studentID)
  ```

  - StudentMgr
    addStudent()

- ```java
  public void setStudentSchool(String studentSchool)
  ```

  - FILEMgr
    loadStudents()
  - StudentMgr
    addStudent()

- ```java
  public void setGender(String gender)
  ```

  - FILEMgr
    loadStudents()
  - StudentMgr
    addStudent()

- ```java
  public void setGPA(double GPA)
  ```
  - FILEMgr
    loadStudents()

- ```java
  public void  setStudentYear(int studentYear)
  ```

  - FILEMgr
    loadStudents()
  - StudentMgr
    addStudent()

- ```java
  private String generateStudentID()
  ```

  - Student
    Student(String)

Total: 1 + 1 + 15 + 5 + 2 + 2 + 2 + 2 + 1 + 1 +2 + 1 + 2 + 1 = 38

## StudentMgr*

- ```java
  public static void addStudent()
  ```

  - Main
    main(String[])

Total: 1

## SubComponent

- ```java
  public SubComponent(String componentName, int componentWeight)
  ```

  - CourseMgr
    enterCourseWorkComponentWeightage(Course)
  - FILEMgr
    loadCourses()
    loadStudentMarks()

- ```java
  public void printComponentType()
  ```

Total: 3

## TutorialGroup

- ```java
  public TutorialGroup(String groupName, int availableVacancies, int totalSeats)
  ```

  - CourseMgr
    addCourse()
  - FILEMgr
    loadCourses()

Total: 2

## ValidationMgr*

- ```java
  public static boolean checkDepartmentValidation(String department)
  ```

  - CourseMgr
    addCourse()
  - HelpInfoMgr
    printProfInDepartment(String, boolean)
  - ProfessorMgr
    addProfessor()
  - StudentMgr
    addStudent()
  - ValidationMgr
    checkCourseDepartmentExists()

- ```java
  public static boolean checkGenderValidation(String gender)
  ```

  - StudentMgr
    addStudent()

- ```java
  public static boolean checkCourseTypeValidation(String courseType)
  ```

  - CourseMgr
    addCourse()

- ```JAVA
  public static boolean checkValidStudentIDInput(String studentID)
  ```

  - addStudent()
    StudentMgr

Total: 5 + 1 + 1 + 1 = 8

# Checking Max's Work

## Course

- ```java
  public Course(String courseID, String courseName, Professor profInCharge, int vacancies, int totalSeats, ArrayList<LectureGroup> lectureGroups, int AU, String courseDepartment, String courseType, int lecWeeklyHour)
  ```

  - FILEMgr
    loadCourses()

- ```java
  public Course(String courseID, String courseName, Professor profInCharge, int vacancies, int totalSeats, ArrayList<LectureGroup> lectureGroups, ArrayList<TutorialGroup> tutorialGroups, ArrayList<LabGroup> labGroups, int AU, String courseDepartment, String courseType, int lecWeeklyHour, int tutWeeklyHour, int labWeeklyHour)
  ```

  - CourseMgr
    addCourse()

- ```java
  public String getCourseID()
  ```

  - CourseMgr
    checkAvailableSlots()
    enterCourseWorkComponentWeightage(Course)
    printCourses()
  - CourseRegistrationMgr
    registerCourse()
    printStudents()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
    writeCourseRegistrationIntoFile(CourseRegistration)
    loadCourseRegistration()
    updateStudentMarks(Mark)
    loadStudentMarks()
    backUpMarks(ArrayList<Mark>)
  - HelpInfoMgr
    printAllCourses()
    printCourseInDepartment(String)
  - MarkMgr
    setCourseWorkMark(boolean)
    printCourseStatistics()
    printStudentTranscript()
  - ValidationMgr
    checkCourseExists(String)
    checkCourseRegistrationExists(String, String)

- ```java
  public String getCourseName() 
  ```

  - CourseMgr
    checkAvailableSlots()
    enterCourseWorkComponentWeightage(Course)
    printCourses()
  - CourseRegistrationMgr
    registerCourse()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
  - MarkMgr
    printCourseStatistics()
    printStudentTranscript()

- ```java
  public int getAU()
  ```

  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
  - MarkMgr
    printCourseStatistics()
    printStudentTranscript()

- ```java
  public Professor getProfInCharge()
  ```

  - CourseMgr
    printCourses()
  - CourseRegistrationMgr
    registerCourse()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public int getVacancies()
  ```

  - CourseMgr
    checkAvailableSlots()
  - CourseRegistrationMgr
    registerCourse()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
  - MarkMgr
    printCourseStatistics()

- ```JAVA
  public int getTotalSeats()
  ```

  - CourseMgr
    checkAvailableSlots()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
  - MarkMgr
    printCourseStatistics()

- ```JAVA
  public String getCourseDepartment()
  ```

  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
  - HelpInfoMgr
    printCourseInDepartment(String)

- ```java
  public String getCourseType()
  ```

  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public int getLecWeeklyHour()
  ```

  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public int getTutWeeklyHour()
  ```

  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public int getLabWeeklyHour()
  ```

  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public ArrayList<LectureGroup> getLectureGroups()
  ```

  - CourseMgr
    checkAvailableSlots()
  - CourseRegistrationMgr
    registerCourse()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public ArrayList<TutorialGroup> getTutorialGroups()
  ```

  - CourseMgr
    checkAvailableSlots()
  - CourseRegistrationMgr
    registerCourse()
    printStudents()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public ArrayList<LabGroup> getLabGroups()
  ```

  - CourseMgr
    checkAvailableSlots()
  - CourseRegistrationMgr
    registerCourse()
    printStudents()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)

- ```java
  public ArrayList<MainComponent> getMainComponents()
  ```

  - CourseMgr
    enterCourseWorkComponentWeightage(Course)
  - CourseRegistrationMgr
    registerCourse()
  - FILEMgr
    writeCourseIntoFile(Course)
    backUpCourse(ArrayList<Course>)
  - MarkMgr
    initializeMark(Student, Course)
    printCourseStatistics()

- ```java
  public void setVacancies(int vacancies)
  ```

  - FILEMgr
    loadCourses()

- ```java
  public void enrolledIn()
  ```

  - registerCourse()
    CourseRegistrationMgr

- ```java
  public void setTutorialGroups(ArrayList<TutorialGroup> tutorialGroups)
  ```

  - FILEMgr
    loadCourses()

- ```java
  public void setLabGroups(ArrayList<LabGroup> labGroups)
  ```

  - loadCourses()
    FILEMgr

- ```java
  public void setMainComponents(ArrayList<MainComponent> mainComponents)
  ```

  - CourseMgr
    enterCourseWorkComponentWeightage(Course)
  - FILEMgr
    loadCourses()

- ```java
  public void setTutWeeklyHour(int tutWeeklyHour)
  ```

  - loadCourses()
    FILEMgr

- ```java
  public void setLabWeeklyHour(int labWeeklyHour)
  ```

  - loadCourses()
    FILEMgr

  Total: 1+1+19+8+4+4+5+4+3+2+2+2+2+4+5+5+6+1+1+1+1+2+1+1 = 85

  ## CourseMgr

- ```java
  public static void addCourse()
  ```

  - main(String[])
    Main

- ```java
  public static void checkAvailableSlots()
  ```

  - main(String[])
    Main

- ```java
  public static void enterCourseWorkComponentWeightage(Course currentCourse)
  ```

  - CourseMgr
    addCourse()
  - Main
    main(String[])

- ```java
  public static void printCourses()
  ```

  - addCourse()
    CourseMgr

  Total: 5

  ## CourseRegistration

- ```java
  public CourseRegistration(Student student, Course course, String lectureGroup, String tutorialGroup, String labGroup)
  ```

  - CourseRegistrationMgr
    registerCourse()
  - FILEMgr
    loadCourseRegistration()

- ```java
  public Student getStudent()
  ```

  - CourseRegistrationMgr
    printStudents()
  - FILEMgr
    writeCourseRegistrationIntoFile(CourseRegistration)
  - ValidationMgr
    checkCourseRegistrationExists(String, String)

- ```java
  public Course getCourse()
  ```

  - CourseRegistrationMgr
    printStudents()
  - FILEMgr
    writeCourseRegistrationIntoFile(CourseRegistration)
  - ValidationMgr
    checkCourseRegistrationExists(String, String)

- ```java
  public String getLectureGroup()
  ```

  - CourseRegistration (inner class)
  - CourseRegistrationMgr
    printStudents()
  - FILEMgr
    writeCourseRegistrationIntoFile(CourseRegistration)

- ```java
  public String getTutorialGroup()
  ```

  - CourseRegistration (inner class)
  - CourseRegistrationMgr
    printStudents()
  - FILEMgr
    writeCourseRegistrationIntoFile(CourseRegistration)

- ```java
  public String getLabGroup()
  ```

  - CourseRegistration (inner class)
  - CourseRegistrationMgr
    printStudents()
  - FILEMgr
    writeCourseRegistrationIntoFile(CourseRegistration)

Total: 2+3+3+3+3+3 = 17


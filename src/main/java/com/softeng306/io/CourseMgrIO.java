package com.softeng306.io;

import com.softeng306.domain.course.CourseBuilder;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.exceptions.ProfessorNotFoundException;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;
import com.softeng306.enums.GroupType;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.ProfessorMgr;

import com.softeng306.validation.RegexValidator;

import java.util.*;

public class CourseMgrIO implements ICourseMgrIO {
    private Scanner scanner = new Scanner(System.in);
    private CourseMgr courseMgr = CourseMgr.getInstance();

    /**
     * Read in the number of groups for a particular stream (lecture, lab, tutorial)
     *
     * @param type       the type of group we are reading in
     * @param compareTo  the upper limit to compare it to
     * @param totalSeats the total number of seats available
     * @return the number of groups the user has inputted
     */
    @Override
    public int readNoOfGroup(String type, int compareTo, int totalSeats) {
        int noOfGroups;

        while (true) {
            System.out.println("Enter the number of " + type + " groups: ");

            if (scanner.hasNextInt()) {
                noOfGroups = scanner.nextInt();
                scanner.nextLine();
                boolean checkLimit;
                if (type.equals(GroupType.LECTURE_GROUP.toString())) {
                    checkLimit = noOfGroups > 0 && noOfGroups <= totalSeats;
                } else {
                    checkLimit = noOfGroups >= 0 && compareTo <= totalSeats;
                }
                if (checkLimit) break;
                System.out.println("Invalid input.");
                printInvalidNoGroup(type);
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return noOfGroups;
    }

    /**
     * Reads in a weekly hour for a group from the user
     *
     * @param type          the type of the group the weekly hour is for
     * @param academicUnits the number of academic units for the course
     * @return int the number of weekly hours for that group
     */
    @Override
    public int readWeeklyHour(String type, int academicUnits) {
        int weeklyHour;
        while (true) {
            System.out.format("Enter the weekly %s hour for this course: %n", type);
            if (scanner.hasNextInt()) {
                weeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (weeklyHour < 0 || weeklyHour > academicUnits) {
                    System.out.format("Weekly %s hour out of bound. Please re-enter.%n", type);
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return weeklyHour;
    }


    /**
     * Reads in the user's choice for creating components now
     *
     * @return int the choice the user specified for creating a component
     */
    @Override
    public int readCreateCourseComponentChoice() {
        int addCourseComponentChoice;
        System.out.println("Create course components and set component weightage now?");
        System.out.println("1. Yes");
        System.out.println("2. Not yet");
        addCourseComponentChoice = scanner.nextInt();
        scanner.nextLine();

        while (addCourseComponentChoice > 2 || addCourseComponentChoice < 0) {
            System.out.println("Invalid choice, please choose again.");
            System.out.println("1. Yes");
            System.out.println("2. Not yet");
            addCourseComponentChoice = scanner.nextInt();
            scanner.nextLine();
        }

        return addCourseComponentChoice;
    }

    @Override
    public void printCourseAdded(String courseID) {
        System.out.println("Course " + courseID + " is added");
    }

    @Override
    public void printComponentsNotInitialisedMessage(String courseID) {
        System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
    }

    @Override
    public void printCourseInfoString(String courseInfoString) {
        System.out.println(courseInfoString);
        System.out.println("--------------------------------------------");
    }

    @Override
    public void printVacanciesForGroups(String[][] groupInformation, String groupType) {
        groupType = groupType.substring(0, 1).toUpperCase() + groupType.substring(1);
        for (String[] group : groupInformation) {
            System.out.format("%s group %s (Available/Total): %s/%s%n",
                    groupType, group[0], group[1], group[2]);
        }
    }

    @Override
    public void printCourseNotExist() {
        System.out.println("This course does not exist. Please check again.");
    }

    @Override
    public int readHasFinalExamChoice() {
        int hasFinalExamChoice;

        System.out.println("Does this course have a final exam? Enter your choice:");
        System.out.println("1. Yes! ");
        System.out.println("2. No, all CAs.");
        hasFinalExamChoice = scanner.nextInt();
        scanner.nextLine();

        return hasFinalExamChoice;
    }

    @Override
    public int readExamWeight() {
        int examWeight;

        System.out.println("Please enter weight of the exam: ");
        examWeight = scanner.nextInt();
        scanner.nextLine();
        while (examWeight > 80 || examWeight <= 0) {
            if (examWeight > 80 && examWeight <= 100) {
                System.out.println("According to the course assessment policy, final exam cannot take up more than 80%...");
            }
            System.out.println("Weight entered is invalid, please enter again: ");
            examWeight = scanner.nextInt();
            scanner.nextLine();
        }

        return examWeight;
    }

    @Override
    public void printEnterContinuousAssessments() {
        System.out.println("Okay, please enter some continuous assessments");
    }

    @Override
    public int readNoOfMainComponents() {
        int numberOfMain;

        while (true) {
            System.out.println("Enter number of main component(s) to add:");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of main component(s) to add:");
            }
            numberOfMain = scanner.nextInt();
            if (numberOfMain < 0) {
                System.out.println("Please enter a valid positive integer:");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return numberOfMain;
    }

    @Override
    public int readMainComponentWeightage(int i, int totalWeightage) {
        int weight;
        while (true) {
            System.out.println("Enter main component " + (i + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter main component " + (i + 1) + " weightage:");
            }
            weight = scanner.nextInt();
            if (weight < 0 || weight > totalWeightage) {
                System.out.println("Please enter a weight between 0 ~ " + totalWeightage + ":");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return weight;
    }

    @Override
    public int readNoOfSubComponents(int mainComponentNo) {
        int noOfSub;

        while (true) {
            System.out.println("Enter number of sub component under main component " + (mainComponentNo + 1) + ":");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of sub component under main component " + (mainComponentNo + 1) + ":");
            }
            noOfSub = scanner.nextInt();
            if (noOfSub < 0) {
                System.out.println("Please enter a valid integer:");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return noOfSub;
    }

    @Override
    public void printWeightageError() {
        System.out.println("Weightage assigned does not tally to 100!");
        System.out.println("You have to reassign!");
    }

    @Override
    public void printComponentsForCourse(String courseId, String courseName, Map<Map<String, String>, Map<String, String>> allGroupInformation) {
        System.out.println(courseId + " " + courseName + " components: ");
        for (Map<String, String> mainComponentInfo : allGroupInformation.keySet()) {
            Map.Entry<String, String> entry = mainComponentInfo.entrySet().iterator().next();
            System.out.println("    " + entry.getKey() + " : " + entry.getValue() + "%");

            Map<String, String> allSubComponentInfo = allGroupInformation.get(mainComponentInfo);
            for (String subComponentInfo : allSubComponentInfo.keySet()) {
                System.out.println("        " + subComponentInfo + " : " + allSubComponentInfo.get(subComponentInfo) + "%");
            }
        }
    }

    @Override
    public String readMainComponentName(int totalWeightage, int mainComponentNo, Set<String> mainComponentNames) {
        return readComponentName(totalWeightage, mainComponentNames, mainComponentNo, CourseMgr.getInstance().getMainComponentString());
    }

    @Override
    public Map<String, Double> readSubComponents(int numberOfSubComponents) {
        Map<String, Double> subComponents = new HashMap<>();
        Set<String> subComponentNames = new HashSet<>();
        boolean invalidDistributionOfWeights = true;
        double subComponentWeight;
        String subComponentName;

        while (invalidDistributionOfWeights) {

            int subComponentTotalWeight = 100;
            for (int j = 0; j < numberOfSubComponents; j++) {
                subComponentName = readComponentName(subComponentTotalWeight, subComponentNames, j, CourseMgr.getInstance().getSubComponentString());
                subComponentWeight = readSubWeight(j, subComponentTotalWeight);
                subComponentNames.add(subComponentName);
                subComponents.put(subComponentName, subComponentWeight);
                subComponentTotalWeight -= subComponentWeight;
            }

            if (subComponentTotalWeight != 0 && numberOfSubComponents != 0) {
                printSubComponentWeightageError();
                subComponents.clear();
                subComponentNames.clear();
                invalidDistributionOfWeights = true;
            } else {
                invalidDistributionOfWeights = false;
            }
            //exit if weight is fully allocated
        }
        return subComponents;
    }

    @Override
    public void printEmptyCourseComponents(String courseID, String courseName) {
        System.out.println("Currently course " + courseID + " " + courseName + " does not have any assessment component.");
    }

    @Override
    public void printCourses(Map<String, List<String>> courseGeneralInfo) {
        List<String> courseIDs = new ArrayList<>(courseGeneralInfo.keySet());
        Collections.sort(courseIDs);
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (String courseID : courseIDs) {
            List<String> info = courseGeneralInfo.get(courseID);
            System.out.println("| " + courseID + " | " + info.get(0) + " | " + info.get(1) + " |");
        }
        System.out.println();
    }

    @Override
    public void printAllCourseIds(List<String> courses) {
        for (String courseID : courses) {
            System.out.println(courseID);
        }
    }

    @Override
    public void printCourseworkWeightageEnteredError() {
        System.out.println("Course Assessment has been settled already!");
    }

    @Override
    public void printCourseStatisticsHeader(List<String> courseInfo) {
        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + courseInfo.get(0) + "\tCourse Name: " + courseInfo.get(1));
        System.out.println("Course AU: " + courseInfo.get(2));
        System.out.println();
        System.out.print("Total Slots: " + courseInfo.get(3));
        int enrolledNumber = (Integer.parseInt(courseInfo.get(3)) - Integer.parseInt(courseInfo.get(4)));
        System.out.println("\tEnrolled Student: " + enrolledNumber);
        System.out.printf("Enrollment Rate: %4.2f %%\n", ((double) enrolledNumber / Double.parseDouble(courseInfo.get(3)) * 100d));
        System.out.println();
    }

    @Override
    public void printMainComponent(String mainComponentName, int mainComponentWeight, double averageCourseMark) {
        System.out.print("Main Component: " + mainComponentName);
        System.out.print("\tWeight: " + mainComponentWeight + "%");
        System.out.println("\t Average: " + averageCourseMark);
    }

    @Override
    public void printSubcomponents(String[][] subComponentInformation, Map<String, Double> courseMarks) {
        for (int i = 0; i < subComponentInformation.length; i++) {
            printSubComponentInfo(subComponentInformation[i][0], subComponentInformation[i][1]);
            System.out.println("\t Average: " + courseMarks.get(subComponentInformation[i][0]));
        }
        System.out.println();
    }

    @Override
    public void printExamStatistics(int examWeight, Double examMark) {
        System.out.print("Final Exam");
        System.out.print("\tWeight: " + examWeight + "%");
        System.out.println("\t Average: " + examMark);
    }

    @Override
    public void printNoExamMessage() {
        System.out.println("This course does not have final exam.");
    }

    @Override
    public void printOverallPerformance(double overallMark) {
        System.out.println();
        System.out.print("Overall Performance: ");
        System.out.printf("%4.2f \n", overallMark);
        System.out.println();
        System.out.println("***********************************************");
        System.out.println();
    }

    @Override
    public String readValidCourseIdFromUser() {
        String courseID;
        while (true) {
            System.out.println("Enter course ID (-h to print all the course ID):");
            courseID = scanner.nextLine();
            while ("-h".equals(courseID)) {
                courseMgr.printAllCourseIds();
                courseID = scanner.nextLine();
            }
            if (!courseMgr.checkCourseExists(courseID)) {
                System.out.println("Invalid Course ID. Please re-enter.");
            } else {
                break;
            }
        }
        return courseID;
    }

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    @Override
    public String readDepartmentWithMoreThanOneCourseFromUser() {
        String courseDepartment;
        while (true) {
            System.out.println("Which department's courses are you interested? (-h to print all the departments)");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                printAllStringsInListByIndex(Department.getListOfAllDepartmentNames());
                courseDepartment = scanner.nextLine();
            }
            if (Department.contains(courseDepartment)) {
                List<String> validCourseString;
                validCourseString = courseMgr.getCourseIdsInDepartment(courseDepartment);
                if (validCourseString.size() == 0) {
                    System.out.println("Invalid choice of department.");
                } else {
                    break;
                }
            } else {
                System.out.println("The department is invalid. Please re-enter.");
            }
        }
        return courseDepartment;
    }

    @Override
    public void addCourse() {

        ICourseBuilder builder = new CourseBuilder();

        String courseID = readCourseId();
        String courseName = readCourseName();

        int totalSeats = readTotalSeats();
        int AU = readAcademicUnitsForCourse();

        String courseDepartment = readAnyCourseDepartment();

        String courseType = readCourseType();

        int noOfLectureGroups = courseMgr.getNumberOfLectureGroups(totalSeats, totalSeats);
        int lecWeeklyHour = courseMgr.getReadWeeklyLectureHour(AU);

        //Name, total seats
        Map<String, Double> lectureGroups = readLectureGroups(totalSeats, noOfLectureGroups);

        int noOfTutorialGroups = courseMgr.getNumberOfTutorialGroups(noOfLectureGroups, totalSeats);
        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            tutWeeklyHour = courseMgr.getReadWeeklyTutorialHour(AU);
        }
        Map<String, Double> tutorialGroups = readGroup(noOfTutorialGroups, totalSeats, GroupType.TUTORIAL_GROUP.toString());

        int noOfLabGroups = courseMgr.getNumberOfLabGroups(noOfLectureGroups, totalSeats);
        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            labWeeklyHour = courseMgr.getReadWeeklyLabHour(AU);
        }
        Map<String, Double> labGroups = readGroup(noOfLabGroups, totalSeats, GroupType.LAB_GROUP.toString());

        String profID = readProfessor(courseDepartment);

        //Setting the objects in the builder
        builder.setCourseID(courseID);

        builder.setCourseName(courseName);

        builder.setTotalSeats(totalSeats);

        builder.setAcademicUnits(AU);

        builder.setCourseDepartment(courseDepartment);

        builder.setCourseType(courseType);

        // Lecture groups
        builder.setLecWeeklyHour(lecWeeklyHour);
        builder.setLectureGroups(lectureGroups);

        // Tutorial groups
        builder.setTutWeeklyHour(tutWeeklyHour);
        builder.setTutorialGroups(tutorialGroups);

        // Lab groups
        builder.setLabWeeklyHour(labWeeklyHour);
        builder.setLabGroups(labGroups);


        //Professor
        try {
            builder.setProfInCharge(profID);
            courseMgr.addCourse(builder);
        } catch (ProfessorNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkAvailableSlots() {
        courseMgr.checkAvailableSlots();
    }

    @Override
    public void enterCourseWorkComponentWeightage() {
        courseMgr.enterCourseWorkComponentWeightage(null);
    }

    @Override
    public void printCourseStatistics() {
        courseMgr.printCourseStatistics();
    }


    @Override
    public void printEmptySpace() {
        System.out.println();
    }

    /**
     * Print out a message to console saying that the number of groups is invalid
     *
     * @param type the type of the group to output the message for
     */
    private void printInvalidNoGroup(String type) {
        if (type.equals(GroupType.LAB_GROUP.toString())) {
            System.out.println("Number of lab group must be non-negative.");
        } else if (type.equals(GroupType.LECTURE_GROUP.toString())) {
            System.out.println("Number of lecture group must be positive but less than total seats in this course.");
        } else if (type.equals(GroupType.TUTORIAL_GROUP.toString())) {
            System.out.println("Number of tutorial group must be non-negative.");
        }
    }


    /**
     * Prints all the strings in the input list by its index
     * (starting from 1), and a :
     * e.g. 1: inputString
     */
    private void printAllStringsInListByIndex(List<String> list) {
        for (int index = 1; index <= list.size(); index++) {
            String name = list.get(index - 1);
            System.out.println(index + ": " + name);
        }
    }

    /**
     * Displays a list of all the course types.
     */
    private void printAllCourseType(List<String> courseTypes) {
        int index = 1;
        for (String courseType : courseTypes) {
            System.out.println(index + ": " + courseType);
            index++;
        }
    }

    /**
     * Prompts the user to enter a group name that conforms with correct group name format
     *
     * @param existingGroups     The groups that have been created already
     * @param groupDisplayString The group string to use in outputs
     * @return User-specficied group name
     */
    private String readValidGroupNameFromUser(Map<String, Double> existingGroups, String groupDisplayString) {
        boolean groupNameExists;
        String groupName;

        System.out.println("Give a name to this " + groupDisplayString + " group");
        do {
            groupNameExists = false;
            System.out.println("Enter a group Name: ");
            groupName = scanner.nextLine();

            if (!RegexValidator.checkValidGroupNameInput(groupName)) {
                groupNameExists = true;
                continue;
            }
            if (existingGroups.isEmpty()) {
                break;
            }
            if (existingGroups.containsKey(groupName)) {
                groupNameExists = true;
                System.out.println("This " + groupDisplayString + " group already exist for this course.");
            }

        } while (groupNameExists);

        return groupName;
    }

    /**
     * Read in a professor from the user for a particular course department
     *
     * @param courseDepartment the course department that the professor should be in
     * @return Professor the professor the user has specified
     */
    private String readProfessor(String courseDepartment) {
        IProfessorMgrIO professorIO = new ProfessorMgrIO();

        List<String> professorsInDepartment = ProfessorMgr.getInstance().getAllProfIDInDepartment(courseDepartment);
        String profID;

        while (true) {
            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                professorIO.printAllProfIDsInDepartment(professorsInDepartment);
                profID = scanner.nextLine();
            }

            if (ProfessorMgr.getInstance().checkProfessorExists(profID)) {
                if (professorsInDepartment.contains(profID)) {
                    break;
                } else {
                    System.out.println("This prof is not in " + courseDepartment + ".");
                    System.out.println("Thus he/she cannot teach this course.");
                }
            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
        }

        return profID;
    }


    /**
     * Read in a courseId from the user
     *
     * @return String courseId
     */
    private String readCourseId() {
        String courseID;
        // Can make the sameCourseID as boolean, set to false.
        while (true) {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();
            if (RegexValidator.checkValidCourseIDInput(courseID)) {

                // Check course ID does not already exist for a course
                if (courseMgr.checkCourseExists(courseID)) {
                    System.out.println("Sorry. The course ID is used. This course already exists.");
                } else {
                    break;
                }
            }
        }

        return courseID;
    }

    /**
     * Read in a course name from the user
     *
     * @return String courseName
     */
    private String readCourseName() {
        System.out.println("Enter course Name: ");
        return scanner.nextLine();
    }


    /**
     * Read in the total number of seats for a course
     *
     * @return int totalSeats for a course
     */
    private int readTotalSeats() {
        int totalSeats;
        while (true) {
            System.out.println("Enter the total vacancy of this course: ");
            if (scanner.hasNextInt()) {
                totalSeats = scanner.nextInt();
                if (totalSeats <= 0) {
                    System.out.println("Please enter a valid vacancy (greater than 0)");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                System.out.println("Please re-enter");
            }
        }

        return totalSeats;
    }

    /**
     * Read in the number of academic units for a course
     *
     * @return int AU representing number of Academic Units
     */
    private int readAU() {
        int AU;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                AU = scanner.nextInt();
                scanner.nextLine();
                if (AU < 0 || AU > 10) {
                    System.out.println("AU out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return AU;
    }

    /**
     * Read in the number of academic units for a course
     *
     * @return number of Academic Units for the course.
     */
    private int readAcademicUnitsForCourse() {
        int AU;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                AU = scanner.nextInt();
                scanner.nextLine();
                if (AU < 0 || AU > 10) {
                    System.out.println("AU out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return AU;
    }

    /**
     * Reads in the sub component weightage from the user
     *
     * @param subComponentNo the sub component to read the weight for
     * @param sub_totWeight  the total subcomponent weightage
     * @return int the sub component weight
     */
    private int readSubWeight(int subComponentNo, int sub_totWeight) {
        int sub_weight;
        while (true) {
            System.out.println("Enter sub component " + (subComponentNo + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter sub component " + (subComponentNo + 1) + " weightage (out of the main component): ");
            }
            sub_weight = scanner.nextInt();
            if (sub_weight < 0 || sub_weight > sub_totWeight) {
                System.out.println("Please enter a weight between 0 ~ " + sub_totWeight + ":");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return sub_weight;
    }

    /**
     * Prints an error when the sub components do not add to 100
     */
    private void printSubComponentWeightageError() {
        System.out.println("ERROR! sub component weightage does not tally to 100");
        System.out.println("You have to reassign!");
    }

    private String readComponentName(int totalWeightAssignable, Set<String> componentNames, int componentNumber, String componentType) {

        String componentName;
        boolean componentExist;

        do {
            componentExist = false;

            if (componentType.equals(courseMgr.getMainComponentString())) {
                System.out.println("Total weightage left to assign: " + totalWeightAssignable);
            } else {
                System.out.println("Total weightage left to assign to sub component: " + totalWeightAssignable);
            }
            System.out.println("Enter " + componentType + " " + (componentNumber + 1) + " name: ");
            componentName = scanner.nextLine();

            if (componentNames.isEmpty()) {
                break;
            }
            if (componentName.equals("Exam")) {
                System.out.println("Exam is a reserved assessment.");
                componentExist = true;
                continue;
            }
            if (componentNames.contains(componentName)) {
                componentExist = true;
                System.out.println("This component already exist. Please enter another name.");
            }
        } while (componentExist);

        return componentName;

    }

    /**
     * Reads in and constructs groups for a course from the user
     *
     * @param numGroups          the total groups to create
     * @param maxSeats           the total number of seats that can be allocated to the groups
     * @param groupDisplayString the type of group being created (used for output only)
     * @return the new groups the user has created
     */
    private Map<String, Double> readGroup(int numGroups, int maxSeats, String groupDisplayString) {
        Map<String, Double> groups = new HashMap<>();
        String groupName;

        int groupCapacity;
        int totalAllocatedSeats = 0;
        for (int i = 0; i < numGroups; i++) {
            groupName = readValidGroupNameFromUser(groups, groupDisplayString);

            while (true) {
                System.out.println("Enter this " + groupDisplayString + " group's capacity: ");
                if (scanner.hasNextInt()) {
                    groupCapacity = scanner.nextInt();
                    scanner.nextLine();
                    totalAllocatedSeats += groupCapacity;

                    if ((i != numGroups - 1) || (totalAllocatedSeats >= maxSeats)) {
                        groups.put(groupName, (double) groupCapacity);
                        break;
                    } else {
                        System.out.println("Sorry, the total capacity you allocated for all the " + groupDisplayString + " groups is not enough for this course.");
                        System.out.println("Please re-enter the capacity for the last " + groupDisplayString + " group " + groupName + " you have entered.");
                        totalAllocatedSeats -= groupCapacity;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }

        return groups;
    }


    /**
     * Read in a course department for a course from the user
     *
     * @return String courseDepartment for a course
     */
    private String readAnyCourseDepartment() {
        String courseDepartment;
        while (true) {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                printAllStringsInListByIndex(Department.getListOfAllDepartmentNames());
                courseDepartment = scanner.nextLine();
            }
            if (Department.contains(courseDepartment)) {
                break;
            } else {
                System.out.println("The department is invalid. Please re-enter.");
            }
        }

        return courseDepartment;
    }

    /**
     * Read in a course type from the user
     *
     * @return String courseType for a course
     */
    private String readCourseType() {
        String courseType;
        while (true) {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                printAllCourseType(CourseMgr.getInstance().getListCourseTypes());
                courseType = scanner.nextLine();
            }
            if (CourseType.contains(courseType)) {
                break;
            } else {
                System.out.println("The course type is invalid. Please re-enter.");
            }
        }
        return courseType;
    }

    /**
     * Reads in and constructs lecture groups for a course from the user
     *
     * @param totalSeats        the total number of seats that can be allocated to the lecture groups
     * @param noOfLectureGroups the total number of lecture groups to make
     * @return the lecture groups the user has specified
     */
    private Map<String, Double> readLectureGroups(int totalSeats, int noOfLectureGroups) {
        String lectureGroupName;
        int lectureGroupCapacity;
        int seatsLeft = totalSeats;

        Map<String, Double> lectureGroups = new HashMap<>();

        for (int i = 0; i < noOfLectureGroups; i++) {
            lectureGroupName = readValidGroupNameFromUser(lectureGroups, GroupType.LECTURE_GROUP.toString());

            while (true) {
                System.out.println("Enter this lecture group's capacity: ");
                while (true) {
                    if (scanner.hasNextInt()) {
                        lectureGroupCapacity = scanner.nextInt();
                        scanner.nextLine();
                        if (lectureGroupCapacity > 0) {
                            break;
                        }
                        System.out.println("Capacity must be positive. Please re-enter.");
                    } else {
                        System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                    }
                }
                seatsLeft -= lectureGroupCapacity;
                if ((seatsLeft > 0 && i != (noOfLectureGroups - 1)) || (seatsLeft == 0 && i == noOfLectureGroups - 1)) {
                    lectureGroups.put(lectureGroupName, (double) lectureGroupCapacity);
                    break;  //break from the while loop
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                    System.out.println("Please re-enter the capacity for the last lecture group " + lectureGroupName + " you have entered.");
                    seatsLeft += lectureGroupCapacity;
                }
            }
        }

        return lectureGroups;
    }

    /**
     * Print the info for a sub component
     */
    private void printSubComponentInfo(String subComponentName, String subComponentWeight) {
        System.out.print("Sub Component: " + subComponentName);
        System.out.print("\tWeight: " + subComponentWeight + "% (in main component)");
    }


}

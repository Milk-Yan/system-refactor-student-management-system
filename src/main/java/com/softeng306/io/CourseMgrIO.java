package com.softeng306.io;

import com.softeng306.domain.course.CourseBuilder;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;
import com.softeng306.enums.GroupType;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;

import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.ProfessorMgr;
import com.softeng306.validation.CourseValidator;
import com.softeng306.validation.GroupValidator;
import com.softeng306.validation.ProfessorValidator;

import java.util.*;

public class CourseMgrIO {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Read in a courseId from the user
     *
     * @return String courseId
     */
    public String readCourseId() {
        String courseID;
        // Can make the sameCourseID as boolean, set to false.
        while (true) {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();
            if (CourseValidator.checkValidCourseIDInput(courseID)) {
                // Check course ID does not already exist for a course
                if (CourseValidator.getCourseFromId(courseID) == null) {
                    break;
                }
                System.out.println("Sorry. The course ID is used. This course already exists.");
            }
        }

        return courseID;
    }

    /**
     * Read in a course name from the user
     *
     * @return String courseName
     */
    public String readCourseName() {
        System.out.println("Enter course Name: ");
        return scanner.nextLine();
    }


    /**
     * Read in the total number of seats for a course
     *
     * @return int totalSeats for a course
     */
    public int readTotalSeats() {
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
    public int readAU() {
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
     * Read in the number of groups for a particular stream (lecture, lab, tutorial)
     *
     * @param type       the type of group we are reading in
     * @param compareTo  the upper limit to compare it to
     * @param totalSeats the total number of seats available
     * @return the number of groups the user has inputted
     */
    public int readNoOfGroup(GroupType type, int compareTo, int totalSeats) {
        int noOfGroups;

        while (true) {
            if (type.equals(GroupType.TUTORIAL_GROUP)) {
                System.out.println("Enter the number of " + type + " groups:");
            } else {
                System.out.println("Enter the number of " + type + " groups: ");
            }
            if (scanner.hasNextInt()) {
                noOfGroups = scanner.nextInt();
                scanner.nextLine();
                boolean checkLimit;
                if (type == GroupType.LECTURE_GROUP) {
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
     * Print out a message to console saying that the number of groups is invalid
     *
     * @param type the type of the group to output the message for
     */
    private void printInvalidNoGroup(GroupType type) {
        if (type == GroupType.LAB_GROUP) {
            System.out.println("Number of lab group must be non-negative.");
        } else if (type == GroupType.LECTURE_GROUP) {
            System.out.println("Number of lecture group must be positive but less than total seats in this course.");
        } else if (type == GroupType.TUTORIAL_GROUP) {
            System.out.println("Number of tutorial group must be non-negative.");
        }
    }

    /**
     * Reads in a weekly hour for a group from the user
     *
     * @param type the type of the group the weekly hour is for
     * @param AU   the number of academic units for the course
     * @return int the number of weekly hours for that group
     */
    public int readWeeklyHour(GroupType type, int AU) {
        int weeklyHour;
        while (true) {
            System.out.format("Enter the weekly %s hour for this course: %n", type);
            if (scanner.hasNextInt()) {
                weeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (weeklyHour < 0 || weeklyHour > AU) {
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
    public int readCreateCourseComponentChoice() {
        // TODO: Use enum instead of int to store choice
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

    /**
     * Prints a success message that a course has been successfully added
     *
     * @param courseID the course to print the success message for
     */
    public void printCourseAdded(String courseID) {
        System.out.println("Course " + courseID + " is added");
    }

    /**
     * Prints an information message that the components have not been initialized
     *
     * @param courseID the course that the components have not been initialized for
     */
    public void printComponentsNotInitialized(String courseID) {
        System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
    }

    /**
     * Print course information to the console for a user
     *
     */
    public void printCourseInfoString(String courseInfoString){
        System.out.println(courseInfoString);
        System.out.println("--------------------------------------------");
    }

    /**
     * Helper method to print out the vacancies for groups
     *
     * @param groupInformation the groups to print
     */
    public void printVacanciesForGroups(String[][] groupInformation, String groupType) {
        groupType = groupType.substring(0, 1).toUpperCase() + groupType.substring(1);
        for (String[] group: groupInformation) {
                System.out.format("%s group %s (Available/Total): %s/%s%n",
                        groupType, group[0], group[1], group[2]);
        }
    }

    /**
     * Print out an error message that the course specified does not exist
     */
    public void printCourseNotExist() {
        System.out.println("This course does not exist. Please check again.");
    }

    /**
     * Reads in the users choice for whether a course should have a final exam
     *
     * @return int whether the user wants to have a final exam for a course
     */
    public int readHasFinalExamChoice() {
        int hasFinalExamChoice;

        System.out.println("Does this course have a final exam? Enter your choice:");
        System.out.println("1. Yes! ");
        System.out.println("2. No, all CAs.");
        hasFinalExamChoice = scanner.nextInt();
        scanner.nextLine();

        return hasFinalExamChoice;
    }

    /**
     * Reads in an exam weighting for a course from the user
     *
     * @return int the exam weight
     */
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

    /**
     * Print out a message to the user asking to enter assessments
     */
    public void printEnterContinuousAssessments() {
        System.out.println("Okay, please enter some continuous assessments");
    }

    /**
     * Reads in the number of main components for a course from the user
     *
     * @return int number of main components specified
     */
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

    /**
     * Reads in the main component weightage from the user
     *
     * @param i              the number entry for the main component
     * @param totalWeightage the current total weightage
     * @return the main component weightage
     */
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

    /**
     * Reads in the number of sub components for a main component from the user
     *
     * @param mainComponentNo the main component number the sub components are for
     * @return int the number of sub components
     */
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

    /**
     * Reads in the sub component weightage from the user
     *
     * @param subComponentNo the sub component to read the weight for
     * @param sub_totWeight  the total subcomponent weightage
     * @return int the sub component weight
     */
    public int readSubWeight(int subComponentNo, int sub_totWeight) {
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
    public void printSubComponentWeightageError() {
        System.out.println("ERROR! sub component weightage does not tally to 100");
        System.out.println("You have to reassign!");
    }

    /**
     * Prints an error that the total weightage does not add to 100
     */
    public void printWeightageError() {
        System.out.println("Weightage assigned does not tally to 100!");
        System.out.println("You have to reassign!");
    }

    /**
     * Prints the components for a course
     *
     */
    public void printComponentsForCourse(String courseId, String courseName, HashMap<HashMap<String, String>, HashMap<String,String>> allGroupInformation) {
        System.out.println(courseId + " " + courseName + " components: ");
        for(HashMap<String, String> mainComponentInfo : allGroupInformation.keySet()){
            Map.Entry<String,String> entry = mainComponentInfo.entrySet().iterator().next();
            System.out.println("    " + entry.getKey() + " : " + entry.getValue() + "%");

            HashMap<String, String> allSubComponentInfo = allGroupInformation.get(mainComponentInfo);
            for(String subComponentInfo : allSubComponentInfo.keySet()){
                System.out.println("        " + subComponentInfo + " : " + allSubComponentInfo.get(subComponentInfo) + "%");
            }

        }
    }

    /**
     * Read in the name for a main component from the user
     *
     * @param totalWeightage  the total weightage for a course
     * @param mainComponentNo the main component number
     * @return the main component name
     */
    public String readMainComponentName(int totalWeightage, int mainComponentNo, HashSet<String> mainComponentNames) {
        return readComponentName(totalWeightage, mainComponentNames, mainComponentNo, MainComponent.COMPONENT_NAME);
    }

    /**
     * Read in the sub components specified by a user for a course
     *
     * @return List of sub components user has specified
     */
    public HashMap<String, Double> readSubComponents(int numberOfSubComponents) {
        HashMap<String, Double> subComponents = new HashMap<>();
        HashSet<String> subComponentNames = new HashSet<>();
        boolean invalidDistributionOfWeights = true;
        double subComponentWeight;
        String subComponentName;

        while (invalidDistributionOfWeights) {

            int subComponentTotalWeight = 100;
            for (int j = 0; j < numberOfSubComponents; j++) {
                subComponentName = readComponentName(subComponentTotalWeight, subComponentNames, j, SubComponent.COMPONENT_NAME);
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


    public String readComponentName(int totalWeightAssignable, Set<String> componentNames, int componentNumber, String componentType) {

        String componentName;
        boolean componentExist;

        do {
            componentExist = false;
            if(componentType.equals(MainComponent.COMPONENT_NAME)){
                System.out.println("Total weightage left to assign: " + totalWeightAssignable);
            } else {
                System.out.println("Total weightage left to assign to sub component: " + totalWeightAssignable);
            }
            System.out.println("Enter " + componentType +" "+ (componentNumber + 1) + " name: ");
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
     * Print an info message that the course specified is empty
     */
    public void printEmptyCourseComponents(String courseID, String courseName) {
        System.out.println("Currently course " + courseID + " " + courseName + " does not have any assessment component.");
    }


    /**
     * Print courses
     *
     */
    public void printCourses(HashMap<String, List<String>> courseGeneralInfo) {
        List<String> courseIDs = new ArrayList<>(courseGeneralInfo.keySet());
        Collections.sort(courseIDs);
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (String courseID : courseIDs) {
            List<String> info = courseGeneralInfo.get(courseID);
            System.out.println("| " + courseID + " | " + info.get(0)  + " | " + info.get(1) + " |");
        }
        System.out.println();
    }

    /**
     * Print all the course ids
     *
     * @param courses the courses to print ids for
     */
    public void printAllCourseIds(List<String> courses) {
        for (String courseID : courses) {
            System.out.println(courseID);
        }
    }

    /**
     * Print error that course assessment is settled
     */
    public void printCourseworkWeightageEnteredError() {
        System.out.println("Course Assessment has been settled already!");
    }

    /**
     * Print course statistics header for a particular course
     *
     */

    public void printCourseStatisticsHeader(List<String> courseInfo) {
        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + courseInfo.get(0) + "\tCourse Name: " + courseInfo.get(1));
        System.out.println("Course AU: " + courseInfo.get(2));
        System.out.println();
        System.out.print("Total Slots: " + courseInfo.get(3));
        int enrolledNumber = (Integer.parseInt(courseInfo.get(3)) - Integer.parseInt(courseInfo.get(4)));
        System.out.println("\tEnrolled Student: " + enrolledNumber);
        System.out.printf("Enrollment Rate: %4.2f %%\n", ((double) enrolledNumber / (double) Double.parseDouble(courseInfo.get(3)) * 100d));
        System.out.println();
    }

    /**
     * Print a main component to the user
     *
     */
    public void printMainComponent(String mainComponentName, int mainComponentWeight, double averageCourseMark) {
        System.out.print("Main Component: " + mainComponentName);
        System.out.print("\tWeight: " + mainComponentWeight + "%");

        System.out.println("\t Average: " + averageCourseMark);
    }

    /**
     * Print statistics for subcomponents
     *
     */
    public void printSubcomponents(String[][] subComponentInformation, Map<String, Double> courseMarks) {
        for(int i = 0; i<subComponentInformation.length; i++){
            printSubComponentInfo(subComponentInformation[i][0], subComponentInformation[i][1]);
            System.out.println("\t Average: " + courseMarks.get(subComponentInformation[i][0]));
        }
        System.out.println();
    }

    /**
     * Print the info for a sub component
     */
    public void printSubComponentInfo(String subComponentName, String subComponentWeight) {
        System.out.print("Sub Component: " + subComponentName);
        System.out.print("\tWeight: " + subComponentWeight + "% (in main component)");
    }

    /**
     * Print statistics for an exam to the user
     *
     */
    public void printExamStatistics(int examWeight, Double examMark) {
        System.out.print("Final Exam");
        System.out.print("\tWeight: " + examWeight + "%");
        System.out.println("\t Average: " + examMark);
    }

    /**
     * Print message that course has no exam
     */
    public void printNoExamMessage() {
        System.out.println("This course does not have final exam.");
    }

    /**
     * Print overall performance based on course marks
     *
     * @param overallMark for the course
     */
    public void printOverallPerformance(double overallMark) {
        System.out.println();
        System.out.print("Overall Performance: ");
        System.out.printf("%4.2f \n", overallMark);

        System.out.println();
        System.out.println("***********************************************");
        System.out.println();
    }

    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    public String readValidCourseIdFromUser() {
        String courseID;
        while (true) {
            System.out.println("Enter course ID (-h to print all the course ID):");
            courseID = scanner.nextLine();
            while ("-h".equals(courseID)) {
                CourseMgr.getInstance().printAllCourseIds();
                courseID = scanner.nextLine();
            }
            if (!CourseValidator.checkCourseIDExists(courseID)) {
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
    public String readDepartmentWithMoreThanOneCourseFromUser() {
        String courseDepartment;
        while (true) {
            System.out.println("Which department's courses are you interested? (-h to print all the departments)");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                this.printAllDepartments(CourseMgr.getInstance().getAllDepartmentsNameList());
                courseDepartment = scanner.nextLine();
            }
            if (CourseMgr.getInstance().checkContainsDepartment(courseDepartment)) {
                List<String> validCourseString;
                validCourseString = CourseMgr.getInstance().getCourseIdsInDepartment(courseDepartment);
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

    public void printAllDepartments(List<String> departments){
        int index = 1;
        for(String department : departments){
            System.out.println(index + ": " + department);
            index++;
        }
    }



    /**
     * Read in a course department for a course from the user
     *
     * @return String courseDepartment for a course
     */
    public String readAnyCourseDepartment() {
        String courseDepartment;
        while (true) {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                this.printAllDepartments(CourseMgr.getInstance().getAllDepartmentsNameList());
                courseDepartment = scanner.nextLine();
            }
            if (CourseMgr.getInstance().checkContainsDepartment(courseDepartment)) {
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
    public String readCourseType() {
        String courseType;
        while (true) {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                CourseType.printAllCourseType();
                courseType = scanner.nextLine();
            }
            if (CourseValidator.checkCourseTypeValidation(courseType)) {
                break;
            }
        }
        return courseType;
    }

    /**
     * Reads in and constructs lecture groups for a course from the user
     *
     * @param totalSeats        the total number of seats for the course
     * @param noOfLectureGroups the number of lecture groups for a course
     * @return the lecture groups the user has specified
     */
    public HashMap<String, Double> readLectureGroups(int totalSeats, int noOfLectureGroups) {
        String lectureGroupName;
        int lectureGroupCapacity;
        int seatsLeft = totalSeats;
        boolean groupNameExists;

        HashMap<String, Double> lectureGroups = new HashMap<>();

        for (int i = 0; i < noOfLectureGroups; i++) {
            System.out.println("Give a name to the lecture group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                lectureGroupName = scanner.nextLine();
                if (!GroupValidator.checkValidGroupNameInput(lectureGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (lectureGroups.isEmpty()) {
                    break;
                }
                if(lectureGroups.containsKey(lectureGroupName)){
                    groupNameExists = true;
                    System.out.println("This lecture group already exist for this course.");
                }

            } while (groupNameExists);


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
                    lectureGroups.put(lectureGroupName, (double)lectureGroupCapacity);
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
     * Reads in and constructs tutorial groups for a course from the user
     *
     * @param totalSeats         the total number of seats for the course
     * @param noOfTutorialGroups the number of tutorial groups for a course
     * @return the tutorial groups the user has specified
     */
    public HashMap<String, Double> readTutorialGroups(int noOfTutorialGroups, int totalSeats) {
        HashMap<String, Double> tutorialGroups = new HashMap<>();
        String tutorialGroupName;
        int tutorialGroupCapacity;
        boolean groupNameExists;
        int totalTutorialSeats = 0;

        for (int i = 0; i < noOfTutorialGroups; i++) {
            System.out.println("Give a name to the tutorial group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                tutorialGroupName = scanner.nextLine();
                if (!GroupValidator.checkValidGroupNameInput(tutorialGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (tutorialGroups.isEmpty()) {
                    break;
                }
                if(tutorialGroups.containsKey(tutorialGroupName)){
                    groupNameExists = true;
                    System.out.println("This tutorial group already exist for this course.");
                }
            } while (groupNameExists);

            while (true) {
                System.out.println("Enter this tutorial group's capacity: ");
                if (scanner.hasNextInt()) {
                    tutorialGroupCapacity = scanner.nextInt();
                    scanner.nextLine();
                    totalTutorialSeats += tutorialGroupCapacity;
                    if ((i != noOfTutorialGroups - 1) || (totalTutorialSeats >= totalSeats)) {
                        tutorialGroups.put(tutorialGroupName, (double)tutorialGroupCapacity);
                        break;
                    } else {
                        System.out.println("Sorry, the total capacity you allocated for all the tutorial groups is not enough for this course.");
                        System.out.println("Please re-enter the capacity for the last tutorial group " + tutorialGroupName + " you have entered.");
                        totalTutorialSeats -= tutorialGroupCapacity;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }

        return tutorialGroups;
    }

    /**
     * Reads in and constructs lab groups for a course from the user
     *
     * @param totalSeats    the total number of seats for the course
     * @param noOfLabGroups the number of lab groups for a course
     * @return the lab groups the user has specified
     */
    public HashMap<String, Double> readLabGroups(int noOfLabGroups, int totalSeats) {
        HashMap<String, Double> labGroups = new HashMap<>();
        int totalLabSeats = 0;
        String labGroupName;
        boolean groupNameExists;
        for (int i = 0; i < noOfLabGroups; i++) {
            System.out.println("Give a name to this lab group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                labGroupName = scanner.nextLine();
                if (!GroupValidator.checkValidGroupNameInput(labGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (labGroups.isEmpty()) {
                    break;
                }
                if(labGroups.containsKey(labGroupName)){
                    groupNameExists = true;
                    System.out.println("This lab group already exist for this course.");
                }
            } while (groupNameExists);

            while (true) {
                System.out.println("Enter this lab group's capacity: ");
                int labGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                totalLabSeats += labGroupCapacity;
                if ((i != noOfLabGroups - 1) || (totalLabSeats >= totalSeats)) {
                    labGroups.put(labGroupName, (double)labGroupCapacity);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lab groups is not enough for this course.");
                    System.out.println("Please re-enter the capacity for the last lab group " + labGroupName + " you have entered.");
                    totalLabSeats -= labGroupCapacity;
                }
            }
        }

        return labGroups;
    }

    /**
     * Read in a professor from the user for a particular course department
     *
     * @param courseDepartment the course department that the professor should be in
     * @return Professor the professor the user has specified
     */
    public String readProfessor(String courseDepartment) {
        List<String> professorsInDepartment = ProfessorMgr.getInstance().getAllProfIDInDepartment(Department.valueOf(courseDepartment));
        String profID;

        while (true) {
            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                ProfessorMgrIO.printAllProfIDsInDepartment(professorsInDepartment);
                profID = scanner.nextLine();
            }

            if (ProfessorValidator.checkProfessorExists(profID)) {
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


    public void addCourse(){

        ICourseBuilder builder = new CourseBuilder();

        String courseID = this.readCourseId();
        String courseName = this.readCourseName();

        int totalSeats = this.readTotalSeats();
        int AU = this.readAU();

        String courseDepartment = this.readAnyCourseDepartment();

        String courseType = this.readCourseType();

        int noOfLectureGroups = CourseMgr.getInstance().getNumberOfLectureGroups(totalSeats, totalSeats);
        int lecWeeklyHour = CourseMgr.getInstance().getReadWeeklyLectureHour(AU);

        //Name, total seats
        HashMap<String, Double> lectureGroups = this.readLectureGroups(totalSeats, noOfLectureGroups);

        int noOfTutorialGroups = CourseMgr.getInstance().getNumberOfTutorialGroups(noOfLectureGroups, totalSeats);
        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            tutWeeklyHour = CourseMgr.getInstance().getReadWeeklyTutorialHour(AU);
        }
        HashMap<String, Double> tutorialGroups = this.readTutorialGroups(noOfTutorialGroups, totalSeats);

        int noOfLabGroups = CourseMgr.getInstance().getNumberOfLabGroups(noOfLectureGroups, totalSeats);
        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            labWeeklyHour = CourseMgr.getInstance().getReadWeeklyLabHour(AU);
        }
        HashMap<String, Double> labGroups = this.readLabGroups(noOfLabGroups, totalSeats);

        String profID = this.readProfessor(courseDepartment);

        //Setting the objects in the builder
        builder.setCourseID(courseID);

        builder.setCourseName(courseName);

        builder.setTotalSeats(totalSeats);

        builder.setAU(AU);

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
        builder.setProfInCharge(profID);
        CourseMgr.getInstance().addCourse(builder);
    }

    public void checkAvailableSlots(){
        CourseMgr.getInstance().checkAvailableSlots();
    }

    public void enterCourseWorkComponentWeightage(){
        CourseMgr.getInstance().enterCourseWorkComponentWeightage(null);
    }

    public void printCourseStatistics(){
        CourseMgr.getInstance().printCourseStatistics();
    }


}

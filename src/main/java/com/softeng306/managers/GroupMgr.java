package com.softeng306.managers;

import com.softeng306.domain.course.group.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GroupMgr {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    A list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    public static String printGroupWithVacancyInfo(String groupType, List<Group> groups) {
        int index;
        HashMap<String, Integer> groupAssign = new HashMap<>(0);
        int selectedGroupNum;
        String selectedGroupName = null;

        if (groups.size() != 0) {
            System.out.println("Here is a list of all the " + groupType + " groups with available slots:");
            do {
                index = 0;
                for (Group group : groups) {
                    if (group.getAvailableVacancies() == 0) {
                        continue;
                    }
                    index++;
                    System.out.println(index + ": " + group.getGroupName() + " (" + group.getAvailableVacancies() + " vacancies)");
                    groupAssign.put(group.getGroupName(), index);
                }
                System.out.println("Please enter an integer for your choice:");
                selectedGroupNum = scanner.nextInt();
                scanner.nextLine();
                if (selectedGroupNum < 1 || selectedGroupNum > index) {
                    System.out.println("Invalid choice. Please re-enter.");
                } else {
                    break;
                }
            } while (true);

            for (HashMap.Entry<String, Integer> entry : groupAssign.entrySet()) {
                String groupName = entry.getKey();
                int num = entry.getValue();
                if (num == selectedGroupNum) {
                    selectedGroupName = groupName;
                    break;
                }
            }

            for (Group group : groups) {
                if (group.getGroupName().equals(selectedGroupName)) {
                    group.enrolledIn();
                    break;
                }
            }
        }
        return selectedGroupName;
    }
}

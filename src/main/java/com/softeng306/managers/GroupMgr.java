package com.softeng306.managers;

import com.softeng306.domain.course.group.Group;
import com.softeng306.enums.GroupType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GroupMgr implements IGroupMgr {
    private Scanner scanner = new Scanner(System.in);

    private static IGroupMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private GroupMgr() { }

    /**
     * Return the IGroupMgr singleton, if not initialised already, create an instance.
     *
     * @return IGroupMgr the singleton instance
     */
    public static IGroupMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new GroupMgr();
        }

        return singleInstance;
    }

    @Override
    public Group printGroupWithVacancyInfo(GroupType groupType, List<Group> groups) {
        int index;
        Map<String, Integer> groupAssign = new HashMap<>(0);
        int selectedGroupNum;

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
                    // valid selection
                    Group selectedGroup = groups.get(selectedGroupNum - 1);
                    selectedGroup.enrolledIn();
                    return selectedGroup;
                }
            } while (true);

        }

        // no groups exist
        return null;
    }
}

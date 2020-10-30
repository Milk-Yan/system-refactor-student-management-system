package com.softeng306.managers;

import com.softeng306.domain.course.group.IGroup;
import com.softeng306.enums.GroupType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Concrete implementation of {@code IGroupMgr}.
 * Provides implementations of methods to deal with lecture/tutorial/lab group operations.
 */
public class GroupMgr implements IGroupMgr {
    /**
     * The input stream to user input from.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Singleton instance of this group manager.
     */
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
    public IGroup printGroupWithVacancyInfo(GroupType groupType, List<IGroup> groups) {
        int index;
        Map<String, Integer> groupAssign = new HashMap<>(0);
        int selectedGroupNum;

        //Make sure there are groups to choose from
        if (groups.size() != 0) {
            System.out.println("Here is a list of all the " + groupType + " groups with available slots:");
            do {
                index = 0;
                for (IGroup group : groups) {
                    //Check if group has spots left for registrations.
                    if (group.getAvailableVacancies() == 0) {
                        continue;
                    }
                    index++;

                    System.out.println(index + ": " + group.getGroupName() + " (" + group.getAvailableVacancies() + " vacancies)");
                    groupAssign.put(group.getGroupName(), index);
                }
                //Get user choice
                System.out.println("Please enter an integer for your choice:");
                selectedGroupNum = scanner.nextInt();
                scanner.nextLine();

                //Check that choice is valid
                if (selectedGroupNum < 1 || selectedGroupNum > index) {
                    System.out.println("Invalid choice. Please re-enter.");
                } else {
                    // valid selection
                    IGroup selectedGroup = groups.get(selectedGroupNum - 1);
                    selectedGroup.updateVacanciesForEnrollment();
                    return selectedGroup;
                }

            } while (true);

        }

        // no groups exist
        return null;
    }

}

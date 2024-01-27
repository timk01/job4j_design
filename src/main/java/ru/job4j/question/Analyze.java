package ru.job4j.question;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Analyze {

    private static Map<Integer, String> getUserMap(Set<User> current) {
        return current.stream()
                .collect(Collectors.toMap(User::getId, User::getName));
    }

    public static Info diff(Set<User> previous, Set<User> current) {
        Info info = new Info(0, 0, 0);

        Map<Integer, String> userMap = getUserMap(current);

        int added = 0;
        int changed = 0;
        int deleted = 0;
        for (User user : previous) {
            if (userMap.containsKey(user.getId())
                    && !userMap.get(user.getId()).equals(user.getName())) {
                ++changed;
            }
            if (!userMap.containsKey(user.getId())) {
                ++deleted;
            }
            userMap.remove(user.getId());
        }

        added = userMap.size();
        info.setAdded(added);
        info.setChanged(changed);
        info.setDeleted(deleted);

        return info;
    }
}
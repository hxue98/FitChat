package fitchat.com.fitchat;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sort {

    public static void sortByPreference(List<User> list, Map<String, Integer> priorityMap) {
        final Map<User, Integer> sortPriority = new HashMap<>();
        User currentUser = Model.getInstance().getCurrentUser();
        for (User user : list) {
            int priority = 0;
            if (Math.abs(currentUser.getAge() - user.getAge()) <= 5) {
                priority += priorityMap.get("age");
            }
            if (currentUser.getGender().equals(user.getGender())) {
                priority += priorityMap.get("gender");
            }
            if (Math.abs(currentUser.getWeight() - user.getWeight()) <= 10) {
                priority += priorityMap.get("weight");
            }
            double distance = getDistance(currentUser.getLatitude(), currentUser.getLongitude(), user.getLatitude(), user.getLongitude());
            if (distance <= 10) {
                priority += (10 - distance) / 3;
            } else {
                priority -= distance / 10;
            }
            sortPriority.put(user, priority);
        }
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                return sortPriority.get(user) - sortPriority.get(t1);
            }
        });
    }

    public static double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        return 43.75 * Math.sqrt((latitude1 - latitude2) *  (latitude1 - latitude2) + (longitude1 - longitude2) * (longitude1 - longitude2));
    }
}

package Comparator;

import Util.Mockable;
import java.util.Comparator;

public class MockComparator implements Comparator<Mockable> {

    public int compare(Mockable o1, Mockable o2) {
        if (o1.getId() < o2.getId()) {
            return 1;
        } else if (o1.getId() > o2.getId()) {
            return -1;
        }
        return 0;
    }
}

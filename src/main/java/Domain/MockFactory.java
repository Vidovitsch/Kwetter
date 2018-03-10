package Domain;

import Comparator.MockComparator;

import java.util.Collections;
import java.util.List;

public class MockFactory {

    public static List<Mockable> createMocks(Class<? extends Mockable> mockable, int numberOfMocks) {
        return null;
    }

    public static void setNextId(Mockable mockable, List<? extends Mockable> mocks) {
        Collections.sort(mocks, new MockComparator());
        mockable.setId(mocks.get(0).getId() + 1);
    }
}


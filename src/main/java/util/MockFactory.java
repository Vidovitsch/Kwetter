package util;

import comparators.MockComparator;
import java.lang.reflect.Field;
import java.util.*;

public class MockFactory {

    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * Generates a mocks of a given type with Mockable as super class.
     * Each property in the mock gets a random String, Date en int value.
     *
     * @param mockable
     * @param numberOfMocks
     * @return List of mocks
     */
    public static List<? extends Mockable> createMocks(Class<? extends Mockable> mockable, int numberOfMocks) {
        return createMocks(mockable, numberOfMocks, null, null);
    }

    public static List<? extends Mockable> createMocks(Class<? extends Mockable> mockable, int numberOfMocks, String fieldName, Object fieldValue) {
        try {
            List<Mockable> mocks = generateList(mockable, numberOfMocks);
            for (Mockable mock : mocks) {
                Field[] fields = mock.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (fieldName != null && field.getName().equals(fieldName)) {
                        presetFieldValue(mock, field, fieldValue);
                    } else {
                        // Set some random stuff
                        randomizeFieldValue(mock, field);
                    }
                }
            }
            return mocks;
        } catch (InstantiationException|IllegalAccessException ex) {
            System.out.println(ex);
        }
        return new ArrayList<>();
    }

    public static void setNextId(Mockable newMock, List<? extends Mockable> existingMocks) {
        if (existingMocks.size() == 0) {
            newMock.setId((long)0);
        } else {
            existingMocks.sort(new MockComparator());
            newMock.setId(existingMocks.get(0).getId() + 1);
        }
    }

    public static void setNextIds(List<? extends Mockable> newMocks, List<? extends Mockable> existingMocks) {
        Long startPoint = (long)0;
        if (existingMocks != null && existingMocks.size() != 0) {
            Collections.sort(existingMocks, new MockComparator());
            startPoint = existingMocks.get(0).getId() + 1;
        }
        for (int i = 0; i < newMocks.size(); i++) {
            newMocks.get(i).setId(startPoint + i);
        }
    }

    public static void setNewIds(List<? extends Mockable> mockables) {
        for (int i = 0; i < mockables.size(); i++) {
            mockables.get(i).setId((long)i);
        }
    }

    private static void randomizeFieldValue(Mockable mock, Field field) throws IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);

        // Set some random stuff
        if (field.getType().isAssignableFrom(String.class)) {
            field.set(mock, generateRandomString(16));
        } else if (field.getType().isAssignableFrom(Date.class)) {
            field.set(mock, new Date());
        } else if (field.getType().isAssignableFrom(int.class)) {
            field.set(mock, getRandomNumber(999));
        }

        field.setAccessible(isAccessible);
    }

    private static void presetFieldValue(Mockable mock, Field field, Object fieldValue) throws IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        field.set(mock, fieldValue);
        field.setAccessible(isAccessible);
    }

    private static List<Mockable> generateList(Class<? extends Mockable> mockable, int numberOfMocks)
            throws IllegalAccessException, InstantiationException{
        List<Mockable> mocks = new ArrayList<>();
        for (int i = 0; i < numberOfMocks; i++) {
            mocks.add(mockable.newInstance());
        }
        return mocks;
    }

    private static String generateRandomString(int length) {
        StringBuffer randStr = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = getRandomNumber(CHAR_LIST.length());
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    private static int getRandomNumber(int max) {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(max);
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
}


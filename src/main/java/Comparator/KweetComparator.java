package Comparator;

import Domain.Kweet;

import java.util.Comparator;

public class KweetComparator implements Comparator<Kweet> {

    public int compare(Kweet o1, Kweet o2) {
        if (o1.getPublicationDate().before(o2.getPublicationDate())) {
            return 1;
        } else if (o1.getPublicationDate().after(o2.getPublicationDate())) {
            return -1;
        }
        return 0;
    }
}

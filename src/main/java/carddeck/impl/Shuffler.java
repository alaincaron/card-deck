package carddeck.impl;

import carddeck.model.Card;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public enum Shuffler {
    ;

    public static <T> void shuffle(List<T> list, Random random) {
        if (list == null) {
            throw new IllegalArgumentException("Invalid null list");
        }
        if (!(list instanceof RandomAccess)) {
            throw new IllegalArgumentException("List is not random access");

        }

        final int n = list.size() - 1;
        int remaining = list.size();
        for (int i = 0; i < n; ++i) {
            final int pos = i + random.nextInt(remaining--);
            final T tmp = list.get(i);
            list.set(i, list.get(pos));
            list.set(pos, tmp);
        }
    }

    public static <T> void shuffle(List<T> list) {
        shuffle(list, new SecureRandom());
    }

}

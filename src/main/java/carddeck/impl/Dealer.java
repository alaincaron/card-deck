package carddeck.impl;

import carddeck.model.Card;
import com.google.common.collect.Lists;

import java.util.List;

public enum Dealer {
    ;
    public static <T> List<T> deal(List<T> deck, int nbItemsToDeal) {
        if (nbItemsToDeal <0) {
            throw new IllegalArgumentException("Invalid number of items to deal: " + nbItemsToDeal);
        }
        if (nbItemsToDeal > deck.size()) {
            return Lists.newArrayList();
        } else {
            final List<T> result = Lists.newArrayListWithCapacity(nbItemsToDeal);
            int idx = deck.size();
            for (int i = 0; i < nbItemsToDeal; ++i) {
                result.add(deck.remove(--idx));
            }
            return result;
        }
    }
}

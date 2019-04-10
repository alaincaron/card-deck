package carddeck.impl;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.leq;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ShufflerTest {

    private final Random mockRandom = mock(Random.class);


    @Test
    public void randomNoChange() {
        final List<Integer> list = Lists.newArrayList(1, 2,  3, 4, 5);
        final List<Integer> expected = Lists.newArrayList(list);

        doReturn(0).when(mockRandom).nextInt(anyInt());

        Shuffler.shuffle(list, mockRandom);
        assertEquals(expected, list);
    }

    @Test
    public void randomReverse() {
        final List<Integer> list = Lists.newArrayList(1, 2,  3, 4, 5);
        final List<Integer> expected = Lists.reverse(Lists.newArrayList(list));

        doReturn(4).when(mockRandom).nextInt(5);
        doReturn(2).when(mockRandom).nextInt(4);
        doReturn(0).when(mockRandom).nextInt(leq(3));

        Shuffler.shuffle(list, mockRandom);
        assertEquals(expected, list);
    }



}
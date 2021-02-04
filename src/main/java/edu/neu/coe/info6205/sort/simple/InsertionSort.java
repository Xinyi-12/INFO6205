/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.simple;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Config;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort() {
        this(new BaseHelper<>(DESCRIPTION));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {

        final Helper<X> helper = getHelper();

        // TO BE IMPLEMENTED
        for (int begin = from + 1; begin < to; begin++) {
            int cur = begin;
            while (cur > 0 && helper.compare(xs,cur, cur - 1) < 0) {
                helper.swap(xs,cur,cur-1);
                cur--;
            }
        }

//        for (X x: xs) {
//            System.out.print(x + "_");
//        }
    }

    /**
     * This is used by unit tests.
     *
     * @param ys  the array to be sorted.
     * @param <Y> the underlying element type.
     */
    public static <Y extends Comparable<Y>> void mutatingInsertionSort(Y[] ys) {
        new InsertionSort<Y>().mutatingSort(ys);
    }

    public static final String DESCRIPTION = "Insertion sort";




    public static void main(String[] args) throws IOException {
        int repeatTime = 10;
        int n = 100;

        Config config = Config.load();
        Helper<Integer> iHelper = HelperFactory.create("insersionSort", n, config);
        iHelper.init(n);
        //create a random array
        Integer[] arrays = iHelper.random(Integer.class, r -> r.nextInt(2000));

        //random running time
        double meanTime = new Timer().repeat(repeatTime,() ->arrays, f ->{
            SortWithHelper<Integer> isort = new InsertionSort<>(iHelper);
            f = isort.sort(f);
            return null;
        });
        System.out.println("【random】 " + meanTime);

        //order running time
        UnaryOperator<Integer[]> ascOrder = t->{
          return Arrays.stream(t).sorted(Comparator.naturalOrder()).toArray(Integer[]::new);
        };
        meanTime = new Timer().repeat(repeatTime,() -> arrays,f->{
            SortWithHelper<Integer> isort = new InsertionSort<>(iHelper);
            f = isort.sort(f);
            return null;
        },ascOrder,null);
        System.out.println("【order】 " + meanTime);

        //reserved running time
        UnaryOperator<Integer[]> descOrder = t->{
          return Arrays.stream(t).sorted(Comparator.reverseOrder()).toArray(Integer[]::new);
        };

        meanTime = new Timer().repeat(repeatTime,()-> arrays,f->{
            SortWithHelper<Integer> isort = new InsertionSort<>(iHelper);
            f = isort.sort(f);
            return null;
        },descOrder,null);
        System.out.println("【reserved order】 " + meanTime);

    }
}


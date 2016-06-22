package utfpr.calcnum.utils;

import java.util.ArrayList;

/**
 * Created by rsegecin on 6/20/2016.
 */
public class Utils {

    public static double [] ConvertToDouble(ArrayList<Double> doubleArrayList) {
        double [] nums = new double[doubleArrayList.size()];

        for (int i = 0; i < doubleArrayList.size(); i++) {
            nums[i] = doubleArrayList.get(i);
        }

        return nums;
    }

}

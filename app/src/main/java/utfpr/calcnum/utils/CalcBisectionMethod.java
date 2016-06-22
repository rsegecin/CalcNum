package utfpr.calcnum.utils;

import java.util.ArrayList;

import utfpr.calcnum.data.DataBisection;
import utfpr.calcnum.data.DataPrecision;

/**
 * Created by rsegecin on 3/21/2016.
 */
public class CalcBisectionMethod {

    public static ArrayList<DataBisection> CalcBisectionMethod(double a, double b, String formula, DataPrecision dp) {
        ArrayList<DataBisection> bisections = new ArrayList<>();

        int steps = Steps(a, b, dp);

        bisections.add(new DataBisection(a, b, formula));

        for (int i = 0; i < steps - 1; i++) {
            if (bisections.get(i).Fa < bisections.get(i).Fb) {
                if (bisections.get(i).Fx > 0) {
                    bisections.add(new DataBisection(bisections.get(i).A, bisections.get(i).X, formula));
                }
                else {
                    bisections.add(new DataBisection(bisections.get(i).X, bisections.get(i).B, formula));
                }
            }
            else {
                if (bisections.get(i).Fx > 0) {
                    bisections.add(new DataBisection(bisections.get(i).X, bisections.get(i).B, formula));
                }
                else {
                    bisections.add(new DataBisection(bisections.get(i).A, bisections.get(i).X, formula));
                }
            }
        }

        return bisections;
    }

    private static int Steps(double a, double b, DataPrecision dp) {
        return (int) Math.ceil((Math.log10(b - a) - dp.Precision) / Math.log10(2));
    }
}

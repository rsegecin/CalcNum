package utfpr.calcnum.utils;

import java.util.ArrayList;

/**
 * Created by rsegecin on 6/20/2016.
 */
public class CalculateSpline {

    private ArrayList<double []> Equations = new ArrayList<>();
    private ArrayList<double []> Coefficients;
    public ArrayList<double []> CubicSplines = new ArrayList<>();

    public CalculateSpline(String values) {
        SetVariables(values);
        CreateCoefficients();
        try {
            MakeGaussString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void MakeGaussString() throws Exception {
        GaussSeidel gaussSeidel = new GaussSeidel();

        String strGauss = "";

        for (int i = 0; i < Coefficients.size(); i++) {

            for (int j = 0; j < Coefficients.get(0).length; j++) {
                strGauss += Coefficients.get(i)[j] + " \t";
            }
            strGauss += "\n";
        }

        ArrayList<double []> g = gaussSeidel.CalculateSeidel(strGauss, 0.001);

        for (int k = 1; k < Equations.get(0).length; k++) {
            double coef [] = new double[4];
            double gk = g.get(g.size() - 1)[k];
            double gkmenos1;

            if ((k - 1) > 0)
                gkmenos1 = g.get(g.size() - 1)[k - 1];
            else
                gkmenos1 = 0;

            coef[0] = (gk - gkmenos1) / ((6 * hValue(k)));
            coef[1] = gk / 2;
            coef[2] = ((yValue(k) - yValue(k - 1)) / hValue(k)) + (((2 * hValue(k) * gk) + (gkmenos1 * hValue(k))) / 6);
            coef[3] = yValue(k);

            CubicSplines.add(coef);
        }
    }

    private void SetVariables(String variablesParam) {
        variablesParam = variablesParam.replace("\t", " ");
        String [] lines = variablesParam.split("\n");

        for (String str: lines) {
            String [] strNumbers = str.split(" ");
            ArrayList<Double> nums = new ArrayList<>();

            for (int i = 0; i < strNumbers.length; i++) {
                if (!strNumbers[i].isEmpty())
                    nums.add(Double.valueOf(strNumbers[i].trim()));
            }
            Equations.add(Utils.ConvertToDouble(nums));
        }
    }

    public void CreateCoefficients() {
        Coefficients = new ArrayList<>();

        int k = 1;
        double g [] = new double[4];
        g[0] = 2 * (hValue(k) + hValue(k + 1));
        g[1] = hValue(k + 1);
        g[2] = 0;
        g[3] = 6 * (((yValue(k + 1) - yValue(k)) / (hValue(k + 1))) - ((yValue(k) - yValue(k - 1)) / (hValue(k))));
        Coefficients.add(g);

        k = 2;
        g = new double[4];
        g[0] = hValue(k);
        g[1] = 2 * (hValue(k) + hValue(k + 1));
        g[2] = hValue(k + 1);
        g[3] = 6 * (((yValue(k + 1) - yValue(k)) / (hValue(k + 1))) - ((yValue(k) - yValue(k - 1)) / (hValue(k))));
        Coefficients.add(g);

        k = 3;
        g = new double[4];
        g[0] = 0;
        g[1] = hValue(k);
        g[2] = 2 * (hValue(k) + hValue(k + 1));
        g[3] = 6 * (((yValue(k + 1) - yValue(k)) / (hValue(k + 1))) - ((yValue(k) - yValue(k - 1)) / (hValue(k))));
        Coefficients.add(g);
    }

    private double hValue(int k) {
        return Equations.get(0)[k] - Equations.get(0)[k - 1];
    }

    private double yValue(int k) {
        return Equations.get(1)[k];
    }

    public double CalculateY(double x) throws Exception {
        int i = GetInterval(x);
        double ret;

        if (i == -1)
            throw new Exception("Ponto menor que os intervalos disponíveis.");

        ret = CubicSplines.get(i)[0] * Math.pow((x - Equations.get(0)[i + 1]), 3);
        ret += CubicSplines.get(i)[1] * Math.pow((x - Equations.get(0)[i + 1]), 2);
        ret += CubicSplines.get(i)[2] * Math.pow((x - Equations.get(0)[i + 1]), 1);
        ret += CubicSplines.get(i)[3];

        return ret;
    }

    private int GetInterval(double x) {
        for (int i = 1; i < Equations.get(0).length; i++) {
            if (x < Equations.get(0)[i]) {
                return i - 1;
            }
        }

        return -1;
    }
}

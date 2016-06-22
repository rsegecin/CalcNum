package utfpr.calcnum.data;

import utfpr.calcnum.utils.MathEval;

/**
 * Created by rsegecin on 3/21/2016.
 */
public class DataBisection {

    public double A;
    public double B;
    public double X;
    public double Fa;
    public double Fb;
    public double Fx;

    public DataBisection(double a, double b, String formula) {
        A = a;
        B = b;
        X = (a+b) / 2;

        MathEval mth = new MathEval();
        mth.setVariable("x", A);
        Fa = mth.evaluate(formula);

        mth = new MathEval();
        mth.setVariable("x", B);
        Fb = mth.evaluate(formula);

        mth = new MathEval();
        mth.setVariable("x", X);
        Fx = mth.evaluate(formula);
    }

}

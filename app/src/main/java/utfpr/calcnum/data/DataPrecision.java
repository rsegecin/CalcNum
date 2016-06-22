package utfpr.calcnum.data;

/**
 * Created by rsegecin on 3/20/2016.
 */
public class DataPrecision {
    public int Precision;

    public DataPrecision(int p) {
        Precision = p;
    }

    public String GetName() {
        return "10^" + Precision;
    }
}

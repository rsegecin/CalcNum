package utfpr.calcnum.utils;

import java.util.ArrayList;

/**
 * Created by rsegecin on 6/19/2016.
 */
public class GaussSeidel {

    private ArrayList<double []> equacoes = new ArrayList<>();
    public ArrayList<double []> iterations = new ArrayList<>();
    double erro;
    double [] variables;
    double [] variablesTmp;

    public ArrayList<double []> CalculateSeidel(String variablesParam, double erroParam) throws Exception {
        erro = erroParam;
        SetVariables(variablesParam);
        return Calculate();
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
            equacoes.add(Utils.ConvertToDouble(nums));
        }
    }

    private ArrayList<double []> Calculate() throws Exception {
        double err = 0;
        double soma;

        if ((equacoes.size() + 1) != equacoes.get(0).length) {
            throw new Exception("Sistema Indeterminado");
        }
        variables = new double[equacoes.size()];
        variablesTmp = new double[equacoes.size()];

        for (int k = 0; k < 300; k++) {
            double iteration [] = new double[variables.length + 2]; // índice + variables.length + erro

            iteration[0] = k;

            for (int i = 0; i < equacoes.size(); i++) {
                soma = Sum(i);
                variables[i] = (equacoes.get(i)[equacoes.get(i).length - 1] - soma) / equacoes.get(i)[i];
                iteration[i + 1] = variables[i];
            }

            err = CurrentError();

            iteration[variables.length + 1] = err;
            iterations.add(iteration);

            if (err < erro) {
                break;
            }

            for (int i = 0; i < equacoes.size(); i++) {
                variablesTmp[i] = variables[i];
            }
        }

        return iterations;
    }

    private double Sum(int exceptIndex) {
        double sum = 0;

        for (int i = 0; i < variables.length; i++) {
            if (i != exceptIndex) {
                sum += equacoes.get(exceptIndex)[i] * variables[i];
            }
        }

        return sum;
    }

    public double CurrentError() {
        double e = Math.abs(variables[0] - variablesTmp[0]);
        double etmp;

        for (int i = 0; i < variables.length; i++) {
            etmp = Math.abs(variables[i] - variablesTmp[i]);
            if (etmp > e) {
                e = etmp;
            }
        }

        return e;
    }

}

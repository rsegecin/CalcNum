package utfpr.calcnum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import utfpr.calcnum.data.DataBisection;
import utfpr.calcnum.data.DataPrecision;
import utfpr.calcnum.utils.BisectionDataItemAdapter;
import utfpr.calcnum.utils.CalcBisectionMethod;
import utfpr.calcnum.utils.SimpleArrayAdapter;

/**
 * Created by rsegecin on 3/20/2016.
 */
public class BisectionMethod extends Fragment {

    ArrayList<DataPrecision> precisionsList = new ArrayList<>();
    ArrayList<String> strPrecisionsList = new ArrayList<>();
    ArrayList<DataBisection> bisectionArrayList;

    Spinner spinnerPrecision;
    TextView tfMin, tfMax, tfFunction;
    Button btnCalculate;
    GridView gvBisection;

    public BisectionMethod() {
        for (int i = 0; i > -10; i--) {
            DataPrecision dp = new DataPrecision(i);
            precisionsList.add(dp);
            strPrecisionsList.add(dp.GetName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bisection_method, container, false);

        getActivity().setTitle("Método de Bissecção");

        tfMin = (TextView) rootView.findViewById(R.id.tfMin);
        tfMax = (TextView) rootView.findViewById(R.id.tfMax);
        spinnerPrecision = (Spinner) rootView.findViewById(R.id.spinnerPrecision);
        tfFunction = (TextView) rootView.findViewById(R.id.tfFunction);
        btnCalculate = (Button) rootView.findViewById(R.id.btnCalculate);
        gvBisection = (GridView) rootView.findViewById(R.id.gvBisection);

        SimpleArrayAdapter adapterPrecision =
                new SimpleArrayAdapter(getContext(), R.layout.listview_item,
                        strPrecisionsList, SimpleArrayAdapter.eTextAlign.left);
        spinnerPrecision.setAdapter(adapterPrecision);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculate();

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                            .getWindowToken(), 0);
                }
            }
        });

        return rootView;
    }

    private void Calculate() {
        double a, b;

        try {
            a = Double.parseDouble(tfMin.getText().toString());
            b = Double.parseDouble(tfMax.getText().toString());

            if (!tfFunction.getText().toString().isEmpty()) {
                bisectionArrayList = CalcBisectionMethod.CalcBisectionMethod(a, b,
                        tfFunction.getText().toString(),
                        precisionsList.get(spinnerPrecision.getSelectedItemPosition()));
            }
            else {
                ShowAlertDialog("Verifique os valores de entrada.");
            }

            BisectionDataItemAdapter adapter = new BisectionDataItemAdapter(getContext(),
                    R.layout.bisection_data_item_view, bisectionArrayList);

            gvBisection.setAdapter(adapter);

            if (bisectionArrayList.size() > 1)
                ShowAlertDialog(String.format("A raíz encontrada é %s.", bisectionArrayList.get(bisectionArrayList.size() - 1).X));
        }
        catch (Exception ex) {
            ShowAlertDialog("Verifique os valores de entrada.");
        }
    }

    public void ShowAlertDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Método de Bissecção");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

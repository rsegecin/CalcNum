package utfpr.calcnum;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import utfpr.calcnum.utils.CalculateSpline;
import utfpr.calcnum.utils.DynamicDataItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragSpline extends Fragment {

    EditText tfCoef;
    EditText tfPonto;
    Button btnCalculate;
    GridView gvSpline;

    public FragSpline() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_frag_spline, container, false);

        tfCoef = (EditText) rootView.findViewById(R.id.tfCoef);
        tfPonto = (EditText) rootView.findViewById(R.id.tfPonto);
        btnCalculate = (Button) rootView.findViewById(R.id.btnCalculate);
        gvSpline = (GridView) rootView.findViewById(R.id.gvSpline);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                CalculateSpline calculateSpline = new CalculateSpline(tfCoef.getText().toString());

                DynamicDataItemAdapter adapter = new DynamicDataItemAdapter(getContext(),
                        R.layout.seidel_data_item_view, calculateSpline.CubicSplines,
                        300, 30, 300);

                gvSpline.setAdapter(adapter);

                ViewGroup.LayoutParams layoutParams = gvSpline.getLayoutParams();
                layoutParams.width = adapter.Width; //this is in pixels
                gvSpline.setLayoutParams(layoutParams);

                ShowAlertDialog("Danielle, f(" + tfPonto.getText() + ") = " +
                        calculateSpline.CalculateY(Double.valueOf(tfPonto.getText().toString())));
            }
            catch (Exception ex) {
                ShowAlertDialog("Verifique a formatação dos coeficientes.");
            }
            }
        });

        return rootView;
    }

    public void ShowAlertDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Spline Cúbica");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

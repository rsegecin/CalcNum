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

import java.util.ArrayList;

import utfpr.calcnum.utils.DynamicDataItemAdapter;
import utfpr.calcnum.utils.GaussSeidel;

public class FragGaussSeidel extends Fragment {

    EditText tfCoef;
    EditText tfError;
    Button btnCalculate;
    GridView gvSeidel;

    public FragGaussSeidel() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gauss_seidel, container, false);

        tfCoef = (EditText) rootView.findViewById(R.id.tfCoef);
        tfError = (EditText) rootView.findViewById(R.id.tfError);
        btnCalculate = (Button) rootView.findViewById(R.id.btnCalculate);
        gvSeidel = (GridView) rootView.findViewById(R.id.gvSeidel);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double error = Double.valueOf(tfError.getText().toString().replace(",", "."));
                    GaussSeidel gaussSeidel = new GaussSeidel();
                    ArrayList<double []> calculus = gaussSeidel.CalculateSeidel(tfCoef.getText().toString().replace(",", "."), error);

                    DynamicDataItemAdapter adapter = new DynamicDataItemAdapter(getContext(),
                            R.layout.seidel_data_item_view, calculus);

                    gvSeidel.setAdapter(adapter);

                    ViewGroup.LayoutParams layoutParams = gvSeidel.getLayoutParams();
                    layoutParams.width = adapter.Width; //this is in pixels
                    gvSeidel.setLayoutParams(layoutParams);
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
        alertDialogBuilder.setTitle("Gauss Seidel");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

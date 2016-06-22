package utfpr.calcnum.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by rsegecin on 6/21/2016.
 */
public class DynamicDataItemAdapter extends ArrayAdapter<double []> {

    Context context;
    int layoutResourceId;
    ArrayList<double []> data;
    private int indexWidth = 120;
    private int paddingWidth = 30;
    private int colWidth = 300;
    public int Width;


    private class ItemViewHolder {
        public TextView tvlist [];

        public ItemViewHolder(int variables, Context context) {
            tvlist = new TextView[variables];

            for (int i = 0; i < variables; i++) {
                TextView tv = new TextView(context);
                tv.setSingleLine(true);
                tv.setId(i);

                if (i == 0) {
                    tv.setLayoutParams(new TableRow.LayoutParams(indexWidth, TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setMinWidth(indexWidth);
                    tv.setMaxWidth(indexWidth);
                }
                else {
                    tv.setLayoutParams(new TableRow.LayoutParams(colWidth, TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setMinWidth(colWidth);
                    tv.setMaxWidth(colWidth);
                }

                if (i != variables - 1)
                    tv.setPadding(0, 0, paddingWidth, 0);

                tvlist[i] = tv;
            }
        }
    }

    public DynamicDataItemAdapter(Context contextParam, int resource, ArrayList<double[]> objects) {
        super(contextParam, resource, objects);
        context = contextParam;
        layoutResourceId = resource;
        data = objects;

        Width = indexWidth + paddingWidth + ((objects.get(0).length - 1) * colWidth + paddingWidth);
    }

    public DynamicDataItemAdapter(Context contextParam, int resource, ArrayList<double[]> objects,
                                  int indexWidthParam, int paddingWidthParam, int colWidthParam) {
        super(contextParam, resource, objects);
        context = contextParam;
        layoutResourceId = resource;
        data = objects;

        indexWidth = indexWidthParam;
        paddingWidth = paddingWidthParam;
        colWidth = colWidthParam;

        Width = indexWidth + paddingWidth + ((objects.get(0).length - 1) * colWidth + paddingWidth);
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        return getCustomView(position, view, viewGroup);
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        return getCustomView(pos, view, viewGroup);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = (LinearLayout) convertView;
        ItemViewHolder vh = new ItemViewHolder(data.get(position).length, getContext());

        NumberFormat formatter;
        formatter = new DecimalFormat("0.###E0");

        if (view == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) vi.inflate(layoutResourceId, null);

            for (int i = 0; i < data.get(position).length; i++) {
                view.addView(vh.tvlist[i]);
                vh.tvlist[i].setText(String.valueOf(formatter.format(data.get(position)[i])));
            }

        }
        else {
            for (int i = 0; i < data.get(position).length; i++) {
                vh.tvlist[i] = (TextView) view.findViewById(i);
            }

            view.setTag(vh.tvlist);

            for (int i = 0; i < data.get(position).length; i++) {
                vh.tvlist[i].setText(String.valueOf(formatter.format(data.get(position)[i])));
            }
        }

        return view;
    }
}

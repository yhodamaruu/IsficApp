package com.isficuniversity.isfic.adaptaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.isficuniversity.isfic.R;

import java.util.List;

public class CodeAdapter extends ArrayAdapter<String> {

    public CodeAdapter(Context context, List<String> codes) {
        super(context, 0, codes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_code, parent, false);
        }

        String code = getItem(position);
        TextView codeText = convertView.findViewById(R.id.code_text);

        codeText.setText(code);

        return convertView;
    }
}

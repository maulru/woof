package com.example.igorg.woof.views;

import android.content.Context;
import java.util.List;
import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Historico;

public class ListAdapterClass extends BaseAdapter {

    Context context;
    List<Historico> valueList;

    public ListAdapterClass(List<Historico> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
    }

    @Override
    public int getCount()
    {
        return this.valueList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.listview_historico, null);

            viewItem.TextViewTIPO = (TextView)convertView.findViewById(R.id.tipo);
            viewItem.TextViewPET = (TextView)convertView.findViewById(R.id.pet);
            viewItem.TextViewDATA= (TextView)convertView.findViewById(R.id.data);
            viewItem.TextViewHORARIO= (TextView)convertView.findViewById(R.id.horario);
            viewItem.TextViewStatus= (TextView)convertView.findViewById(R.id.status);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.TextViewTIPO.setText(valueList.get(position).tipo);
        viewItem.TextViewPET.setText(valueList.get(position).pet);
        viewItem.TextViewDATA.setText(valueList.get(position).data);
        viewItem.TextViewHORARIO.setText(valueList.get(position).horario);
        viewItem.TextViewStatus.setText(valueList.get(position).status);

        return convertView;
    }
}

class ViewItem
{
    TextView TextViewTIPO;
    TextView TextViewPET;
    TextView TextViewDATA;
    TextView TextViewHORARIO;
    TextView TextViewStatus;

}
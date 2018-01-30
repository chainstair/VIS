package at.fhooe.mc.android.btleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DevicesListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<BluetoothModel> bluetoothDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);

        listView = (ListView) findViewById(android.R.id.list);



    }


    private class ListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return bluetoothDeviceList.size();
        }

        @Override
        public Object getItem(int position) {
            return bluetoothDeviceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));
            return convertView;
        }
    }
}

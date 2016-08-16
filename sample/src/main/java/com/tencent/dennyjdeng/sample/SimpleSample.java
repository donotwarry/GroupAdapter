package com.tencent.dennyjdeng.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.dennyjdeng.groupadapter.GroupAdapter;

/**
 * Created by dennyjdeng on 2016/8/12.
 */
public class SimpleSample extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new Adapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        GroupAdapter.IndexPath ip = adapter.getIndexForPosition(position);
        Toast.makeText(this, adapter.data[ip.row], Toast.LENGTH_SHORT).show();
    }

    class Adapter extends GroupAdapter {

        private String[] data = {"A", "B", "C", "D", "E", "F", "G"};

        public Adapter(Context context) {
            super(context);
        }

        @Override
        public int getSectionCount() {
            return 10;
        }

        @Override
        public int getCountInSection(int section) {
            return 5;
        }

        @Override
        public View getViewForRow(View convertView, ViewGroup parent, int section, int row) {
            TextView tv = new TextView(SimpleSample.this);
            tv.setText(data[row]);
            tv.setPadding(10, 10, 10, 10);
            return tv;
        }
    }
}

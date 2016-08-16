package com.tencent.dennyjdeng.sample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] samples = {"Simple", "CustomSection"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter());
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("gasample://" + samples[i])));
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return samples.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv = new TextView(MainActivity.this);
            tv.setTextSize(20);
            int padding = 30;
            tv.setPadding(padding, padding, padding, padding);
            tv.setText(samples[i]);
            return tv;
        }
    }
}

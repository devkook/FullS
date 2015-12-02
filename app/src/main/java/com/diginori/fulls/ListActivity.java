package com.diginori.fulls;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diginori.fulls.db.History;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ListActivity extends Activity {


    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvivity_list);

        mListView = (ListView) findViewById(R.id.listView_gogo);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter.mListData.get(position);
                Toast.makeText(ListActivity.this, mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });

        realm = Realm.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RealmQuery<History> query = realm.where(History.class);
        RealmResults<History> result = query.findAll();
        this.setTitle("COUNT:" + result.size());

        for (int i = 0; i < result.size(); i++) {

            String content = result.get(i).getLog();
            String date = result.get(i).getDate();

            addItem(content, date);
        }

    }

    private void addItem(String content, String date) {
        Drawable img = getResources().getDrawable(R.mipmap.ic_launcher);
        mAdapter.addItem(
                img,
                content,
                date);

//        this.setTitle("입력성공");
    }


    private class ViewHolder {
        public ImageView mIcon;

        public TextView mText;

        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public void addItem(Drawable icon, String mTitle, String mDate){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mDate = mDate;

            mListData.add(addInfo);
            dataChange();
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mDate = (TextView) convertView.findViewById(R.id.mDate);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.mTitle);
            holder.mDate.setText(mData.mDate);

            return convertView;
        }
    }
}

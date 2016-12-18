package toast.s07150813.gdmec.edu.cn.listviewdemo;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
          private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"ArrayAdapter");
        menu.add(0,1,0,"SimpleCursorAdapter");
        menu.add(0,2,0,"SimpleAdapter");
        menu.add(0,3,0,"BaseAdapter");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                arrayAdapter();
                break;
            case 1:
                simpleCursorAdapter();
                break;
            case 2:
                simpleAdapter();
                break;
            case 3:
                baseAdapter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void arrayAdapter(){
       final String[] data = {"one","two","three","four","five"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,data[position],Toast.LENGTH_LONG).show();
            }
        });
    }
    public void simpleCursorAdapter(){
        final Cursor mCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        startManagingCursor(mCursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_expandable_list_item_1,
                mCursor,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{android.R.id.text1},
                0);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    public void simpleAdapter(){
        final List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap();
        map.put("title","g1");
        map.put("info","gg1");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap();
        map.put("title","g2");
        map.put("info","gg2");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap();
        map.put("title","g3");
        map.put("info","gg3");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);

        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this,list,R.layout.simple,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.img});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,list.get(position).get("title").toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    static class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button btn;
        public LinearLayout layout;
    }

    public void baseAdapter(){
        class MyBaseAdapter extends BaseAdapter{
            private List<Map<String,Object>> data;
            private Context context;
            private LayoutInflater layoutInflater;
            public MyBaseAdapter(Context context,List<Map<String,Object>> data){
                super();
                this.data = data;
                this.context = context;
                this.layoutInflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if(convertView==null){
                    holder = new ViewHolder();
                    convertView = layoutInflater.inflate(R.layout.l1,parent,false);
                    holder.img = (ImageView) convertView.findViewById(R.id.img);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.info = (TextView) convertView.findViewById(R.id.info);
                    holder.btn = (Button) convertView.findViewById(R.id.button);
                    holder.layout = (LinearLayout) convertView.findViewById(R.id.l1);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.img.setImageResource(Integer.parseInt(data.get(position).get("img").toString()));
                holder.title.setText(data.get(position).get("title").toString());
                holder.info.setText(data.get(position).get("info").toString());
                if(position%2==1){
                  holder.layout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
                }else{
                    holder.layout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
                }
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,position,Toast.LENGTH_LONG).show();
                    }
                });
                return convertView;
            }
        }
        final List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("title","g1");
        map.put("info","gg1");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);
         map = new HashMap<>();
        map.put("title","g5");
        map.put("info","gg5");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<>();
        map.put("title","g6");
        map.put("info","gg6");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);

        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(this,list);
        listView.setAdapter(myBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,list.get(position).get("title").toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.com.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("그리드뷰 영화 포스터");

        final GridView gv = (GridView) findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context c) {
            context = c;
        }
        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        Integer[] posterID = {R.drawable.movie01, R.drawable.movie02, R.drawable.movie03,
                R.drawable.movie04, R.drawable.movie05, R.drawable.movie06,
                R.drawable.movie07, R.drawable.movie08, R.drawable.movie09,
                R.drawable.movie10 };
        String[] posterName = {"내부자들", "열정같은소리하고있네", "도리화가", "검은사제들", "헝거게임", "괴물의아이", "파워레인저", "크림슨피크", "스펙터", "위선자들"};
//        Integer[] posterID = { R.drawable.movie01, R.drawable.movie01 ,R.drawable.movie01 , R.drawable.movie01, R.drawable.movie01, R.drawable.movie01, R.drawable.movie01, R.drawable.movie01 ,R.drawable.movie01, R.drawable.movie01};
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            final ImageView imageview = new ImageView(context);
//            imageview.setLayoutParams(new GridView.LayoutParams(100, 150));
//            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imageview.setPadding(5, 5, 5, 5);
//            imageview.setImageResource(posterID[position]);

            convertView = getLayoutInflater().inflate(R.layout.adapter,null);

            TextView poster  = (TextView) convertView.findViewById(R.id.PosterName);
            ImageView imageview = (ImageView) convertView.findViewById(R.id.imagePoster);

            imageview.setImageResource(posterID[position]);
            poster.setText(posterName[position]);

            convertView.setLayoutParams(new GridView.LayoutParams(100, 150));
            convertView.setPadding(5,5,5,5);

            final int pos = position;

            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(posterID[pos]);
                    dlg.setTitle(posterName[pos]);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageview;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

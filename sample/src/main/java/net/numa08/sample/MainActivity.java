package net.numa08.sample;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.numa08.sample.models.Heike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout mainTab;
    ViewPager mainPager;
    HeikeFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainTab = (TabLayout) findViewById(R.id.main_tab);
        mainPager = (ViewPager) findViewById(R.id.main_pager);

        final List<Heike> heikeList = new ArrayList<>(
                Arrays.asList(
                        new Heike(getString(R.string.kiyomori), R.drawable.kiyomori),
                        new Heike(getString(R.string.shigemori), R.drawable.shigemori),
                        new Heike(getString(R.string.munemori), R.drawable.munemori),
                        new Heike(getString(R.string.sigehira), R.drawable.shigehira),
                        new Heike(getString(R.string.antoku), R.drawable.antoku)
                )
        );

        adapter = new HeikeFragmentAdapter(getSupportFragmentManager(), heikeList);
        adapter.registerDataSetObserver(dataSetObserver);

        mainPager.setAdapter(adapter);
        mainPager.setOffscreenPageLimit(1);
        mainTab.setupWithViewPager(mainPager);
        mainTab.setTabMode(TabLayout.MODE_FIXED);
        mainTab.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.unregisterDataSetObserver(dataSetObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_heike) {
            if (adapter.getCount() < 1) {
                return false;
            }
            final int position = mainPager.getCurrentItem();
            adapter.removeItem(position);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            mainTab.setupWithViewPager(mainPager);
        }
    };
}

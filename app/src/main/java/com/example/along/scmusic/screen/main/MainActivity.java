package com.example.along.scmusic.screen.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.along.scmusic.R;
import com.example.along.scmusic.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new MainViewModel(new MainAdapter(getSupportFragmentManager()));
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mViewModel);
    }
}

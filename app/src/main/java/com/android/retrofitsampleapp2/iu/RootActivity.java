package com.android.retrofitsampleapp2.iu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.iu.users.UsersFragment;

public class RootActivity extends AppCompatActivity {

    private static final String TAG_USER_CONTAINER_LAYOUT_KEY = "TAG_USER_CONTAINER_LAYOUT_KE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        openUsersFragment();

    }

    private void openUsersFragment() {
        Fragment usersFragment = new UsersFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_layout, usersFragment, TAG_USER_CONTAINER_LAYOUT_KEY)
                .commit();
    }
}
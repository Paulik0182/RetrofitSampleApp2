package com.android.retrofitsampleapp2.iu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitUserEntity;
import com.android.retrofitsampleapp2.iu.projects.ProjectsFragment;
import com.android.retrofitsampleapp2.iu.users.UsersFragment;

public class RootActivity extends AppCompatActivity implements UsersFragment.Controller {

    private static final String TAG_USER_CONTAINER_LAYOUT_KEY = "TAG_USER_CONTAINER_LAYOUT_KE";
    private static final String TAG_PROJECT_CONTAINER_LAYOUT_KEY = "TAG_PROJECT_CONTAINER_LAYOUT_KEY";

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

    public void openProjectFragment(GitUserEntity user) {
        Fragment projectFragment = ProjectsFragment.newInstance(user.getLogin());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_layout, projectFragment, TAG_PROJECT_CONTAINER_LAYOUT_KEY)
                .addToBackStack(null)
                .commit();

        Toast.makeText(this, "Нажали " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }
}
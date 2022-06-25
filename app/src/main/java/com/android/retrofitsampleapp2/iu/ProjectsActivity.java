package com.android.retrofitsampleapp2.iu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.retrofitsampleapp2.R;

public class ProjectsActivity extends AppCompatActivity {

    private static final String LOGIN_EXTRA_KEY = "LOGIN_EXTRA_KEY";

    public static Intent getLaunchIntent(Context context, String login) {
        Intent intent = new Intent(context, ProjectsActivity.class);
        intent.putExtra(LOGIN_EXTRA_KEY, login);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        final String login = getIntent().getStringExtra(LOGIN_EXTRA_KEY);
        Toast.makeText(this, login, Toast.LENGTH_SHORT).show();
    }
}

package com.example.studentinformationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentinformationmanagement.dao.UserDAO;
import com.example.studentinformationmanagement.util.Const;
import com.example.studentinformationmanagement.util.DataUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private FirebaseUser currentUser;
    private Menu myMenu;

    @Override
    protected void onStart() {
        super.onStart();
        UserDAO userDAO = new UserDAO();
        try {
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            if (currentUser == null ||userDAO.getCurrentUser(null).getStatus().equals(Const.STATUS.LOCKED)) {

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            Log.d("Current user", "Current user: " + currentUser.getEmail());

        } catch (Exception ex) {
            Log.d("Exception onStart MainActivity", ex.getMessage());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            MaterialToolbar toolbar = findViewById(R.id.tool_bar);

            drawerLayout = findViewById(R.id.draw_layout);
            navigationView = findViewById(R.id.nav_view);

            //Setting navText
            settingNavHeader();
            toolbar.setNavigationOnClickListener(v -> {
                drawerLayout.openDrawer(GravityCompat.START);
            });

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);


        } catch (Exception ex) {
            Log.d("Exception onCreate MainActivity", ex.getMessage());
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        super.onPrepareOptionsMenu(menu);
        menu.removeItem(R.id.nav_accountManagement);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.nav_profile) {
            moveToProfile();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_accountManagement) {
             moveToAccountManager();
             drawerLayout.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_studentManagement) {


        } else if (id == R.id.nav_dataFile) {

        } else if (id == R.id.nav_logout) {
            signOut();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void moveToAccountManager() {
        Intent intent = new Intent(this, UserAccountActivity.class);
        startActivity(intent);
        finish();
    }
    private void moveToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
//        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void settingNavHeader() {
        View headerView = navigationView.getHeaderView(0);
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        String email = mAuth.getCurrentUser().getEmail();
        DocumentReference docRef = Const.DATABASE_REFERENCE.collection(Const.COLLECTION.USER).document(email);
        docRef.get().addOnCompleteListener(task1 -> {
            DocumentSnapshot document = task1.getResult();

            TextView navUsername = headerView.findViewById(R.id.txtUsernameHeader);
            TextView navEmail = headerView.findViewById(R.id.txtEmailHear);
            navUsername.setText(document.getString(Const.FIELD.USER_NAME));
            navEmail.setText(document.getString(Const.FIELD.EMAIL));
            DataUtil.setAvatar(document.getString(Const.FIELD.AVATAR),
                    headerView.findViewById(R.id.imageViewHeader),R.drawable.default_avatar);

        });

    }
}
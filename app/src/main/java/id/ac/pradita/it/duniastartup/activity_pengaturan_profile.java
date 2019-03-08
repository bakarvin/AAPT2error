package id.ac.pradita.it.duniastartup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import id.ac.pradita.it.duniastartup.Fragment.Profile;

public class activity_pengaturan_profile extends AppCompatActivity {

    RelativeLayout ubahData, exit;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_profile);

        ubahData = findViewById(R.id.ubahDataLayout);
        exit = findViewById(R.id.exitLayout);
        back = findViewById(R.id.imageBack);

        ubahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_pengaturan_profile.this,activity_pengaturan_akun.class));
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        // TODO Re-design menu profile + ubah Profile atau cari cara intent ke fragment + data
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),home.class));
            }
        });
    }
    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login.class));
    }
}

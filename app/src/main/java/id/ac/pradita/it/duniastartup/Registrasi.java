package id.ac.pradita.it.duniastartup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import id.ac.pradita.it.duniastartup.Fragment.DatePickerFragment;

public class Registrasi extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView txtTglLahir;
    RadioGroup rdGroup;
    RadioButton rdButton;
    EditText txtEmail, txtNama, txtTelp, txtPass;

    Button daftar;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser user;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        txtEmail = findViewById(R.id.txtEmailRegis);
        txtNama = findViewById(R.id.txtNameRegis);
        txtTelp = findViewById(R.id.txtTelpRegis);
        txtPass = findViewById(R.id.txtPassRegis);
        txtTglLahir = findViewById(R.id.txtTanggal);
        rdGroup= findViewById(R.id.rdGroup);
        daftar = findViewById(R.id.buttonIsi);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        txtTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString();
                String nama = txtNama.getText().toString();
                String password = txtPass.getText().toString();
                String telp = txtTelp.getText().toString();
                String tglLahir = txtTglLahir.getText().toString();
                String jenKel = rdButton.getText().toString();

                if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama, Email dan Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password harus memiliki minimal 8 karakter", Toast.LENGTH_LONG).show();
                } else {
                    register(nama, email, password, telp, tglLahir, jenKel);
                }

            }
        });
    }

    //fungsi Register(1x register ada 3 data username,email,password)
    void register(final String nama, final String email, String password, final String telp, final String tglLahir, final String jenKel) {

        progressDialog.show();
        progressDialog.setMessage("Loading");
        //ini fungsi dari firebase Authentication buat nge-register email sama password ke- firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            String userId = user.getUid();
                            //ini buat register ke firebase database(beda sama firebase Authentication)
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", nama);
                            hashMap.put("email", email);
                            hashMap.put("telp", telp);
                            hashMap.put("jenkel", jenKel);
                            hashMap.put("tglLahir", tglLahir);
                            hashMap.put("facebook", "default");
                            hashMap.put("twitter", "default");
                            hashMap.put("imageUrl", "default");

                            //kalo berhasil register nanti masuk ke form Login
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        progressDialog.dismiss();
//                                        dialog.dismiss();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal daftar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void rbClick(View view){
        int radioId = rdGroup.getCheckedRadioButtonId();
        rdButton = findViewById(radioId);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        txtTglLahir.setText(currentDate);
    }
}

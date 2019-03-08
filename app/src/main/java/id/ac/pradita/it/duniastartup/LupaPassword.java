package id.ac.pradita.it.duniastartup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LupaPassword extends AppCompatActivity {
    public static  final String EXTRA_TEXT = "di.ac.pradita.it.duniastartup";

    private EditText passEmail;
    private Button resetPass;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        passEmail = (EditText)findViewById(R.id.et_email);
        resetPass = (Button)findViewById(R.id.btn_LPassword);
        fbAuth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail = passEmail.getText().toString().trim();

                if(userEmail.equals("")){
                    Toast.makeText(LupaPassword.this, "Enter your Registered Email id",Toast.LENGTH_SHORT).show();
                } else {
                    fbAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LupaPassword.this, "Password Email sent", Toast.LENGTH_SHORT).show();
                                finish();
//                                startActivity(new Intent(LupaPassword.this, LupaPassword2.class));
                                Intent email = new Intent(LupaPassword.this, LupaPassword2.class);
                                email.putExtra(EXTRA_TEXT, userEmail);
                                startActivity(email);
                            }else{
                                Toast.makeText(LupaPassword.this, "Error in Sending Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

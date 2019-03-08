package id.ac.pradita.it.duniastartup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import id.ac.pradita.it.duniastartup.Fragment.DatePickerFragment;
import id.ac.pradita.it.duniastartup.model.User;

public class activity_pengaturan_akun extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageView imgBack;
    ProgressDialog dialog;
    FirebaseUser user;
    ImageView imgProfile,imgSetting;
    EditText txtTanggal;
    TextView txtNameProfile, txtEmailProfile, txtPhoneProfile, tvTanggal;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://catalogstartup.appspot.com/");
    Uri imageCamera, xUri;
    DatabaseReference reference;
    Button btnSimpan;

    int PICK_IMAGE_REQUEST = 111;

    StorageReference storageReference;
    StorageTask task;
    String snapshotid;

    public activity_pengaturan_akun() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_akun);


        imgProfile = findViewById(R.id.imageView);
        txtNameProfile = findViewById(R.id.txtNama);
        imgBack = findViewById(R.id.imgBack);
        txtEmailProfile = findViewById(R.id.txtEmail);
        txtPhoneProfile= findViewById(R.id.txtPhone);
        btnSimpan = findViewById(R.id.btnSimpan);
        tvTanggal = findViewById(R.id.tvTanggal);

//        txtAddress = findViewById(R.id.txtAddress);
//        txtTwitterProfile = findViewById(R.id.txtTwitProfile);
//        txtFacebookProfile = findViewById(R.id.txtFaceProfile);

        dialog = new ProgressDialog(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        getDataProfile();
        storageReference = FirebaseStorage.getInstance().getReference("UploadProfile");
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });
        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiBack();
            }
        });
        return;
    }

    private void validasiBack(){
        AlertDialog.Builder build = new AlertDialog.Builder(activity_pengaturan_akun.this);
        build.setMessage("Apakah anda yakin keluar tanpa merubah data ?");
        build.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(activity_pengaturan_akun.this, activity_pengaturan_profile.class));
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void openGalery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void getDataProfile() {
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        txtEmailProfile.setText(user.getEmail());
                        txtNameProfile.setText(user.getUsername());
                        txtPhoneProfile.setText(user.getTelp());
//                        txtTwitterProfile.setText(user.getTwitter());
//                        txtFacebookProfile.setText(user.getFacebook());

                        if (user.getImageUrl().equals("default")) {
                            imgProfile.setImageResource(R.drawable.ic_user);
                        } else {

                            Glide.with(activity_pengaturan_akun.this)
                                    .load(user.getImageUrl())
                                    .into(imgProfile);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    String getFileExtention(Uri uri) {
        ContentResolver resolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }

//    void uploadimageProfile() {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setMessage("Loading");
//        dialog.show();
//        dialog.setCancelable(false);
//
//        if (imageCamera != null) {
//            final StorageReference referenceStore = storageReference.child(System.currentTimeMillis()
//                    + "." + getFileExtention(imageCamera));
//
//            task = referenceStore.putFile(imageCamera);
//            task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return referenceStore.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();
//                        final String mUri = downloadUri.toString();
//                        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("imageUrl", mUri);
//                        reference.updateChildren(hashMap);
//                        dialog.dismiss();
//                    } else {
//                        dialog.dismiss();
//                        Toast.makeText(activity_pengaturan_akun.this, "Gagal Upload", Toast.LENGTH_LONG).show();
//
//                    }
//                }
//
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(activity_pengaturan_akun.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//            });
//        } else {
//            Toast.makeText(this, "Tidak ada image yg di pilih", Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageCamera = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageCamera);

                //Setting image to ImageView
                imgProfile.setImageBitmap(bitmap);
//                uploadimageProfile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        tvTanggal.setText(currentDate);
    }
}
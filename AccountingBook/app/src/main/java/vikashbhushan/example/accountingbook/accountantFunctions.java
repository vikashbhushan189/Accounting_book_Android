package vikashbhushan.example.accountingbook;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class accountantFunctions extends AppCompatActivity {
    private static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    public static final int GALLERY_REQUEST_CODE = 105;
    public static final String TAG = "tag";

    ProgressBar progressBar;
    ImageView selectedImage;
    Button cameraBtn, galleryBtn, listButton, logoutButton;
    String currentPhotoPath;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    final FirebaseUser firebaseUser = fAuth.getCurrentUser();
    final String EID = firebaseUser.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountant_functions);

        progressBar = findViewById(R.id.progressBar);
        selectedImage = findViewById(R.id.displayImageView);
        cameraBtn = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);
        listButton = findViewById(R.id.button5);
        logoutButton = findViewById(R.id.logoutButton);
        storageReference = FirebaseStorage.getInstance().getReference();



        cameraBtn.setOnClickListener(new View.OnClickListener() {//yyyyMMdd
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                Log.d("tag", "key: " + EID);
                DatabaseReference ref = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("timestamp");
                Log.d("date", "date: " + ref);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyyMMdd");
                        Date c = Calendar.getInstance().getTime();
                        String formattedDate = curFormater.format(c);
                        Log.d("tag", "formate: " + formattedDate);
                        String imageDate = dataSnapshot.getValue(String.class);
                        Log.d("date", "date: " + imageDate);
                        if (imageDate.equals(formattedDate)) {
                            Toast.makeText(accountantFunctions.this, "only 1 image upload is allowed per day", Toast.LENGTH_LONG).show();
                            cameraBtn.setEnabled(false);
                        } else {
                            askCameraPermissions();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //Toast.makeText(accountantFunctions.this, "camera Btn is Clicked. ",Toast.LENGTH_SHORT).show();

            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                Log.d("tag", "key: " + EID);
                DatabaseReference ref = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("timestamp");
                Log.d("date", "date: " + ref);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyyMMdd");
                        Date c = Calendar.getInstance().getTime();
                        String formattedDate = curFormater.format(c);
                        Log.d("tag", "formate: " + formattedDate);
                        String imageDate = dataSnapshot.getValue(String.class);
                        Log.d("date", "date: " + imageDate);
                        if (imageDate.equals(formattedDate)) {
                            Toast.makeText(accountantFunctions.this, "only 1 image upload is allowed per day", Toast.LENGTH_LONG).show();
                            galleryBtn.setEnabled(false);
                        } else {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Toast.makeText(MainActivity.this, "gallery Btn is Clicked. ",Toast.LENGTH_SHORT).show();


            }
        });
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountantFunction1Activity();
            }
        });
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(accountantFunctions.this, zoomImage.class);
                startActivity(intent11);
            }
        });
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                selectedImage.setImageURI(Uri.fromFile(f));

                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("imagename");
                ref.setValue(f.getName());

                uploadImageToFirebase(f.getName(), contentUri);


            }

        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_" + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri: " + imageFileName);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String key1 = getIntent().getStringExtra("EID");
                DatabaseReference ref = database.getReference().child("Accountantuser").child(key1.replace(".", ",")).child("imagename");
                ref.setValue(imageFileName);
                uploadImageToFirebase(imageFileName, contentUri);
            }

        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference image = storageReference.child("images/").child(EID + "/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        Log.d(TAG, "onSuccess:upload image url is: " + uri.toString());
                        String xx = uri.toString();
                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyyMMdd");
                        Date c = Calendar.getInstance().getTime();
                        String formattedDate = curFormater.format(c);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("image");
                        myRef.setValue(xx);
                        DatabaseReference myRef2 = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("Uploads");
                        {
                            Upload upload = new Upload(formattedDate, xx);
                            myRef2.child(formattedDate).setValue(upload);

                        }
                        DatabaseReference myRef1 = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("timestamp");
                        myRef1.setValue(formattedDate);
                        DatabaseReference myRef3 = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("Date");
                        myRef3.setValue(Integer.parseInt(formattedDate));
                        final DocumentReference documentReference = db.collection("users").document(EID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("image1", xx);
                        documentReference.set(user);
                        Picasso.get().load(uri).into(selectedImage);
                    }

                });
                Toast.makeText(accountantFunctions.this, "image is successfully uploaded", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(accountantFunctions.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir=Environment.getExternalStorageDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */

        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        Log.d("tag", "absolute path: " + image.getAbsolutePath());
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("tag", " image path  " + photoFile);
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "vikashbhushan.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    public void resetFile(View view) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("imagename");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                final StorageReference image = storageReference.child("images/").child(EID + "/" + value);
                SimpleDateFormat curFormater = new SimpleDateFormat("yyyyMMdd");
                Date c = Calendar.getInstance().getTime();
                String formattedDate = curFormater.format(c);
                DatabaseReference myRef2 = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("Uploads").child(formattedDate);
                myRef2.removeValue();
                image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        selectedImage.setImageResource(android.R.color.transparent);
                        DatabaseReference myRef = database.getReference().child("Accountantuser").child(EID.replace(".", ",")).child("timestamp");
                        myRef.setValue(" ");
                        cameraBtn.setEnabled(true);
                        galleryBtn.setEnabled(true);
                        Toast.makeText(accountantFunctions.this, "image reset successfull", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Toast.makeText(accountantFunctions.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void accountantFunction1Activity() {
        Intent intent = new Intent(this, accountantFunction1.class);
        startActivity(intent);
    }

    public void logout_onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), AccountantLogin.class));
        finish();
    }
}

package com.example.mozo;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ProfileEdit_Fragment extends Fragment {

    View view;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private Button mButtonShowUploads;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;

    private Uri mImageUri;

    private StorageReference mStorageRef;

    private StorageTask mUploadTask;

    static int imageNumber = 1;
    private FirebaseAuth mAuth;
    private String useruid;
    APIServices apiServices = new APIServices();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profileedit_fragment, null);
        mButtonChooseImage = view.findViewById(R.id.chooseImageButton);
        mButtonUpload = view.findViewById(R.id.uploadImageButton);
        mButtonShowUploads = view.findViewById(R.id.viewUploadButton);
        mImageView = view.findViewById(R.id.imageView);
        mProgressBar = view.findViewById(R.id.progressBar);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        useruid = currentUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference(useruid);
        //mDatabaseRef = FirebaseDatabase.getInstance().getReference(useruid);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mButtonShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(imageNumber
                    + "." + getFileExtension(mImageUri));
            imageNumber++;
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 1000);

                            Toast.makeText(getActivity().getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            updateUser(String.valueOf(downloadUrl));
                            //final String sdownload_url = String.valueOf(downloadUrl);
                            //Upload upload = new Upload(sdownload_url);
                            //String uploadId = mDatabaseRef.push().getKey();
                            //mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser(String downloadUrl) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("s_p",MODE_PRIVATE);
        Gson gson = new Gson();
        String user_json = sharedPreferences.getString("user", null);
        String user_id = sharedPreferences.getString("id", null);
        User user = gson.fromJson(user_json,User.class);
        ArrayList image_list = new ArrayList<String>();
        if(user.getImage_url() != null){
            for(String url: user.getImage_url()){
                image_list.add(url);
            }
            image_list.add(downloadUrl);
            user.getImage_url().clear();
            user.getImage_url().addAll(image_list);
        }else{
            image_list.add(downloadUrl);
            user.setImage_url(image_list);
        }
        apiServices.createUser(getContext(),user_id,user);
        String user_json2 = gson.toJson(user);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("user", user_json2);
        prefsEditor.apply();
    }

    private void openImagesActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), ImagesActivity.class);
        intent.putExtra("userid",useruid);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        useruid = currentUser.getUid();
    }
}

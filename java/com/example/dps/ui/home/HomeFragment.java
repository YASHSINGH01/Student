package com.example.dps.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.dps.MainActivity;
import com.example.dps.R;
import com.example.dps.Register;
import com.example.dps.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment  {

    private HomeViewModel homeViewModel;
    Button button;
    ImageView view;
    FirebaseAuth muth;
    FirebaseFirestore firestore;
    FirebaseUser user;
    String userId;
    EditText name ,father,email,mother;
    StorageReference storageReference;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
               new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        button=root.findViewById(R.id.button3);
        name=root.findViewById(R.id.editName);
        father=root.findViewById(R.id.Fathername);
        email=root.findViewById(R.id.mail);
        mother=root.findViewById(R.id.mother);
        muth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        user=muth.getCurrentUser();
        view=root.findViewById(R.id.imageView2);
        storageReference= FirebaseStorage.getInstance().getReference();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1001);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=name.getText().toString().trim();
                String fathername=father.getText().toString().trim();
                String mail=email.getText().toString().trim();
                String mothername=mother.getText().toString().trim();
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(fathername)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(mothername)) {
                    name.setError("Please fill it");
                    return;
                }

                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().build();

                user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference df=firestore.collection("Students").document(user.getUid());
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("Name",username);
                        userInfo.put("Email id",mail);
                        userInfo.put("Father Name",fathername);
                        userInfo.put("Mother Name",mothername);
                        df.set(userInfo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Authentication Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
               /* if(user!=null) {
                    muth.signOut();
                    user = null;
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }*/
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference filepath = storageReference.child("userProfilePics").child("photo" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                   // Glide.with(getTargetFragment()).load(downloadUrl).into(profileImage);
                    Map<String,Object> userProfileImage = new HashMap<>();
                    userProfileImage.put("profileImage",downloadUrl.toString());
                  //  userReference.update(userProfileImage);
                }
            });
            }
           /* if (resultCode== Activity.RESULT_OK){
                Uri image=data.getData();

         }*/
        }
}
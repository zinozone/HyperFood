package com.project.hyperfood.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.common.model.CongenitalDisease;
import com.project.hyperfood.common.model.User;
import com.project.hyperfood.databinding.ActivityRegisterBinding;
import com.project.hyperfood.common.utils.EmailUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import static com.project.hyperfood.application.HyperFoodApplication.DISEASE;
import static com.project.hyperfood.application.HyperFoodApplication.USER;

public class RegisterActivity extends AbstractActivity {

    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(v -> validate());
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        getDisease();
        setGender();
    }

    private void validate(){
        if (
                !EmailUtil.isEmailValid(binding.inputEmail.getText().toString().trim())
                || TextUtils.isEmpty(binding.inputEmail.getText().toString().trim())
                || TextUtils.isEmpty(binding.inputPassword.getText().toString().trim())
                || TextUtils.isEmpty(binding.inputFirstName.getText().toString().trim())
                || TextUtils.isEmpty(binding.inputLastName.getText().toString().trim())
                || binding.inputDisease.getSelectedItem().toString().equals(R.string.select_disease)
                || binding.inputGender.getSelectedItem().toString().equals(R.string.select_gender)
                || TextUtils.isEmpty(binding.inputAge.getText().toString().trim())
        ){
            Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
        }else {
            register();
        }
    }

    private void register(){
        createProgressDialog(getString(R.string.loading_register));
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(binding.inputEmail.getText().toString().trim(), binding.inputPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUser(user);
                        } else {
                            Toast.makeText(getContext(), R.string.register_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUser(FirebaseUser firebaseUser){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(USER).child(firebaseUser.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null){
                    Toast.makeText(getContext(), R.string.register_success, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), R.string.register_fail, Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), R.string.register_fail, Toast.LENGTH_SHORT).show();
            }
        });
        userRef.setValue(getUserData());
    }

    private User getUserData(){
        User user = new User();
        user.setEmail(binding.inputEmail.getText().toString().trim());
        user.setFirstName(binding.inputFirstName.getText().toString().trim());
        user.setLastName(binding.inputLastName.getText().toString().trim());
        user.setCongenitalDisease(binding.inputDisease.getSelectedItem().toString());
        user.setAge(binding.inputAge.getText().toString().trim());
        user.setGender(binding.inputGender.getSelectedItem().toString().trim());
        return user;
    }

    private void getDisease(){
        DatabaseReference diseaseRef = FirebaseDatabase.getInstance().getReference(DISEASE);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> stringList = new ArrayList<>();
                stringList.add(getString(R.string.select_disease));
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                  CongenitalDisease congenitalDisease = snapshot.getValue(CongenitalDisease.class);
                  stringList.add(congenitalDisease.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.inputDisease.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        diseaseRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setGender(){
        List<String> stringList = new ArrayList<>();
        stringList.add(getString(R.string.select_disease));
        stringList.add(getString(R.string.male));
        stringList.add(getString(R.string.female));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inputGender.setAdapter(adapter);
    }
}

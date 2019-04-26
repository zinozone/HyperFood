package com.project.hyperfood.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.hyperfood.R;
import com.project.hyperfood.common.model.CongenitalDisease;
import com.project.hyperfood.common.model.User;
import com.project.hyperfood.common.preferences.HPF;
import com.project.hyperfood.common.utils.EmailUtil;
import com.project.hyperfood.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.project.hyperfood.application.HyperFoodApplication.DISEASE;
import static com.project.hyperfood.application.HyperFoodApplication.USER;

public class ProfileActivity extends AbstractActivity{

    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";
    private static final String GENDER_KEY = "gender";
    private static final String AGE_KEY = "age";
    private static final String CONGENITAL_DISEASE = "congenitalDisease";

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnEdit.setOnClickListener(v -> enableEdit());
        binding.btnCancel.setOnClickListener(v -> disableEdit());
        binding.btnSave.setOnClickListener(v -> validate());
        setData();
        setGender();
        getDisease();
    }

    private void setData(){
        binding.inputEmail.setText(HPF.getInstance().getUser().getEmail());
        binding.inputFirstName.setText(HPF.getInstance().getUser().getFirstName());
        binding.inputLastName.setText(HPF.getInstance().getUser().getLastName());
        binding.inputDisease.setText(HPF.getInstance().getUser().getCongenitalDisease());
        binding.inputGender.setText(HPF.getInstance().getUser().getGender());
        binding.inputAge.setText(HPF.getInstance().getUser().getAge());
    }

    private void validate(){
        if (
                        TextUtils.isEmpty(binding.inputFirstName.getText().toString().trim())
                        || TextUtils.isEmpty(binding.inputLastName.getText().toString().trim())
                        || binding.spinnerDisease.getSelectedItem().toString().equals(R.string.select_disease)
                        || binding.spinnerGender.getSelectedItem().toString().equals(R.string.select_gender)
                        || TextUtils.isEmpty(binding.inputAge.getText().toString().trim())
        ){
            Toast.makeText(getContext(), R.string.validate_register, Toast.LENGTH_SHORT).show();
        }else {
            updateUser(FirebaseAuth.getInstance().getUid());
        }
    }

    private void updateUser(String uid){
        binding.inputDisease.setText(binding.spinnerDisease.getSelectedItem().toString());
        binding.inputGender.setText(binding.spinnerGender.getSelectedItem().toString());

        HashMap<String, Object> userValue = new HashMap<>();
        userValue.put(FIRST_NAME_KEY, binding.inputFirstName.getText().toString().trim());
        userValue.put(LAST_NAME_KEY, binding.inputLastName.getText().toString().trim());
        userValue.put(GENDER_KEY, binding.inputGender.getText().toString().trim());
        userValue.put(AGE_KEY, binding.inputAge.getText().toString().trim());
        userValue.put(CONGENITAL_DISEASE, binding.inputDisease.getText().toString().trim());

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(USER).child(uid);
        userRef.updateChildren(userValue);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null){
                    HPF.getInstance().setUser(user);
                    Toast.makeText(getContext(), "แก้ไขข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                    disableEdit();
                }else {
                    Toast.makeText(getContext(), "กรุณาลองอีกรั้ง", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        userRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void enableEdit(){
        binding.inputFirstName.setEnabled(true);
        binding.inputLastName.setEnabled(true);
        binding.inputAge.setEnabled(true);

        binding.btnSave.setVisibility(View.VISIBLE);
        binding.btnCancel.setVisibility(View.VISIBLE);
        binding.viewDisease.setVisibility(View.VISIBLE);
        binding.viewGender.setVisibility(View.VISIBLE);

        binding.viewInputDisease.setVisibility(View.GONE);
        binding.viewInputGender.setVisibility(View.GONE);
        binding.btnEdit.setVisibility(View.GONE);
    }

    private void disableEdit(){
        binding.inputFirstName.setEnabled(false);
        binding.inputLastName.setEnabled(false);
        binding.inputAge.setEnabled(false);
        binding.inputFirstName.requestFocus();

        binding.btnSave.setVisibility(View.GONE);
        binding.btnCancel.setVisibility(View.GONE);
        binding.viewDisease.setVisibility(View.GONE);
        binding.viewGender.setVisibility(View.GONE);

        binding.viewInputDisease.setVisibility(View.VISIBLE);
        binding.viewInputGender.setVisibility(View.VISIBLE);
        binding.btnEdit.setVisibility(View.VISIBLE);
    }

    private void getDisease(){
        DatabaseReference diseaseRef = FirebaseDatabase.getInstance().getReference(DISEASE);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int position = 0;
                int i = 0;
                List<String> stringList = new ArrayList<>();
                stringList.add(getString(R.string.select_disease));
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CongenitalDisease congenitalDisease = snapshot.getValue(CongenitalDisease.class);
                    stringList.add(congenitalDisease.getName());
                    i++;
                    if (congenitalDisease.getName().equals(HPF.getInstance().getUser().getCongenitalDisease())){
                        position = i;
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerDisease.setAdapter(adapter);

                binding.spinnerDisease.setSelection(position);
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
        stringList.add(getString(R.string.select_gender));
        stringList.add(getString(R.string.male));
        stringList.add(getString(R.string.female));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGender.setAdapter(adapter);

        if (HPF.getInstance().getUser().getGender().equals(getString(R.string.male))){
            binding.spinnerGender.setSelection(1);
        }else {
            binding.spinnerGender.setSelection(2);
        }

    }
}

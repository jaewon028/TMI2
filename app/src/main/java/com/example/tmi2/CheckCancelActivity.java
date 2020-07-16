package com.example.tmi2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tmi2.model.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckCancelActivity extends AppCompatActivity implements ReservationListAdapter.OnItemClickListener {

    RecyclerView list;
    ReservationListAdapter mAdatper;
    private DatabaseReference mDatabase;
    private ArrayList<Reservation> items = new ArrayList<>();
    String lastKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkn_cancel);

        //데이터베이스의 reservations에 접근하여 유저의 uid에 따른 아이템 초기화 및
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("reservations").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation item = snapshot.getValue(Reservation.class);
                    items.add(item);
                    mAdatper.notifyDataSetChanged();
                }
                if (items.size() == 0)
                    Toast.makeText(getApplicationContext(), "예매정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        list = findViewById(R.id.list);
        mAdatper = new ReservationListAdapter(items, this);
        list.setAdapter(mAdatper);
    }

    @Override
    public void OnCancelClicked(View v, int position, Reservation item) {
        Intent intent = new Intent(CheckCancelActivity.this, CancelPayActivity.class);
        intent.putExtra("reservation", item);

        lastKey = item.getKey();
        startActivityForResult(intent,333);

        mAdatper.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            mDatabase.child("reservations").child(FirebaseAuth.getInstance().getUid()).child(lastKey).removeValue();
        }
    }
}

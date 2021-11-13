package com.example.traveltheworld.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traveltheworld.R;
import com.example.traveltheworld.adapter.BookAdapter;
import com.example.traveltheworld.adapter.RecentAdapter;
import com.example.traveltheworld.adapter.TopPlacesAdapter;
import com.example.traveltheworld.databinding.ActivityBookingFragmentBinding;
import com.example.traveltheworld.model.Book;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.TopPlacesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {
    ActivityBookingFragmentBinding binding;
    BookAdapter bookAdapter;
    List<Book> bookList;
    String idUser;
    public static BookingFragment newInstance() {

        Bundle args = new Bundle();

        BookingFragment fragment = new BookingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_booking_fragment,container,false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcBookList.setLayoutManager(layoutManager);
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(getContext(),bookList, new BookAdapter.IClickListener() {
            @Override
            public void iClickDeleteListenerItem(Book book) {
                onClickDeleteData(book);
            }
        });
        binding.rcBookList.setAdapter(bookAdapter);
        getListBookFromReatimeDataBase();
        binding.tvHuyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDeleteAllData();
            }
        });
        return binding.getRoot();
    }

    private void onClickDeleteAllData() {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn hủy tất cả chuyến đi không ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("booking").child(idUser);
                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Cancel",null).show();
    }

    private void onClickDeleteData(Book book) {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.app_name))
        .setMessage("Bạn có chắc chắn muốn hủy chuyến này không ?")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("booking").child(idUser);
                myRef.child(String.valueOf(book.getPlaceName())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("Cancel",null).show();
    }

    private void getListBookFromReatimeDataBase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        idUser = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("booking").child(idUser);
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot data : snapshot.getChildren()) {
//                    Book book = data.getValue(Book.class);
//                    bookList.add(book);
//                }
//                bookAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "That bai", Toast.LENGTH_SHORT).show();
//            }
//        });
        myRef.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book= snapshot.getValue(Book.class);
                if(book != null){
                    bookList.add(book);
                    bookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Book book= snapshot.getValue(Book.class);
                if (book == null|| bookList== null||bookList.isEmpty()){
                    return;
                }
                for(int i=0; i<bookList.size(); i++){
                    if (book.getPlaceName()==bookList.get(i).getPlaceName()){
                        bookList.remove(bookList.get(i));
                        break;
                    }
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
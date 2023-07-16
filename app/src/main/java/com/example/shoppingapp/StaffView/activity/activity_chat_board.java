package com.example.shoppingapp.StaffView.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.PagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_chat_board extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem mchat,mcall,mstatus;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    androidx.appcompat.widget.Toolbar mtoolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tabLayout=findViewById(R.id.include);
        mchat=findViewById(R.id.chat);
        mcall=findViewById(R.id.calls);
        mstatus=findViewById(R.id.status);
        viewPager=findViewById(R.id.fragmentcontainer);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        mtoolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24);
        mtoolbar.setOverflowIcon(drawable);


        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0 || tab.getPosition()==1|| tab.getPosition()==2)
                {
                    pagerAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        DocumentReference documentReference=firebaseFirestore.collection("NGUOIDUNG").document(firebaseAuth.getUid());
//        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(),"Now User is Offline",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        DocumentReference documentReference=firebaseFirestore.collection("NGUOIDUNG").document(firebaseAuth.getUid());
//        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(),"Now User is Online",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }



//    ActivityChatBoardBinding binding;
//    FirebaseFirestore dtb;
//    adapter_chat_board adapterChatBoard;
//    private FirebaseFirestore firebaseFirestore;
//    LinearLayoutManager linearLayoutManager;
//    private FirebaseAuth firebaseAuth;
//
//    ImageView mimageviewofuser;
//
//    FirestoreRecyclerAdapter<User,NoteViewHolder> chatAdapter;
//
//    public class NoteViewHolder extends RecyclerView.ViewHolder
//    {
//        private TextView particularusername;
//        private TextView statusofuser;
//
//        public NoteViewHolder(@NonNull View itemView) {
//            super(itemView);
//            particularusername=itemView.findViewById(R.id.nameofuser);
//            statusofuser=itemView.findViewById(R.id.statusofuser);
//            mimageviewofuser=itemView.findViewById(R.id.imageviewofuser);
//        }
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        chatAdapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if(chatAdapter!=null)
//        {
//            chatAdapter.stopListening();
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityChatBoardBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        firebaseAuth= FirebaseAuth.getInstance();
//        firebaseFirestore= FirebaseFirestore.getInstance();
//
//
//
//        // Query query=firebaseFirestore.collection("Users");
//        Query query=firebaseFirestore.collection("NGUOIDUNG");
////        Query query=firebaseFirestore.collection("NGUOIDUNG").whereNotEqualTo("maND",firebaseAuth.getUid());
//        FirestoreRecyclerOptions<User> allusername=new FirestoreRecyclerOptions.Builder<User>().setQuery(query, User.class).build();
//
//        chatAdapter=new FirestoreRecyclerAdapter<User, NoteViewHolder>(allusername) {
//            @Override
//            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull User firebasemodel) {
//
//                noteViewHolder.particularusername.setText(firebasemodel.getFullName());
//                String uri=firebasemodel.getAvatar();
//
//                Picasso.get().load(uri).into(mimageviewofuser);
////                if(firebasemodel.getStatus().equals("Online"))
////                {
////                    noteViewHolder.statusofuser.setText(firebasemodel.getStatus());
////                    noteViewHolder.statusofuser.setTextColor(Color.GREEN);
////                }
////                else
////                {
////                    noteViewHolder.statusofuser.setText(firebasemodel.getStatus());
////                }
//
//                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent=new Intent(activity_chat_board.this, activity_chat.class);
//                        intent.putExtra("name",firebasemodel.getFullName());
//                        intent.putExtra("receiveruid",firebasemodel.getMaND());
//                        intent.putExtra("imageuri",firebasemodel.getAvatar());
//                        startActivity(intent);
//                    }
//                });
//
//            }
//
//            @NonNull
//            @Override
//            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user_row,parent,false);
//                return new NoteViewHolder(view);
//            }
//        };
//
//
//        binding.RCVChatBoard.setHasFixedSize(true);
//        linearLayoutManager=new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        binding.RCVChatBoard.setLayoutManager(linearLayoutManager);
//        binding.RCVChatBoard.setAdapter(chatAdapter);
//
//
////        binding = ActivityChatBoardBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////
////        adapterChatBoard = new adapter_chat_board(this);
////        binding.RCVChatBoard.setAdapter(adapterChatBoard);
////        binding.RCVChatBoard.setLayoutManager(new LinearLayoutManager(this));
////
////        dtb = FirebaseFirestore.getInstance();
////        dtb.collection("NGUOIDUNG").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////            @Override
////            public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                adapterChatBoard.clear();
////                for(DocumentSnapshot d : task.getResult()){
////                    User user = d.toObject(User.class);
////                    adapterChatBoard.add(user);
////                }
////            }
////        });
//
////        dtb = FirebaseDatabase.getInstance().getReference("Users");
////        dtb.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                adapterChatBoard.clear();
////                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
////                    String uid = dataSnapshot.getKey();
////                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){
////                        User user = dataSnapshot.child(uid).getValue(User.class);
////                        adapterChatBoard.add(user);
////                    }
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
//    }
//
////    dtb = FirebaseFirestore.getInstance();
////        dtb.collection("NGUOIDUNG").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////        @Override
////        public void onComplete(@NonNull Task<QuerySnapshot> task) {
////            adapterChatBoard.clear();
////            for(DocumentSnapshot d : task.getResult()){
////                User user = d.toObject(User.class);
////                adapterChatBoard.add(user);
////            }
////        }
////    });
}
package com.example.ga_23s1_comp2100_6442;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.model.Request;
import com.example.ga_23s1_comp2100_6442.ultilities.Constant;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    List<Request> requests;
    List<String> requestIds;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setRequests(List<Request> requests,List<String> ids) {
        this.requests = requests;
        this.requestIds=ids;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.request_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request currentRequest = requests.get(position);
        String currentRequestId = requestIds.get(position);
        System.out.println(currentRequest.getInformation());
        TextView courseNameTextView = holder.rootView.findViewById(R.id.sender_name);
        courseNameTextView.setText(currentRequest.getSenderName());
        TextView courseAuthorTextView = holder.rootView.findViewById(R.id.request_info);
        String[] information = currentRequest.getInformation().split("/");
        String info;
        if (Objects.equals(currentRequest.getRequestType(), Request.RequestType.Follow)) {
            info = currentRequest.getSenderName()+" wants to follow you";
        } else {
            info = currentRequest.getSenderName()+" wants to join "+information[0];
        }
        courseAuthorTextView.setText(info);

        //set accept and deny buttons
        Button accept = holder.rootView.findViewById(R.id.accept);
        Button deny = holder.rootView.findViewById(R.id.deny);

        //accept request
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //follow request
                if (Objects.equals(currentRequest.getRequestType(), Request.RequestType.Follow)) {
                    //add to current student's followers
                    db.collection("students").document(currentRequest.getReceiver()).update("followers", FieldValue.arrayUnion(currentRequest.getSender()));
                    //add to the sender's following
                    db.collection("students").document(currentRequest.getSender()).update("following", FieldValue.arrayUnion(currentRequest.getReceiver()));
                    //remove current request
                    db.collection("requests").document(currentRequestId).delete();
                } else { //enroll request
                    //add student studentsEnrolled in current course
                    db.collection(Constant.COURSE_COLLECTION_TEST).document(information[1]).update("studentsEnrolled", FieldValue.arrayUnion(currentRequest.getSender()));
                    //add current course to this student
                    db.collection("students").document(currentRequest.getSender()).update("coursesEnrolled", FieldValue.arrayUnion(information[1]));
                    //remove student from the pending list in current course
                    db.collection(Constant.COURSE_COLLECTION_TEST).document(information[1]).update("studentsApplied", FieldValue.arrayRemove(currentRequest.getSender()));
                    //remove current request
                    db.collection("requests").document(currentRequestId).delete();
                }
                accept.setEnabled(false);
                deny.setEnabled(false);
            }
        });
        //deny request
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove current request
                db.collection("requests").document(currentRequestId).delete();
                //remove student from the applying list in this course
                db.collection(Constant.COURSE_COLLECTION_TEST).document(information[1]).update("studentsApplied", FieldValue.arrayRemove(currentRequest.getSender()));
                accept.setEnabled(false);
                deny.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;

        public ViewHolder(CardView rootView) {
            super(rootView);
            this.rootView = rootView;
        }
    }
//    private void setEventHandlerForHolder(ViewHolder holder, Course currentCourse) {
//        holder.rootView.setOnClickListener(event -> {
//            Intent intent = new Intent(holder.rootView.getContext(), playVideo.class);
//            intent.putExtra("vn", currentCourse.getCourseId());
//            holder.rootView.getContext().startActivity(intent);
//        });
//    }
}

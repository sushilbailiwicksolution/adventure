package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.EnquiryHistory;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class EnquiryHistoryAdapter extends RecyclerView.Adapter<EnquiryHistoryAdapter.EnquiryViewHolder> {

    private Context context;
    private List<EnquiryHistory> enquiryHistoryList;

    public EnquiryHistoryAdapter(Context context, List<EnquiryHistory> enquiryHistoryList) {
        this.context = context;
        this.enquiryHistoryList = enquiryHistoryList;
    }

    @Override
    public EnquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EnquiryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_enquiry_history,parent,false));
    }

    @Override
    public void onBindViewHolder(EnquiryViewHolder holder, int position) {
        EnquiryHistory enquiryHistory = enquiryHistoryList.get(holder.getAdapterPosition());
        if (!enquiryHistory.getUserName().isEmpty() && enquiryHistory.getUserName() != null){
            holder.textUserName.setText(enquiryHistory.getUserName());
        }

        if (!enquiryHistory.getUserEmail().isEmpty() && enquiryHistory.getUserEmail() != null){
            holder.textUserEmail.setText(enquiryHistory.getUserEmail());
        }

        if (!enquiryHistory.getUserMobile().isEmpty() && enquiryHistory.getUserMobile() != null){
            holder.textUserMobile.setText(enquiryHistory.getUserMobile());
        }

        if (!enquiryHistory.getPersonCount().isEmpty() && enquiryHistory.getPersonCount() != null){
            holder.textPersonCount.setText(enquiryHistory.getPersonCount());
        }

        if (!enquiryHistory.getPackageName().isEmpty() && enquiryHistory.getPackageName() != null){
            holder.textPackageName.setText(enquiryHistory.getPackageName());
        }

        if (!enquiryHistory.getServiceName().isEmpty() && enquiryHistory.getServiceName() != null){
            holder.textServiceName.setText(enquiryHistory.getServiceName());
        }

        if (!enquiryHistory.getVendorName().isEmpty() && enquiryHistory.getVendorName() != null){
            holder.textVendorName.setText(enquiryHistory.getVendorName());
        }

        if (!enquiryHistory.getBookingDate().isEmpty() && enquiryHistory.getBookingDate() != null){
            holder.textBookingDate.setText(enquiryHistory.getBookingDate());
        }


    }

    @Override
    public int getItemCount() {
        return enquiryHistoryList.size();
    }

    class EnquiryViewHolder extends RecyclerView.ViewHolder {

        private TextView textName,textUserName,textEmail,textUserEmail,textMobile,textUserMobile,textNoOfPerson,textPersonCount,
                textPackage,textPackageName,textService,textServiceName,textVendor,textVendorName,textBooking,textBookingDate;

        EnquiryViewHolder(View itemView) {
            super(itemView);
            getUiObject(itemView);
        }

        private void getUiObject(View view) {
            textName = view.findViewById(R.id.textViewName);
            textUserName = view.findViewById(R.id.txtViewName);
            textEmail = view.findViewById(R.id.textViewEmail);
            textUserEmail = view.findViewById(R.id.txtViewUserEmail);
            textMobile = view.findViewById(R.id.textViewMobile);
            textUserMobile = view.findViewById(R.id.txtViewUserMobile);
            textNoOfPerson = view.findViewById(R.id.textViewNumberOfPerson);
            textPersonCount = view.findViewById(R.id.txtViewPersonCount);
            textPackage = view.findViewById(R.id.textViewPackName);
            textPackageName = view.findViewById(R.id.txtViewPackageName);
            textService = view.findViewById(R.id.textViewServiceName);
            textServiceName = view.findViewById(R.id.txtViewService);
            textVendor = view.findViewById(R.id.textViewVendor);
            textVendorName = view.findViewById(R.id.txtViewVendorName);
            textBooking = view.findViewById(R.id.textViewBooking);
            textBookingDate = view.findViewById(R.id.txtViewBookingDate);
        }

    }
}

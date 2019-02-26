package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.Order_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 05-10-2018.
 */

public class User_OrderAdapter extends RecyclerView.Adapter<User_OrderAdapter.ViewHolder> {

    Context context;
    private List<Order_Beans> orderHistoryList;

    public User_OrderAdapter(Context context, List<Order_Beans> orderHistoryList) {
        this.context = context;
        this.orderHistoryList = orderHistoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Order_Beans order_beans = orderHistoryList.get(holder.getAdapterPosition());

        if (!order_beans.getUserPackage().isEmpty() && order_beans.getUserPackage() != null) {
            if (order_beans.getSports().equalsIgnoreCase("camping")) {
                holder.llChildLayout.setVisibility(View.VISIBLE);
                holder.llCheckInOut.setVisibility(View.VISIBLE);
                holder.mPackageText.setText("Campname : ");
                holder.mUserPackage.setText(order_beans.getCampName());
            } else {
                holder.llChildLayout.setVisibility(View.GONE);
                holder.llCheckInOut.setVisibility(View.GONE);
                holder.mPackageText.setText("Package : ");
                holder.mUserPackage.setText(order_beans.getUserPackage());
            }
        }

        if (!order_beans.getUserName().isEmpty() && order_beans.getUserName() != null) {
            holder.mUserName.setText(order_beans.getUserName());
        }

        if (!order_beans.getUserEmail().isEmpty() && order_beans.getUserEmail() != null) {
            holder.mUserEmail.setText(order_beans.getUserEmail());
        }

        if (!order_beans.getUserMobile().isEmpty() && order_beans.getUserMobile() != null) {
            holder.mUserMobile.setText(order_beans.getUserMobile());
        }

        if (!order_beans.getPersonCount().isEmpty() && order_beans.getPersonCount() != null) {
            if (order_beans.getSports().equalsIgnoreCase("camping")) {
                holder.mPersonCountText.setText("Adult : ");
                holder.mPersonCount.setText(order_beans.getAdultCount());
            } else {
                holder.mPersonCountText.setText("Persons : ");
                holder.mPersonCount.setText(order_beans.getPersonCount());
            }

        }

        if (!order_beans.getVendorName().isEmpty() && order_beans.getVendorName() != null) {
            holder.mVendorName.setText(order_beans.getVendorName());
        }

        if (!order_beans.getBookingDate().isEmpty() && order_beans.getBookingDate() != null){
            holder.mBookingDate.setText(order_beans.getBookingDate());
        }

        if (!order_beans.getOrderDate().isEmpty() && order_beans.getOrderDate() != null){
            holder.mOrderDate.setText(order_beans.getOrderDate());
        }

        if (!order_beans.getPaymentStatus().isEmpty() && order_beans.getPaymentStatus() != null) {
            holder.mPaymentStatus.setText(order_beans.getPaymentStatus());
        }

        if (!order_beans.getStartingPoint().isEmpty() && order_beans.getStartingPoint() != null){
            holder.mStartingPoint.setText(order_beans.getStartingPoint());
        }

        if (!order_beans.getSports().isEmpty() && order_beans.getSports() != null) {
            holder.mSport.setText(order_beans.getSports());
        }

      /*  if (!order_beans.getAdultCount().isEmpty() && order_beans.getAdultCount() != null){
            holder.mAdultCount.setText(order_beans.getAdultCount());
        }*/

        if (!order_beans.getChildAboveFiveCount().isEmpty() && order_beans.getChildAboveFiveCount() != null){
            holder.mChildAboveFive.setText(order_beans.getChildAboveFiveCount());
        }

        if (!order_beans.getChildBelowFiveCount().isEmpty() && order_beans.getChildBelowFiveCount() != null){
            holder.mChildBelowFive.setText(order_beans.getChildBelowFiveCount());
        }

        if (!order_beans.getCheckIn().isEmpty() && order_beans.getCheckIn() != null){
            holder.mCheckIn.setText(order_beans.getCheckIn());
        }

        if (!order_beans.getCheckOut().isEmpty() && order_beans.getCheckOut() != null){
            holder.mCheckOut.setText(order_beans.getCheckOut());
        }

    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameText, mUserName, mEmailText, mUserEmail, mMobileText, mUserMobile, mPackageText, mUserPackage, mPersonCountText, mPersonCount, mVendorNameText, mVendorName,
                mBookingDateText, mBookingDate, mOrderDateText, mOrderDate, mPaymentStatusText, mPaymentStatus, mStartingPointText, mStartingPoint, mSportText, mSport,
                mAdultText, mAdultCount, mChildAboveFiveText, mChildAboveFive, mChildBelowFiveText, mChildBelowFive, mCheckInText, mCheckIn, mCheckOutText, mCheckOut, mCampNameText, mCampName;

        private LinearLayout llCheckInOut,llChildLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            getUiObject(itemView);
        }

        private void getUiObject(View itemView) {
            mNameText = itemView.findViewById(R.id.textViewName);
            mUserName = itemView.findViewById(R.id.txtViewName);
            mEmailText = itemView.findViewById(R.id.textViewEmail);
            mUserEmail = itemView.findViewById(R.id.txtViewUserEmail);
            mMobileText = itemView.findViewById(R.id.textViewMobile);
            mUserMobile = itemView.findViewById(R.id.txtViewUserMobile);
            mPackageText = itemView.findViewById(R.id.textViewPackName);
            mUserPackage = itemView.findViewById(R.id.txtViewPackageName);
            mPersonCountText = itemView.findViewById(R.id.textViewNumberOfPerson);
            mPersonCount = itemView.findViewById(R.id.txtViewPersonCount);
            mVendorNameText = itemView.findViewById(R.id.textViewVendor);
            mVendorName = itemView.findViewById(R.id.txtViewVendorName);
            mBookingDateText = itemView.findViewById(R.id.textViewBooking);
            mBookingDate = itemView.findViewById(R.id.txtViewBookingDate);
            mOrderDateText = itemView.findViewById(R.id.textViewOrder);
            mOrderDate = itemView.findViewById(R.id.txtViewOrderDate);
            mPaymentStatusText = itemView.findViewById(R.id.textPaymentStatus);
            mPaymentStatus = itemView.findViewById(R.id.txtViewPaymentStatus);
            mStartingPointText = itemView.findViewById(R.id.txtStartingPoint);
            mStartingPoint = itemView.findViewById(R.id.txtViewStartingPoint);
            mSportText = itemView.findViewById(R.id.textViewServiceName);
            mSport = itemView.findViewById(R.id.txtViewService);
          /*  mAdultText = itemView.findViewById(R.id.textViewMobile);
            mAdultCount = itemView.findViewById(R.id.textViewMobile);*/
            mChildAboveFiveText = itemView.findViewById(R.id.textChildAboveFive);
            mChildAboveFive = itemView.findViewById(R.id.textViewChildAboveFive);
            mChildBelowFiveText = itemView.findViewById(R.id.textChildBelowFive);
            mChildBelowFive = itemView.findViewById(R.id.textViewChildBelowFive);
            mCheckInText = itemView.findViewById(R.id.textCheckIn);
            mCheckIn = itemView.findViewById(R.id.textViewCheckIn);
            mCheckOutText = itemView.findViewById(R.id.textCheckOut);
            mCheckOut = itemView.findViewById(R.id.textViewCheckOut);
          /*  mCampNameText = itemView.findViewById(R.id.textViewMobile);
            mCampName = itemView.findViewById(R.id.textViewMobile);*/
            llCheckInOut = itemView.findViewById(R.id.llCheckInOut);
            llChildLayout = itemView.findViewById(R.id.llChildLayout);

        }

    }

}




package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.PaymentHistory;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    private Context context;
    private List<PaymentHistory> paymentHistoryList;

    public PaymentHistoryAdapter(Context context, List<PaymentHistory> paymentHistoryList) {
        this.context = context;
        this.paymentHistoryList = paymentHistoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_payment_history,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PaymentHistory paymentHistory = paymentHistoryList.get(holder.getAdapterPosition());
        if (!paymentHistory.getServiceName().isEmpty() && paymentHistory.getServiceName() != null){
            holder.textServiceName.setText(paymentHistory.getServiceName());
        }

        if (!paymentHistory.getOrderDate().isEmpty() && paymentHistory.getOrderDate() != null){

            Log.e("=>",paymentHistory.getOrderDate());
            SimpleDateFormat recievedFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date parsedDate = recievedFormat.parse(paymentHistory.getOrderDate());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.getDefault());
                String format = simpleDateFormat.format(parsedDate);

                holder.textOrderDate.setText(format);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if (!paymentHistory.getOrderAmount().isEmpty() && paymentHistory.getOrderAmount() != null){
            holder.textOrderAmount.setText("Rs. "+paymentHistory.getOrderAmount());
        }

        if (!paymentHistory.getPaymentStatus().isEmpty() && paymentHistory.getPaymentStatus() != null){
            holder.textPaymentStatus.setText(paymentHistory.getPaymentStatus());
        }

        if (!paymentHistory.getTransactionId().isEmpty() && paymentHistory.getTransactionId() != null){
            holder.textTransactionId.setText(paymentHistory.getTransactionId());
        }


    }

    @Override
    public int getItemCount() {
        return paymentHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textService,textServiceName,textOrder,textOrderDate,textAmount,textOrderAmount,textPayment,
                textPaymentStatus,textTransaction,textTransactionId;

        ViewHolder(View itemView) {
            super(itemView);
            getUiObject(itemView);
        }


        private void  getUiObject(View view){
            textService = view.findViewById(R.id.textViewService);
            textServiceName = view.findViewById(R.id.txtViewServiceName);
            textOrder = view.findViewById(R.id.textViewOrder);
            textOrderDate = view.findViewById(R.id.txtViewOrderDate);
            textAmount = view.findViewById(R.id.textViewAmount);
            textOrderAmount = view.findViewById(R.id.txtViewAmount);
            textPayment = view.findViewById(R.id.textViewPaymentStatus);
            textPaymentStatus = view.findViewById(R.id.txtViewPaymentStatus);
            textTransaction = view.findViewById(R.id.textViewTransaction);
            textTransactionId = view.findViewById(R.id.txtViewTransactionId);
        }

    }
}

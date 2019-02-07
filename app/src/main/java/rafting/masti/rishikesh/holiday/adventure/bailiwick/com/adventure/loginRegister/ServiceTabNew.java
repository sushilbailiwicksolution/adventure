package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.ProductCatagory;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.ServiceMasterRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Camp.CampMasterActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.Servicebeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting.RaftingActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;


/**
 * Created by Prince on 23-11-2017.
 */

public class ServiceTabNew extends Fragment implements ServiceMasterRecyclerAdapter.ItemClickRecListInterface {

    View rootView;
    RecyclerView recyclerService;
    ArrayList<Servicebeans> ServiceBeanList;
    ServiceMasterRecyclerAdapter ServiceListRecyclerAdapter;
    private Context context;
    ProgressDialog prg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.service_tab_new, container, false);
        context = getActivity();
        createIDS();
        if (new CheckConnectivity().isConnected(context)) {
            createList(SharedPref.getUserID());

        }

        return rootView;
    }

    private void createList(final String userID) {

        prg.setMessage("Please Wait....");
        prg.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if (new CheckConnectivity().isConnected(context)) {
                            try {
                                prg.dismiss();
                                Log.e("Response : prince ", response);
                                if (response != null) {
                                    JSONObject jsData = new JSONObject(response);
                                    String status = jsData.getString("status");
                                    if (status.equalsIgnoreCase("1")) {

                                        JSONArray jsArray = jsData.getJSONArray("services");
                                        for (int i = 0; i < jsArray.length(); i++) {
                                            ServiceBeanList.add(new Servicebeans(jsArray.getJSONObject(i).getString("id"),
                                                    jsArray.getJSONObject(i).getString("service_type"),
                                                    jsArray.getJSONObject(i).getString("service_name"),
                                                    jsArray.getJSONObject(i).getString("images")));

                                        }

                                        ServiceListRecyclerAdapter.notifyDataSetChanged();

                                    } else {
                                        String msg = jsData.getString("msg");
                                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    Toast.makeText(context, "Invalid Response !!!", Toast.LENGTH_LONG).show();

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }
                        } else {
                            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prg.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                Log.e("Error :", error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");
                // params.put("Accept-Language", "fr");
                Log.e("Param header ", "" + header);

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("action", UtilsUrl.Action_GETService);
                params.put("user_id", userID);
                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }


    private void createIDS() {
        prg = new ProgressDialog(context);
        recyclerService = (RecyclerView) rootView.findViewById(R.id.recyclerview_service);
        recyclerService.setNestedScrollingEnabled(false);
        ServiceBeanList = new ArrayList<>();
        ServiceListRecyclerAdapter = new ServiceMasterRecyclerAdapter(getActivity(), ServiceBeanList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerService.setLayoutManager(mLayoutManager);
        recyclerService.setItemAnimator(new DefaultItemAnimator());

        recyclerService.setAdapter(ServiceListRecyclerAdapter);

    }


    @Override
    public void onItemClick(int position) {
        Log.e("position", "its at : " + position);

        if (ServiceBeanList.get(position).getStr_type().equalsIgnoreCase(Itags.OTHER)) {
            ShowCatagory(ServiceBeanList.get(position).getStr_id(), ServiceBeanList.get(position).getStr_type(), ServiceBeanList.get(position).getStr_name(), ServiceBeanList.get(position).getStr_sserviceImage());

        } else if (ServiceBeanList.get(position).getStr_type().equalsIgnoreCase(Itags.RAFTING)) {
            ShowRafting(ServiceBeanList.get(position).getStr_id(), ServiceBeanList.get(position).getStr_type(), ServiceBeanList.get(position).getStr_name(), ServiceBeanList.get(position).getStr_sserviceImage());


        } else if (ServiceBeanList.get(position).getStr_type().equalsIgnoreCase(Itags.CAMPING)) {
            ShowCamping(ServiceBeanList.get(position).getStr_id(), ServiceBeanList.get(position).getStr_type(), ServiceBeanList.get(position).getStr_name(), ServiceBeanList.get(position).getStr_sserviceImage());

        } else {
            Toast.makeText(getActivity(), "Not Valid Service", Toast.LENGTH_SHORT).show();
        }


    }

    private void ShowCamping(String str_id, String str_type, String str_name, String str_sserviceImage) {
        Intent i = new Intent(getActivity(), CampMasterActivity.class);
        i.putExtra("masterId", str_id);
        i.putExtra("masterTittle", str_name);
        i.putExtra("masterType", str_type);
        i.putExtra("masterImage", str_sserviceImage);

        startActivity(i);


    }

    private void ShowRafting(String str_id, String str_type, String str_name, String str_sserviceImage) {
        Intent i = new Intent(getActivity(), RaftingActivity.class);
        i.putExtra("masterId", str_id);
        i.putExtra("masterTittle", str_name);
        i.putExtra("masterType", str_type);
        i.putExtra("masterImage", str_sserviceImage);

        startActivity(i);

    }

    private void ShowCatagory(String str_id, String str_type, String str_name, String str_sserviceImage) {
        Intent i = new Intent(getActivity(), ProductCatagory.class);
        i.putExtra("masterId", str_id);
        i.putExtra("masterTittle", str_name);
        i.putExtra("masterType", str_type);
        i.putExtra("masterImage", str_sserviceImage);

        startActivity(i);
    }

}

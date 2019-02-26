package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.biking.BikingActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.bungy_jump.BungeeJumpActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.camp.CampMasterActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.Servicebeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.rafting.RaftingActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.cycling.CyclingActivity;

/**
 * Created by Prince on 23-11-2017.
 */

public class ServiceTabNew extends Fragment implements ServiceMasterRecyclerAdapter.ItemClickRecListInterface {

    View rootView;
    RecyclerView recyclerService;
    ArrayList<Servicebeans> serviceBeanList;
    ServiceMasterRecyclerAdapter serviceListRecyclerAdapter;
    private Context context;
    ProgressDialog prg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.service_tab_new, container, false);
        context = getActivity();
        createIDS();

        createList(SharedPref.getUserID());
      /*  if (new CheckConnectivity().isConnected(context)) {

        }*/

        return rootView;
    }

    private void createList(final String userID) {


        if (new CheckConnectivity().isConnected(context)) {

            prg.setMessage("Please Wait....");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                JSONArray jsArray = jsData.getJSONArray("services");
                                for (int i = 0; i < jsArray.length(); i++) {
                                    serviceBeanList.add(new Servicebeans(jsArray.getJSONObject(i).getString("id"),
                                            jsArray.getJSONObject(i).getString("service_type"),
                                            jsArray.getJSONObject(i).getString("service_name"),
                                            jsArray.getJSONObject(i).getString("images")));
                                }
                                serviceListRecyclerAdapter.notifyDataSetChanged();

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
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr");
                    Log.e("Param header ", "" + header);

                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("action", UtilsUrl.Action_GETService);
                    params.put("user_id", userID);
                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }


    private void createIDS() {
        prg = new ProgressDialog(context);
        recyclerService = rootView.findViewById(R.id.recyclerview_service);
        recyclerService.setNestedScrollingEnabled(false);
        serviceBeanList = new ArrayList<>();
        serviceListRecyclerAdapter = new ServiceMasterRecyclerAdapter(getActivity(), serviceBeanList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerService.setLayoutManager(mLayoutManager);
        recyclerService.setItemAnimator(new DefaultItemAnimator());

        recyclerService.setAdapter(serviceListRecyclerAdapter);

    }

    @Override
    public void onItemClick(int position) {
        Log.e("position", "its at : " + position);
        if (serviceBeanList.get(position).getStr_id().equalsIgnoreCase(Itags.CYCLING)) {
            openCyclingPage(serviceBeanList.get(position).getStr_id());
        }else if (serviceBeanList.get(position).getStr_id().equalsIgnoreCase(Itags.BIKING)){
            openBikePage(serviceBeanList.get(position).getStr_id());
        }else if (serviceBeanList.get(position).getStr_id().equalsIgnoreCase(Itags.BUNGY_JUMPING)){
            openBungyJumpingPage(serviceBeanList.get(position).getStr_id());
        } else if (serviceBeanList.get(position).getStr_type().equalsIgnoreCase(Itags.OTHER)) {
            showCategory(serviceBeanList.get(position).getStr_id(), serviceBeanList.get(position).getStr_type(), serviceBeanList.get(position).getStr_name(), serviceBeanList.get(position).getStr_sserviceImage());
        } else if (serviceBeanList.get(position).getStr_type().equalsIgnoreCase(Itags.RAFTING)) {
            showRafting(serviceBeanList.get(position).getStr_id(), serviceBeanList.get(position).getStr_type(), serviceBeanList.get(position).getStr_name(), serviceBeanList.get(position).getStr_sserviceImage());
        } else if (serviceBeanList.get(position).getStr_type().equalsIgnoreCase(Itags.CAMPING)) {
            showCamping(serviceBeanList.get(position).getStr_id(), serviceBeanList.get(position).getStr_type(), serviceBeanList.get(position).getStr_name(), serviceBeanList.get(position).getStr_sserviceImage());
        } else{
            Toast.makeText(getActivity(), "Not Valid Service", Toast.LENGTH_SHORT).show();
        }

    }

    private void openCyclingPage(String servId){
        Intent cycleIntent = new Intent(getActivity(), CyclingActivity.class);
        cycleIntent.putExtra("service_id",servId);
        startActivity(cycleIntent);
    }

    private void openBikePage(String servId){
        Intent bikeIntent = new Intent(getActivity(), BikingActivity.class);
        bikeIntent.putExtra("service_id",servId);
        startActivity(bikeIntent);
    }

    private void openBungyJumpingPage(String servId){
        Intent bungeeIntent = new Intent(getActivity(), BungeeJumpActivity.class);
        bungeeIntent.putExtra("service_id",servId);
        startActivity(bungeeIntent);
    }

    private void showCamping(String str_id, String str_type, String str_name, String str_sserviceImage) {
        Intent i = new Intent(getActivity(), CampMasterActivity.class);
        i.putExtra("masterId", str_id);
        i.putExtra("masterTittle", str_name);
        i.putExtra("masterType", str_type);
        i.putExtra("masterImage", str_sserviceImage);

        startActivity(i);

    }

    private void showRafting(String str_id, String str_type, String str_name, String str_sserviceImage) {
        Intent i = new Intent(getActivity(), RaftingActivity.class);
        i.putExtra("masterId", str_id);
        i.putExtra("masterTittle", str_name);
        i.putExtra("masterType", str_type);
        i.putExtra("masterImage", str_sserviceImage);

        startActivity(i);

    }

    private void showCategory(String str_id, String str_type, String str_name, String str_sserviceImage) {

        Log.e("Show => ", "Str id" + str_id + " type & " + str_type + " & name " + str_name + " & serviceimage " + str_sserviceImage);

        Intent i = new Intent(getActivity(), ProductCatagory.class);
        i.putExtra("masterId", str_id);
        i.putExtra("masterTittle", str_name);
        i.putExtra("masterType", str_type);
        i.putExtra("masterImage", str_sserviceImage);

        startActivity(i);
    }

}

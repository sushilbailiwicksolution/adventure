package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;

/**
 * Created by Prince on 06-03-2018.
 */

public class ActivityDemo extends RootActivity {
 /*   RecyclerView recyclerview_product_catagory;
    private Context context;
    SpotsDialog prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        prog = new SpotsDialog(this, R.style.Custom);
        createids();
        context = ActivityDemo.this;
        getValue();

    }

    private void getValue() {


            prog.setTitle("Loading Please wait.");
            prog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            if (new CheckConnectivity().isConnected(context)) {
                                try {
                                    prog.dismiss();
                                    Log.e("Response : prince ", response);
                                    if (response != null) {
                                        JSONObject jsData = new JSONObject(response);
                                        String status = jsData.getString("status");
                                        if (status.equalsIgnoreCase("1")) {

                                            JSONArray jsArray = jsData.getJSONArray("services");
                                            for (int i = 0; i < jsArray.length(); i++) {
                                                productListBeanList.add(new ProductListBean(
                                                        jsArray.getJSONObject(i).getString("package_id"),
                                                        jsArray.getJSONObject(i).getString("package_name"),
                                                        jsArray.getJSONObject(i).getString("service_name"),
                                                        jsArray.getJSONObject(i).getString("service_id"),
                                                        jsArray.getJSONObject(i).getString("short_description"),
                                                        jsArray.getJSONObject(i).getString("description"),
                                                        jsArray.getJSONObject(i).getString("country"),
                                                        jsArray.getJSONObject(i).getString("city"),
                                                        jsArray.getJSONObject(i).getString("state"),
                                                        jsArray.getJSONObject(i).getString("price"),
                                                        jsArray.getJSONObject(i).getString("image"),
                                                        jsArray.getJSONObject(i).getString("status"),
                                                        false
                                                ));


                                            }

                                            productListRecyclerAdapter.notifyDataSetChanged();

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
                    prog.dismiss();
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

                    params.put("action", UtilsUrl.Action_VendorList);
                    params.put("service_id", service_id);
                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };

            AppController1.getInstance().addToRequestQueue(stringRequest);



    }
    private void createids() {
        recyclerview_product_catagory = (RecyclerView) findViewById(R.id.recyclerview_product_catagory);

    }*/
}

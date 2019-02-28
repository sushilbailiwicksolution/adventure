package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.CartListModel;

public class DbOperation {

    private final static String PREFS_NAME = "MyPrefsFile";

    /**
     * Method to get Raft booking entries
     */
    public static int getRaftCount(Context c, String cart_id) {

        DbHelper dbhelperShopCart = new DbHelper(c);
        Cursor cur;
        try {
            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();
            {
                cur = database.rawQuery("select * from " + DbUtils.Cart_table + " where cart_id ='"
                        + cart_id + "'", null);
            }
            int len = cur.getCount();
           Log.e("length ",""+len);
            if (len > 0) {
                SharedPreferences pageNumPref = c.getSharedPreferences(PREFS_NAME, 0);
                pageNumPref.edit().putInt("dbsize", len).apply();
            } else {
                cur.close();
                DbHelper.closedatabase();
            }
            cur.moveToNext();
            cur.close();
            DbHelper.closedatabase();
            return len;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Insert Rafting
    public static boolean insertRaftingCart(Context activity, String id, String tittle, String Str_service,
                                            String Str_check_in, String Str_check_out, String Str_no_nights, String Str_adult,
                                            String Str_child, String Str_starting_point, String Str_booking_date,
                                            String Str__qty, String price, String total, String imageurl, String isRafting,
                                            String str_name, String str_mobile, String str_message, String str_email) {

        DbHelper dbhelperShopCart = new DbHelper(activity);

        Cursor cur = null;
        try {

            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();

            cur = database.rawQuery("select * from " + DbUtils.Cart_table, null);

            int len = cur.getCount();
            System.out.println("length " + len);

            ContentValues newValues = new ContentValues();
            // Assign values for each row.
            Log.e("vendorID", id);
            Log.e("price", price);
            Log.e("amount", total);
            Log.e("amount", total);

            newValues.put(DbUtils.cart_id, id);
            newValues.put(DbUtils.cart_tittle, tittle);
            newValues.put(DbUtils.cart_service, Str_service);
            newValues.put(DbUtils.cart_check_in, Str_check_in);
            newValues.put(DbUtils.cart_check_out, Str_check_out);
            newValues.put(DbUtils.cart_no_nights, Str_no_nights);
            newValues.put(DbUtils.cart_adult, Str_adult);
            newValues.put(DbUtils.cart_child, Str_child);

            newValues.put(DbUtils.cart_starting_point, Str_starting_point);
            newValues.put(DbUtils.cart_booking_date, Str_booking_date);
            newValues.put(DbUtils.cart__qty, Str__qty);
            newValues.put(DbUtils.cart_price, price);
            newValues.put(DbUtils.cart_total, total);
            newValues.put(DbUtils.cart_image_url, imageurl);
            newValues.put(DbUtils.cart_isRafting, isRafting);
            newValues.put(DbUtils.cart_name, str_name);
            newValues.put(DbUtils.cart_mobile, str_mobile);
            newValues.put(DbUtils.cart_message, str_message);
            newValues.put(DbUtils.cart_email, str_email);

            // Insert the row into your table
            database.insert(DbUtils.Cart_table, null, newValues);

            database.close();
            DbHelper.closedatabase();
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            if (cur != null) {
                cur.close();
            }
        }

    }

    // Insert Camping
    public static boolean insertCampaningCart(Context activity, String id, String tittle, String Str_service,
                                              String Str_check_in, String Str_check_out, String Str_no_nights, String Str_adult,
                                              String Str_child, String Str_starting_point, String Str_booking_date,
                                              String Str__qty, String price, String total, String imageurl, String isRafting, String str_room_type_id,
                                              String str_name, String str_mobile, String str_message, String str_email) {

        DbHelper dbhelperShopCart = new DbHelper(activity);

        Cursor cur = null;

        try {
            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();
            cur = database.rawQuery("select * from " + DbUtils.Cart_table, null);
            int len = cur.getCount();
            Log.e("length ",""+len);

            ContentValues newValues = new ContentValues();
            // Assign values for each row.
            Log.e("vendorID", id);
            Log.e("price", price);
            Log.e("amount", total);
            Log.e("amount", total);

            newValues.put(DbUtils.cart_id, id);
            newValues.put(DbUtils.cart_tittle, tittle);
            newValues.put(DbUtils.cart_service, Str_service);
            newValues.put(DbUtils.cart_check_in, Str_check_in);
            newValues.put(DbUtils.cart_check_out, Str_check_out);
            newValues.put(DbUtils.cart_no_nights, Str_no_nights);

            newValues.put(DbUtils.cart_adult, Str_adult);
            newValues.put(DbUtils.cart_child, Str_child);

            newValues.put(DbUtils.cart_starting_point, Str_starting_point);
            newValues.put(DbUtils.cart_booking_date, Str_booking_date);
            newValues.put(DbUtils.cart__qty, Str__qty);
            newValues.put(DbUtils.cart_price, price);
            newValues.put(DbUtils.cart_total, total);
            newValues.put(DbUtils.cart_image_url, imageurl);
            newValues.put(DbUtils.cart_isRafting, isRafting);

            newValues.put(DbUtils.cart_room_type, str_room_type_id);
            newValues.put(DbUtils.cart_name, str_name);
            newValues.put(DbUtils.cart_mobile, str_mobile);
            newValues.put(DbUtils.cart_message, str_message);
            newValues.put(DbUtils.cart_email, str_email);

            // Insert the row into your table
            database.insert(DbUtils.Cart_table, null, newValues);
            ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
            //	database.execSQL(sql);
            database.close();
            DbHelper.closedatabase();
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            if (cur != null) {
                cur.close();
            }
        }

    }


    // Get Cart List
    public static ArrayList<CartListModel> getCartList(Context c) {
        DbHelper dbhelperShopCart = new DbHelper(c);
        Cursor cur = null;
        ArrayList<CartListModel> cartListModals;
        cartListModals = new ArrayList<>();
        try {
            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();
            {
                //cur = database.rawQuery("select Quotes from LoveQuotes where isFav =1", null);
                cur = database.rawQuery("select * from " + DbUtils.Cart_table, null);
            }
            int len = cur.getCount();
            System.out.println("length " + len);
            if (len > 0) {
                SharedPreferences pageNumPref = c.getSharedPreferences(
                        PREFS_NAME, 0);
                pageNumPref.edit().putInt("dbsize", len).apply();
            } else {
                cur.close();
                DbHelper.closedatabase();
            }
            cur.moveToNext();
            for (int i = 0; i < len; i++) {
                cartListModals.add(new CartListModel(cur.getString(cur.getColumnIndex(DbUtils.cart_id)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_tittle)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_service)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_check_in)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_check_out)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_no_nights)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_adult)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_child)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_starting_point)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_booking_date)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart__qty)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_price)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_total)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_image_url)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_isRafting)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_room_type)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_name)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_mobile)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_message)),
                        cur.getString(cur.getColumnIndex(DbUtils.cart_email))
                ));

                Log.e("Cart Detail Id =>", "" + cur.getString(cur.getColumnIndex(DbUtils.cart_id)));
                cur.moveToNext();

                //	System.out.println(data[i]);
            }
            cur.close();
            DbHelper.closedatabase();
            return cartListModals;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (cur != null) {
                cur.close();
            }
        }
    }

    /**
     * insertion For Cycle
     */
    public static int insertCampaningCart(Context c, String cart_id) {

        DbHelper dbhelperShopCart = new DbHelper(c);
        Cursor cur;
        try {
            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();
            {
                cur = database.rawQuery("select * from " + DbUtils.Cart_table + " where cart_id ='"
                        + cart_id + "'", null);
            }
            int len = cur.getCount();
            Log.e("lenght ",""+len);
            if (len > 0) {
                SharedPreferences pageNumPref = c.getSharedPreferences(PREFS_NAME, 0);
                pageNumPref.edit().putInt("dbsize", len).apply();
            } else {
                cur.close();
                DbHelper.closedatabase();
            }
            cur.moveToNext();
            cur.close();
            DbHelper.closedatabase();
            return len;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Delete Cart Itemt
    public static boolean deleteCartItem(Context c, String Cart_id) {

        DbHelper dbhelperShopCart = new DbHelper(c);
        try {
            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();

            //   String sql = "DELETE from " + DbUtils.Man_Ki_Baat_table + " where" + DbUtils.Entry_no + "='" + ENTRY_no + "' ";
            database.execSQL("delete from " + DbUtils.Cart_table + " where cart_id='" + Cart_id + "'");
            ///   database.execSQL(sql);
            database.close();
            DbHelper.closedatabase();
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    // delete Cart Table
    public static void deleteAll_Cart(Context c) {
        DbHelper dbhelperShopCart = new DbHelper(c);
        try {
            dbhelperShopCart.createDataBase();
            SQLiteDatabase database = DbHelper.openDataBase();

            String sql = "delete from " + DbUtils.Cart_table;
            database.execSQL(sql);
            database.close();
            DbHelper.closedatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Prince on 25-01-2018.
 */

public class DBfunction {

    public static void ExportDatabasee(Context con, String DATABASE_NAME) {
        String databasePath = con.getDatabasePath(DATABASE_NAME).getPath();
        String DB_PATH = "/data/data/rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure/";

        String DB_NAME = Database_Utils.DB_NAME;
        databasePath = DB_PATH + DB_NAME;
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = new File("/mnt/sdcard/Vistraka_prince");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                Toast.makeText(con, "Export Succesfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

}

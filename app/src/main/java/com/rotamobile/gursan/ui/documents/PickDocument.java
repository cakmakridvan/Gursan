package com.rotamobile.gursan.ui.documents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.rotamobile.gursan.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PickDocument extends AppCompatActivity {

    private String encode_document = "";

    public static final int PICKFILE_REQUEST_CODE = 3;

    byte[] veri;
    byte [] datam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_document);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(uri.getPath());
            //veri = convertFileToByteArray(file);


            try {
                InputStream ips = PickDocument.this.getContentResolver().openInputStream(uri);
                byte[] bytes = new byte[ips.available()];
                ips.read(bytes, 0, bytes.length);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //to encode base64 from byte array
            //encode_document = Base64.encodeToString(datam, Base64.DEFAULT);
            Log.i("Base64_document",encode_document);

            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }




}

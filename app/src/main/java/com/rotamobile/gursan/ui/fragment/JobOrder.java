package com.rotamobile.gursan.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;

import java.util.ArrayList;
import java.util.List;


public class JobOrder extends Fragment {


    private Spinner spin_proje,spin_bolge,spin_alan,spin_bina,spin_cihaz,spin_isemriTipi,spin_isemriTalebi,spin_kisiler;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.job_order, container, false);

        initializer();

        return view;
    }

    private void initializer() {

        //Proje
        spin_proje = view.findViewById(R.id.spinner_proje);
        List<String> list_proje = new ArrayList<String>();
        list_proje.add("Proje Seçiniz");
        list_proje.add("İBB Halkalı");
        list_proje.add("Başakşehir");
        ArrayAdapter<String> dataAdapter_proje = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_proje){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_proje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_proje.setAdapter(dataAdapter_proje);

        spin_proje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Bölge
        spin_bolge = view.findViewById(R.id.spinner_bolge);
        List<String> list_bolge = new ArrayList<String>();
        list_bolge.add("Bölge Seçiniz");
        list_bolge.add("Avrupa Yakası");
        list_bolge.add("Asya Yakası");
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_bolge){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_bolge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_bolge.setAdapter(dataAdapter_bolge);

        spin_bolge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      //Bina
        spin_bina = view.findViewById(R.id.spinner_bina);
        List<String> list_bina = new ArrayList<String>();
        list_bina.add("Bina Seçiniz");
        list_bina.add("A-Blok");
        list_bina.add("B-Blok");
        ArrayAdapter<String> dataAdapter_bina = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_bina){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_bina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_bina.setAdapter(dataAdapter_bina);

        spin_bina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


      //Alan
        spin_alan = view.findViewById(R.id.spinner_alan);
        List<String> list_alan = new ArrayList<String>();
        list_alan.add("Alan Seçiniz");
        list_alan.add("Giriş Katı");
        list_alan.add("Teras Katı");
        ArrayAdapter<String> dataAdapter_alan = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_alan){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        dataAdapter_alan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_alan.setAdapter(dataAdapter_alan);
        spin_alan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       //Cihaz
        spin_cihaz = view.findViewById(R.id.spinner_cihaz);
        List<String> list_cihaz = new ArrayList<String>();
        list_cihaz.add("Cihaz Seçiniz");
        list_cihaz.add("Klima");
        list_cihaz.add("Musluk");
        ArrayAdapter<String> dataAdapter_cihaz = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_cihaz){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_cihaz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_cihaz.setAdapter(dataAdapter_cihaz);
        spin_cihaz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     //İş Emri Tipi
     spin_isemriTipi = view.findViewById(R.id.spinner_isemritipi);
        List<String> list_isEmriTipi = new ArrayList<String>();
        list_isEmriTipi.add("İş Emri Tipini Seçiniz");
        list_isEmriTipi.add("Talep");
        list_isEmriTipi.add("Arıza");
        ArrayAdapter<String> dataAdapter_isEmriTipi = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_isEmriTipi){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_isEmriTipi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_isemriTipi.setAdapter(dataAdapter_isEmriTipi);
        spin_isemriTipi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //İş Emri Modeli
        spin_isemriTalebi = view.findViewById(R.id.spinner_isemritalebi);
        List<String> list_isEmriTalebi = new ArrayList<String>();
        list_isEmriTalebi.add("İş Modelini Seçiniz");
        list_isEmriTalebi.add("Elektirik");
        list_isEmriTalebi.add("Mekanik");
        list_isEmriTalebi.add("İnşaat");
        ArrayAdapter<String> dataAdapter_isEmriTalebi = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_isEmriTalebi){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_isEmriTalebi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_isemriTalebi.setAdapter(dataAdapter_isEmriTalebi);
        spin_isemriTalebi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Kişiler
        spin_kisiler = view.findViewById(R.id.spinner_kisiler);
        List<String> list_kisiler = new ArrayList<String>();
        list_kisiler.add("Kişi Seçiniz");
        list_kisiler.add("Yavuz");
        list_kisiler.add("Emre");
        ArrayAdapter<String> dataAdapter_kisiler = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_kisiler){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };
        dataAdapter_kisiler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_kisiler.setAdapter(dataAdapter_kisiler);
        spin_kisiler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


}

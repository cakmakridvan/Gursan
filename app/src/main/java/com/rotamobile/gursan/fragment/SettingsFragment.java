package com.rotamobile.gursan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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

import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.utility.LocaleHelper;

import java.util.Locale;

import static android.graphics.ColorSpace.Model.XYZ;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner select_language;
    private TextView languages;
    private String[] array_language;


    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {

    }


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        select_language = view.findViewById(R.id.spinner_language);
        array_language = getResources().getStringArray(R.array.name_language);

        ArrayAdapter<String> langAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spin_language,R.id.language,array_language);
        select_language.setAdapter(langAdapter);
        select_language.setOnItemSelectedListener(this);

        return view;

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String get_selected_lang = String.valueOf(select_language.getSelectedItem());
        if(get_selected_lang.equals("İngilizce") || get_selected_lang.equals("English")){

            //Change Application level locale
            LocaleHelper.setLocale(getActivity(), "en");
            restartActivity();

        }
        else if(get_selected_lang.equals("Türkçe") || get_selected_lang.equals("Turkish")){

            //Change Application level locale
            LocaleHelper.setLocale(getActivity(), "tr");
            restartActivity();

        }

        Toast.makeText(getActivity(),get_selected_lang,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void restartActivity() {
        Intent intent = new Intent(getActivity(),Main.class);
        getActivity().finish();
        startActivity(intent);
    }
}

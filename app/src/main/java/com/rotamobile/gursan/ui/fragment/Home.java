package com.rotamobile.gursan.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.todoList.DataList;
import com.rotamobile.gursan.model.todoList.ListItemAllMessages;
import com.rotamobile.gursan.ui.adapters.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetTodoList;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ListItemAdapter adap;

    private List<ListItemAllMessages> listItems;

    private DataList response_todoList;
    private ArrayList<ListItemAllMessages> todoList;
    private String get_mesaj_todoList = "";

    private TodoListTask todoListTask = null;

    private String get_userID,get_userTypeID;
    private ProgressDialog progressDialog;
    private TextView liste_bos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        recyclerView = view.findViewById(R.id.recycler_home_allmesaj);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();

        liste_bos = view.findViewById(R.id.empty_list);

        //get UserID from Login
        get_userID = Paper.book().read("user_id");
        //get UserTypeID
        get_userTypeID = Paper.book().read("user_type_id");

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        //TodoList Service Running
        todoListTask = new TodoListTask(Integer.parseInt(get_userID),1,Integer.parseInt(get_userTypeID),2005);
        todoListTask.execute((Void) null);

        return view;
    }


    public class TodoListTask extends AsyncTask<Void, Void, Boolean> {

        private Integer user_id;
        private Integer project_id;
        private Integer user_type_id;
        private Integer status;

        TodoListTask(Integer user_id,Integer project_id,Integer user_type_id,Integer status){

            this.user_id = user_id;
            this.project_id = project_id;
            this.user_type_id = user_type_id;
            this.status = status;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String todo_result = GetTodoList(user_id,project_id,user_type_id,status);
                if(!todo_result.trim().equalsIgnoreCase("false")){

                    response_todoList = new Gson().fromJson(todo_result, DataList.class);
                    todoList = response_todoList.getData_list();
                    Log.i("Tag:deviceList",""+todoList);
                    get_mesaj_todoList = "true";


                }else{
                    get_mesaj_todoList = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_todoList.equals("false")){

                progressDialog.dismiss();

                if(todoList.size() > 0){

                    for(int i = 0;i<todoList.size(); i++) {

                        ListItemAllMessages listItemAllMessages = new ListItemAllMessages(todoList.get(i).getID(),todoList.get(i).getProjectName(),todoList.get(i).getSubjectText(),
                                todoList.get(i).getBuildingName(),todoList.get(i).getDeviceName(),todoList.get(i).getStartDate(),todoList.get(i).getEndDate(),
                                todoList.get(i).getWorkUser(),todoList.get(i).getTerritoryName(),todoList.get(i).getAreaName(),todoList.get(i).getProjectID(),
                                todoList.get(i).getTerritoryID(),todoList.get(i).getBuildingID(),todoList.get(i).getAreaID(),todoList.get(i).getDeviceID(),
                                todoList.get(i).getSubjectID(),todoList.get(i).getInsertUserID(),todoList.get(i).getAssignedUserID(),todoList.get(i).getAuthorizationUpdate(),
                                todoList.get(i).getDescription());
                        listItems.add(listItemAllMessages);
                    }
                    adapter = new ListItemAdapter(listItems,getActivity());
                    //for SearchView in RecyclerView
                    adap = new ListItemAdapter(listItems,getActivity());
                    /*recyclerView.setAdapter(adapter);*/
                    recyclerView.setAdapter(adap);
                }else{
                    progressDialog.dismiss();
                    liste_bos.setVisibility(View.VISIBLE);
                }

            }else{
                progressDialog.dismiss();
                liste_bos.setVisibility(View.VISIBLE);
                liste_bos.setText("Hata!! Tekrar Deneyiniz ");

            }
        }


    }




}

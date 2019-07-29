package com.rotamobile.gursan.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.todoList.DataList;
import com.rotamobile.gursan.model.todoList.ListItemAllMessages;
import com.rotamobile.gursan.ui.adapters.ListItemAdapter;
import com.rotamobile.gursan.utils.enums.Enums;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetTodoList;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
  //Search in RecyclerView
    private ListItemAdapter adap;
    private List<ListItemAllMessages> listItems;

    private DataList response_todoList;
    private ArrayList<ListItemAllMessages> todoList;
    private String get_mesaj_todoList = "";

    private TodoListTask todoListTask = null;

    private String get_userID,get_userTypeID,get_projectID;
    private ProgressDialog progressDialog;
    private TextView liste_bos;
    private SwipeRefreshLayout refreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        recyclerView = view.findViewById(R.id.recycler_home_allmesaj);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initialize SwipeRefreshLayout
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        //set colors for loading progressbar
        refreshLayout.setColorSchemeColors(0xFFFF0000, 0xFF00FF00);

        listItems = new ArrayList<>();

        liste_bos = view.findViewById(R.id.empty_list);

        //get UserID from Login
        get_userID = Paper.book().read("user_id");
        //get UserTypeID
        get_userTypeID = Paper.book().read("user_type_id");
        //get ProjectID
        get_projectID = Paper.book().read("project_id");

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        //TodoList Service Running
        todoListTask = new TodoListTask(Integer.parseInt(get_userID),Integer.parseInt(get_projectID),Integer.parseInt(get_userTypeID), Enums.msj_acik);
        todoListTask.execute((Void) null);

        //OnRefreshListener executes when layout is pull down to perform a refresh
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh();
            }
        });


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

                    if(liste_bos.getVisibility() == View.VISIBLE){
                        liste_bos.setVisibility(View.GONE);
                    }

                    for(int i = 0;i<todoList.size(); i++) {

                        ListItemAllMessages listItemAllMessages = new ListItemAllMessages(todoList.get(i).getID(),todoList.get(i).getProjectName(),todoList.get(i).getSubjectText(),
                                todoList.get(i).getBuildingName(),todoList.get(i).getDeviceName(),todoList.get(i).getStartDate(),todoList.get(i).getEndDate(),
                                todoList.get(i).getWorkUser(),todoList.get(i).getTerritoryName(),todoList.get(i).getAreaName(),todoList.get(i).getProjectID(),
                                todoList.get(i).getTerritoryID(),todoList.get(i).getBuildingID(),todoList.get(i).getAreaID(),todoList.get(i).getDeviceID(),
                                todoList.get(i).getSubjectID(),todoList.get(i).getInsertUserID(),todoList.get(i).getAssignedUserID(),todoList.get(i).getAuthorizationUpdate(),
                                todoList.get(i).getDescription(),todoList.get(i).getWorkID(),todoList.get(i).getWorkOrderServiceID(),todoList.get(i).getWorkOrderCategoryID(),
                                todoList.get(i).getWorkOrderTypeID(),todoList.get(i).getWorkImportanceID(),todoList.get(i).getMoveTypeID());
                        listItems.add(listItemAllMessages);
                    }
                    adapter = new ListItemAdapter(listItems,getActivity(),Enums.msj_acik);
                    //for SearchView in RecyclerView
                    adap = new ListItemAdapter(listItems,getActivity(),Enums.msj_acik);
                    /*recyclerView.setAdapter(adapter);*/
                    recyclerView.setAdapter(adap);

                    refreshLayout.setRefreshing(false);
                }else{
                    progressDialog.dismiss();
                    liste_bos.setVisibility(View.VISIBLE);

                    refreshLayout.setRefreshing(false);
                }

            }else{
                progressDialog.dismiss();
                liste_bos.setVisibility(View.VISIBLE);
                liste_bos.setText("Hata!! Tekrar Deneyiniz ");

                refreshLayout.setRefreshing(false);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            todoListTask = null;
            progressDialog.dismiss();
        }
    }

  //For Search RecyclerView Button for Message
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.home_search_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search2:
                if(listItems.size()>0) {
                    SearchView searchView = (SearchView) item.getActionView();

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {

                            adap.getFilter().filter(s);
                            return false;
                        }
                    });
                }
                break;

        }
        return true;
    }

    //method to add content to listview while refresh
    private void startRefresh() {

        listItems.clear();
        adapter = new ListItemAdapter(listItems,getActivity(),Enums.msj_acik);
        //for SearchView in RecyclerView
        adap = new ListItemAdapter(listItems,getActivity(),Enums.msj_acik);
        /*recyclerView.setAdapter(adapter);*/
        recyclerView.setAdapter(adap);

        //TodoList Service Running
        todoListTask = new TodoListTask(Integer.parseInt(get_userID),Integer.parseInt(get_projectID),Integer.parseInt(get_userTypeID), Enums.msj_acik);
        todoListTask.execute((Void) null);


    }





}

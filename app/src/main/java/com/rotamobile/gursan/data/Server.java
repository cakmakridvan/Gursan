package com.rotamobile.gursan.data;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Server {

    public static final String Main_URL = "http://178.18.200.116:86/api/";
    public static final String Main_URL_token = "http://178.18.200.116:86/";


    public static String GetUsers(String username, String password) {

        String method_Login = "UserService/UserLogin";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName", username);
            jsonObject.put("Password", password);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "hata";
        }

    }

    public static String GetProjects(String userID) {

        String method_Projects = "ProjectService/ProjectAuthorized";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "UserId=" + userID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String GetTerritory(Integer projectID) {

        String method_Projects = "TerritoryService/TerritoryOfProject";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "ProjectId=" + projectID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String GetBuilding(Integer territoryID) {

        String method_Projects = "BuildingService/BuildingOfTerritory";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Projects + "?" + "TerritoryId=" + territoryID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String GetArea(Integer buildingID) {

        String method_Projects = "AreaService/AreaOfBuilding";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Projects + "?" + "BuildingId=" + buildingID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String GetDevice(Integer areaID) {

        String method_Projects = "DeviceService/DeviceOfArea";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Projects + "?" + "AreaId=" + areaID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String GetUserList() {

        String method_Projects = "UserService/UserGet";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String GetSubjects() {

        String method_Projects = "HelperService/GetAllSubjects";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String TodoAdd(Integer project_ID, Integer territory_ID, Integer building_ID, Integer area_ID,
                                 Integer device_ID, Integer workOrderType_ID, Integer workImportance_ID, Integer workOrderCategory_ID, Integer workTopic_ID,
                                 Integer user_ID, String description, Integer insertUser_ID, Integer updateUser_ID) {

        String method_Login = "Todo/TodoAdd";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ProjectID", project_ID);
            jsonObject.put("TerritoryID", territory_ID);
            jsonObject.put("BuildingID", building_ID);
            jsonObject.put("AreaID", area_ID);
            jsonObject.put("DeviceID", device_ID);
            jsonObject.put("WorkOrderTypeID", workOrderType_ID);
            jsonObject.put("WorkImportanceID", workImportance_ID);
            jsonObject.put("WorkOrderCategoryID", workOrderCategory_ID);
            jsonObject.put("SubjectID", workTopic_ID);
            jsonObject.put("AssignedUserID", user_ID);
            jsonObject.put("Description", description);
            jsonObject.put("InsertUserID", insertUser_ID);
            jsonObject.put("UpdateUserID", updateUser_ID);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String GetTodoList(Integer userID, Integer project_id, Integer user_type_id, Integer status) {

        String method_Projects = "Todo/TodoList";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "UserID=" + userID + "&" + "ProjectID=" + project_id + "&" + "UserTypeID=" + user_type_id + "&" +
                "Status=" + status);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String TodoListUpdate(Integer id, Integer project_ID, Integer territory_ID, Integer building_ID, Integer area_ID,
                                        Integer device_ID, Integer workTopic_ID,
                                        Integer assigned_user_ID, Integer updateUser_ID, Integer workOrderService_ID, String description, Integer moveType_id,
                                        Integer WorkOrderCategory_ID, Integer WorkOrderType_ID, Integer WorkImportance_ID) {

        String method_Login = "Todo/TodoListUpdate";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);// WorkOrder ID
            jsonObject.put("ProjectID", project_ID);
            jsonObject.put("TerritoryID", territory_ID);
            jsonObject.put("BuildingID", building_ID);
            jsonObject.put("AreaID", area_ID);
            jsonObject.put("DeviceID", device_ID);
            jsonObject.put("SubjectID", workTopic_ID);
            jsonObject.put("AssignedUserID", assigned_user_ID);
            jsonObject.put("UpdateUserID", updateUser_ID);
            jsonObject.put("WorkOrderServiceID", workOrderService_ID);
            jsonObject.put("Description", description);
            jsonObject.put("MoveTypeID",moveType_id);
            jsonObject.put("WorkOrderCategoryID",WorkOrderCategory_ID);
            jsonObject.put("WorkOrderTypeID",WorkOrderType_ID);
            jsonObject.put("WorkImportanceID",WorkImportance_ID);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String DocumentAdd(Integer workOrderID, Integer documentTypeID, Boolean active, String documentContent, String commentText, Integer insertUserID,
                                     Integer updateUserID) {

        String method_Login = "DocumentService/DocumentAdd";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", 0);
            jsonObject.put("CommentID", 0);
            jsonObject.put("WorkOrderID", workOrderID);// WorkOrder ID
            jsonObject.put("DocumentTypeID", documentTypeID);
            jsonObject.put("DocumentContent", documentContent);
            jsonObject.put("Active", active);
            jsonObject.put("InsertUserID", insertUserID);
            jsonObject.put("InsertDate", "2019-03-28T12:46:04.135Z");
            jsonObject.put("UpdateUserID", updateUserID);
            jsonObject.put("UpdateDate", "2019-03-28T12:46:04.135Z");
            jsonObject.put("UserID", 0);
            jsonObject.put("CommentText", commentText);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String GetDefinedList(Integer workID) {

        String method_Projects = "WODefinedTaskService/GetByWorkOrder";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "workOrderID=" + workID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }


    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    public static String PostDefinedList(Integer work_orderID, Integer insert_userID, int[] selectedList) {

        String method_Login = "WODefinedTaskService/DefinedTaskAdd";
        String json = "";

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("WorkOrderID", work_orderID);
            jsonObject.put("InsertUserID", insert_userID);
            JSONArray array = new JSONArray();
            for (int i = 0; i < selectedList.length; i++) {
                if(selectedList[i] != 0) {
                    array.put(selectedList[i]);
                }
            }
            jsonObject.put("SelectedList", array);
            json = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

        HttpPost httppost = new HttpPost(Main_URL + method_Login );
        try {
            StringEntity entity = new StringEntity(json);
// Depends on your web service
            httppost.setEntity(entity);
            httppost.setHeader("Content-type", "application/json");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            BasicResponseHandler handler = new BasicResponseHandler();
            String response = httpclient.execute(httppost, handler);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }

    }

    public static String GetByUserTypeWithProjectAuth(Integer user_TypeID,Integer project_ID) {

        String method_Login = "UserService/GetByUserTypeWithProjectAuth";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Main_URL + method_Login + "?" + "userID=" + user_TypeID + "&" + "projectID=" + project_ID);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;

    }

    public static String WorkStatusList(Integer workOrder_id) {

        String method_Projects = "WorkStatus/WorkStatusListGet";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "workOrderID=" + workOrder_id);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String WorkStatusAdd(Integer workOrder_id,Integer moveType_id,Integer assigned_id,Integer insertUser_id) {

        String method_Login = "WorkStatus/WorkSatusAdd";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("WorkOrderID",workOrder_id);
            jsonObject.put("MoveTypeID",moveType_id);
            jsonObject.put("AssignedUserID", assigned_id);
            jsonObject.put("InsertUserID", insertUser_id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String GetAllMaterial() {

        String method_Projects = "ProductService/GetAllWithStock";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String MaterialAdd(Integer amount, Integer workOrder_id,Integer product_id,Integer insert_user_id) {

        String method_Login = "MaterialService/MaterialAdd";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Amount", amount);
            jsonObject.put("WorkOrderID", workOrder_id);
            jsonObject.put("ProductID", product_id);
            jsonObject.put("InsertUserID",insert_user_id);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String MaterialUpdate(Integer id, Integer updateUser_id, Integer amount){

        String method_Login = "MaterialService/MaterialUpdate";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("UpdateUserID", updateUser_id);
            jsonObject.put("Amount", amount);
            jsonObject.put("ManagerConfirm",false);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String MaterialDelete(Integer id, Integer updateUser_id){

        String method_Login = "MaterialService/MaterialDelete";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("UpdateUserID",updateUser_id);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String GetHistoryDevice(Integer code){

        String method_Projects = "HelperService/GetHistoryOfDevice";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "code=" + code);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;

    }

    public static String RequestAdd(Integer id, String subject, String description, Integer unit_id, Integer amount, Integer insertUser_id){

        String method_Login = "RequestService/RequestAdd";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("WorkOrderID", id);
            jsonObject.put("ProductAndService", subject);
            jsonObject.put("Description", description);
            jsonObject.put("UnitID", unit_id);
            jsonObject.put("Amount", amount);
            jsonObject.put("InsertUserID", insertUser_id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String GetDisServisByWorkOrder(Integer workOrder_id){

        String method_Projects = "RequestService/GetByWorkOrder";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "workOrderID=" + workOrder_id);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;

    }

    public static String ProductUnit(){

        String method_Login = "RotaTypeService/ProductUnit";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path
            JSONObject jsonObject = new JSONObject();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String RequestGetByWorkOrder(Integer workOrder_id){

        String method_Projects = "RequestService/GetByWorkOrder";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "workOrderID=" + workOrder_id);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;
    }

    public static String RequestUpdate(Integer workOrder_id, Integer updateUser_id, Integer amount, String subject, String description, Integer unit_id){

        String method_Login = "RequestService/RequestUpdate";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", workOrder_id);
            jsonObject.put("UpdateUserID", updateUser_id);
            jsonObject.put("Amount", amount);
            jsonObject.put("ProductAndService", subject);
            jsonObject.put("Description", description);
            jsonObject.put("UnitID", unit_id);
            jsonObject.put("Derivative", "");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String RequestDelete(Integer id, Integer updateUser_id){

        String method_Login = "RequestService/RequestDelete";

        try {

            URL url = new URL(Main_URL + method_Login); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", id);
            jsonObject.put("UpdateUserID",updateUser_id);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "false";
        }

    }

    public static String GetToken(String username,String password,String grantType){

        String method_Projects = "getToken";

        try {

            URL url = new URL(Main_URL_token + method_Projects); // here is your URL path

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName", username);
            jsonObject.put("Password", password);
            jsonObject.put("grant_type", grantType);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "hata";
        }

    }

    public static String TestToken(String token){

        String method_Projects = "Test/Get";

        try {

            URL url = new URL(Main_URL_token + method_Projects); // here is your URL path

            JSONObject jsonObject = new JSONObject();


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Bearer ",token);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(jsonObject));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                //User Info issue
                //return new String("false : "+responseCode);
                Log.i("Exception: ", "" + responseCode);
                return "false";
            }

        } catch (Exception e) {
            //Connection issue
            //return new String("Exception: " + e.getMessage());
            Log.i("Exception: ", e.getMessage());
            return "hata";
        }

    }

    public static String GetIcServisByWorkOrder(Integer workOrder_id){

        String method_Projects = "MaterialService/GetByWorkOrder";

        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(Main_URL + method_Projects + "?" + "workOrderID=" + workOrder_id);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            return "false";
        } finally {
            try {

                if (inputStream != null) inputStream.close();

            } catch (Exception squish) {
                return "false";
            }
        }
        return result;

    }




}
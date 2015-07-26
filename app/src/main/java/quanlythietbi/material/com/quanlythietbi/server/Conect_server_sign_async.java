package quanlythietbi.material.com.quanlythietbi.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.Toast;

import quanlythietbi.material.com.quanlythietbi.activity.FlashScreenAc;
import quanlythietbi.material.com.quanlythietbi.object.CallbackResult;
import quanlythietbi.material.com.quanlythietbi.object.ObjectClient;
import quanlythietbi.material.com.quanlythietbi.server.Function;
import quanlythietbi.material.com.quanlythietbi.server.M_READ_JSON;

public class Conect_server_sign_async extends AsyncTask<String, String, String> {
        String mUrl = "";
        public String TAG_KQ = "NOT CONNECT";
        public String TAG_ERROR = "Không kết nối được server";
        ObjectClient mOC;
        Context mcon;

        CountDownTimer mcountdown;
        final int time_connnect = 180000;
        CallbackResult mCB=null;
        
        public Conect_server_sign_async(Context mcon,String mUrl,ObjectClient mOC){
        	this.mcon = mcon;
        	this.mUrl= mUrl;
        	this.mOC = mOC;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mcountdown = new CountDownTimer(time_connnect, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    Toast.makeText(mcon, "time out", Toast.LENGTH_LONG).show();
                }
            }.start();
        }

        protected String doInBackground(String... kq) {
            try {
                ObjectClient mOC = new ObjectClient();
                mOC.setCommand("dangnhap");
                upload(mOC);
            } catch (Exception e) {
            }

            return null;

        }

        protected void onPostExecute(String result) {
            mcountdown.cancel();
            ((Activity)mcon).runOnUiThread(new Runnable() {
                public void run() {
                    if(mCB!=null){
                        Toast.makeText(mcon,""+mCB.getList_banquyen().size(),Toast.LENGTH_LONG).show();
                        try {
							FlashScreenAc.my_list = mCB.getList_banquyen();
							((FlashScreenAc)mcon).to_main();
                            ((FlashScreenAc)mcon).finish();
						} catch (Exception e) {
							// TODO: handle exception
						}
                    }else{
                        Toast.makeText(mcon,TAG_KQ,Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

        public void upload(ObjectClient mOC) {
            TAG_KQ = TAG_ERROR;
            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            DataInputStream dis = null;
            try {
                URL url = new URL(mUrl);
                URLConnection urlConn = url.openConnection();
                urlConn.setConnectTimeout(3000);
                urlConn.setReadTimeout(180000);
                connection = (HttpURLConnection) urlConn;
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/octet-stream");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setAllowUserInteraction(true);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                outputStream = new DataOutputStream(
                        connection.getOutputStream());

                TAG_KQ = Function.alldata2server(mOC);
                Function.write_String_to_byte(outputStream, TAG_KQ);
                outputStream.flush();
                dis = new DataInputStream(connection.getInputStream());
                TAG_KQ = "";
                TAG_KQ = Function.byte_to_String(dis);
                outputStream.close();
                dis.close();
                JsonParser jp = new JsonParser();
                JsonObject mJO = jp.parse(TAG_KQ).getAsJsonObject();
//                TAG_KQ="mJO :"+mJO.toString();
                mCB = M_READ_JSON.read_callback(mJO);
                if (mCB != null) {
                    try {
                        TAG_KQ = mCB.getResultString();
                    } catch (Exception e) {
                        TAG_KQ = "loi doc callback :"+e.toString();
                    }
                } else {
                    TAG_KQ = "ko doc dc JSON";
                }
            } catch (Exception ex) {
                    TAG_KQ = TAG_ERROR;

            }

        }

    }

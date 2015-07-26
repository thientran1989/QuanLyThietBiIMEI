package quanlythietbi.material.com.quanlythietbi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import quanlythietbi.material.com.quanlythietbi.R;
import quanlythietbi.material.com.quanlythietbi.object.Obj_banquyen;


public class Adapter_MBA extends
        RecyclerView.Adapter<Adapter_MBA.ContactViewHolder> {
    private List<Obj_banquyen> list_all;
    private List<Obj_banquyen> list_tuyen;
    Context mcon;

    CountDownTimer mcountdown;
    final int time_connnect = 20000;
    String ketquaserver = "loi";
    String IMEI="";
    //	ObjectClient mOC;
//	CallbackResult mCB =null;

    public Adapter_MBA(Context mcon,List<Obj_banquyen> list_all) {
        this.mcon=mcon;
        this.list_all = list_all;
        this.list_tuyen = new ArrayList<Obj_banquyen>(list_all);
    }
    public void get_search(String key){
        list_tuyen.clear();
        if (key.length()>0){
            for (Obj_banquyen oMBA : list_all){
                if (oMBA.getIMEI().contains(key)){
                    list_tuyen.add(oMBA);
                }
            }
        }else{
            list_tuyen = new ArrayList<Obj_banquyen>(this.list_all);
        }
        notifyDataSetChanged();

    }
    public void update_TT(String my_IMEI){
        if (my_IMEI.length()>0){
            for (Obj_banquyen oMBA : list_tuyen){
                if (oMBA.getIMEI().equals(my_IMEI)){
                    if(oMBA.getTTRANG().equals("DANGKY")){
                        oMBA.setTTRANG("OK");
                    }else if(oMBA.getTTRANG().equals("OK")){
                        oMBA.setTTRANG("KHOA");
                    }else if(oMBA.getTTRANG().equals("KHOA")){
                        oMBA.setTTRANG("OK");
                    }
                }
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list_tuyen.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Obj_banquyen ci = list_tuyen.get(i);

        contactViewHolder.tv_IMEI.setText("" + ci.getIMEI());
        contactViewHolder.tv_date.setText(ci.getdate_label());
        contactViewHolder.tv_tinhtrang.setText(get_tinhtrang_label(ci.getTTRANG()));
        contactViewHolder.btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    showBasicLongContent(TT);
                IMEI = ci.getIMEI();
                change_status();
            }
        });


//        contactViewHolder.setClickListener(new ContactViewHolder.ClickListener() {
//            public void onClick(View v, int pos, boolean isLongClick) {
//                if (isLongClick) {
//                    Toast.makeText(mcon,"longclick "+ci.getTRU(),Toast.LENGTH_LONG).show();
//                } else {
//                    String TT = "thong tin ";
//                    TT=TT+"Tại trụ :"+ci.getTRU()+"\n";
////                    TT=TT+"MSTS :"+ci.getMSTS()+"\n";
////                    TT=TT+"Thuộc kho :"+ci.getKho()+"\n";
////                    TT=TT+"Nhà sx :"+ci.getNha_sanxuat()+"\n";
////                    TT=TT+"Công suất :"+ci.getCong_suat()+"\n";
////                    TT=TT+"Nấc vận hành :"+ci.getNac_vanhanh()+"\n";
//                    showBasicLongContent(TT);
//                }
//            }
//        });
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.cardview_baihat, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView tv_IMEI;
        public TextView tv_date;
        public TextView tv_tinhtrang;
        public Button btn_change;

        private ClickListener clickListener;

        public ContactViewHolder(View v) {
            super(v);

            tv_IMEI = (TextView) v
                    .findViewById(R.id.tv_IMEI_cardview_banquyen);
            tv_date = (TextView) v
                    .findViewById(R.id.tv_date_cardview_banquyen);
            tv_tinhtrang = (TextView) v
                    .findViewById(R.id.tv_tinhtrang_cardview_banquyen);
            btn_change = (Button) v
                    .findViewById(R.id.btn_change_cardview_banquyen);

        }
        /* Interface for handling clicks - both normal and long ones. */
        public interface ClickListener {
            public void onClick(View v, int position, boolean isLongClick);

        }

        /* Setter for listener. */
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View v) {

            // If long clicked, passed last variable as true.
            clickListener.onClick(v, getPosition(), true);
            return true;
        }


    }
//    private void showBasicLongContent(String thongtin) {
//        new MaterialDialog.Builder(mcon)
//                .title(R.string.thong_tin_may)
//                .content(thongtin)
//                    .positiveText(R.string.dong)
//                .negativeText(R.string.sua)
//                .show();
//    }
public String get_tinhtrang_label(String TTIMEI){
    String TT ="unknowm";
    try {
        if(TTIMEI.equals("DANGKY")){
            TT = mcon.getString(R.string.dangky);
        }else if(TTIMEI.equals("OK")){
            TT = mcon.getString(R.string.ok);
        }else if(TTIMEI.equals("KHOA")){
            TT = mcon.getString(R.string.khoa);
        }
    }catch (Exception e){

    }
    return TT;
}

    // kiem tra user
    class ktuser_from_server extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mcountdown = new CountDownTimer(time_connnect, 1000) {
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    showToast(
                            "Quá thời gian kết nối server, thử lại sau !!!");
                }

            }.start();
        }

        protected String doInBackground(String... kq) {
            String url = "http://2-dot-quanlythietbi-997.appspot.com/changestatus";
            try {
                kt_user(url);
            } catch (Exception e) {
            }

            return null;

        }
        protected void onPostExecute(String result) {
            mcountdown.cancel();
            ((Activity)mcon).runOnUiThread(new Runnable() {
                public void run() {
                    if(ketquaserver.equals("OK")){
                        update_TT(IMEI);
                        Toast.makeText(mcon.getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(mcon.getApplicationContext(),ketquaserver,Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
    public void kt_user(String urlServer) {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        try {
            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeUTF(IMEI);
            outputStream.flush();
            outputStream.close();
            DataInputStream dis = new DataInputStream(
                    connection.getInputStream());
            ketquaserver = dis.readUTF();

        } catch (Exception ex) {
            ketquaserver=("loi"+ex.toString());
        }
    }
    public void showToast(String t) {
        Toast.makeText(mcon.getApplicationContext(), t, Toast.LENGTH_LONG).show();

    }
    public void show_dialog_canhbao(String t) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        new ktuser_from_server().execute();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mcon);
        builder.setCancelable(false);
        builder.setMessage(t)
                .setPositiveButton("Thử lại", dialogClickListener)
                .setNegativeButton("Thoát", dialogClickListener)
                .setTitle("Cảnh báo !").show();
    }
    public void change_status() {
        try {
            new ktuser_from_server().execute();
        } catch (Exception e) {

        }
    }
}

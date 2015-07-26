package quanlythietbi.material.com.quanlythietbi.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

import quanlythietbi.material.com.quanlythietbi.R;
import quanlythietbi.material.com.quanlythietbi.object.Obj_banquyen;
import quanlythietbi.material.com.quanlythietbi.server.Conect_server_sign_async;


public class FlashScreenAc extends Activity{
	public static List<Obj_banquyen> my_list =null;
    private String URL_FEED = "http://2-dot-quanlythietbi-997.appspot.com/getbanquyen";
	Conect_server_sign_async mCONECT=null;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashscreen);
        my_list = new ArrayList<Obj_banquyen>();
        try {
            mCONECT = new Conect_server_sign_async(this,URL_FEED,null);
        }catch(Exception e){

        }
        try {
            if(mCONECT!=null){
                mCONECT.execute();
            }
        }catch(Exception e){

        }


    }

	@Override
	public void onBackPressed() {
		
	}
	public void to_main(){
		Intent intent = new Intent(FlashScreenAc.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	


}

package quanlythietbi.material.com.quanlythietbi.server;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import quanlythietbi.material.com.quanlythietbi.object.CallbackResult;


public class M_READ_JSON {

	public static CallbackResult read_callback(JsonObject mJO) {
		CallbackResult mCB = null;
		try {
			Gson gson = new Gson();
			mCB = gson.fromJson(mJO.get("CB").getAsString(),CallbackResult.class);
		} catch (Exception e) {

		}
		return mCB;
	}
}

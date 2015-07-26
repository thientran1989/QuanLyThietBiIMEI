package quanlythietbi.material.com.quanlythietbi.object;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Obj_banquyen implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long id_IMEI;
	public String IMEI;
	public String APP;
	public String TTRANG;
	public long date;

	public Long getId_IMEI() {
		return id_IMEI;
	}

	public void setId_IMEI(Long id_IMEI) {
		this.id_IMEI = id_IMEI;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getAPP() {
		return APP;
	}

	public void setAPP(String aPP) {
		APP = aPP;
	}

	public long getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date.getTime();
	}

	public String getTTRANG() {
		return TTRANG;
	}

	public void setTTRANG(String tTRANG) {
		TTRANG = tTRANG;
	}
	public String getdate_label(){
		String dateString ="unknowm";
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			dateString = df.format(new Date(getDate()));
		}catch (Exception e){

		}
		return dateString;
	}


}

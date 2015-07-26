package quanlythietbi.material.com.quanlythietbi.object;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class CallbackResult implements Serializable {
	private String Command="";
	private String ResultString="";
	private String diengiai1="";
	private List<FeedItem> list_item;
	private List<Obj_banquyen> list_banquyen;
	public CallbackResult() {
		super();
	}
	
	public String getDiengiai1() {
		return diengiai1;
	}

	public void setDiengiai1(String diengiai1) {
		this.diengiai1 = diengiai1;
	}

	public String getCommand() {
		return Command;
	}
	public void setCommand(String command) {
		Command = command;
	}
	public String getResultString() {
		return ResultString;
	}
	public void setResultString(String resultString) {
		ResultString = resultString;
	}

	public List<FeedItem> getList_item() {
		return list_item;
	}

	public void setList_item(List<FeedItem> list_item) {
		this.list_item = list_item;
	}

	public List<Obj_banquyen> getList_banquyen() {
		return list_banquyen;
	}

	public void setList_banquyen(List<Obj_banquyen> list_banquyen) {
		this.list_banquyen = list_banquyen;
	}
	

}

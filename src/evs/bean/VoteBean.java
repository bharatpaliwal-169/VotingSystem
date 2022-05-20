package evs.bean;

public class VoteBean {
	private String e_id;
	private String c_id;
	private String v_id;
	
	public VoteBean(String e_id,String c_id,String v_id) {
		this.e_id = e_id;
		this.c_id = c_id;
		this.v_id = v_id;
	}
	
	public String getE_id() {
		return e_id;
	}
	public String getC_id() {
		return c_id;
	}
	public String getV_id() {
		return v_id;
	}
	
}

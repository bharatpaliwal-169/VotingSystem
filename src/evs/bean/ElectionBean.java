package evs.bean;

public class ElectionBean {
	private String election_id;
	private String election_name;
	private int election_status;
	
	public ElectionBean(String election_id,String election_name ,int election_status) {
		this.election_id = election_id;
		this.election_name = election_name;
		this.election_status = election_status;
	}

	public String getElection_id() {
		return election_id;
	}

	
	public String getElection_name() {
		return election_name;
	}

	
	public int getElection_status() {
		return election_status;
	}

	
}

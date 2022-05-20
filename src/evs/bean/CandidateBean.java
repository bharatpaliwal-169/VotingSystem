package evs.bean;

public class CandidateBean {
	private String candidate_name;
	private String candidate_id;
	
	public CandidateBean(String name,String id) {
		this.candidate_name = name;
		this.candidate_id = id;
	}

	public String getCandidate_name() {
		return candidate_name;
	}

	public String getCandidate_id() {
		return candidate_id;
	}
	
}

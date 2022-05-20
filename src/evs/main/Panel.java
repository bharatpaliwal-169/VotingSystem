package evs.main;

import java.util.Random;
import javax.swing.JOptionPane;
import evs.bean.*;
import evs.dao.*;
import java.util.*;
public class Panel {
	
	//list of all elections that are live. --All users
	public static void AAEList() {
		ArrayList<ElectionBean> a=ElectionDao.viewAllActiveElections();
		String str="";
		for(ElectionBean e:a){
				str+=e.getElection_id()+" "+e.getElection_name()+" "+"\n";	
		}
		JOptionPane.showMessageDialog(null, str);
	}
	
	
	public static void afterSuccess(UserBean ub) {
		int access = ub.getUserType();
		
		switch(access) {
			case 1: //user
					
				int user_opt = Integer.parseInt(
						JOptionPane.showInputDialog
						("Select as Follows:\n "
								+ "1-> Get my Voter ID \n"
								+ " 2-> See Live Elections \n "
								+ "3 -> Caste Vote \n "
								+ "4 -> See Result\n"
								+ "5 -> Exit"));
					switch(user_opt) {
						case 1: //become voter
							UserDao.becomeVoter(ub);
							Panel.afterSuccess(ub);
							break;
						case 2:
							//Live Election list
							AAEList();
							Panel.afterSuccess(ub);
							
							break;
							
						//Hard-Point
						case 3:
							// Caste Vote
							String v_id = JOptionPane.showInputDialog("Enter your voter id \n"); 
							JOptionPane.showMessageDialog(null, "let us validate your voter ID \n");
							//validate voter id
							if(ElectionDao.checkVoter(v_id) != 0) {
								// Show list of active elections
								AAEList();
								String e_id = JOptionPane.showInputDialog("Enter "
										+ "the Election id in which you want to participate ");
								
								// show list of candidates in that election
								CandidateDao.listOfParticipants(e_id);
								String c_id = JOptionPane.showInputDialog("Enter the candidate id you want to vote");
								
								//validate candidate is contesting election or not
								if(CandidateDao.checkParticipant(e_id,c_id)) {
									
									VoteBean avb = new VoteBean(e_id,c_id,v_id);
									ElectionDao.casteVote(avb);									
								}
								else {
									JOptionPane.showMessageDialog(null,"You have entered wrond candidate id : terminated!");
									Panel.afterSuccess(ub);
								}
								
							}
							else {
								JOptionPane.showMessageDialog(null,"Please get a voter Id "
										+ "or enter the valid voter id \n");
							}
							Panel.afterSuccess(ub);
							break;
							
						case 4:
							//view Results of prev elections
							ElectionDao.publishResults();
							Panel.afterSuccess(ub);
							break;
						case 5:
							return;
						default:
							break;
					}
					
				break;
			case 2: //EO
				int eo_opt = Integer.parseInt(
						JOptionPane.showInputDialog
						("Select as Follows:\n 1-> Add a new Candidate \n"
						+ "2-> Add candidate to election \n "
						+ "3-> update/delete candidate \n"
						+ "4-> Exit Panel \n"));
				
				switch(eo_opt) {
					case 1: //add candidate
						String cName=JOptionPane.showInputDialog("Enter Candidate's Name :\n "
								+ "(Please Avoid spaces instead use '_' underscore when required )\n");
						Random rand = new Random();
						String cid = cName + rand.nextInt(99999);
						
						CandidateBean cb = new CandidateBean(cName,cid);
						CandidateDao.addCandidate(cb);
						Panel.afterSuccess(ub);
						break;
						
					case 2: // participate a candidate into a election
						String compi = JOptionPane.showInputDialog("Enter the participating Election's ID :\n ");
						String parti = JOptionPane.showInputDialog("Enter the candidate's ID :\n ");
						CandidateDao.addParticipants(compi,parti);
						JOptionPane.showMessageDialog(null,"All the best "
								+ parti
								+ " for "
								+ compi
								+ " election");
						Panel.afterSuccess(ub);
						break;
					
					case 3:
						int eo_choice = Integer.parseInt(
								JOptionPane.showInputDialog
								("Select as Follows:\n 1-> Update Candidate \n"
								+ "2-> Delete Candidate from DB \n "));
						if(eo_choice == 1) {
							String update_id = JOptionPane.showInputDialog("Enter the id of that candidate \n");
							String update_name = JOptionPane.showInputDialog("Enter updated name \n");
							
							CandidateBean ucb = new CandidateBean(update_name,update_id);
							CandidateDao.updateCandidate(ucb);
						}
						if(eo_choice == 2) {
							String del_id = JOptionPane.showInputDialog("Enter the id of that candidate \n");
							CandidateDao.deleteCandidate(del_id);
						}
						Panel.afterSuccess(ub);
						break;
					
					case 4:
						return;
					
					default:
						break;
				}
				
				
				break;
			case 3:	//admin
				int admin_opt = Integer.parseInt(
						JOptionPane.showInputDialog
						("Select as Follows:\n 1-> Create Election \n"
						+ " 2-> Update Elections \n 3 -> Delete a Election \n "
						+ "4 -> Publish Result of an Election \n 5-> Start/Stop Election\n"
						+ "6 -> View All Elections\n"
						+ "7-> Exit Panel\n"));
				switch(admin_opt) {
					case 1: // create election
						String eName=JOptionPane.showInputDialog("Enter Election's Name :\n "
								+ "(Please Avoid spaces instead use '_' underscore when required )\n");
						Random rand = new Random();
						String eid = "" + rand.nextInt(99999);
						int status = Integer.parseInt(
								JOptionPane.showInputDialog
								("Set as Follows:\n 1-> Live Election \n"
								+ " 2-> Static Election \n "));
						
						ElectionBean eb = new ElectionBean(eid,eName,status);
						ElectionDao.createElection(eb);
						Panel.afterSuccess(ub);
						break;
					
					case 2: //update election
						String update_id = JOptionPane.showInputDialog("Enter Election's ID :\n ");
						String new_name = JOptionPane.showInputDialog("Enter Updated Election's Name :\n ");
						int update_status = Integer.parseInt(
								JOptionPane.showInputDialog("Enter Updated Election's Status :\n "));
						
						ElectionBean ueb = new ElectionBean(update_id,new_name,update_status);
						ElectionDao.updateElection(ueb);
						Panel.afterSuccess(ub);
						break;
					
					case 3: //delete
						String delete_id = JOptionPane.showInputDialog("Enter Election's ID :\n ");
						ElectionDao.deleteElection(delete_id);
						Panel.afterSuccess(ub);
						break;
					
					case 4: // publish result
						String result_id = JOptionPane.showInputDialog("Enter Election's ID :\n ");
						ElectionDao.Result(result_id);
						Panel.afterSuccess(ub);
						break;
					
					case 5: // start / stop
						String status_update_id = JOptionPane.showInputDialog("Enter Election's ID :\n ");
						ElectionDao.statusUpdate(status_update_id);
						Panel.afterSuccess(ub);
						break;
					case 6: //viewAll
						AAEList();
						Panel.afterSuccess(ub);						
						break;
					case 7://exit
						return;
					default:
						break;
						
				}
				break;
			default:
				break;
		}
	}
	

}

package Request;

import java.util.Scanner;

import test.Project;
import test.Supervisor;
import databaseProject.DatabaseProjectAccessor;
import databaseProject.ProjectStatusUpdator;
import databaseRequest.RequestPrinter;
import databaseUser.databaseUserAccessor;

/**
 * Transfer project to another supervisor
 * @author A34_3
 *
 */

public class RequestTransfer extends Request{
	
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Constructor
	 * @param projectID index of project in local array
	 * @param supervisorID ID of replacement supervisor
	 */
	public RequestTransfer(int projectID, String supervisorID) {
		setpending(true);
		settype(3);
		setreplacementID(supervisorID);
		setprojectID(projectID);
		System.out.println("Transfer request sent");
	}
	
	/**
	 * Second constructor
	 */
	public RequestTransfer() {
	}
	@Override
	public void settleRequest(boolean approval) {

		//added to check if replacement supervisor has capacity to take 1 more proj
		Supervisor replacement = (Supervisor)databaseUserAccessor.getUser(getreplacementID());

		if(replacement.getNumProject() >= 2){
			RequestPrinter.alertMessage();
			int choice = sc.nextInt();
			if(choice==0) {setdblapproval(false);}
			else {setdblapproval(true);}
		}

		this.setpending(false);
		this.setapproval(approval);
		if(approval == true && getdblapproval() == true) { //changed here to include dblapproval
			Project p = DatabaseProjectAccessor.getProject(getprojectID());
			p.setSupervisor(getreplacementID());
			DatabaseProjectAccessor.updateProjectInDatabase(p);
			//Student s = (Student)databaseUserAccessor.getUser(senderID);
			//s.setSupervisor(this.replacementID);
			Supervisor original = (Supervisor)databaseUserAccessor.getUser(getsenderID());
			//Supervisor replacement = (Supervisor)databaseUserAccessor.getUser(replacementID);
			original.removeProjectFromArray(getprojectID());
			original.decrementNumProject();
			ProjectStatusUpdator.setAllProjectsAvailable(original.getUserID());
			replacement.incrementNumProject();
			replacement.addProjectToArray(getprojectID());
			if(replacement.getNumProject()>=2) {
				ProjectStatusUpdator.setAllProjectsUnavailable(replacement.getUserID());
			}
		}
	}
}
package sc2002_assignment;
import java.util.ArrayList;
import java.util.Scanner;

import databaseProject.DatabaseProjectAccessor;
import request.*;

public class Student extends User {
	private Project p;
	private Supervisor s;
	private FYPcoordinator FYPcoor;
	private boolean deregistered = false;
	
	private ArrayList<Integer> incomingRequest = new ArrayList<Integer>();
	private ArrayList<Integer> outgoingRequest = new ArrayList<Integer>();
	
	Scanner sc = new Scanner(System.in);
	
	Student(String name, String email, String ID) {
		super(name, email, ID); 
	}
	
	public void setProject(Project p) {
		this.p = p;
	}
	
	public void setFYPcoor(FYPcoordinator FYPcoor) {
		this.FYPcoor = FYPcoor;
	}
	
	public void viewAllProject() {
		DatabaseProjectAccessor.viewAvailableProject(); //implement later w method chain
	}
	
	
	public void viewProject() {		
		System.out.println("Your project title is: " + p.getTitle());
	}
	
	public void changeTitle() {
		System.out.println("Enter new title");
		String newTitle = sc.nextLine();
		Request r = new RequestChangeTitle(newTitle);
		r.senderID = this.getUserID();
		r.receiverID = s.getUserID();
		int index = DatabaseRequestAccessor.addRequest(r);
		r.requestIndex = index;
		this.outgoingRequest.add(index);
		s.addToIncomingRequest(index);
	}
	
	public void allocate() {
		if(!deregistered) {
			System.out.println("Enter projectID to be allocated");
			int projectID = sc.nextInt();
			Request r = new RequestAllocate(projectID);
			r.senderID = this.getUserID();
			r.receiverID = DatabaseProjectAccessor.getProject(projectID).getSupervisor().getUserID();
			int index = DatabaseRequestAccessor.addRequest(r);
			Supervisor s = DatabaseProjectAccessor.getProject(projectID).getSupervisor();
			this.incomingRequest.add(index);
			s.addToIncomingRequest(index);
		}
		else {
			System.out.println("Since you have previously deregistered a project, you are not allowed to register for another one.");
		}
		
	}
	
	public void deregister() {
		System.out.println("Deregistering project request sent");
		Request r = new RequestDeregister();
		r.senderID = this.getUserID();
		r.receiverID = FYPcoor.getUserID();
		int index = DatabaseRequestAccessor.addRequest(r);
		outgoingRequest.add(index);
		FYPcoor.addToIncomingRequest(index);
		deregistered = true;

	}
	
}

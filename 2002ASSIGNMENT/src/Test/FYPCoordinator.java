package Test;

import java.util.ArrayList;

import java.util.Scanner;

import Request.Request;
import databaseProject.DatabaseProjectAccessor;
import databaseProject.ProjectPrinter;
import Request.*;
import databaseRequest.DatabaseRequestAccessor;
import databaseRequest.RequestPrinter;

public class FYPCoordinator extends Supervisor{

	public FYPCoordinator(String name, String email, String ID) {
		super(name,email,ID);		
	}
	
	private generateProjectDetails() {
		int valid=0;
			do{
				System.out.println("Enter /1 to generate based on status");
				System.out.println("Enter /2 to generate based on supervisor id");
				choice = sc.nextLine();
				if(choice.equals("/1") || choice.equals("/2")){
					valid=1;
				}
			}while(valid!=1);
			if(choice.equals("/1")){
				valid=0;
				do{
					System.out.println("Enter status of project:");
					System.out.println("Enter /1 for available projects");
					System.out.println("Enter /2 for reserved projects");
					System.out.println("Enter /3 for taken projects");
					choice = sc.nextLine();
					if(choice.equals("/1")){
						ProjectPrinter.printAllBasedOnStatus(0);
						valid=1;
					}
					else if(choice.equals("/2")){
						ProjectPrinter.printAllBasedOnStatus(1);
						valid=1;
					}
					else if(choice.equals("/3")){
						ProjectPrinter.printAllBasedOnStatus(2);
						valid=1;
					}

				}while(valid!=1);

			}
			else if(choice.equals("/2")){
				// TODO not implemented yet
			}
	}
	
	
	public void displayOptions() {
		String choice = "dummy";
		
		while (!choice.equals("/0)) {
			System.out.println("/0: Log out");
			System.out.println("/1: Change password");
			System.out.println("/2: View your request history");
			System.out.print("/3: View pending requests");
			if(incomingRequest.size() > super.getSettledRequests()) System.out.print(" NEW!");
			System.out.println();
			System.out.println("/4: View all requests in system");
			System.out.println("/5: View your projects");
			System.out.println("/6: View all projects in system");
			System.out.println("/7: Settle requests");
			System.out.println("/8: Generate project details");

			choice = sc.nextLine();
			if(choice.equals("/1")) {setPassword();}
			else if(choice.equals("/2")) {super.viewAllRequests();}
			else if(choice.equals("/3")) {super.viewPendingRequests();}
			else if(choice.equals("/4")) {
				//EVERYTHING IN DATABASE
				for(int i=0; i<DatabaseRequestAccessor.getSize(); i++) {
					RequestPrinter.printAllRequest(i);
				}
			}
			else if(choice.equals("/5")) {super.viewAllProjects();}
			else if(choice.equals("/6")) {
				for(int i=0; i<DatabaseProjectAccessor.getSize(); i++) {
					ProjectPrinter.printAllProject(i);
				}
			}
			else if(choice.equals("/7")) {super.settleRequests();}
			else if(choice.equals("/8")) {generateProjectDetails();}
				
		}
	}
}
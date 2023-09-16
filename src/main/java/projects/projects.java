package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DBException;
import projects.service.ProjectService;

public class Projects {

	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();

	//@formatter:off
	private List<String> operations = List.of(
				"1) Add a Project",
				"2) List Projects",
				"3) Select a Project",
				"4) Update Project Details",
				"5) Delete a Project"
			);
	//@formatter:on
	
	private Object curProject;

	public static void main(String[] args) {

		new Projects().processUserSelections();

	}

	private void processUserSelections() {
		boolean done = false;

		while (!done) {
			try {
				int selection = getUserSelection();

				switch (selection) {

				case -1:
					done = exitMenu();
					break;

				case 1:
					createProject();
					break;
					
				case 2:
					listProjects();
					break;
				
				case 3:
					selectProject();
					break;
					
				case 4:
					updateProjectDetails();
					break;
					
				case 5:
					deleteProject();
					break;

				default:
					System.out.println("\n" + selection + " is not valid. Try again");
					break;

				}
			} catch (Exception e) {
				System.out.println("\nError: " + e.toString() + " Try again.");
			}
		}

	}
	private void deleteProject() {
		listProjects();
		Integer projectID = getIntInput("Enter the ID of the project to delete");
		if(Objects.nonNull(projectID)) {
			projectService.deleteProject(projectID);
			System.out.println(projectID + " has been deleted.");
			
			if(Objects.nonNull(curProject) && ((Project) curProject).getProjectId().equals(projectID)) {
				curProject = null;
			
		}
	}
		
	}

	//this will return the user back to the menu if they try to update a project before selecting one
	private void updateProjectDetails() {
		if (Objects.isNull(curProject)) {
			System.out.println("\nPlease select a project.");
			return;
		}
		//Gathering user input for each field
		String projectName = getStringInput ("Enter the project name. Currently: " + ((Project) curProject).getProjectName());
		BigDecimal estimatedHours = getDecimalInput ("Enter the new estimated hours. Currently: " + ((Project) curProject).getEstimatedHours());
		BigDecimal actualHours = getDecimalInput ("Enter the new actual hours. Currently: " + ((Project) curProject).getActualHours());
		Integer difficulty = getIntInput("Enter new difficulty. Currently: " + ((Project) curProject).getDifficulty());
		String notes = getStringInput ("Enter new notes. Currently: " + ((Project)curProject).getNotes());
		
		Project project = new Project();
		
		project.setProjectId(((Project) curProject).getProjectId());
		
		project.setProjectName(Objects.isNull(projectName)
				? ((Project) curProject).getProjectName() : projectName);
		
		project.setEstimatedHours(Objects.isNull(estimatedHours)
				? ((Project) curProject).getEstimatedHours() : estimatedHours);
		
		project.setActualHours(Objects.isNull(actualHours)
				? ((Project) curProject).getActualHours() : actualHours);
		
		project.setDifficulty(Objects.isNull(difficulty)
				? ((Project) curProject).getDifficulty() : difficulty);
		
		project.setNotes(Objects.isNull(notes)
				? ((Project) curProject).getNotes() : notes);
		
		projectService.modifyProjectDetails(project);
		curProject = projectService.fetchProjectById(((Project) curProject).getProjectId());
		
		
	}

	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("enter a project ID to select a project");
		
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);
		
		if (Objects.isNull(curProject)){
			System.out.println("\nInvalid project ID selected.");
		}
		

		
	}

	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		System.out.println("\nProjects:");
		
		projects.forEach(project -> System.out.println("  " + project.getProjectId()
														+ ": " + project.getProjectName()));
	}

	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");

		Project project = new Project();

		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);

	}

	private BigDecimal getDecimalInput(String prompt) {

		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		} catch (NumberFormatException e) {
			throw new DBException(input + " is not a valid decimal number.");
		}
	}

	private boolean exitMenu() {
		System.out.println("\nExiting the Menu. See ya!");
		return true;
	}

	private int getUserSelection() {
		printOperations();

		Integer input = getIntInput("Enter a menu selection");

		return Objects.isNull(input) ? -1 : input;
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new DBException(input + " is not a valid number.");
		}
	}

	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
		String input = scanner.nextLine();

		return input.isBlank() ? null : input.trim();
	}

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");

		operations.forEach(input -> System.out.println("   " + input));
		
		if (Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}

}

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
				"3) Select a Project"
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

				default:
					System.out.println("\n" + selection + " is not valid. Try again");
					break;

				}
			} catch (Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}

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
		System.out.println("nProjects:");
		
		projects.forEach(project -> System.out.println("  " + project.getProjectId()
														+ ": " + project.getProjectName()));
	}

	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficult (1-5");
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

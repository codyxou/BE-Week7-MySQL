DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS project_category;

CREATE TABLE project_category(
	project_id INT NOT NULL,
	category_id INT NOT NULL,
	FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE category(
	category_id INT AUTO_INCREMENT NOT NULL,
	category_name VARCHAR(128) NOT NULL,
	PRIMARY KEY (category_id)
);

CREATE TABLE step(
	step_id INT AUTO_INCREMENT NOT NULL,
	project_id INT NOT NULL,
	step_text TEXT NOT NULL,
	step_order INT NOT NULL,
	PRIMARY KEY (step_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id)ON DELETE CASCADE
);

CREATE TABLE material(
	material_id INT AUTO_INCREMENT NOT NULL,
	project_id INT NOT NULL,
	material_name VARCHAR(128),
	num_required INT,
	cost DECIMAL(7,2),
	PRIMARY KEY (material_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE project(
	project_id INT AUTO_INCREMENT NOT NULL,
	project_name VARCHAR(128) NOT NULL,
	estimated_hours DECIMAL (7,2),
	actual_hours DECIMAL (7,2),
	difficulty INT,
	notes TEXT,
	PRIMARY KEY (project_id) 
	
);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (3, "TV Bracket", 1, 99.99);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (3, "6-inch Screws", 8, 15.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (3, "Stud Finder", 1, 45.00);
INSERT INTO step (project_id, step_text, step_order) VALUES (3, "Use the stud finder to detect studs, use a pencil to mark location", 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (3, "Align bracket to marked studs", 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (3, "take 4 screws to each side of the bracket and drill into stud", 3);
INSERT INTO step (project_id, step_text, step_order) VALUES (3, "Mount the other bracket to the TV, and carefully mount to the wall bracket", 4);
INSERT INTO category(category_id, category_name) VALUES (3, "Home Entertainment");
INSERT INTO category(category_id, category_name) VALUES (4, "Repair");
INSERT INTO project_category (project_id, category_id) VALUES(3,3);
INSERT INTO project_category (project_id, category_id) VALUES(3,4);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (4, "4 foot wood plank", 7, 75.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (4, "6 foot wood plank", 2, 75.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (4, "6-inch Screws", 12, 27.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (4, "Drill", 1, 85.00);
INSERT INTO step (project_id, step_text, step_order) VALUES (4, "take 2 4-foot wood planks and and your 6-foot wood planks, create a rectangle", 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (4, "use screws to secure the shape in place", 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (4, "take the rest of the screws and space them inside to create holders for the shelves", 3);
INSERT INTO step (project_id, step_text, step_order) VALUES (4, "Align the smaller planks on top of the screw holders to create the shelves", 4);
INSERT INTO category(category_id, category_name) VALUES (5, "Furniture");
INSERT INTO category(category_id, category_name) VALUES (6, "Construction");
INSERT INTO project_category (project_id, category_id) VALUES(4,5);
INSERT INTO project_category (project_id, category_id) VALUES(4,6);
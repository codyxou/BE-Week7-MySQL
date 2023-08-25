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
	category_id INT NOT NULL,
	category_name VARCHAR(128) NOT NULL,
	PRIMARY KEY (category_id) AUTO_INCREMENT
);

CREATE TABLE step(
	step_id INT NOT NULL,
	project_id INT NOT NULL,
	step_text TEXT NOT NULL,
	step_order INT NOT NULL,
	PRIMARY KEY (step_id) AUTO_INCREMENT,
	FOREIGN KEY (project_id) REFERENCES project (project_id)ON DELETE CASCADE
);

CREATE TABLE material(
	material_id INT NOT NULL,
	project_id INT NOT NULL,
	material_name VARCHAR(128),
	num_required INT,
	cost DECIMAL(7,2),
	PRIMARY KEY (material_id) AUTO_INCREMENT,
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE project(
	project_id INT NOT NULL,
	project_name VARCHAR(128) NOT NULL,
	estimated_hours DECIMAL (7,2),
	actual_hours DECIMAL (7,2),
	difficulty INT,
	notes TEXT,
	PRIMARY KEY (project_id) AUTO_INCREMENT
	
);
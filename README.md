# _WildLife_tracker Program_

#### By Garrett Boggs

## Description

_A WildLife tracker program created with spark framework, java, and sql databases_

## Setup/Installation Requirements

* In PSQL:
* CREATE DATABASE wildlife_tracker;
* CREATE TABLE animals (id serial PRIMARY KEY, name varchar, type varchar, health varchar, age varchar);
* CREATE TABLE sightings (id serial PRIMARY KEY, int animalid, ranger_name varchar, location varchar, sight_time timestamp);
* CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;

* _Copy the repository from GitHub_
* _Open in code editor of your choice_
* _Make sure you have gradle and junit installed!_
* _Gradle run and open at [http://localhost:4567](http://localhost:4567)_

## GitHub link

https://github.com/GarrettBoggs/wildlife_tracker

## Licensing

* This project is licensed under the terms of the MIT license.

## Specs

  **Animals can be created**

  * Example input: New Animal: "Zebra"
  * Example output: Animal: Zebra

  **EndangeredAnimals can be created**

  * Example input: New EndangeredAnimal: "Hippo" Health: healthy Age: young
  * Example output: EndangeredAnimal: "Hippo" Health: healthy Age: young

  **Sightings can be created**

  * Example input: New Sighting: Location: Lake Ranger: Steve
  * Example output: Sighting -- Location: Lake Ranger: Steve Time: 2016-09-30 15:06:21.711323

  **Animals, EndangeredAnimals, and Sightings can be deleted**

  * Example input: User clicks delete button
  * Example output: The object is deleted

  **Animals, EndangeredAnimals, and Sightings can be updated**

  * Example input: User fills out necessary info and clicks update button
  * Example output: Information is updated

  **One Animal or EndangeredAnimal can have multiple sightings**

  * Example input: New Animal: "Zebra"
  * Example output: Animal: Zebra
  * Example input: Select Zebra
  * Example output: shown all sightings of the Zebra

Copyright (c) 2016 **Garrett Boggs**

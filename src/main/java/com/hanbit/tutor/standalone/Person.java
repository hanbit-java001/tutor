package com.hanbit.tutor.standalone;

public class Person {

	private int age;
	private String name;
	private Location location;

	public Person() {
		age = 1;
		location = new Location(Location.HOSPITAL, "서울 마포구");
	}

	public void liveYear() {
		this.age++;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	private void go(Location location) {
		this.location = location;
	}

	public void goHome() {
		go(new Location(Location.HOME, "서울 강서구"));
	}

	public void goSchool() {
		go(new Location(Location.SCHOOL, "서울 은평구"));
	}

	public String toString() {
		String personString = "나이: " + getAge() + " 위치: " + getLocation();

		return personString;
	}

}

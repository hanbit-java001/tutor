package com.hanbit.tutor.standalone;

public class Person {

	public static final String HOSPITAL = "병원";
	public static final String HOME = "집";
	public static final String SCHOOL = "학교";

	private int age;
	private String name;
	private String location;

	public Person() {
		age = 1;
		location = HOSPITAL;
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

	public String getLocation() {
		return location;
	}

	private void go(String location) {
		this.location = location;
	}

	public void goHome() {
		go(HOME);
	}

	public void goSchool() {
		go(SCHOOL);
	}

}

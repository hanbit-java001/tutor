package com.hanbit.tutor.standalone;

public class Location {

	public static final String HOSPITAL = "병원";
	public static final String HOME = "집";
	public static final String SCHOOL = "학교";

	private String name;
	private String address;

	public Location(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String toString() {
		return name;
	}

}

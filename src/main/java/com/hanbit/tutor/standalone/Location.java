package com.hanbit.tutor.standalone;

import static org.hamcrest.CoreMatchers.instanceOf;

public class Location {

	public static final String HOSPITAL = "병원";
	public static final String HOME = "집";
	public static final String SCHOOL = "학교";

	public static Location LOC_HOME = new Location(HOME, "서울 강서구");
	public static Location LOC_HOSPITAL = new Location(HOSPITAL, "서울 마포구");
	public static Location LOC_SCHOOL = new School("서울 은평구");

	private String name;
	private String address;

	private boolean opened;
	private boolean toilet;

	private int rooms;

	public Location(String name, String address) {
		this.name = name;
		this.address = address;

		Location school = new School("서울");

		if (school instanceof School) {
			School middleSchool = (School) school;
		}
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

	public int getRooms() {
		return rooms;
	}

	public boolean isOpened() {
		return opened;
	}

	public boolean hasToilet() {
		return toilet;
	}

}

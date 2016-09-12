package com.hanbit.tutor.standalone;

public class Life {

	public static void main(String[] args) {
		Person someone = new Person();
		Person anotherone = new Person();

		for (int year = 0;year < 100; year++) {
			if (Person.HOSPITAL.equals(someone.getLocation())
					&& someone.getAge() == 1) {
				someone.goHome();
			}

			String personString = "나이: " + someone.getAge()
				+ " 위치: " + someone.getLocation();

			System.out.println(personString);

			someone.liveYear();
		}
	}

}

package com.hanbit.tutor.standalone;

public class Life {

	public static void main(String[] args) {
		Person someone = new Person();

		for (int year = 0;year < 100; year++) {
			if (Location.HOSPITAL.equals( someone.getLocation().getName() )
					&& someone.getAge() == 1) {
				someone.goHome();
			}

			System.out.println(someone);

			someone.liveYear();
		}
	}

}

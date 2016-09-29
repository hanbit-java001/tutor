package com.hanbit.tutor.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hanbit.tutor.core.service.SecurityService;
import com.hanbit.tutor.core.vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/applicationContext-core.xml",
		"classpath:spring/applicationContext-dao.xml"
})
public class SecurityServiceTest {

	@Autowired
	private SecurityService securityService;

	@BeforeClass
	public static void setUp() {
		System.out.println("before");
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("after");
	}

	@Test
	public void encryptPasswordTest() {
		String rawPassword = "abcd";
		String encryptedPassword = securityService.encryptPassword(rawPassword);

		Assert.assertNotNull(encryptedPassword);
	}

	@Test
	public void matchPasswordTest() {
		String rawPassword = "abcd";
		String encryptedPassword = securityService.encryptPassword(rawPassword);

		System.out.println(encryptedPassword);

		boolean matched = securityService.matchPassword(rawPassword, encryptedPassword);

		Assert.assertTrue(matched);
	}

	@Test(expected=RuntimeException.class)
	public void getValidMemberTest() {
		String email = "aaaa@naver.com";
		String password = "abcd";

		MemberVO member = securityService.getValidMember(email, password);

		Assert.assertEquals(email, member.getEmail());
	}

}

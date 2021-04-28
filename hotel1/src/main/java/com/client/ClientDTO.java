package com.client;

public class ClientDTO {
	private int clientNum;
	private String firstName;
	private String lastName;
	private String email;
	private String region;
	private String tel;
	private String creditCorp;
	private String creditNum;
	private int creditYear;
	private int creditMonth;
	
	
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCreditCorp() {
		return creditCorp;
	}
	public void setCreditCorp(String creditCorp) {
		this.creditCorp = creditCorp;
	}
	public String getCreditNum() {
		return creditNum;
	}
	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}
	public int getCreditYear() {
		return creditYear;
	}
	public void setCreditYear(int creditYear) {
		this.creditYear = creditYear;
	}
	public int getCreditMonth() {
		return creditMonth;
	}
	public void setCreditMonth(int creditMonth) {
		this.creditMonth = creditMonth;
	}
	public int getClientNum() {
		return clientNum;
	}
	public void setClientNum(int clientNum) {
		this.clientNum = clientNum;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

}

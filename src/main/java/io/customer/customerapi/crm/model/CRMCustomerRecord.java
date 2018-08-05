package io.customer.customerapi.crm.model;

public class CRMCustomerRecord {

	private String customerFirstName;
	private String customerLastName;
	private String birthDate;
	private String emailAddress;
	private CRMAddressRecord homeAddress;
	private CRMAddressRecord officeAddress;

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public CRMAddressRecord getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(CRMAddressRecord homeAddress) {
		this.homeAddress = homeAddress;
	}

	public CRMAddressRecord getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(CRMAddressRecord officeAddress) {
		this.officeAddress = officeAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((customerFirstName == null) ? 0 : customerFirstName.hashCode());
		result = prime * result + ((customerLastName == null) ? 0 : customerLastName.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((homeAddress == null) ? 0 : homeAddress.hashCode());
		result = prime * result + ((officeAddress == null) ? 0 : officeAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CRMCustomerRecord other = (CRMCustomerRecord) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (customerFirstName == null) {
			if (other.customerFirstName != null)
				return false;
		} else if (!customerFirstName.equals(other.customerFirstName))
			return false;
		if (customerLastName == null) {
			if (other.customerLastName != null)
				return false;
		} else if (!customerLastName.equals(other.customerLastName))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (homeAddress == null) {
			if (other.homeAddress != null)
				return false;
		} else if (!homeAddress.equals(other.homeAddress))
			return false;
		if (officeAddress == null) {
			if (other.officeAddress != null)
				return false;
		} else if (!officeAddress.equals(other.officeAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CRMCustomerRecord [customerFirstName=" + customerFirstName + ", customerLastName=" + customerLastName
				+ ", birthDate=" + birthDate + ", emailAddress=" + emailAddress + ", homeAddress=" + homeAddress
				+ ", officeAddress=" + officeAddress + "]";
	}

}

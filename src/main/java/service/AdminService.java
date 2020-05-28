package service;

import pojo.AdminPOJO;

public interface AdminService {

	void menu(AdminPOJO refAdmin);
	void viewCustomerList();
	void setAccountStatus();
	void setCreditLimit();
	void setCardStatus();
}

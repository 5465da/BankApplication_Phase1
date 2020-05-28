package controller;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import dao.AdminDAOImpl;
import dao.CustomerDAOImpl;

import dao.EmailDAOImpl;
import dao.LoginDAOimpl;
import service.AdminServiceImpl;
import service.CredentialServiceImpl;
import service.CustomerServiceImpl;
import service.ServiceMenuImpl;
import pojo.AdminPOJO;
import pojo.CustomerPOJO;

public class Controller {
	final static Logger logger = Logger.getLogger(Controller.class);
	AdminDAOImpl refAdminDAO = new AdminDAOImpl();
	CustomerDAOImpl refCustomerDAO = new CustomerDAOImpl();

	public void toServiceMenu() {
		ServiceMenuImpl refServiceMenu = new ServiceMenuImpl();
		refServiceMenu.menu();
	}

	public void toAdminModule(AdminPOJO refAdmin) {
		AdminServiceImpl refAdminServiceImpl = new AdminServiceImpl();
		refAdminServiceImpl.menu(refAdmin);
	}

	public void toCustomerModule(CustomerPOJO refCustomer) {
		CustomerServiceImpl refCustomerServiceImpl = new CustomerServiceImpl();
		refCustomerServiceImpl.menu(refCustomer);
	}

	public void getList() {
		refAdminDAO.getCustList();
	}

	public void setLimit(int custID, double creditLimit) {
		refAdminDAO.setCreditLimit(custID, creditLimit);
	}

	public String toLogin(String email, String password) {
		LoginDAOimpl refLogin = new LoginDAOimpl();
		return refLogin.login(email, password);
	}

	public boolean verifyEmail(String email) {
		LoginDAOimpl refLogin = new LoginDAOimpl();
		return refLogin.locateEmail(email);
	}
	
	public boolean toActivateAcc(int custID) {
		return refAdminDAO.activateAcc(custID);
	}

	public boolean toDeactivate(int custID) {
		return refAdminDAO.deactivateAcc(custID);
	}
	
	public boolean updatePass(String password,String email) {
		LoginDAOimpl refLoginDAO = new LoginDAOimpl();
		return refLoginDAO.updatePass(password,email);
	}
	
	public boolean tempPass(String tempPassword,String email) {
		LoginDAOimpl refLoginDAO = new LoginDAOimpl();
		return refLoginDAO.updateTempPass(tempPassword, email);
	}
	// ------------- customer stuff

	public boolean registerCustomer(String email, String password, String name) {
		return refCustomerDAO.registerCustomer(email, password,name);
	}

	public void custPayment(int option, CustomerPOJO refCustomer) {
		try {
			refCustomerDAO.custPayment(option, refCustomer);
		} catch (SQLException e) {
			logger.error(e);
		}
	}
	
	public boolean applyCreditCard(CustomerPOJO refCustomer) {
		return refCustomerDAO.custApplyCreditCard(refCustomer);
	}

	public void getCustomerDetails(CustomerPOJO refCustomer) {
		 refCustomerDAO.getCustomerID(refCustomer);
	}
	
	public boolean getLimit (CustomerPOJO refCustomer) {
		return refCustomerDAO.getCustomerLimit(refCustomer);
	}
	
	public void viewDetails(CustomerPOJO refCustomer) {
		refCustomerDAO.getPaymentDetails(refCustomer);
	}

	public boolean getCardStatus(CustomerPOJO refCustomer) {
		return refCustomerDAO.getCustCardStatus(refCustomer);
	}
	
	public void approveCreditCard(int customerID) {
		refAdminDAO.activateCreditCard(customerID);
	}
	
	public void deniedCreditCard(int CustomerID) {
		refAdminDAO.rejectCreditCard(CustomerID);
	}

	public String[] getEmailCredential(String email) {
		 EmailDAOImpl refEmailDAO = new EmailDAOImpl();
		 return refEmailDAO.sendMail(email);
	}
	
	public String tocsHashing(String password) {
		CredentialServiceImpl refCSI = new CredentialServiceImpl();
		return refCSI.hashing(password);
	}
	public String tocsNewPass() {
		CredentialServiceImpl refCSI = new CredentialServiceImpl();
		return refCSI.generateNewPass();
	}
	
}

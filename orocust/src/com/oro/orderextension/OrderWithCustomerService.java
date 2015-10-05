package com.oro.orderextension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JOptionPane;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.floreantpos.main.Application;
import com.floreantpos.model.Customer;
import com.floreantpos.model.OrderType;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.User;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.dao.UserDAO;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.order.DefaultOrderServiceExtension;
import com.floreantpos.ui.views.order.OrderController;
import com.floreantpos.ui.views.order.OrderView;
import com.floreantpos.ui.views.order.RootView;
import com.floreantpos.util.POSUtil;
import com.floreantpos.util.TicketAlreadyExistsException;

@PluginImplementation
public class OrderWithCustomerService extends DefaultOrderServiceExtension {
	public String getName() {
		return "Order+Customer.";
	}

	public String getDescription() {
		return "This extension enables storing customer information with ticekt";
	}

	public void init() {
	}

	public void createNewTicket(OrderType ticketType) throws TicketAlreadyExistsException {
		DeliverySelectionDialog dialog = new DeliverySelectionDialog(Application.getPosWindow(), ticketType);
		dialog.setSize(800, 650);
		dialog.setLocationRelativeTo(Application.getPosWindow());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			Customer customer = dialog.getCustomer();
			Date deliveryDate = dialog.getDeliveryDate();
			String shippingAddress = dialog.getDeliveryAddress();

			Ticket ticket = new Ticket();
			ticket.setPriceIncludesTax(Application.getInstance().isPriceIncludesTax());
			ticket.setType(ticketType);
			ticket.setTerminal(Application.getInstance().getTerminal());
			ticket.setOwner(Application.getCurrentUser());
			ticket.setShift(Application.getInstance().getCurrentShift());

			ticket.setCustomer(customer);

			if (dialog.willCustomerPickup()) {
				ticket.setCustomerWillPickup(Boolean.valueOf(true));
			} else {
				ticket.setDeliveryDate(deliveryDate);
				ticket.setDeliveryAddress(shippingAddress);
				ticket.setExtraDeliveryInfo(dialog.getExtraDeliveryInfo());
				ticket.setCustomerWillPickup(Boolean.valueOf(false));
			}

			Calendar currentTime = Calendar.getInstance();
			ticket.setCreateDate(currentTime.getTime());
			ticket.setCreationHour(Integer.valueOf(currentTime.get(11)));

			OrderView.getInstance().setCurrentTicket(ticket);
			RootView.getInstance().showView("ORDER_VIEW");
		}
	}

	public void setCustomerToTicket(int ticketId) {
	}

	public void setDeliveryDate(int ticketId) {
	}

	public void assignDriver(int ticketId) {
		List<User> drivers = UserDAO.getInstance().findDrivers();

		if ((drivers == null) || (drivers.size() == 0)) {
			POSMessageDialog.showError(Application.getPosWindow(), "No driver found to assign");
			return;
		}

		Ticket ticket = TicketDAO.getInstance().get(Integer.valueOf(ticketId));

		AssignDriverDialog dialog = new AssignDriverDialog(Application.getPosWindow());
		dialog.setData(ticket, drivers);
		dialog.setSize(550, 450);
		dialog.setLocationRelativeTo(Application.getPosWindow());
		dialog.setVisible(true);
	}

	public boolean finishOrder(int ticketId) {
		Ticket ticket = TicketDAO.getInstance().get(Integer.valueOf(ticketId));

		int due = (int) POSUtil.getDouble(ticket.getDueAmount());
		if (due != 0) {
			POSMessageDialog.showError(Application.getPosWindow(), "Ticket is not fully paid");
			return false;
		}

		int option = JOptionPane.showOptionDialog(Application.getPosWindow(), "Ticket# " + ticket.getId() + " will be closed.", "Confirm", 2, 1, null, null, null);

		if (option != 0) {
			return false;
		}

		OrderController.closeOrder(ticket);

		return true;
	}

	public void createCustomerMenu(JMenu menu) {
		menu.add(new CustomerExplorerAction());
	}
}

/*
 * Location:
 * C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension
 * \OrderWithCustomerService.class Java compiler version: 5 (49.0) JD-Core
 * Version: 0.7.1
 */
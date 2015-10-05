package com.oro.orderextension;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Frame;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.floreantpos.model.Customer;
import com.floreantpos.model.OrderType;

public class DeliverySelectionDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527596067434740235L;
	private CustomerSelectionView customerSelectionView;
	private DeliverySelectionView deliverySelectionView;
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private boolean canceled = true;

	private OrderType ticketType;

	public DeliverySelectionDialog() {
		this(null, OrderType.HOME_DELIVERY);
	}

	public DeliverySelectionDialog(Frame owner, OrderType ticketType) {
		super(owner, "Select customer and delivery info", true);
		this.ticketType = ticketType;

		createUI();

		setDefaultCloseOperation(2);
	}

	private void createUI() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.contentPanel = new JPanel();
		getContentPane().add(this.contentPanel, "Center");
		this.cardLayout = new CardLayout(0, 0);
		this.contentPanel.setLayout(this.cardLayout);

		this.customerSelectionView = new CustomerSelectionView(this);
		this.deliverySelectionView = new DeliverySelectionView(this);
		this.customerSelectionView.setNextPage(this.deliverySelectionView);
		this.deliverySelectionView.setPreviousPage(this.customerSelectionView);

		this.contentPanel.add(this.customerSelectionView, this.customerSelectionView.getName());
		this.contentPanel.add(this.deliverySelectionView, this.deliverySelectionView.getName());

		this.cardLayout.show(this.contentPanel, this.customerSelectionView.getName());
	}

	protected void finishWizard() {
		Component[] components = this.contentPanel.getComponents();
		for (Component component : components) {
			if (component.isVisible()) {
				WizardPage wizardPage = (WizardPage) component;

				if (!wizardPage.finish()) {
					return;
				}

				this.canceled = false;
				dispose();
			}
		}
	}

	protected void gotoNextPage() {
		Component[] components = this.contentPanel.getComponents();
		for (Component component : components) {
			if (component.isVisible()) {
				WizardPage wizardPage = (WizardPage) component;

				if (!wizardPage.finish()) {
					return;
				}

				WizardPage nextPage = wizardPage.getNextPage();

				if (wizardPage.canGoNext()) {
					Customer customer = this.customerSelectionView.getCustomer();

					this.deliverySelectionView.setRecipientName(customer.getName());
					this.deliverySelectionView.setDeliveryAddress(customer.getAddress());
					this.cardLayout.show(this.contentPanel, nextPage.getName());
				}

				return;
			}
		}
	}

	public boolean isCanceled() {
		return this.canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Customer getCustomer() {
		return this.customerSelectionView.getCustomer();
	}

	public Date getDeliveryDate() {
		return this.deliverySelectionView.getDeliveryDate();
	}

	public String getDeliveryAddress() {
		return this.deliverySelectionView.getShippingAddress();
	}

	public String getExtraDeliveryInfo() {
		return this.deliverySelectionView.getExtraDeliveryInfo();
	}

	public boolean willCustomerPickup() {
		return this.deliverySelectionView.willCustomerPickup();
	}

	public OrderType getTicketType() {
		return this.ticketType;
	}

	public void setTicketType(OrderType ticketType) {
		this.ticketType = ticketType;
	}
}

/*
 * Location:
 * C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension
 * \DeliverySelectionDialog.class Java compiler version: 5 (49.0) JD-Core
 * Version: 0.7.1
 */
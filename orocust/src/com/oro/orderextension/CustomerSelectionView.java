package com.oro.orderextension;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.customer.CustomerListTableModel;
import com.floreantpos.customer.CustomerTable;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.swing.POSTextField;
import com.floreantpos.swing.PosSmallButton;
import com.floreantpos.swing.QwertyKeyPad;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.forms.CustomerForm;

public class CustomerSelectionView extends JPanel implements WizardPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6196749083227247824L;
	private WizardPage nextPage;
	private PosSmallButton btnCreateNewCustomer;
	private CustomerTable customerTable;
	private POSTextField tfPhone;
	private POSTextField tfLoyaltyNo;
	private POSTextField tfName;
	private PosSmallButton btnInfo;
	protected Customer selectedCustomer;
	private DeliverySelectionDialog deliverySelectionDialog;

	public CustomerSelectionView() {
		createUI();
	}

	public CustomerSelectionView(DeliverySelectionDialog deliverySelectionDialog) {
		this.deliverySelectionDialog = deliverySelectionDialog;

		createUI();
	}

	private void createUI() {
		setPreferredSize(new Dimension(690, 553));
		setLayout(new MigLayout("", "[549px,grow]", "[grow][][shrink 0,fill][grow][grow]"));

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "OroCust 0.7", 4, 2, null, null));
		add(panel_4, "cell 0 0,grow");
		panel_4.setLayout(new MigLayout("", "[grow][][][]", "[grow][][][]"));

		JLabel lblNewLabel = new JLabel("");
		panel_4.add(lblNewLabel, "cell 0 0 1 3,grow");

		JLabel lblByPhone = new JLabel("Search by Phone");
		panel_4.add(lblByPhone, "cell 1 0");

		this.tfPhone = new POSTextField();
		panel_4.add(this.tfPhone, "cell 2 0");
		this.tfPhone.setColumns(16);

		PosSmallButton psmlbtnSearch = new PosSmallButton();
		panel_4.add(psmlbtnSearch, "cell 3 0 1 3,growy");
		psmlbtnSearch.setFocusable(false);
		psmlbtnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.searchCustomer();
			}

		});
		psmlbtnSearch.setText("SEARCH");

		JLabel lblByName = new JLabel("Or Loyalty #");
		panel_4.add(lblByName, "cell 1 1,alignx trailing");

		this.tfLoyaltyNo = new POSTextField();
		panel_4.add(this.tfLoyaltyNo, "cell 2 1");
		this.tfLoyaltyNo.setColumns(16);

		JLabel lblByEmail = new JLabel("Or Name");
		panel_4.add(lblByEmail, "cell 1 2,alignx trailing");

		this.tfName = new POSTextField();
		panel_4.add(this.tfName, "cell 2 2");
		this.tfName.setColumns(16);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel_4.add(panel_2, "cell 0 3 4 1,growx");
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		panel_2.add(scrollPane, "Center");

		this.customerTable = new CustomerTable();
		this.customerTable.setModel(new CustomerListTableModel());
		this.customerTable.setFocusable(false);
		this.customerTable.setRowHeight(35);
		this.customerTable.getSelectionModel().setSelectionMode(0);
		this.customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				CustomerSelectionView.this.selectedCustomer = CustomerSelectionView.this.customerTable.getSelectedCustomer();
				if (CustomerSelectionView.this.selectedCustomer == null) {

					CustomerSelectionView.this.btnInfo.setEnabled(false);
				}
			}
		});
		scrollPane.setViewportView(this.customerTable);

		JPanel panel = new JPanel();
		panel_2.add(panel, "South");

		this.btnInfo = new PosSmallButton();
		this.btnInfo.setFocusable(false);
		panel.add(this.btnInfo);
		this.btnInfo.setEnabled(false);
		this.btnInfo.setText("DETAIL");

		PosSmallButton btnHistory = new PosSmallButton();
		btnHistory.setEnabled(false);
		btnHistory.setText("HISTORY");
		panel.add(btnHistory);

		this.btnCreateNewCustomer = new PosSmallButton();
		this.btnCreateNewCustomer.setFocusable(false);
		panel.add(this.btnCreateNewCustomer);
		this.btnCreateNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.createNewCustomer();
			}
		});
		this.btnCreateNewCustomer.setText("NEW");

		PosSmallButton btnCancel = new PosSmallButton();
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.deliverySelectionDialog.setCanceled(true);
				CustomerSelectionView.this.deliverySelectionDialog.dispose();
			}
		});
		btnCancel.setText("CANCEL");
		panel.add(btnCancel);

		PosSmallButton btnSelect = new PosSmallButton();
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.deliverySelectionDialog.gotoNextPage();
			}
		});
		btnSelect.setText("SELECT");
		panel.add(btnSelect);

		JPanel panel_3 = new JPanel(new BorderLayout());
		add(panel_3, "cell 0 1,grow, gapright 2px");

		QwertyKeyPad qwertyKeyPad = new QwertyKeyPad();
		panel_3.add(qwertyKeyPad);
		this.tfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.searchCustomer();
			}
		});
		this.tfLoyaltyNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.searchCustomer();
			}
		});
		this.tfPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectionView.this.searchCustomer();
			}
		});
	}

	protected void searchCustomer() {
		String phone = this.tfPhone.getText();
		String name = this.tfName.getText();
		String loyalty = this.tfLoyaltyNo.getText();

		if ((StringUtils.isEmpty(phone)) && (StringUtils.isEmpty(loyalty)) && (StringUtils.isEmpty(name))) {
			List<Customer> list = CustomerDAO.getInstance().findAll();
			this.customerTable.setModel(new CustomerListTableModel(list));
			return;
		}

		List<Customer> list = CustomerDAO.getInstance().findBy(phone, loyalty, name);
		CustomerListTableModel model = new CustomerListTableModel(list);
		model.setPageSize(list.size());
		this.customerTable.setModel(model);
	}

	protected void createNewCustomer() {
		CustomerForm form = new CustomerForm();
		BeanEditorDialog dialog = new BeanEditorDialog(form, BackOfficeWindow.getInstance(), true);
		dialog.open();

		if (!dialog.isCanceled()) {
			this.selectedCustomer = ((Customer) form.getBean());
			this.deliverySelectionDialog.gotoNextPage();
		}
	}

	public Customer getCustomer() {
		return this.selectedCustomer;
	}

	public void setNextPage(WizardPage nextPage) {
		this.nextPage = nextPage;
	}

	public String getName() {
		return "C";
	}

	public boolean canGoNext() {
		return true;
	}

	public boolean canGoBack() {
		return false;
	}

	public WizardPage getPreviousPage() {
		return null;
	}

	public WizardPage getNextPage() {
		return this.nextPage;
	}

	public boolean canFinishWizard() {
		return true;
	}

	public boolean finish() {
		if (this.selectedCustomer == null) {
			POSMessageDialog.showError("Please select a customer.");
			return false;
		}

		return true;
	}
}

/*
 * Location:
 * C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension
 * \CustomerSelectionView.class Java compiler version: 5 (49.0) JD-Core Version:
 * 0.7.1
 */
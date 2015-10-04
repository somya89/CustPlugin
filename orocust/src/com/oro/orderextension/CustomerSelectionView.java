/*     */ package com.oro.orderextension;
/*     */ 
/*     */ import com.floreantpos.customer.CustomerListTableModel;
/*     */ import com.floreantpos.customer.CustomerTable;
/*     */ import com.floreantpos.model.Customer;
/*     */ import com.floreantpos.model.dao.CustomerDAO;
/*     */ import com.floreantpos.swing.POSTextField;
/*     */ import com.floreantpos.swing.PosSmallButton;
/*     */ import com.floreantpos.swing.QwertyKeyPad;
/*     */ import com.floreantpos.ui.dialog.BeanEditorDialog;
/*     */ import com.floreantpos.ui.dialog.POSMessageDialog;
/*     */ import com.floreantpos.ui.forms.CustomerForm;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.List;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import net.miginfocom.swing.MigLayout;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomerSelectionView
/*     */   extends JPanel
/*     */   implements WizardPage
/*     */ {
/*     */   private WizardPage nextPage;
/*     */   private PosSmallButton btnCreateNewCustomer;
/*     */   private CustomerTable customerTable;
/*     */   private POSTextField tfPhone;
/*     */   private POSTextField tfLoyaltyNo;
/*     */   private POSTextField tfName;
/*     */   private PosSmallButton btnInfo;
/*     */   protected Customer selectedCustomer;
/*     */   private DeliverySelectionDialog deliverySelectionDialog;
/*     */   
/*     */   public CustomerSelectionView()
/*     */   {
/*  47 */     createUI();
/*     */   }
/*     */   
/*     */   public CustomerSelectionView(DeliverySelectionDialog deliverySelectionDialog) {
/*  51 */     this.deliverySelectionDialog = deliverySelectionDialog;
/*     */     
/*  53 */     createUI();
/*     */   }
/*     */   
/*     */   private void createUI() {
/*  57 */     setPreferredSize(new Dimension(690, 553));
/*  58 */     setLayout(new MigLayout("", "[549px,grow]", "[grow][][shrink 0,fill][grow][grow]"));
/*     */     
/*  60 */     JPanel panel_4 = new JPanel();
/*  61 */     panel_4.setBorder(new TitledBorder(null, "OroCust 0.7", 4, 2, null, null));
/*  62 */     add(panel_4, "cell 0 0,grow");
/*  63 */     panel_4.setLayout(new MigLayout("", "[grow][][][]", "[grow][][][]"));
/*     */     
/*  65 */     JLabel lblNewLabel = new JLabel("");
/*  66 */     panel_4.add(lblNewLabel, "cell 0 0 1 3,grow");
/*     */     
/*  68 */     JLabel lblByPhone = new JLabel("Search by Phone");
/*  69 */     panel_4.add(lblByPhone, "cell 1 0");
/*     */     
/*  71 */     this.tfPhone = new POSTextField();
/*  72 */     panel_4.add(this.tfPhone, "cell 2 0");
/*  73 */     this.tfPhone.setColumns(16);
/*     */     
/*  75 */     PosSmallButton psmlbtnSearch = new PosSmallButton();
/*  76 */     panel_4.add(psmlbtnSearch, "cell 3 0 1 3,growy");
/*  77 */     psmlbtnSearch.setFocusable(false);
/*  78 */     psmlbtnSearch.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  80 */         CustomerSelectionView.this.searchCustomer();
/*     */       }
/*  82 */     });
/*  83 */     psmlbtnSearch.setText("SEARCH");
/*     */     
/*  85 */     JLabel lblByName = new JLabel("Or Loyalty #");
/*  86 */     panel_4.add(lblByName, "cell 1 1,alignx trailing");
/*     */     
/*  88 */     this.tfLoyaltyNo = new POSTextField();
/*  89 */     panel_4.add(this.tfLoyaltyNo, "cell 2 1");
/*  90 */     this.tfLoyaltyNo.setColumns(16);
/*     */     
/*  92 */     JLabel lblByEmail = new JLabel("Or Name");
/*  93 */     panel_4.add(lblByEmail, "cell 1 2,alignx trailing");
/*     */     
/*  95 */     this.tfName = new POSTextField();
/*  96 */     panel_4.add(this.tfName, "cell 2 2");
/*  97 */     this.tfName.setColumns(16);
/*     */     
/*  99 */     JPanel panel_2 = new JPanel();
/* 100 */     panel_2.setBorder(new EmptyBorder(10, 0, 0, 0));
/* 101 */     panel_4.add(panel_2, "cell 0 3 4 1,growx");
/* 102 */     panel_2.setLayout(new BorderLayout(0, 0));
/*     */     
/* 104 */     JScrollPane scrollPane = new JScrollPane();
/* 105 */     scrollPane.setFocusable(false);
/* 106 */     panel_2.add(scrollPane, "Center");
/*     */     
/* 108 */     this.customerTable = new CustomerTable();
/* 109 */     this.customerTable.setModel(new CustomerListTableModel());
/* 110 */     this.customerTable.setFocusable(false);
/* 111 */     this.customerTable.setRowHeight(35);
/* 112 */     this.customerTable.getSelectionModel().setSelectionMode(0);
/* 113 */     this.customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
/*     */     {
/*     */       public void valueChanged(ListSelectionEvent e)
/*     */       {
/* 117 */         CustomerSelectionView.this.selectedCustomer = CustomerSelectionView.this.customerTable.getSelectedCustomer();
/* 118 */         if (CustomerSelectionView.this.selectedCustomer == null)
/*     */         {
/*     */ 
/*     */ 
/* 122 */           CustomerSelectionView.this.btnInfo.setEnabled(false);
/*     */         }
/*     */       }
/* 125 */     });
/* 126 */     scrollPane.setViewportView(this.customerTable);
/*     */     
/* 128 */     JPanel panel = new JPanel();
/* 129 */     panel_2.add(panel, "South");
/*     */     
/* 131 */     this.btnInfo = new PosSmallButton();
/* 132 */     this.btnInfo.setFocusable(false);
/* 133 */     panel.add(this.btnInfo);
/* 134 */     this.btnInfo.setEnabled(false);
/* 135 */     this.btnInfo.setText("DETAIL");
/*     */     
/* 137 */     PosSmallButton btnHistory = new PosSmallButton();
/* 138 */     btnHistory.setEnabled(false);
/* 139 */     btnHistory.setText("HISTORY");
/* 140 */     panel.add(btnHistory);
/*     */     
/* 142 */     this.btnCreateNewCustomer = new PosSmallButton();
/* 143 */     this.btnCreateNewCustomer.setFocusable(false);
/* 144 */     panel.add(this.btnCreateNewCustomer);
/* 145 */     this.btnCreateNewCustomer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 147 */         CustomerSelectionView.this.createNewCustomer();
/*     */       }
/* 149 */     });
/* 150 */     this.btnCreateNewCustomer.setText("NEW");
/*     */     
/* 152 */     PosSmallButton btnCancel = new PosSmallButton();
/* 153 */     btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 155 */         CustomerSelectionView.this.deliverySelectionDialog.setCanceled(true);
/* 156 */         CustomerSelectionView.this.deliverySelectionDialog.dispose();
/*     */       }
/* 158 */     });
/* 159 */     btnCancel.setText("CANCEL");
/* 160 */     panel.add(btnCancel);
/*     */     
/* 162 */     PosSmallButton btnSelect = new PosSmallButton();
/* 163 */     btnSelect.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 165 */         CustomerSelectionView.this.deliverySelectionDialog.gotoNextPage();
/*     */       }
/* 167 */     });
/* 168 */     btnSelect.setText("SELECT");
/* 169 */     panel.add(btnSelect);
/*     */     
/* 171 */     JPanel panel_3 = new JPanel(new BorderLayout());
/* 172 */     add(panel_3, "cell 0 1,grow, gapright 2px");
/*     */     
/* 174 */     QwertyKeyPad qwertyKeyPad = new QwertyKeyPad();
/* 175 */     panel_3.add(qwertyKeyPad);
/* 176 */     this.tfName.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 178 */         CustomerSelectionView.this.searchCustomer();
/*     */       }
/* 180 */     });
/* 181 */     this.tfLoyaltyNo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 183 */         CustomerSelectionView.this.searchCustomer();
/*     */       }
/* 185 */     });
/* 186 */     this.tfPhone.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 188 */         CustomerSelectionView.this.searchCustomer();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   protected void searchCustomer() {
/* 194 */     String phone = this.tfPhone.getText();
/* 195 */     String name = this.tfName.getText();
/* 196 */     String loyalty = this.tfLoyaltyNo.getText();
/*     */     
/* 198 */     if ((StringUtils.isEmpty(phone)) && (StringUtils.isEmpty(loyalty)) && (StringUtils.isEmpty(name))) {
/* 199 */       List<Customer> list = CustomerDAO.getInstance().findAll();
/* 200 */       this.customerTable.setModel(new CustomerListTableModel(list));
/* 201 */       return;
/*     */     }
/*     */     
/* 204 */     List<Customer> list = CustomerDAO.getInstance().findBy(phone, loyalty, name);
/* 205 */     this.customerTable.setModel(new CustomerListTableModel(list));
/*     */   }
/*     */   
/*     */   protected void createNewCustomer() {
/* 209 */     CustomerForm form = new CustomerForm();
/* 210 */     BeanEditorDialog dialog = new BeanEditorDialog(form);
/* 211 */     dialog.open();
/*     */     
/* 213 */     if (!dialog.isCanceled()) {
/* 214 */       this.selectedCustomer = ((Customer)form.getBean());
/* 215 */       this.deliverySelectionDialog.gotoNextPage();
/*     */     }
/*     */   }
/*     */   
/*     */   public Customer getCustomer()
/*     */   {
/* 221 */     return this.selectedCustomer;
/*     */   }
/*     */   
/*     */   public void setNextPage(WizardPage nextPage) {
/* 225 */     this.nextPage = nextPage;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 230 */     return "C";
/*     */   }
/*     */   
/*     */   public boolean canGoNext()
/*     */   {
/* 235 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canGoBack()
/*     */   {
/* 240 */     return false;
/*     */   }
/*     */   
/*     */   public WizardPage getPreviousPage()
/*     */   {
/* 245 */     return null;
/*     */   }
/*     */   
/*     */   public WizardPage getNextPage()
/*     */   {
/* 250 */     return this.nextPage;
/*     */   }
/*     */   
/*     */   public boolean canFinishWizard()
/*     */   {
/* 255 */     return true;
/*     */   }
/*     */   
/*     */   public boolean finish()
/*     */   {
/* 260 */     if (this.selectedCustomer == null) {
/* 261 */       POSMessageDialog.showError("Please select a customer.");
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\CustomerSelectionView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
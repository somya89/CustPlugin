/*     */ package com.oro.orderextension;
/*     */ 
/*     */ import com.floreantpos.PosException;
/*     */ import com.floreantpos.model.OrderType;
/*     */ import com.floreantpos.swing.PosSmallButton;
/*     */ import com.floreantpos.swing.QwertyKeyPad;
/*     */ import com.floreantpos.swing.TimeSelector;
/*     */ import com.floreantpos.ui.dialog.POSMessageDialog;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import net.miginfocom.swing.MigLayout;
/*     */ import org.jdesktop.swingx.JXDatePicker;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeliverySelectionView
/*     */   extends JPanel
/*     */   implements WizardPage
/*     */ {
/*     */   private WizardPage previousPage;
/*     */   private Date selectedDate;
/*     */   private String shippingAddress;
/*     */   private JXDatePicker datePicker;
/*     */   private JTextArea taDeliveryAddress;
/*     */   private TimeSelector timeSelector;
/*     */   private JTextField tfRecipientName;
/*     */   private JTextArea taExtraInsruction;
/*     */   private PosSmallButton btnCancel;
/*     */   private DeliverySelectionDialog deliverySelectionDialog;
/*     */   private JCheckBox cbCustomerPickup;
/*     */   
/*     */   public DeliverySelectionView()
/*     */   {
/*  45 */     this(null);
/*     */   }
/*     */   
/*     */   public DeliverySelectionView(DeliverySelectionDialog dialog) {
/*  49 */     this.deliverySelectionDialog = dialog;
/*     */     
/*  51 */     createUI();
/*     */   }
/*     */   
/*     */   private void createUI() {
/*  55 */     JPanel topPanel = new JPanel(new MigLayout("fill", "[grow][][grow][grow]", "[][][][grow][grow][shrink 0][]"));
/*  56 */     topPanel.setBorder(new TitledBorder(null, "OroCust 0.7", 4, 2, null, null));
/*     */     
/*  58 */     setLayout(new MigLayout("fill", "[549px,grow]", "[grow,fill][]"));
/*     */     
/*     */ 
/*  61 */     JLabel label = new JLabel("");
/*  62 */     topPanel.add(label, "cell 0 0");
/*     */     
/*  64 */     JLabel lblRecepientName = new JLabel("Recipient Name:");
/*  65 */     topPanel.add(lblRecepientName, "cell 1 0,alignx trailing");
/*     */     
/*  67 */     this.tfRecipientName = new JTextField();
/*  68 */     topPanel.add(this.tfRecipientName, "cell 2 0,growx");
/*  69 */     this.tfRecipientName.setColumns(10);
/*     */     
/*  71 */     JLabel label_1 = new JLabel("");
/*  72 */     topPanel.add(label_1, "cell 3 0");
/*     */     
/*  74 */     this.cbCustomerPickup = new JCheckBox("Customer will pickup");
/*  75 */     this.cbCustomerPickup.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  77 */         boolean enabled = DeliverySelectionView.this.cbCustomerPickup.isSelected();
/*  78 */         DeliverySelectionView.this.setPickupEnable(enabled);
/*     */       }
/*     */       
/*  81 */     });
/*  82 */     JLabel lblDeliveryDate = new JLabel("Delivery Date:");
/*  83 */     topPanel.add(lblDeliveryDate, "cell 1 1,alignx trailing,growy");
/*     */     
/*  85 */     this.datePicker = new JXDatePicker();
/*  86 */     this.datePicker.setDate(new Date());
/*  87 */     topPanel.add(this.datePicker, "flowx,cell 2 1");
/*  88 */     topPanel.add(this.cbCustomerPickup, "cell 2 2");
/*     */     
/*  90 */     this.timeSelector = new TimeSelector();
/*  91 */     topPanel.add(this.timeSelector, "cell 2 1,growy");
/*     */     
/*  93 */     JLabel lblShippingAddress = new JLabel("Delivery Address:");
/*  94 */     topPanel.add(lblShippingAddress, "cell 1 3,alignx trailing,aligny top");
/*     */     
/*  96 */     JScrollPane scrollPane = new JScrollPane();
/*  97 */     scrollPane.setPreferredSize(new Dimension(3, 60));
/*  98 */     topPanel.add(scrollPane, "cell 2 3,grow");
/*     */     
/* 100 */     this.taDeliveryAddress = new JTextArea();
/* 101 */     this.taDeliveryAddress.setRows(4);
/* 102 */     scrollPane.setViewportView(this.taDeliveryAddress);
/*     */     
/* 104 */     JLabel lblExtraInstruction = new JLabel("Extra Instruction:");
/* 105 */     topPanel.add(lblExtraInstruction, "cell 1 4,alignx trailing,aligny top");
/*     */     
/* 107 */     JScrollPane scrollPane_1 = new JScrollPane();
/* 108 */     topPanel.add(scrollPane_1, "cell 2 4,grow");
/*     */     
/* 110 */     this.taExtraInsruction = new JTextArea();
/* 111 */     this.taExtraInsruction.setPreferredSize(new Dimension(0, 40));
/* 112 */     this.taExtraInsruction.setRows(3);
/* 113 */     scrollPane_1.setViewportView(this.taExtraInsruction);
/*     */     
/* 115 */     JPanel panel_1 = new JPanel();
/* 116 */     topPanel.add(panel_1, "cell 1 5 2 1,shrinky 0,grow");
/*     */     
/* 118 */     this.btnCancel = new PosSmallButton();
/* 119 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 121 */         DeliverySelectionView.this.deliverySelectionDialog.setCanceled(true);
/* 122 */         DeliverySelectionView.this.deliverySelectionDialog.dispose();
/*     */       }
/* 124 */     });
/* 125 */     this.btnCancel.setText("CANCEL");
/* 126 */     panel_1.add(this.btnCancel);
/*     */     
/* 128 */     PosSmallButton btnSaveProceed = new PosSmallButton();
/* 129 */     btnSaveProceed.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 131 */         DeliverySelectionView.this.deliverySelectionDialog.finishWizard();
/*     */       }
/* 133 */     });
/* 134 */     btnSaveProceed.setText("SAVE & PROCEED");
/* 135 */     panel_1.add(btnSaveProceed);
/*     */     
/* 137 */     add(topPanel, "cell 0 0,grow");
/*     */     
/* 139 */     JPanel panel = new JPanel(new BorderLayout());
/* 140 */     add(panel, "cell 0 1,grow, gapright 2px");
/*     */     
/* 142 */     QwertyKeyPad qwertyKeyPad = new QwertyKeyPad();
/* 143 */     panel.add(qwertyKeyPad);
/*     */     
/* 145 */     if (this.deliverySelectionDialog.getTicketType().equals(OrderType.PICKUP)) {
/* 146 */       this.cbCustomerPickup.setSelected(true);
/* 147 */       setPickupEnable(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private Date captureDeliveryDate() {
/* 152 */     Date date = this.datePicker.getDate();
/* 153 */     int hour = this.timeSelector.getSelectedHour();
/* 154 */     int min = this.timeSelector.getSelectedMin();
/*     */     
/* 156 */     if ((hour == -1) || (hour < 1) || (hour > 12)) {
/* 157 */       throw new PosException("Please select hour from 1 to 12.");
/*     */     }
/*     */     
/* 160 */     if ((min == -1) || (min < 1) || (min > 59)) {
/* 161 */       throw new PosException("Please select minute value from 1 to 59.");
/*     */     }
/*     */     
/* 164 */     Calendar calendar = Calendar.getInstance();
/* 165 */     calendar.setTime(date);
/* 166 */     calendar.set(10, hour == 12 ? 0 : hour);
/* 167 */     calendar.set(12, min);
/* 168 */     calendar.set(9, this.timeSelector.getAmPm());
/*     */     
/* 170 */     return calendar.getTime();
/*     */   }
/*     */   
/*     */   private String captureShippingAddress() {
/* 174 */     return this.taDeliveryAddress.getText();
/*     */   }
/*     */   
/*     */   public Date getDeliveryDate() {
/* 178 */     return this.selectedDate;
/*     */   }
/*     */   
/*     */   public String getShippingAddress() {
/* 182 */     return this.shippingAddress;
/*     */   }
/*     */   
/*     */   public String getExtraDeliveryInfo() {
/* 186 */     return this.taExtraInsruction.getText();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 191 */     return "D";
/*     */   }
/*     */   
/*     */   public boolean canGoNext()
/*     */   {
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canGoBack()
/*     */   {
/* 201 */     return true;
/*     */   }
/*     */   
/*     */   public void setRecipientName(String name) {
/* 205 */     this.tfRecipientName.setText(name);
/*     */   }
/*     */   
/*     */   public void setDeliveryAddress(String shippingAddress) {
/* 209 */     this.taDeliveryAddress.setText(shippingAddress);
/*     */   }
/*     */   
/*     */   public void setPreviousPage(WizardPage previousPage) {
/* 213 */     this.previousPage = previousPage;
/*     */   }
/*     */   
/*     */   public WizardPage getPreviousPage()
/*     */   {
/* 218 */     return this.previousPage;
/*     */   }
/*     */   
/*     */   public WizardPage getNextPage()
/*     */   {
/* 223 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canFinishWizard()
/*     */   {
/* 228 */     return true;
/*     */   }
/*     */   
/*     */   public boolean willCustomerPickup() {
/* 232 */     return this.cbCustomerPickup.isSelected();
/*     */   }
/*     */   
/*     */   public boolean finish()
/*     */   {
/*     */     try {
/* 238 */       this.selectedDate = captureDeliveryDate();
/* 239 */       this.shippingAddress = captureShippingAddress();
/* 240 */       return true;
/*     */     } catch (PosException e) {
/* 242 */       POSMessageDialog.showError(e.getMessage()); }
/* 243 */     return false;
/*     */   }
/*     */   
/*     */   private void setPickupEnable(boolean enabled)
/*     */   {
/* 248 */     this.taDeliveryAddress.setEditable(!enabled);
/*     */   }
/*     */ }


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\DeliverySelectionView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
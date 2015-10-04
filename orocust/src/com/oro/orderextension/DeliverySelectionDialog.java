/*     */ package com.oro.orderextension;
/*     */ 
/*     */ import com.floreantpos.model.Customer;
/*     */ import com.floreantpos.model.OrderType;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.CardLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.util.Date;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class DeliverySelectionDialog
/*     */   extends JDialog
/*     */ {
/*     */   private CustomerSelectionView customerSelectionView;
/*     */   private DeliverySelectionView deliverySelectionView;
/*     */   private JPanel contentPanel;
/*     */   private CardLayout cardLayout;
/*  21 */   private boolean canceled = true;
/*     */   
/*     */   private OrderType ticketType;
/*     */   
/*     */ 
/*     */   public DeliverySelectionDialog()
/*     */   {
/*  28 */     this(null, OrderType.HOME_DELIVERY);
/*     */   }
/*     */   
/*     */   public DeliverySelectionDialog(Frame owner, OrderType ticketType) {
/*  32 */     super(owner, "Select customer and delivery info", true);
/*  33 */     this.ticketType = ticketType;
/*     */     
/*  35 */     createUI();
/*     */     
/*  37 */     setDefaultCloseOperation(2);
/*     */   }
/*     */   
/*     */   private void createUI() {
/*  41 */     getContentPane().setLayout(new BorderLayout(0, 0));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  47 */     this.contentPanel = new JPanel();
/*  48 */     getContentPane().add(this.contentPanel, "Center");
/*  49 */     this.cardLayout = new CardLayout(0, 0);
/*  50 */     this.contentPanel.setLayout(this.cardLayout);
/*     */     
/*  52 */     this.customerSelectionView = new CustomerSelectionView(this);
/*  53 */     this.deliverySelectionView = new DeliverySelectionView(this);
/*  54 */     this.customerSelectionView.setNextPage(this.deliverySelectionView);
/*  55 */     this.deliverySelectionView.setPreviousPage(this.customerSelectionView);
/*     */     
/*  57 */     this.contentPanel.add(this.customerSelectionView, this.customerSelectionView.getName());
/*  58 */     this.contentPanel.add(this.deliverySelectionView, this.deliverySelectionView.getName());
/*     */     
/*  60 */     this.cardLayout.show(this.contentPanel, this.customerSelectionView.getName());
/*     */   }
/*     */   
/*     */   protected void finishWizard() {
/*  64 */     Component[] components = this.contentPanel.getComponents();
/*  65 */     for (Component component : components) {
/*  66 */       if (component.isVisible()) {
/*  67 */         WizardPage wizardPage = (WizardPage)component;
/*     */         
/*  69 */         if (!wizardPage.finish()) {
/*  70 */           return;
/*     */         }
/*     */         
/*  73 */         this.canceled = false;
/*  74 */         dispose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void gotoNextPage()
/*     */   {
/*  81 */     Component[] components = this.contentPanel.getComponents();
/*  82 */     for (Component component : components) {
/*  83 */       if (component.isVisible()) {
/*  84 */         WizardPage wizardPage = (WizardPage)component;
/*     */         
/*  86 */         if (!wizardPage.finish()) {
/*  87 */           return;
/*     */         }
/*     */         
/*  90 */         WizardPage nextPage = wizardPage.getNextPage();
/*     */         
/*  92 */         if (wizardPage.canGoNext()) {
/*  93 */           Customer customer = this.customerSelectionView.getCustomer();
/*     */           
/*  95 */           this.deliverySelectionView.setRecipientName(customer.getName());
/*  96 */           this.deliverySelectionView.setDeliveryAddress(customer.getAddress());
/*  97 */           this.cardLayout.show(this.contentPanel, nextPage.getName());
/*     */         }
/*     */         
/* 100 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCanceled() {
/* 106 */     return this.canceled;
/*     */   }
/*     */   
/*     */   public void setCanceled(boolean canceled) {
/* 110 */     this.canceled = canceled;
/*     */   }
/*     */   
/*     */   public Customer getCustomer() {
/* 114 */     return this.customerSelectionView.getCustomer();
/*     */   }
/*     */   
/*     */   public Date getDeliveryDate() {
/* 118 */     return this.deliverySelectionView.getDeliveryDate();
/*     */   }
/*     */   
/*     */   public String getDeliveryAddress() {
/* 122 */     return this.deliverySelectionView.getShippingAddress();
/*     */   }
/*     */   
/*     */   public String getExtraDeliveryInfo() {
/* 126 */     return this.deliverySelectionView.getExtraDeliveryInfo();
/*     */   }
/*     */   
/*     */   public boolean willCustomerPickup() {
/* 130 */     return this.deliverySelectionView.willCustomerPickup();
/*     */   }
/*     */   
/*     */   public OrderType getTicketType() {
/* 134 */     return this.ticketType;
/*     */   }
/*     */   
/*     */   public void setTicketType(OrderType ticketType) {
/* 138 */     this.ticketType = ticketType;
/*     */   }
/*     */ }


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\DeliverySelectionDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
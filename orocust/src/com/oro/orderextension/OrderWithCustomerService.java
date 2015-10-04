/*     */ package com.oro.orderextension;
/*     */ 
/*     */ import com.floreantpos.main.Application;
/*     */ import com.floreantpos.model.Customer;
/*     */ import com.floreantpos.model.OrderType;
/*     */ import com.floreantpos.model.Ticket;
/*     */ import com.floreantpos.model.User;
/*     */ import com.floreantpos.model.dao.TicketDAO;
/*     */ import com.floreantpos.model.dao.UserDAO;
/*     */ import com.floreantpos.ui.dialog.POSMessageDialog;
/*     */ import com.floreantpos.ui.views.order.DefaultOrderServiceExtension;
/*     */ import com.floreantpos.ui.views.order.OrderController;
/*     */ import com.floreantpos.ui.views.order.OrderView;
/*     */ import com.floreantpos.ui.views.order.RootView;
/*     */ import com.floreantpos.util.POSUtil;
/*     */ import com.floreantpos.util.TicketAlreadyExistsException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.xeoh.plugins.base.annotations.PluginImplementation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @PluginImplementation
/*     */ public class OrderWithCustomerService
/*     */   extends DefaultOrderServiceExtension
/*     */ {
/*     */   public String getName()
/*     */   {
/*  33 */     return "Order+Customer.";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  37 */     return "This extension enables storing customer information with ticekt";
/*     */   }
/*     */   
/*     */ 
/*     */   public void init() {}
/*     */   
/*     */   public void createNewTicket(OrderType ticketType)
/*     */     throws TicketAlreadyExistsException
/*     */   {
/*  46 */     DeliverySelectionDialog dialog = new DeliverySelectionDialog(Application.getPosWindow(), ticketType);
/*  47 */     dialog.setSize(800, 650);
/*  48 */     dialog.setLocationRelativeTo(Application.getPosWindow());
/*  49 */     dialog.setVisible(true);
/*     */     
/*  51 */     if (!dialog.isCanceled()) {
/*  52 */       Customer customer = dialog.getCustomer();
/*  53 */       Date deliveryDate = dialog.getDeliveryDate();
/*  54 */       String shippingAddress = dialog.getDeliveryAddress();
/*     */       
/*  56 */       Ticket ticket = new Ticket();
/*  57 */       ticket.setPriceIncludesTax(Application.getInstance().isPriceIncludesTax());
/*  58 */       ticket.setType(ticketType);
/*  59 */       ticket.setTerminal(Application.getInstance().getTerminal());
/*  60 */       ticket.setOwner(Application.getCurrentUser());
/*  61 */       ticket.setShift(Application.getInstance().getCurrentShift());
/*     */       
/*  63 */       ticket.setCustomer(customer);
/*     */       
/*  65 */       if (dialog.willCustomerPickup()) {
/*  66 */         ticket.setCustomerWillPickup(Boolean.valueOf(true));
/*     */       }
/*     */       else {
/*  69 */         ticket.setDeliveryDate(deliveryDate);
/*  70 */         ticket.setDeliveryAddress(shippingAddress);
/*  71 */         ticket.setExtraDeliveryInfo(dialog.getExtraDeliveryInfo());
/*  72 */         ticket.setCustomerWillPickup(Boolean.valueOf(false));
/*     */       }
/*     */       
/*  75 */       Calendar currentTime = Calendar.getInstance();
/*  76 */       ticket.setCreateDate(currentTime.getTime());
/*  77 */       ticket.setCreationHour(Integer.valueOf(currentTime.get(11)));
/*     */       
/*  79 */       OrderView.getInstance().setCurrentTicket(ticket);
/*  80 */       RootView.getInstance().showView("ORDER_VIEW");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCustomerToTicket(int ticketId) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDeliveryDate(int ticketId) {}
/*     */   
/*     */ 
/*     */   public void assignDriver(int ticketId)
/*     */   {
/*  95 */     List<User> drivers = UserDAO.getInstance().findDrivers();
/*     */     
/*  97 */     if ((drivers == null) || (drivers.size() == 0)) {
/*  98 */       POSMessageDialog.showError(Application.getPosWindow(), "No driver found to assign");
/*  99 */       return;
/*     */     }
/*     */     
/* 102 */     Ticket ticket = TicketDAO.getInstance().get(Integer.valueOf(ticketId));
/*     */     
/* 104 */     AssignDriverDialog dialog = new AssignDriverDialog(Application.getPosWindow());
/* 105 */     dialog.setData(ticket, drivers);
/* 106 */     dialog.setSize(550, 450);
/* 107 */     dialog.setLocationRelativeTo(Application.getPosWindow());
/* 108 */     dialog.setVisible(true);
/*     */   }
/*     */   
/*     */   public boolean finishOrder(int ticketId)
/*     */   {
/* 113 */     Ticket ticket = TicketDAO.getInstance().get(Integer.valueOf(ticketId));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 120 */     int due = (int)POSUtil.getDouble(ticket.getDueAmount());
/* 121 */     if (due != 0) {
/* 122 */       POSMessageDialog.showError(Application.getPosWindow(), "Ticket is not fully paid");
/* 123 */       return false;
/*     */     }
/*     */     
/* 126 */     int option = JOptionPane.showOptionDialog(Application.getPosWindow(), "Ticket# " + ticket.getId() + " will be closed.", "Confirm", 2, 1, null, null, null);
/*     */     
/*     */ 
/* 129 */     if (option != 0) {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     OrderController.closeOrder(ticket);
/*     */     
/* 135 */     return true;
/*     */   }
/*     */   
/*     */   public void createCustomerMenu(JMenu menu)
/*     */   {
/* 140 */     menu.add(new CustomerExplorerAction());
/*     */   }
/*     */ }


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\OrderWithCustomerService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
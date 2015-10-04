/*    */ package com.oro.orderextension;
/*    */ 
/*    */ import com.floreantpos.bo.ui.BackOfficeWindow;
/*    */ import com.floreantpos.customer.CustomerExplorer;
/*    */ import com.floreantpos.util.POSUtil;
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class CustomerExplorerAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public CustomerExplorerAction()
/*    */   {
/* 16 */     super("Customer");
/*    */   }
/*    */   
/*    */   public CustomerExplorerAction(String name) {
/* 20 */     super(name);
/*    */   }
/*    */   
/*    */   public CustomerExplorerAction(String name, Icon icon) {
/* 24 */     super(name, icon);
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 28 */     BackOfficeWindow backOfficeWindow = POSUtil.getBackOfficeWindow();
/*    */     
/* 30 */     CustomerExplorer explorer = null;
/* 31 */     JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
/* 32 */     int index = tabbedPane.indexOfTab("Customer");
/* 33 */     if (index == -1) {
/* 34 */       explorer = new CustomerExplorer();
/* 35 */       tabbedPane.addTab("Customer", explorer);
/*    */     }
/*    */     else {
/* 38 */       explorer = (CustomerExplorer)tabbedPane.getComponentAt(index);
/*    */     }
/* 40 */     tabbedPane.setSelectedComponent(explorer);
/*    */   }
/*    */ }


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\CustomerExplorerAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package com.oro.orderextension;
/*     */ 
/*     */ import com.floreantpos.model.Ticket;
/*     */ import com.floreantpos.model.User;
/*     */ import com.floreantpos.model.dao.TicketDAO;
/*     */ import com.floreantpos.swing.PosSmallButton;
/*     */ import com.floreantpos.ui.dialog.POSMessageDialog;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import net.miginfocom.swing.MigLayout;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.Transaction;
/*     */ import org.jdesktop.swingx.JXTable;
/*     */ 
/*     */ public class AssignDriverDialog
/*     */   extends JDialog
/*     */ {
/*     */   private JTextField tfRecipientName;
/*     */   private JTextField tfDeliveryDate;
/*     */   private Ticket ticket;
/*     */   private JTextArea tfDeliveryAddress;
/*     */   private JTextArea tfExtraInstruction;
/*     */   private JXTable driverTable;
/*     */   private PosSmallButton btnCancel;
/*     */   private PosSmallButton btnSave;
/*  42 */   protected boolean canceled = true;
/*     */   
/*     */   public AssignDriverDialog(Frame parent) {
/*  45 */     super(parent, "Assign Driver", true);
/*  46 */     createUI();
/*     */     
/*  48 */     setDefaultCloseOperation(2);
/*     */   }
/*     */   
/*     */   private void createUI() {
/*  52 */     getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][60px,grow,shrink 0][60px,shrink 0][grow][shrink 0]"));
/*     */     
/*  54 */     JLabel lblRecipientName = new JLabel("Recipient Name");
/*  55 */     getContentPane().add(lblRecipientName, "cell 0 0,alignx trailing");
/*     */     
/*  57 */     this.tfRecipientName = new JTextField();
/*  58 */     this.tfRecipientName.setEnabled(true);
/*  59 */     this.tfRecipientName.setEditable(false);
/*  60 */     this.tfRecipientName.setFocusable(false);
/*  61 */     getContentPane().add(this.tfRecipientName, "cell 1 0,growx");
/*  62 */     this.tfRecipientName.setColumns(10);
/*     */     
/*  64 */     JLabel lblDeliveryDate = new JLabel("Delivery Date");
/*  65 */     getContentPane().add(lblDeliveryDate, "cell 0 1,alignx trailing");
/*     */     
/*  67 */     this.tfDeliveryDate = new JTextField();
/*  68 */     this.tfDeliveryDate.setEnabled(true);
/*  69 */     this.tfDeliveryDate.setEditable(false);
/*  70 */     this.tfDeliveryDate.setFocusable(false);
/*  71 */     getContentPane().add(this.tfDeliveryDate, "cell 1 1,growx");
/*  72 */     this.tfDeliveryDate.setColumns(10);
/*     */     
/*  74 */     JLabel lblDeliveryAddress = new JLabel("Delivery Address");
/*  75 */     getContentPane().add(lblDeliveryAddress, "cell 0 2,aligny top");
/*     */     
/*  77 */     this.tfDeliveryAddress = new JTextArea();
/*  78 */     this.tfDeliveryAddress.setEnabled(true);
/*  79 */     this.tfDeliveryAddress.setEditable(false);
/*  80 */     this.tfDeliveryAddress.setFocusable(false);
/*  81 */     this.tfDeliveryAddress.setBorder(new LineBorder(Color.LIGHT_GRAY));
/*  82 */     this.tfDeliveryAddress.setRows(4);
/*  83 */     getContentPane().add(this.tfDeliveryAddress, "cell 1 2,grow");
/*     */     
/*  85 */     JLabel lblExtraInstruction = new JLabel("Extra Instruction");
/*  86 */     getContentPane().add(lblExtraInstruction, "cell 0 3,aligny top");
/*     */     
/*  88 */     this.tfExtraInstruction = new JTextArea();
/*  89 */     this.tfExtraInstruction.setEnabled(true);
/*  90 */     this.tfExtraInstruction.setEditable(false);
/*  91 */     this.tfExtraInstruction.setFocusable(true);
/*  92 */     this.tfExtraInstruction.setBorder(new LineBorder(Color.LIGHT_GRAY));
/*  93 */     this.tfExtraInstruction.setRows(4);
/*  94 */     getContentPane().add(this.tfExtraInstruction, "cell 1 3,grow");
/*     */     
/*  96 */     JPanel panel = new JPanel();
/*  97 */     getContentPane().add(panel, "cell 0 4 2 1,grow");
/*  98 */     panel.setLayout(new BorderLayout(0, 0));
/*     */     
/* 100 */     JScrollPane scrollPane = new JScrollPane();
/* 101 */     panel.add(scrollPane, "Center");
/*     */     
/* 103 */     this.driverTable = new JXTable();
/* 104 */     scrollPane.setViewportView(this.driverTable);
/*     */     
/* 106 */     JPanel panel_1 = new JPanel();
/* 107 */     getContentPane().add(panel_1, "cell 0 5 2 1,grow");
/*     */     
/* 109 */     this.btnCancel = new PosSmallButton();
/* 110 */     this.btnCancel.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 112 */         AssignDriverDialog.this.canceled = true;
/* 113 */         AssignDriverDialog.this.dispose();
/*     */       }
/* 115 */     });
/* 116 */     this.btnCancel.setText("CANCEL");
/* 117 */     panel_1.add(this.btnCancel);
/*     */     
/* 119 */     this.btnSave = new PosSmallButton();
/* 120 */     this.btnSave.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 122 */         AssignDriverDialog.this.saveTicket();
/*     */       }
/* 124 */     });
/* 125 */     this.btnSave.setText("SAVE");
/* 126 */     panel_1.add(this.btnSave);
/*     */     
/* 128 */     this.driverTable.setModel(new DriverTableModel());
/* 129 */     this.driverTable.setFocusable(false);
/* 130 */     this.driverTable.setRowHeight(35);
/* 131 */     this.driverTable.getSelectionModel().setSelectionMode(0);
/*     */   }
/*     */   
/*     */   protected void saveTicket() {
/* 135 */     int selectedRow = this.driverTable.getSelectedRow();
/*     */     
/* 137 */     if (selectedRow < 0) {
/* 138 */       POSMessageDialog.showError("Please select a driver");
/* 139 */       return;
/*     */     }
/*     */     
/* 142 */     DriverTableModel model = (DriverTableModel)this.driverTable.getModel();
/* 143 */     User driver = model.getDriver(selectedRow);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */     this.ticket.setAssignedDriver(driver);
/*     */     
/* 158 */     Session session = TicketDAO.getInstance().createNewSession();
/* 159 */     Transaction transaction = null;
/*     */     
/*     */     try
/*     */     {
/* 163 */       transaction = session.beginTransaction();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 169 */       session.saveOrUpdate(this.ticket);
/*     */       
/* 171 */       transaction.commit();
/*     */       
/* 173 */       this.canceled = false;
/* 174 */       dispose();
/*     */     }
/*     */     catch (Exception e) {
/* 177 */       e.printStackTrace();
/* 178 */       POSMessageDialog.showError(e.getMessage());
/*     */       
/* 180 */       if (transaction != null) transaction.rollback();
/*     */     } finally {
/* 182 */       session.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setData(Ticket ticket, List<User> drivers) {
/* 187 */     this.ticket = ticket;
/*     */     
/* 189 */     if (StringUtils.isNotEmpty(ticket.getProperty("CUSTOMER_NAME"))) {
/* 190 */       this.tfRecipientName.setText(ticket.getProperty("CUSTOMER_NAME"));
/*     */     }
/*     */     
/* 193 */     if (ticket.getDeliveryDate() != null) {
/* 194 */       this.tfDeliveryDate.setText(ticket.getDeliveryDate().toString());
/*     */     }
/*     */     
/* 197 */     this.tfDeliveryAddress.setText(ticket.getDeliveryAddress());
/* 198 */     this.tfExtraInstruction.setText(ticket.getExtraDeliveryInfo());
/*     */     
/* 200 */     this.driverTable.setModel(new DriverTableModel(drivers));
/*     */   }
/*     */   
/*     */   class DriverTableModel extends AbstractTableModel {
/* 204 */     private final String[] columns = { "DRIVER'S PHONE", "NAME", "AVAILABLE" };
/*     */     
/*     */     private List<User> drivers;
/*     */     
/*     */     public DriverTableModel() {}
/*     */     
/*     */     public DriverTableModel()
/*     */     {
/* 212 */       this.drivers = customers;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 217 */       if (this.drivers == null) {
/* 218 */         return 0;
/*     */       }
/*     */       
/* 221 */       return this.drivers.size();
/*     */     }
/*     */     
/*     */     public int getColumnCount()
/*     */     {
/* 226 */       return this.columns.length;
/*     */     }
/*     */     
/*     */     public String getColumnName(int column)
/*     */     {
/* 231 */       return this.columns[column];
/*     */     }
/*     */     
/*     */     public Object getValueAt(int rowIndex, int columnIndex)
/*     */     {
/* 236 */       if (this.drivers == null) {
/* 237 */         return null;
/*     */       }
/*     */       
/* 240 */       User driver = (User)this.drivers.get(rowIndex);
/*     */       
/* 242 */       switch (columnIndex) {
/*     */       case 0: 
/* 244 */         return driver.getPhoneNo();
/*     */       case 1: 
/* 246 */         return driver.getFirstName() + " " + driver.getLastName();
/*     */       
/*     */       case 2: 
/* 249 */         return driver.isAvailableForDelivery().booleanValue() ? "Yes" : "No";
/*     */       }
/* 251 */       return null;
/*     */     }
/*     */     
/*     */     public List<User> getDrivers() {
/* 255 */       return this.drivers;
/*     */     }
/*     */     
/*     */     public User getDriver(int index) {
/* 259 */       if (this.drivers == null) {
/* 260 */         return null;
/*     */       }
/*     */       
/* 263 */       if ((index < 0) || (index >= this.drivers.size())) {
/* 264 */         return null;
/*     */       }
/*     */       
/* 267 */       return (User)this.drivers.get(index);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCanceled()
/*     */   {
/* 273 */     return this.canceled;
/*     */   }
/*     */ }


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\AssignDriverDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
package com.oro.orderextension;

public abstract interface WizardPage
{
  public abstract String getName();
  
  public abstract boolean canGoNext();
  
  public abstract boolean canGoBack();
  
  public abstract boolean canFinishWizard();
  
  public abstract boolean finish();
  
  public abstract WizardPage getPreviousPage();
  
  public abstract WizardPage getNextPage();
}


/* Location:              C:\Users\SOMYA\Desktop\divya\orocust-0.7.jar!\com\oro\orderextension\WizardPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
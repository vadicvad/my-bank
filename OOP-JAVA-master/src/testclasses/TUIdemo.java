package testclasses;

import com.mybank.domain.*;
import jexer.TAction;
import jexer.TApplication;
import jexer.TField;
import jexer.TText;
import jexer.TWindow;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;

public class TUIdemo extends TApplication {

    private static final int ABOUT_APP = 2000;
    private static final int CUST_INFO = 2010;


       
    public static void main(String[] args) throws Exception {
        TUIdemo tdemo = new TUIdemo();
        (new Thread(tdemo)).start();
        Account checkingAccount = new CheckingAccount(500,500);
        Account SavingAccount = new SavingsAccount(500,10);
        Bank.addCustomer("Dmitro","Kovalenko");
        Bank.addCustomer("Mykola","Kurilko" );
     Bank.getCustomer(0).addAccount(checkingAccount);
     Bank.getCustomer(1).addAccount(SavingAccount);          
    }

    public TUIdemo() throws Exception {
        super(BackendType.SWING);

        addToolMenu();
        //custom 'File' menu
        TMenu fileMenu = addMenu("&File");
        fileMenu.addItem(CUST_INFO, "&Customer Info");
        fileMenu.addDefaultItem(TMenu.MID_SHELL);
        fileMenu.addSeparator();
        fileMenu.addDefaultItem(TMenu.MID_EXIT);
        //end of 'File' menu  

        addWindowMenu();

        //custom 'Help' menu
        TMenu helpMenu = addMenu("&Help");
        helpMenu.addItem(ABOUT_APP, "&About...");
        //end of 'Help' menu 

        setFocusFollowsMouse(true);
        //Customer window
        ShowCustomerDetails();
    }

    @Override
    protected boolean onMenu(TMenuEvent menu) {
        if (menu.getId() == ABOUT_APP) {
            messageBox("About", "\t\t\t\t\t   Just a simple Jexer demo.\n\nCopyright \u00A9 2019 Alexander \'Taurus\' Babich").show();
            return true;
        }
        if (menu.getId() == CUST_INFO) {
            ShowCustomerDetails();
            return true;
        }
        return super.onMenu(menu);
    }

    private void ShowCustomerDetails() {
        TWindow custWin = addWindow("Customer Window", 2, 1, 40, 10,TWindow.NOZOOMBOX);
        custWin.newStatusBar("Enter valid customer number and press Show...");
        
        custWin.addLabel("Enter customer number: ", 2, 2);
        TField custNo = custWin.addField(24, 2, 3, false);
        TText details = custWin.addText("Owner Name: \nAccount Type: \nAccount Balance: ", 2, 4, 38, 8);
        custWin.addButton("&Show", 28, 2, new TAction() {
                      @Override
            public void DO() {
                String account_type = "";
                String num = custNo.getText();
                if ("".equals(num)) {
                    messageBox("Error", "You must provide a valid customer number!").show();
                } else {
                    int Num = Integer.parseInt(num);
                    Customer customer = Bank.getCustomer(Num);
                    Account account = customer.getAccount(0);

                    if ( account instanceof SavingsAccount) {
                        account_type = "Savings Account";
                    } else if ( account instanceof CheckingAccount) {
                        account_type = "Checking Account";
                    } else {
                        account_type = "Unknown Account Type";
                    }

                    details.setText("Owner Name: " + Bank.getCustomer(Num).toString() + "\nAccount Type: " + account_type + "\nAccount Balance: " + Bank.getCustomer(Num).getAccount(0).getBalance());

                }
            }
        });
    }
}



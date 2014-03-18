package gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WeekViewPane extends JPanel {
    static JLabel monthLbl, yearLbl;
    static JButton prevBtn, nextBtn;
    static JTable calendarTable;
    static JComboBox<String> yearCmb;
    static DefaultTableModel calendarModel; //Table model
    static JScrollPane calendarScroll; //The scrollpane
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    
    public WeekViewPane() {

        //Create controls
        monthLbl = new JLabel ("January");
        yearLbl = new JLabel ("Change year:");
        yearCmb = new JComboBox<String>();
        prevBtn = new JButton ("Previous month");
        nextBtn = new JButton ("Next month");
        calendarModel = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        calendarTable = new JTable(calendarModel);
        calendarScroll = new JScrollPane(calendarTable);
        
        //Set border
        setBorder(BorderFactory.createTitledBorder("Calendar"));
        
        //Register action listeners
        prevBtn.addActionListener(new btnPrev_Action());
        nextBtn.addActionListener(new btnNext_Action());
        yearCmb.addActionListener(new cmbYear_Action());
        
        add(monthLbl);
        add(yearLbl);
        add(yearCmb);
        add(prevBtn);
        add(nextBtn);
        add(calendarScroll);
        
        //Set bounds
        setBounds(0, 0, 320, 335);
        monthLbl.setBounds(160-monthLbl.getPreferredSize().width/2, 25, 100, 25);
        yearLbl.setBounds(10, 305, 80, 20);
        yearCmb.setBounds(230, 305, 80, 20);
        prevBtn.setBounds(10, 25, 50, 25);
        nextBtn.setBounds(260, 25, 50, 25);
        calendarScroll.setBounds(10, 50, 300, 250);
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        
        //Add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            calendarModel.addColumn(headers[i]);
        }
        
        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background
        
        //No resize/reorder
        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection
        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Set row/column count
        calendarTable.setRowHeight(38);
        calendarModel.setColumnCount(7);
        calendarModel.setRowCount(6);
        
        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            yearCmb.addItem(String.valueOf(i));
        }
        
        //Refresh calendar
        refreshCalendar (realMonth, realYear); //Refresh calendar
    }
    
    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow buttons
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
        if (month == 0 && year <= realYear-10){prevBtn.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100){nextBtn.setEnabled(false);} //Too late
        monthLbl.setText(months[month]); //Refresh the month label (at the top)
        monthLbl.setBounds(160-monthLbl.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
        yearCmb.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                calendarModel.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            calendarModel.setValueAt(i, row, column);
        }
        
        //Apply renderers
        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new tblCalendarRenderer());
    }
    
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (yearCmb.getSelectedItem() != null){
                String b = yearCmb.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
}

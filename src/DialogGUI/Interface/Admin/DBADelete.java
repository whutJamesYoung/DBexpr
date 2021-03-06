package DialogGUI.Interface.Admin;

import entity.*;
import handle.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

//删除车辆、司机或者顾客信息
public class DBADelete extends JPanel {
    private JTable table;
    private JTextField idT;
    private String[][] data;
    private int flag;  //车辆1 司机2 顾客3
    private int id;

    /**
     * Create the panel.
     */
    public DBADelete() {
        setLayout(null);

        JPanel titleP = new JPanel();
        titleP.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        titleP.setBounds(0, 0, 1457, 82);
        add(titleP);
        titleP.setLayout(null);

        JLabel titleL = new JLabel("删除");
        titleL.setBounds(703, 0, 159, 82);
        titleL.setFont(new Font("宋体", Font.BOLD, 50));
        titleP.add(titleL);

        JPanel MP = new JPanel();
        MP.setBounds(148, 81, 1309, 721);
        add(MP);
        MP.setLayout(null);

        JLabel idL = new JLabel("ID号");
        idL.setFont(new Font("宋体", Font.PLAIN, 35));
        idL.setBounds(417, 13, 111, 78);
        MP.add(idL);

        table = new JTable();
        table.setFont(new Font("宋体", Font.PLAIN, 35));
        table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        table.setBounds(0, 104, 1114, 617);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowHeight(60);
        MP.add(table);

        idT = new JTextField();
        idT.setFont(new Font("宋体", Font.PLAIN, 35));
        idT.setBounds(616, 14, 221, 78);
        MP.add(idT);
        idT.setColumns(15);

        JButton SearchBnt = new JButton("查询");
        SearchBnt.setEnabled(false);
        SearchBnt.setFont(new Font("宋体", Font.PLAIN, 45));
        SearchBnt.setBounds(1119, 0, 176, 123);
        MP.add(SearchBnt);
        
                JButton DeleteBnt = new JButton("删除");
                DeleteBnt.setBounds(1119, 598, 176, 123);
                MP.add(DeleteBnt);
                DeleteBnt.setEnabled(false);
                DeleteBnt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            boolean tag;
                            if (flag == 1) {
                                tag = DataProcessing.delete(Delete_SQL_sen.delete_car(id));
                            } else if (flag == 2)
                                tag = DataProcessing.delete(Delete_SQL_sen.delete_driver(id));
                            else
                                tag = DataProcessing.delete(Delete_SQL_sen.delete_client(id));
                            if (!tag)
                                JOptionPane.showMessageDialog(null, "正在使用中", "出错了", JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
                DeleteBnt.setFont(new Font("宋体", Font.PLAIN, 45));

        JPanel SelectP = new JPanel();
        SelectP.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        SelectP.setBounds(0, 81, 148, 721);
        add(SelectP);
        SelectP.setLayout(null);

        SearchBnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ids = idT.getText();
                if (ids.equals(""))
                    return;
                id = Integer.parseInt(ids);
                try {
                    if (flag == 1) {
                        Car[] car = DataProcessing.searchCar(Search_SQL_sen.get_a_car(id));
                        if (car == null) {
                            JOptionPane.showMessageDialog(null, "没有查到此车辆", "未查到", JOptionPane.INFORMATION_MESSAGE);
                            idT.setText("");
                            ParseCar(null); //保证表格被刷新
                        } else
                            ParseCar(car[0]);
                    } else if (flag == 2) {
                        Driver[] driver = DataProcessing.searchDriver(Search_SQL_sen.get_a_driver(id));
                        if (driver == null) {
                            JOptionPane.showMessageDialog(null, "没有查到此司机", "未查到", JOptionPane.INFORMATION_MESSAGE);
                            idT.setText("");
                            ParseDriver(null); //保证表格被刷新
                        } else
                            ParseDriver(driver[0]);
                    } else {
                        Client[] c = DataProcessing.searchClient(Search_SQL_sen.get_a_client(id));
                        if (c == null) {
                            JOptionPane.showMessageDialog(null, "没有查到此客户", "未查到", JOptionPane.INFORMATION_MESSAGE);
                            idT.setText("");
                            ParseClient(null);  //保证表格被刷新
                        } else
                            ParseClient(c[0]);
                    }
                } catch (SQLException error) {
                    error.printStackTrace();
                }
                DeleteBnt.setEnabled(true);
            }
        });

        JButton CBnt = new JButton("车辆");
        CBnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                flag = 1;
                setCarT();
                SearchBnt.setEnabled(true);
            }
        });
        CBnt.setBounds(0, 116, 148, 95);
        CBnt.setFont(new Font("宋体", Font.PLAIN, 45));
        SelectP.add(CBnt);

        JButton DBnt = new JButton("司机");
        DBnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flag = 2;
                setDriverT();
                SearchBnt.setEnabled(true);
            }
        });
        DBnt.setFont(new Font("宋体", Font.PLAIN, 45));
        DBnt.setBounds(0, 324, 148, 95);
        SelectP.add(DBnt);

        JButton ClBnt = new JButton("客户");
        ClBnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flag = 3;
                setClientT();
                SearchBnt.setEnabled(true);
            }
        });
        ClBnt.setFont(new Font("宋体", Font.PLAIN, 45));
        ClBnt.setBounds(0, 537, 148, 95);
        SelectP.add(ClBnt);

    }

    private void setCarT() {
        data = new String[][]{
                {"车辆型号", null},
                {"车牌照", null},
                {"购入日期", null},
                {"价格", null},
                {"维修日期", null},
                {"运行公里", null},
                {"运行小时", null},
                {"租金率", null},
        };
        table.setModel(new DefaultTableModel(
                data, new String[]{"\u6807\u7B7E", "\u503C"}
        ) {
            public boolean isCellEditable(int row, int column) {//表格不允许被编辑
                return false;
            }
        });
    }

    private void setDriverT() {
        data = new String[][]{
                {"姓名", null},
                {"参加工作年月", null},
                {"基本工资", null},
        };
        table.setModel(new DefaultTableModel(
                data, new String[]{"\u6807\u7B7E", "\u503C"}
        ) {
            public boolean isCellEditable(int row, int column) {//表格不允许被编辑
                return false;
            }
        });
    }

    private void setClientT() {
        data = new String[][]{
                {"客户姓名", null},
                {"客户单位", null},
                {"客户电话", null},
                {"客户地址", null},
                {"邮编", null},
        };
        table.setModel(new DefaultTableModel(
                data, new String[]{"\u6807\u7B7E", "\u503C"}
        ) {
            public boolean isCellEditable(int row, int column) {//表格不允许被编辑
                return false;
            }
        });
    }

    /**
     * 这里对data进行的是内封装，耦合程度比较大，但操作起来更方便，这导致本类是不安全的
     */
    private void ParseCar(Car car) {
        if (car == null) {
            for (int i = 0; i < 8; i++) {
                data[i][1] = "";
            }
        } else {
            data[0][1] = car.getType();
            data[1][1] = car.getLicense();
            data[2][1] = ParseEntity.ParseDate2S(car.getPurchase_date());
            data[3][1] = Double.toString(car.getPrice());
            data[4][1] = ParseEntity.ParseDate2S(car.getMaintain_date());
            data[5][1] = Double.toString(car.getMile());
            data[6][1] = Double.toString(car.getWorking_time());
            data[7][1] = Double.toString(car.getRent_rate());
        }
        table.setModel(new DefaultTableModel(
                data, new String[]{"\u6807\u7B7E", "\u503C"}
        ) {
            public boolean isCellEditable(int row, int column) {//表格不允许被编辑
                return false;
            }
        });
    }

    private void ParseDriver(Driver driver) {
        if (driver == null) {
            for (int i = 0; i < 3; i++) {
                data[i][1] = "";
            }
        } else {
            data[0][1] = driver.getName();
            data[1][1] = ParseEntity.ParseDate2S(driver.getEnroll_date());
            data[2][1] = Double.toString(driver.getSalary());
        }
        table.setModel(new DefaultTableModel(
                data, new String[]{"\u6807\u7B7E", "\u503C"}
        ) {
            public boolean isCellEditable(int row, int column) {//表格不允许被编辑
                return false;
            }
        });
    }

    private void ParseClient(Client client) {
        if (client == null) {
            for (int i = 0; i < 5; i++) {
                data[i][1] = "";
            }
        } else {
            data[0][1] = client.getName();
            data[1][1] = client.getCompany();
            data[2][1] = client.getTel();
            data[3][1] = client.getAddr();
            data[4][1] = client.getZipcode();
        }
        table.setModel(new DefaultTableModel(
                data, new String[]{"\u6807\u7B7E", "\u503C"}
        ) {
            public boolean isCellEditable(int row, int column) {//表格不允许被编辑
                return false;
            }
        });
    }

}

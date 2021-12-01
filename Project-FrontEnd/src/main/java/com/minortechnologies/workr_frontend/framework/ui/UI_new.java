package com.minortechnologies.workr_frontend.framework.ui;

import com.minortechnologies.workr_backend.usecase.security.Security;
import com.minortechnologies.workr_frontend.entities.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class UI_new {

    public static void main(String[] args) { new UI_new(); }

    private User[] users;
    private JFrame frame;

    private User currentUser;
    private int numUsers = 0;

    private JPanel cards, loginPanel, signupPanel,
            accountPanel, listingsPanel, searchPanel,
            resultsPanel;
    private CardLayout cardLayout;

    private final Font TITLE_FONT = new Font("Times New Roman", Font.BOLD, 20);
    private final Font ERROR_FONT = new Font("Times New Roman", Font.BOLD, 12);

    private void setup() {
        frame = new JFrame("Workr v0.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
    }

    public UI_new() {
        cards = new JPanel(new CardLayout());

        loginPanel = loginScreen();
        signupPanel = signupScreen();
        accountPanel = accountScreen();
        listingsPanel = listingsScreen();
        searchPanel = searchScreen();

        cards.add(loginPanel, "login");
        cards.add(signupPanel, "signup");
        cards.add(accountPanel, "main");
        cards.add(listingsPanel, "listings");
        cards.add(searchPanel, "search");

        cardLayout = (CardLayout) cards.getLayout();
        frame.add(cards);
        frame.setVisible(true);
    }

    private JPanel loginScreen() {
        loginPanel = new JPanel();
        loginPanel.setLayout(null);

        //header
        JLabel header = new JLabel("Welcome to Workr!");
        header.setFont(TITLE_FONT);
        header.setBounds(160, 50, 200, 50);
        loginPanel.add(header);

        //username field
        JTextField username = new JTextField();
        username.setBounds(200, 100, 200, 25);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 100, 100, 25);
        loginPanel.add(username);
        loginPanel.add(usernameLabel);

        //password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 125, 100, 25);
        JPasswordField password = new JPasswordField();
        password.setBounds(200, 125, 200, 25);
        loginPanel.add(password);
        loginPanel.add(passwordLabel);

        //login button
        JButton loginButton = new JButton("Log in");
        loginButton.setBounds(325, 150, 75, 25);
        loginPanel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean userFound = false;
                    for (User user : users) {
                        if (user == null) {
                            break;
                        }
                        if (user.ACCOUNT_NAME.equals(username.getText())) {
                            userFound = true;
                            currentUser = user;
                            cardLayout.show(cards, "main");
                            break;
                        }
                    }
                    if (!userFound) {
                        noSuchUserWarning(loginPanel);
                    }
                } catch (IndexOutOfBoundsException n) {
                    noSuchUserWarning(loginPanel);
                }
            }
        });

        //signup redirect
        JButton signupButton = new JButton("No account? Sign up here");
        signupButton.setBounds(275, 400, 200, 25);
        loginPanel.add(signupButton);
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "signup");
            }
        });
        return loginPanel;
    }

    private void noSuchUserWarning(JPanel panel) {
        JLabel noSuchUser = new JLabel("ERROR: Account does not exist");
        noSuchUser.setFont(ERROR_FONT);
        noSuchUser.setForeground(Color.RED);
        noSuchUser.setBounds(200, 200, 200, 25);
        panel.add(noSuchUser);

        frame.revalidate(); //updating to show error
        frame.repaint();
    }

    private JPanel signupScreen() {
        signupPanel = new JPanel();
        signupPanel.setLayout(null);

        //header
        JLabel header = new JLabel("Join Workr Today");
        header.setFont(TITLE_FONT);
        header.setBounds(175, 50, 200, 50);
        signupPanel.add(header);

        //username field
        JTextField username = new JTextField();
        username.setBounds(200, 100, 200, 25);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 100, 100, 25);
        signupPanel.add(username);
        signupPanel.add(usernameLabel);

        //password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 125, 100, 25);
        JPasswordField password = new JPasswordField();
        password.setBounds(200, 125, 200, 25);
        signupPanel.add(password);
        signupPanel.add(passwordLabel);

        //confirm password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 150, 150, 25);
        JPasswordField confirmPassword = new JPasswordField();
        confirmPassword.setBounds(200, 150, 200, 25);
        signupPanel.add(confirmPassword);
        signupPanel.add(confirmPasswordLabel);

        //confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(310, 175, 90, 25);
        signupPanel.add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = username.getText();
                char[] newPassword = password.getPassword();

                if (Arrays.equals(newPassword, confirmPassword.getPassword())) {
                    User newUser = new User(newUsername, newPassword, Security.generateNewToken());
                    users[numUsers] = newUser;
                    numUsers++;
                    currentUser = newUser;
                    cardLayout.show(cards, "main");
                } else {
                    JLabel passwordMismatch = new JLabel("ERROR: Passwords do not match");
                    passwordMismatch.setFont(ERROR_FONT);
                    passwordMismatch.setForeground(Color.RED);
                    passwordMismatch.setBounds(200, 200, 200, 25);
                    signupPanel.add(passwordMismatch);
                    frame.revalidate(); //updating to show error
                    frame.repaint();
                }
            }
        });

        //create new user when confirm button is pushed

        //login button
        JButton loginButton = new JButton("I have an account");
        loginButton.setBounds(275, 400, 200, 25);
        signupPanel.add(loginButton);

        //login listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "login");
            }
        });

        return signupPanel;
    }

    private JPanel accountScreen() {
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(null);
        JLabel header;

        try {
            header = new JLabel("Welcome, " + currentUser.ACCOUNT_NAME);
        } catch (NullPointerException n) {
            //in case username is not defined
            header = new JLabel("Welcome to Workr");
        }

        header.setFont(TITLE_FONT);
        header.setBounds(150, 25, 300, 50);
        accountPanel.add(header);

        //search for listings
        JButton search = new JButton("Search listings");
        search.setBounds(150, 75, 200, 50);
        accountPanel.add(search);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "search");
            }
        });

        //view saved listings
        JButton viewListings = new JButton("View my listings");
        viewListings.setBounds(150, 125, 200, 50);
        accountPanel.add(viewListings);
        viewListings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "listings");
            }
        });

        //log out
        JButton logout = new JButton("Log out");
        logout.setBounds(150, 175, 200, 50);
        accountPanel.add(logout);
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "login");
            }
        });

        //quit
        JButton quit = new JButton("Quit to OS");
        quit.setBounds(150, 225, 200, 50);
        accountPanel.add(quit);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        return accountPanel;
    }

    private JPanel searchScreen() {
        return searchPanel;
    }

    private JPanel listingsScreen() {
        return listingsPanel;
    }

}

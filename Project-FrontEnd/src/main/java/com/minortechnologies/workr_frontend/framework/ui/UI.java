package com.minortechnologies.workr_frontend.framework.ui;

import com.minortechnologies.workr_frontend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.JobType;
import com.minortechnologies.workr_frontend.entities.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
    GUI built with Swing technology.
    All JPanel layouts are manually overridden and custom designed.
 */
public class UI {

    private User[] users;
    private int numUsers = 0;

    private JFrame frame;
    private JPanel cards, loginPanel, signupPanel,
            accountPanel, listingsPanel, searchPanel,
            resultsPanel;
    private CardLayout cardLayout;
    private LocalCache cache;

    private final Font TITLE_FONT = new Font("Helvetica", Font.BOLD, 20);
    private final Font ERROR_FONT = new Font("Helvetica", Font.BOLD, 12);

    private User currentUser;

    public UI() {
        //cache = new LocalCache();
        //cache.loadSavedListings();

        setup();
        users = new User[100];
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

    /*
        Set up the JFrame.
     */
    private void setup() {
        frame = new JFrame("Workr v0.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
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

                    for (int i = 0; i < users.length; i++) {

                        if (users[i] == null) {
                            break;
                        }

                        if (users[i].ACCOUNT_NAME.equals(username.getText())) {
                            userFound = true;
                            currentUser = users[i];
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
                    User newUser = new User(newUsername);
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

    private JPanel listingsScreen() {

        JPanel listingsPanel = new JPanel();
        listingsPanel.setLayout(null);

        //header
        JLabel header = new JLabel("View your listings");
        header.setFont(TITLE_FONT);
        header.setBounds(150, 25, 300, 50);

        //listings table
        DefaultListModel demoList = new DefaultListModel();
        JList list;
        JLabel noDataWarning;

        try {
            String[] listingHeaders;

            HashSet<String> set = currentUser.getWatchedListings();

            ArrayList<JobListing> listings = new ArrayList<>(set);
            demoList.addElement(listings);
            list = new JList(demoList);
        } catch (NullPointerException n) {
            list = new JList();
            noDataWarning = new JLabel("You don't have any saved listings!");
            noDataWarning.setBounds(50, 225, 400, 50);
            listingsPanel.add(noDataWarning);
        }

        list.setBounds(50, 25, 400, 250);
        listingsPanel.add(list);

        //return button
        JButton goBack = new JButton("Return");
        goBack.setBounds(150, 325, 200, 50);
        listingsPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "main");
            }
        });


        return listingsPanel;

    }

    private JPanel searchScreen() {

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);

        //header
        JLabel header = new JLabel("Search for your next job");
        header.setBounds(100, 50, 300, 50);
        header.setFont(TITLE_FONT);
        searchPanel.add(header);

        //search terms field
        JTextField searchTerms = new JTextField("search terms");
        searchTerms.setBounds(100, 100, 300, 25);
        searchPanel.add(searchTerms);

        //location field
        JTextField location = new JTextField("location");
        location.setBounds(100, 125, 300, 25);
        searchPanel.add(location);

        //radio buttons to choose dates
        ButtonGroup dateButtons = new ButtonGroup();

        JRadioButton oneDay = new JRadioButton("1 Day Old");
        JRadioButton threeDay = new JRadioButton(("3 Days Old"));
        JRadioButton sevenDay = new JRadioButton(("7 Days Old"));
        JRadioButton fourteenDay = new JRadioButton(("14 Days Old"));

        dateButtons.add(oneDay);
        dateButtons.add(threeDay);
        dateButtons.add(sevenDay);
        dateButtons.add(fourteenDay);

        oneDay.setBounds(50, 175, 100, 25);
        threeDay.setBounds(150, 175, 100, 25);
        sevenDay.setBounds(250, 175, 100, 25);
        fourteenDay.setBounds(350, 175, 100, 25);

        searchPanel.add(oneDay);
        searchPanel.add(threeDay);
        searchPanel.add(sevenDay);
        searchPanel.add(fourteenDay);


        //radio buttons to choose fulltime/parttime
        ButtonGroup typeButtons = new ButtonGroup();

        JRadioButton partTime = new JRadioButton("Part time");
        JRadioButton fullTime = new JRadioButton(("Full time"));

        typeButtons.add(partTime);
        typeButtons.add(fullTime);

        partTime.setBounds(150, 200, 100, 25);
        fullTime.setBounds(250, 200, 100, 25);

        searchPanel.add(partTime);
        searchPanel.add(fullTime);

        //search button
        JButton generateQuery = new JButton("Search");
        generateQuery.setBounds(200, 225, 100, 50);
        searchPanel.add(generateQuery);
        generateQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String terms = searchTerms.getText();
                String jobLocation = location.getText();

                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime dateTime;
                String range = dateButtons.getSelection().getActionCommand();

                switch (range) {
                    case "1 Day Old":
                        dateTime = currentTime.minusDays(1);
                        break;
                    case "3 Days Old":
                        dateTime = currentTime.minusDays(3);
                        break;
                    case "7 Days Old":
                        dateTime = currentTime.minusDays(7);
                        break;
                    case "14 Days Old":
                        dateTime = currentTime.minusDays(14);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select a range!");
                }

                JobType jobType;
                String selectedType = typeButtons.getSelection().getActionCommand();

                switch (selectedType) {
                    case "Full time":
                        jobType = JobType.FULL_TIME;
                    case "Part time":
                        jobType = JobType.PART_TIME;
                    default:

                }
            }


        });
        //return button
        JButton goBack = new JButton("Return");
        goBack.setBounds(150, 325, 200, 50);
        searchPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "main");
            }
        });

        return searchPanel;

    }

}

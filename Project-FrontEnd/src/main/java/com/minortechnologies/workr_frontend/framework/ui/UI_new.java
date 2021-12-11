package com.minortechnologies.workr_frontend.framework.ui;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.JobType;
import com.minortechnologies.workr_frontend.entities.user.User;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static com.minortechnologies.workr_frontend.framework.network.SendHTTP.executeGet;
import static com.minortechnologies.workr_frontend.framework.network.SendHTTP.executePost;

public class UI_new {

    public static void main(String[] args) {
        new UI_new();
    }

    private JFrame frame;

    private String currentUser = null;
    private Map<String, Object> currentAuth;
    private User curr;
    private ArrayList<JobListing> listings = new ArrayList<>();

    final CardLayout cardLayout;
    JPanel cards = new JPanel(new CardLayout());

    private final Font TITLE_FONT = new Font("Times New Roman", Font.BOLD, 20);
    private final Font ERROR_FONT = new Font("Times New Roman", Font.BOLD, 12);

    private void setup() {
        frame = new JFrame("Workr v0.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
    }

    public UI_new() {
        setup();
        JPanel loginPanel = loginScreen();
        JPanel signupPanel = signupScreen();
        JPanel accountPanel = accountScreen();
        JPanel listingsPanel = listingsScreen();
        JPanel searchPanel = searchScreen();
        JPanel userPanel = userScreen();

        cards.add(loginPanel, "login");
        cards.add(signupPanel, "signup");
        cards.add(accountPanel, "main");
        cards.add(listingsPanel, "listings");
        cards.add(searchPanel, "search");
        cards.add(userPanel, "user");

        cardLayout = (CardLayout) cards.getLayout();
        frame.add(cards);
        frame.setVisible(true);
    }

    private JPanel loginScreen() {
        JPanel loginPanel = new JPanel();
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
        loginButton.addActionListener(e -> {
            boolean userFound = false;
            HashMap<String, String> body = new HashMap<>();
            body.put("login", username.toString());
            body.put("passwowrd", password.toString());

            try {
                currentUser = executeGet("http://localhost:8080/User/SignIn", body);
                if (currentUser != null) {
                    sendMessage(loginPanel, "Success!");
                    String urlget = "http://localhost:8080/User/" +
                            username +
                            "/GetAllUserData";
                    HashMap<String, String> body2 = new HashMap<>();
                    body2.put("token", currentUser);
                    String accountData = executeGet(urlget, body2);
                    JSONObject data = new JSONObject(accountData);
                    currentAuth = data.toMap();
                    cardLayout.show(cards, "main");
                }
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                sendMessage(loginPanel, "Error: No such user exists!");
            }

//            try {
//                for (User user : users) {
//                    if (user == null) {
//                        break;
//                    }
//                    if (user.ACCOUNT_NAME.equals(username.getText())) {
//                        userFound = true;
//                        currentUser = user;
//                        cardLayout.show(cards, "main");
//                        break;
//                    }
//                }
//                if (!userFound) {
//                    noSuchUserWarning(loginPanel);
//                }
//            } catch (IndexOutOfBoundsException n) {
//                noSuchUserWarning(loginPanel);
//            }
        });

        //signup redirect
        JButton signupButton = new JButton("No account? Sign up here");
        signupButton.setBounds(275, 400, 200, 25);
        loginPanel.add(signupButton);
        signupButton.addActionListener(e -> cardLayout.show(cards, "signup"));

        frame.repaint();
        frame.revalidate();
        return loginPanel;
    }

    private void sendMessage(JPanel panel, String message) {
        JLabel noSuchUser = new JLabel(message);
        noSuchUser.setFont(ERROR_FONT);
        if (message.toLowerCase(Locale.ROOT).contains("error")) {
            noSuchUser.setForeground(Color.RED);
        }
        else {
            noSuchUser.setForeground(Color.GREEN);
        }
        noSuchUser.setBounds(200, 300, 200, 25);
        panel.add(noSuchUser);

        frame.revalidate(); //updating to show error
        frame.repaint();
    }

    private JPanel signupScreen() {
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(null);

        //header
        JLabel header = new JLabel("Join Workr Today");
        header.setFont(TITLE_FONT);
        header.setBounds(175, 50, 200, 50);
        signupPanel.add(header);

        //username field
        JTextField username = new JTextField();
        username.setBounds(225, 100, 200, 25);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 100, 100, 25);
        signupPanel.add(username);
        signupPanel.add(usernameLabel);

        //account name field
        JTextField account_name = new JTextField();
        account_name.setBounds(225, 125, 200, 25);
        JLabel accountLabel = new JLabel("Full Name:");
        accountLabel.setBounds(100, 125, 200, 25);
        signupPanel.add(account_name);
        signupPanel.add(accountLabel);

        //email field
        JTextField email = new JTextField();
        email.setBounds(225, 150, 200, 25);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 150, 200, 25);
        signupPanel.add(email);
        signupPanel.add(emailLabel);

        //password field
        JPasswordField password = new JPasswordField();
        password.setBounds(225, 175, 200, 25);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 175, 200, 25);
        signupPanel.add(password);
        signupPanel.add(passwordLabel);

        //confirm password field
        JPasswordField confirmPassword = new JPasswordField();
        confirmPassword.setBounds(225, 200, 200, 25);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(100, 200, 150, 25);
        signupPanel.add(confirmPassword);
        signupPanel.add(confirmPasswordLabel);

        //confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(310, 250, 90, 25);
        signupPanel.add(confirmButton);
        confirmButton.addActionListener(e -> {
            char[] newPassword = password.getPassword();
            StringBuilder pword = new StringBuilder();
            for (char a : newPassword) {pword.append(a);}

            HashMap<String, String> body = new HashMap<>();
            body.put("accountName", account_name.getText());
            body.put("login", username.getText());
            body.put("email", email.getText());
            body.put("password", pword.toString());
            JSONObject createBody = new JSONObject(body);

            if (Arrays.equals(newPassword, confirmPassword.getPassword())) {
                try {
                    executePost("http://localhost:8080/User/Create", null, createBody.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                sendMessage(signupPanel, "Success!");
                cardLayout.show(cards, "login");
            } else {
                JLabel passwordMismatch = new JLabel("ERROR: Passwords do not match");
                passwordMismatch.setFont(ERROR_FONT);
                passwordMismatch.setForeground(Color.RED);
                passwordMismatch.setBounds(200, 300, 200, 25);
                signupPanel.add(passwordMismatch);
                frame.revalidate(); //updating to show error
                frame.repaint();
            }
        });

        //create new user when confirm button is pushed

        //login button
        JButton loginButton = new JButton("I have an account");
        loginButton.setBounds(275, 400, 200, 25);
        signupPanel.add(loginButton);

        //login listener
        loginButton.addActionListener(e -> cardLayout.show(cards, "login"));

        return signupPanel;
    }

    private JPanel accountScreen() {
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(null);
        JLabel header;

        try {
            header = new JLabel("Welcome, " +  currentAuth.get("accountName"));
        } catch (NullPointerException n) {
            //in case username is not defined
            header = new JLabel("Welcome to Workr");
        }

        header.setFont(TITLE_FONT);
        header.setBounds(150, 25, 300, 50);
        accountPanel.add(header);

        //edit profile
        JButton edit_profile = new JButton("Edit Profile");
        edit_profile.setBounds(150, 75, 200, 50);
        accountPanel.add(edit_profile);
        edit_profile.addActionListener(e -> cardLayout.show(cards, "user"));

        //search for listings
        JButton search = new JButton("Search listings");
        search.setBounds(150, 125, 200, 50);
        accountPanel.add(search);
        search.addActionListener(e -> cardLayout.show(cards, "search"));

        //view saved listings
        JButton viewListings = new JButton("View my listings");
        viewListings.setBounds(150, 175, 200, 50);
        accountPanel.add(viewListings);
        viewListings.addActionListener(e -> cardLayout.show(cards, "listings"));

        //log out
        JButton logout = new JButton("Log out");
        logout.setBounds(150, 225, 200, 50);
        accountPanel.add(logout);
        logout.addActionListener(e -> cardLayout.show(cards, "login"));

        //quit
        JButton quit = new JButton("Quit to OS");
        quit.setBounds(150, 275, 200, 50);
        accountPanel.add(quit);
        quit.addActionListener(e -> System.exit(0));

        return accountPanel;
    }

    private JPanel userScreen() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(null);

        try {
            Entry user = ICreateEntry.createEntry(currentAuth);
            curr = (User) user;
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }

        //header
        JLabel header = new JLabel((String) currentAuth.get("accountName"));
        header.setFont(TITLE_FONT);
        header.setBounds(200, 50, 200, 50);
        userPanel.add(header);

        //skills
        JTextField skills = new JTextField();
        skills.setBounds(200, 100, 200, 25);
        JLabel skillsLabel = new JLabel("Skills:");
        skillsLabel.setBounds(100, 100, 100, 25);
        userPanel.add(skills);
        userPanel.add(skillsLabel);
        ArrayList<String> skillsLst = (ArrayList<String>) Arrays.asList(skills.toString().split("\\s*,\\s*"));
        curr.addData("skills", skillsLst);

        //Rel work exp
        JTextField addExpButton = new JTextField();
        addExpButton.setBounds(200, 125, 50, 25);
        JLabel relLabel = new JLabel("Related Work Experience:");
        relLabel.setBounds(100, 125, 100, 25);
        userPanel.add(relLabel);
        userPanel.add(addExpButton);
//        ArrayList<String> relLst = (ArrayList<String>) Arrays.asList(addExpButton.toString().split("\\s*,\\s*"));
//        Map<String, Object> relLst1;
//        ArrayList<Experience> relLst2 = new ArrayList<>();
//        for (String rel : relLst) {
//            Entry exp = ICreateEntry.createEntry()
//        }

//        curr.addData("relWorkExp", relLst);

        //Unrel work exp
        JTextField addExpButton1 = new JTextField();
        addExpButton1.setBounds(200, 150, 50, 25);
        JLabel unRelLabel = new JLabel("Unrelated Work Experience:");
        unRelLabel.setBounds(100, 150, 100, 25);
        userPanel.add(unRelLabel);
        userPanel.add(addExpButton1);
//        ArrayList<Experience> unRelLst = (ArrayList<Experience>) Arrays.asList(addExpButton1.toString().split("\\s*,\\s*"));
//        curr.addData("urelWorkExp", unRelLst);

        //leadership
        JTextField addExpButton2 = new JTextField();
        addExpButton2.setBounds(200, 175, 50, 25);
        JLabel ledLabel = new JLabel("Leadership:");
        ledLabel.setBounds(100, 175, 100, 25);
        userPanel.add(ledLabel);
        userPanel.add(addExpButton2);
        //updateResume.put("relWorkExp", rel_work.toString());
//        addExpButton2.addActionListener(e -> {
//            cardLayout.show(cards, "entry");
//        });

        //location
        JTextField location = new JTextField();
        location.setBounds(200, 200, 200, 25);
        JLabel locLabel = new JLabel("Location:");
        locLabel.setBounds(100, 200, 100, 25);
        userPanel.add(location);
        userPanel.add(locLabel);

        //awards
        JTextField addAwardsButton = new JTextField();
        addAwardsButton.setBounds(200, 225, 50, 25);
        JLabel awardsLabel = new JLabel("Awards:");
        awardsLabel.setBounds(100, 225, 100, 25);
        userPanel.add(awardsLabel);
        userPanel.add(addAwardsButton);
        //updateResume.put("relWorkExp", rel_work.toString());
//        addAwardsButton.addActionListener(e -> {
//            cardLayout.show(cards, "entry");
//        });

        //incentive
        JTextField incentiveButton = new JTextField();
        incentiveButton.setBounds(200, 250, 50, 25);
        JLabel incentiveLabel = new JLabel("Incentive:");
        incentiveLabel.setBounds(100, 250, 100, 25);
        userPanel.add(incentiveLabel);
        userPanel.add(incentiveButton);
        //updateResume.put("relWorkExp", rel_work.toString());
//        addAwardsButton.addActionListener(e -> {
//            cardLayout.show(cards, "entry");
//        });

        //scores
        JTextField scoresButton = new JTextField();
        scoresButton.setBounds(200, 275, 50, 25);
        JLabel scoresLabel = new JLabel("Scores:");
        scoresLabel.setBounds(100, 275, 100, 25);
        userPanel.add(scoresLabel);
        userPanel.add(scoresButton);
        //updateResume.put("relWorkExp", rel_work.toString());
//        addAwardsButton.addActionListener(e -> {
//            cardLayout.show(cards, "entry");
//        });

        //gpa
        JTextField gpa = new JTextField();
        gpa.setBounds(200, 300, 200, 25);
        JLabel gpaLabel = new JLabel("GPA:");
        gpaLabel.setBounds(100, 300, 100, 25);
        userPanel.add(gpa);
        userPanel.add(gpaLabel);

        //confirm button
        JButton confirmBut = new JButton("Confirm");
        confirmBut.setBounds(310, 350, 90, 25);
        userPanel.add(confirmBut);
//        confirmBut.addActionListener(e -> {
//
//        });

        return userPanel;
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
        generateQuery.addActionListener(e -> {
            String terms = searchTerms.getText();
            String jobLocation = location.getText();

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime dateTime = currentTime.minusDays(14);
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

            JobType jobType = JobType.FULL_TIME;
            String selectedType = typeButtons.getSelection().getActionCommand();

            switch (selectedType) {
                case "Full time":
                    jobType = JobType.FULL_TIME;
                case "Part time":
                    jobType = JobType.PART_TIME;
                default:

            }

            HashMap<String, String> params = new HashMap<>();
            params.put("dateTime", dateTime.toString());
            params.put("location", jobLocation);
            params.put("jobType", jobType.toString());
            params.put("searchTerms", terms);

            try {
                String listingData = executeGet("http://localhost:8080/JobListing/Search", params);
                JSONArray lData = new JSONArray(listingData);
                List<Object> dataList = lData.toList();

                if (listingData.isEmpty()) {

                }
                else if (lData.isEmpty()) {

                }
                else {
                    for (Object data : dataList) {
                        if (data instanceof Map) {
                            Map<String, Object> listData = (Map<String, Object>) data;
                            Entry listingAsEntry = ICreateEntry.createEntry(listData);
                            if (listingAsEntry instanceof JobListing) {
                                listings.add((JobListing) listingAsEntry);
                            }
                        }
                    }
                    cardLayout.show(cards, "listingsScreen");
                }
            } catch (IOException | MalformedDataException ex) {
                ex.printStackTrace();
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

    private JPanel listingsScreen() {
        JPanel listingsPanel = new JPanel();
        listingsPanel.setLayout(null);

        //header
        JLabel header = new JLabel("View your listings");
        header.setFont(TITLE_FONT);
        header.setBounds(150, 25, 300, 50);

//        //listings table
//        DefaultListModel demoList = new DefaultListModel();
//        JList list;
//        JLabel noDataWarning;
//
//        try {
//            String[] listingHeaders;
//
//            HashSet<JobListing> set = currentUser.getWatchedListings();
//
//            ArrayList<JobListing> listings = new ArrayList<>(set);
//            demoList.addElement(listings);
//            list = new JList(demoList);
//        } catch (NullPointerException n) {
//            list = new JList();
//            noDataWarning = new JLabel("You don't have any saved listings!");
//            noDataWarning.setBounds(50, 225, 400, 50);
//            listingsPanel.add(noDataWarning);
//        }
//
//        list.setBounds(50, 25, 400, 250);
//        listingsPanel.add(list);
//
//        //return button
//        JButton goBack = new JButton("Return");
//        goBack.setBounds(150, 325, 200, 50);
//        listingsPanel.add(goBack);
//        goBack.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cards, "main");
//            }
//        });


        return listingsPanel;
    }

}

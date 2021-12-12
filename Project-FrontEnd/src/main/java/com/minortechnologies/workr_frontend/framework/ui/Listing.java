package com.minortechnologies.workr_frontend.framework.ui;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.minortechnologies.workr_frontend.framework.network.SendHTTP.executeGet;
import static com.minortechnologies.workr_frontend.framework.network.SendHTTP.executePost;

public class Listing {

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    public static JobListing maker() throws MalformedDataException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("listingDate", "2021-11-24");
        data.put("requirements", "Java");
        data.put("origin", "Minor Technologies Discord");
        data.put("pay", 3);
        data.put("listingType", "CUSTOM");
        data.put("applicationReq", "Send me a discord @JaeJay#5154");
        data.put("description", "Software Development Job at Minor Technologies to work on Workr. \nHave some more words to fill up the description");
        data.put("jobField", "Computer Science");
        data.put("title", "Software Developer - Minor Technologies");
        data.put("uuid", "2fc5b0f2-b88e-496b-afa7-89ec0d3810d7");
        data.put("qualifications", "Computer Science Minor at UofT");
        data.put("location", "Toronto");
        data.put("crossPlatformDuplicates", null);
        data.put("jobType","FULL_TIME");
        Entry listing = ICreateEntry.createEntry(data);
        return (JobListing) listing;
    }

    public static JobListing maker2() throws MalformedDataException {
        HashMap<String, Object> data2 = new HashMap<>();
        data2.put("listingDate", "2021-11-24");
        data2.put("requirements", "Java");
        data2.put("origin", "Minor Technologies Discord");
        data2.put("pay", 3);
        data2.put("listingType", "CUSTOM");
        data2.put("applicationReq", "Send me a discord @JaeJay#5154");
        data2.put("description", "Software Development Job at Minor Technologies to work on Workr. \nHave some more words to fill up the description");
        data2.put("jobField", "Computer Science");
        data2.put("title", "Software Developer - Minor Technologies2");
        data2.put("uuid", "2fc5b0f2-b88e-496b-afa7-89ec0d3810d8");
        data2.put("qualifications", "Computer Science Minor at UofT");
        data2.put("location", "Toronto");
        data2.put("crossPlatformDuplicates", null);
        data2.put("jobType","FULL_TIME");
        Entry listing1 = ICreateEntry.createEntry(data2);
        return (JobListing) listing1;
    }

    public static JPanel Liting(JobListing l, float score) {
        JPanel listing = new JPanel(new BorderLayout());

        JLabel label;
        listing.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        label = new JLabel(l.getTitle());
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        label.setFont(new Font("Serif", Font.BOLD, 14));
        listing.add(label, c);

        label = new JLabel(l.getListingDate().toString());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        label.setFont(new Font("Serif", Font.PLAIN, 12));
        listing.add(label, c);

        label = new JLabel(String.valueOf(score));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        label.setFont(new Font("Serif", Font.BOLD, 13));
        listing.add(label, c);

        label = new JLabel((String) l.getData("location"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        label.setFont(new Font("Serif", Font.PLAIN, 11));
        listing.add(label, c);

        label = new JLabel(l.getData("pay").toString());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        label.setFont(new Font("Serif", Font.ITALIC, 12));
        listing.add(label, c);

        label = new JLabel((String) l.getData("description"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        label.setFont(new Font("Serif", Font.PLAIN, 10));
        listing.add(label, c);

        JButton btn = new JButton("Save");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 4;
        c.gridy = 0;
        btn.setFont(new Font("Serif", Font.PLAIN, 10));
        listing.add(btn, c);

        listing.setBorder(BorderFactory.createLineBorder(Color.black));
        listing.setSize(700, 200);
        return listing;
    }

    public static JPanel Liting(JobListing l) {
        return Liting(l, 99);
    }

    public static JPanel scrollable(JPanel[] data) {
        JPanel inside = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane();
        inside.add(scrollPane);
        for (JPanel i : data) {
            inside.add(i);
        }
        return inside;
    }

    public static void main(String[] args) {
        JobListing data;
        JobListing data2;

        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel[] a = new JPanel[2];

        try {
            data = maker();
            data2 = maker2();
            a[0] = Liting(data, 99);
            a[1] = Liting(data2, 11);
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }
        JPanel insidePane = new JPanel();
        insidePane = scrollable(a);

        frame.add(insidePane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

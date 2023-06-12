package lk.ijse.dep10.app;

import lk.ijse.dep10.app.model.UserSocketDetails;
import lk.ijse.dep10.shared.Header;
import lk.ijse.dep10.shared.Message;
import lk.ijse.dep10.shared.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class AppInitializer {
    public static ArrayList<User> userArrayList = new ArrayList<>();
    public static ArrayList<UserSocketDetails> onlineList = new ArrayList<>();
    public static ArrayList<String> activeList = new ArrayList<>();
    private static File file = new File("/home/gayashan/Documents/DEP/Phase - 1/java-se/network-io/modified-chat-app/server-app/src/main/resources/userList.db");
    private static boolean login = false;
    public static String chat = "";
    public static UserSocketDetails userSocketDetails;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*Reading file*/



//        System.out.println("Reading file for user list");
//        System.out.println(file.exists());
//        System.out.println(file.isFile());
//        FileInputStream fis = new FileInputStream(file);
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        userArrayList = (ArrayList<User>) ois.readObject();
//        ois.close();
//        System.out.println(userArrayList.size());
//        for (User user : userArrayList) {
//            System.out.printf("Username : %s\t\tPassword : %s\n", user.getUsername(), user.getPassword());
//        }
//
//        System.out.println("Initializing Server Socket");
//        ServerSocket serverSocket = new ServerSocket(5050);
//
//        while (true) {
//            System.out.println("Listening for connection");
//            Socket localSocket = serverSocket.accept();
//            userSocketDetails = new UserSocketDetails(localSocket);
//            System.out.println("Connected : " + localSocket.getRemoteSocketAddress());
//
//            new Thread(() -> {
//                UserSocketDetails userSocketDetails2 = userSocketDetails;
//                Message msg = null;
//                String username = null;
//                try {
//                    System.out.println("Inside new thread");
//                    ObjectOutputStream userOos = userSocketDetails2.getOos();
//                    ObjectInputStream userOis = userSocketDetails2.getOis();
//                    while (true) {
//                        System.out.println("Waiting to read message");
//                        msg = (Message) userOis.readObject();
//                        System.out.println("Message received");
//                        if (msg.getHeader() == Header.SIGNUP) {
//                            FileOutputStream fileFos = new FileOutputStream(file);
//                            ObjectOutputStream fileOos = new ObjectOutputStream(fileFos);
//                            userArrayList.add((User) msg.getContent());
//                            fileOos.writeObject(new Message(Header.USER_LIST, activeList));
//                            System.out.println(((User) msg.getContent()).getUsername());
//                            System.out.println(((User) msg.getContent()).getPassword());
//                            System.out.println("file written");
//                            fileOos.close();
//                        } else if (msg.getHeader() == Header.LOGIN) {
//                            login = false;
//                            for (User user : userArrayList) {
//                                if (
//                                        user.getUsername().equals(((User) msg.getContent()).getUsername()) &&
//                                                user.getPassword().equals(((User) msg.getContent()).getPassword())
//                                ) {
//                                    login = true;
//                                    userOos.writeObject(msg);
//                                    userOos.flush();
//                                    onlineList.add(userSocketDetails2);
//                                    activeList.add(((User) msg.getContent()).getUsername());
//                                    username = ((User) msg.getContent()).getUsername();
//                                    broadcastLoggedUsers();
//                                    sendChatHistory(userSocketDetails2);
//                                }
//                            }
//                            if (!login) userOos.writeObject(new Message(Header.LOGIN, null));
//                        } else if (msg.getHeader() == Header.MESSAGE) {
//                            System.out.println("Message received to server, preparing to broadcast chat history");
//                            chat += msg.getContent() + "\n";
//                            broadcastChatHistory();
//                        }
//
//                    }
//
//
//                } catch (Exception e) {
//                    System.out.println("online list size : " + onlineList.size());
//                    System.out.println("active list size : " + activeList.size());
//                    System.out.println("Error 1");
//                    System.out.println("Exit request received from client");
//                    onlineList.remove(userSocketDetails2);
//                    activeList.remove(username);
//                    System.out.println("online list size : " + onlineList.size());
//                    System.out.println("active list size : " + activeList.size());
//                    broadcastLoggedUsers();
//                    e.printStackTrace();
//                }
//            } ).start();
//        }


    }

    private static void sendChatHistory(UserSocketDetails userSocketDetails) {
        System.out.println("Send Chat history");
        try {
            userSocketDetails.getOos().writeObject(new Message(Header.MESSAGE, chat));
            userSocketDetails.getOos().flush();
        } catch (IOException e) {
            System.out.println("Error 7");
            e.printStackTrace();
        }
    }

    private static void broadcastLoggedUsers() {
        System.out.println("broadcast logged users");
        System.out.println("Online list : " + onlineList.size());
        System.out.println("Active list : " + activeList.size());
        for (UserSocketDetails userSocketDetails : onlineList) {
            try {
                userSocketDetails.getOos().writeObject(new Message(Header.USER_LIST, new ArrayList(activeList)));
                userSocketDetails.getOos().flush();
                System.out.println(userSocketDetails.getOos().toString() + activeList);
            } catch (IOException e) {
                System.out.println("Error 10");
                e.printStackTrace();
            }
        }
    }

    private static void broadcastChatHistory() {
        System.out.println("Broadcast chat history");
        for (UserSocketDetails userSocketDetails : onlineList) {
            try {
                userSocketDetails.getOos().writeObject(new Message(Header.MESSAGE, chat));
                userSocketDetails.getOos().flush();
            } catch (IOException e) {
                System.out.println("Error 8");
                e.printStackTrace();
            }
        }
    }


}

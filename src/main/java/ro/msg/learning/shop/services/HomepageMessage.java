package ro.msg.learning.shop.services;

public class HomepageMessage {
    private String messageOfHomePage;

    public HomepageMessage() {
        this.messageOfHomePage = "Welcome to OnlineShop!";
    }

    public HomepageMessage(String messageOfHomePage) {
        this.messageOfHomePage = messageOfHomePage;
    }

    public String getMessageOfHomePage() {
        return messageOfHomePage;
    }

    public void setMessageOfHomePage(String messageOfHomePage) {
        this.messageOfHomePage = messageOfHomePage;
    }
}

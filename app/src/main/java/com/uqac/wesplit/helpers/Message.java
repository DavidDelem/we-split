package com.uqac.wesplit.helpers;

public class Message {

    private String _id;
    private String message;
    private String name;
    private String userid;
    private String date;

    public Message() {
    }

    public Message(String _id, String message, String name, String userid, String date) {
        this._id = _id;
        this.message = message;
        this.name = name;
        this.userid = userid;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (_id != null ? !_id.equals(message1._id) : message1._id != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null)
            return false;
        if (name != null ? !name.equals(message1.name) : message1.name != null) return false;
        if (userid != null ? !userid.equals(message1.userid) : message1.userid != null)
            return false;
        return date != null ? date.equals(message1.date) : message1.date == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "_id='" + _id + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userid + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

package edu.upc.dsa.modelos;

public class JoinGroupRequest {
    private String email; //
    private String groupId;

    public JoinGroupRequest() {}
    public JoinGroupRequest(String email, String groupId) {
        this.email = email;
        this.groupId = groupId;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
}

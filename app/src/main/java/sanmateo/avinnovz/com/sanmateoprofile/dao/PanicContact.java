package sanmateo.avinnovz.com.sanmateoprofile.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PANIC_CONTACT.
 */
public class PanicContact {

    private Long id;
    private String contactName;
    private String contactNo;

    public PanicContact() {
    }

    public PanicContact(Long id) {
        this.id = id;
    }

    public PanicContact(Long id, String contactName, String contactNo) {
        this.id = id;
        this.contactName = contactName;
        this.contactNo = contactNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

}

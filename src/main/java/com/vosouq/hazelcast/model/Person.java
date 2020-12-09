package com.vosouq.hazelcast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
//@Getter
//@Setter
//@EqualsAndHashCode
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
//public class Person implements DataSerializable {
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "person")
    private FicoScoring ficoScoring;
    private String identityCode;
    private String firstName;
    private String lastName;
    private String mobile;
    private String address;
    private String accountNumber;

/*    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(id);
//        if (ficoScoring == null)
            ficoScoring.writeData(out);
        out.writeUTF(identityCode);
        out.writeUTF(firstName);
        out.writeUTF(lastName);
        out.writeUTF(mobile);
        out.writeUTF(address);
        out.writeUTF(accountNumber);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        id = in.readLong();
//        if (ficoScoring == null) {
            ficoScoring = new FicoScoring();
            ficoScoring.readData(in);
//        }
        identityCode = in.readUTF();
        firstName = in.readUTF();
        lastName = in.readUTF();
        mobile = in.readUTF();
        address = in.readUTF();
        accountNumber = in.readUTF();
    }*/

    @Override
    public String toString(){
        return "person";
    }



}


